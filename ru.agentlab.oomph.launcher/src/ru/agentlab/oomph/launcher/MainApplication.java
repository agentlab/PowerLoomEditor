package ru.agentlab.oomph.launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.fx.core.di.LocalInstance;
import org.powerloom.service.PowerLoomService;

import edu.isi.powerloom.PLI;
import edu.isi.powerloom.PlIterator;
import edu.isi.powerloom.logic.LogicObject;
import edu.isi.stella.Module;
import edu.isi.stella.Stella_Object;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication {

    public static final String MODULES_PANE_NAME = "Modules"; //$NON-NLS-1$
    public static final String CONCEPTS_PANE_NAME = "Concepts"; //$NON-NLS-1$
    public static final String RELATIONS_PANE_NAME = "Relations"; //$NON-NLS-1$
    public static final String INSTANCES_PANE_NAME = "Instances"; //$NON-NLS-1$
    public static final String PROPOSITIONS_PANE_NAME = "Propositions"; //$NON-NLS-1$
    public static final String RULES_PANE_NAME = "Rules"; //$NON-NLS-1$
    private static final int DEFAULT_WIDTH = 1250;
    private static final int DEFAULT_HEIGHT = 900;
    private static final String PATH_TO_MAIN_WINDOW = "\\panes\\MainWindow.fxml"; //$NON-NLS-1$
    private static final String INITIAL_SETTINGS_FILE_NAME = "initialSettings.plm"; //$NON-NLS-1$
    private static final String ROOT_CONCEPT_NAME = "CONCEPT"; //$NON-NLS-1$
    private static final String ROOT_RELATION_NAME = "RELATION"; ////$NON-NLS-1$
    //initial settings use this module
    //private static final String MODULE_NAME = "BMSTU";

    private PowerLoomService powerLoomService;
    Module selectedModule;
    private ListView<Stella_Object> conceptsListView;
    private ListView<Stella_Object> relationsListView;
    private ListView<Stella_Object> instancesListView;
    private ListView<Stella_Object> propositionsListView;
    private ListView<Stella_Object> rulesListView;

    @PostConstruct
    public void run(IApplicationContext applicationContext, javafx.application.Application jfxApplication,
        Stage primaryStage, @LocalInstance FXMLLoader loader) {
        loader.setLocation(getClass().getResource(PATH_TO_MAIN_WINDOW));
        try
        {
            BorderPane mainPane = loader.load();
            Scene s = new Scene(mainPane, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            primaryStage.setScene(s);

            loadPowerloom();
            findListViews(mainPane);
            initMouseListeners();
            configureModulesPane(getTitledPane(mainPane, MODULES_PANE_NAME));

            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadPowerloom() {
        try
        {
            powerLoomService = PowerLoomService.getInstance();
            powerLoomService.loadPlm(INITIAL_SETTINGS_FILE_NAME);
        }
        catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private void findListViews(Pane mainPane) {
        conceptsListView = findListView(mainPane, CONCEPTS_PANE_NAME);
        relationsListView = findListView(mainPane, RELATIONS_PANE_NAME);
        instancesListView = findListView(mainPane, INSTANCES_PANE_NAME);
        propositionsListView = findListView(mainPane, PROPOSITIONS_PANE_NAME);
        rulesListView = findListView(mainPane, RULES_PANE_NAME);
    }

    @SuppressWarnings("unchecked")
    private ListView<Stella_Object> findListView(Pane mainPane, String paneName) {
        TitledPane conceptsPane = getTitledPane(mainPane, paneName);
        if (conceptsPane != null && conceptsPane.getContent() instanceof ListView<?>)
        {
            return (ListView<Stella_Object>)conceptsPane.getContent();
        }

        return null;
    }

    private void initMouseListeners() {
        if (conceptsListView != null)
        {
            conceptsListView.addEventHandler(MouseEvent.MOUSE_RELEASED, new ListViewMouseListener());
        }

        if (relationsListView != null)
        {
            relationsListView.addEventHandler(MouseEvent.MOUSE_RELEASED, new ListViewMouseListener());
        }

    }

    private TitledPane getTitledPane(Pane pane, String paneName) {
        for (Node node : pane.getChildren())
        {
            if (node instanceof TitledPane && ((TitledPane)node).getText().equals(paneName))
            {
                return (TitledPane)node;
            }
            else if (node instanceof Pane)
            {
                TitledPane modulesPane = getTitledPane((Pane)node, paneName);
                if (modulesPane != null)
                {
                    return modulesPane;
                }
            }
        }

        return null;
    }

    private void configureModulesPane(TitledPane modulesPane) {
        if (modulesPane == null || !(modulesPane.getContent() instanceof TreeView))
        {
            return;
        }

        TreeView<Module> treeView = createTreeView(modulesPane);
        TreeItem<Module> root = new TreeItem<>(new Module());
        treeView.setRoot(root);
        PlIterator iterator = PLI.getModules(true);
        while (iterator.nextP())
        {
            Module module = (Module)iterator.value;
            if (PLI.getParentModules(module).emptyP())
            {
                addChildModule(root, module);
            }
        }
    }

    private void addChildModule(TreeItem<Module> root, Module module) {
        TreeItem<Module> child = createChild(module);
        root.getChildren().add(child);
        PlIterator iterator = PLI.getChildModules(module);
        while (iterator.nextP())
        {
            addChildModule(child, (Module)iterator.value);
        }
    }

    @SuppressWarnings("unchecked")
    private TreeView<Module> createTreeView(TitledPane modulesPane) {
        TreeView<Module> treeView = (TreeView<Module>)modulesPane.getContent();
        treeView.addEventHandler(MouseEvent.MOUSE_RELEASED, new TreeViewMouseListener());

        return treeView;

    }

    private TreeItem<Module> createChild(Module module) {
        TreeItem<Module> child = new TreeItem<>(module);

        return child;
    }

    private class TreeViewMouseListener
        implements EventHandler<MouseEvent> {

        @SuppressWarnings("unchecked")
        @Override
        public void handle(MouseEvent me) {
            if (me.getSource() instanceof TreeView<?>)
            {
                TreeView<Module> source = (TreeView<Module>)me.getSource();
                selectedModule = source.getSelectionModel().getSelectedItem().getValue();
                clearAll();
                if (!selectedModule.equals(source.getRoot().getValue()))
                {
                    fillConcepts(selectedModule);
                    fillRelations(selectedModule);
                    fillInstances(selectedModule);
                }
            }
        }

        private void clearAll() {
            if (conceptsListView != null)
            {
                conceptsListView.getItems().clear();
            }

            if (relationsListView != null)
            {
                relationsListView.getItems().clear();
            }

            if (instancesListView != null)
            {
                instancesListView.getItems().clear();
            }

            if (propositionsListView != null)
            {
                propositionsListView.getItems().clear();
            }

            if (rulesListView != null)
            {
                rulesListView.getItems().clear();
            }
        }

        private void fillConcepts(Module selectedModule) {
            if (conceptsListView == null)
            {
                return;
            }

            LogicObject logicObject = PLI.getConcept(ROOT_CONCEPT_NAME, selectedModule, null);
            if (logicObject != null)
            {
                PlIterator iterator = PLI.getConceptInstances(logicObject, selectedModule, null);
                while (iterator.nextP())
                {
                    Stella_Object concept = iterator.value;
                    conceptsListView.getItems().add(concept);
                }
            }
        }

        private void fillRelations(Module selectedModule) {
            if (relationsListView == null)
            {
                return;
            }

            LogicObject logicObject = PLI.getRelation(ROOT_RELATION_NAME, selectedModule, null);
            if (logicObject != null)
            {
                PlIterator iterator = PLI.getRelationExtension(logicObject, selectedModule, null);
                while (iterator.nextP())
                {
                    Stella_Object relation = iterator.value;
                    relationsListView.getItems().add(relation);
                }
            }
        }

        private void fillInstances(Module selectedModule) {
            if (instancesListView == null)
            {
                return;
            }

            //how to get concept instances?
//            while (iterator.nextP())
//            {
//                Stella_Object instance = iterator.value;
//                instancesListView.getItems().add(instance);
//            }
        }
    }

    private class ListViewMouseListener
        implements EventHandler<MouseEvent> {
        @SuppressWarnings("unchecked")
        @Override
        public void handle(MouseEvent me) {
            if (me.getSource() instanceof ListView<?>)
            {
                if (selectedModule == null)
                {
                    return;
                }

                ListView<Stella_Object> source = (ListView<Stella_Object>)me.getSource();
                Stella_Object selectedObject = source.getSelectionModel().getSelectedItem();

                clear();
                if (selectedObject instanceof LogicObject)
                {
                    fillPropositions((LogicObject)selectedObject);
                    fillRules((LogicObject)selectedObject);
                }
            }
        }

        private void clear() {
            if (propositionsListView != null)
            {
                propositionsListView.getItems().clear();
            }

            if (rulesListView != null)
            {
                rulesListView.getItems().clear();
            }
        }

        private void fillPropositions(LogicObject selectedObject) {
            if (propositionsListView == null)
            {
                return;
            }

            PlIterator iterator = PLI.getPropositionsOf(selectedObject, selectedModule, null);
            while (iterator.nextP())
            {
                Stella_Object proposition = iterator.value;
                propositionsListView.getItems().add(proposition);
            }
        }

        private void fillRules(LogicObject selectedObject) {
            if (rulesListView == null)
            {
                return;
            }

            PlIterator iterator = PLI.getRules(selectedObject, selectedModule, null);
            while (iterator.nextP())
            {
                Stella_Object rule = iterator.value;
                rulesListView.getItems().add(rule);
            }
        }
    }
}

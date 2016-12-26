/**
 *
 */
package ru.agentlab.oomph.launcher.paneControllers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;

import javafx.fxml.FXML;
import ru.agentlab.oomph.launcher.PrintService;

/**
 * @author Kisareva_N
 *
 */
public class AbstractPaneController {
    @Inject
    protected PrintService service;

    @PostConstruct
    protected void created(IEclipseContext context) {
        // Does nothing
    }

    @FXML
    protected void initialize() {
        // Does nothing
    }
}

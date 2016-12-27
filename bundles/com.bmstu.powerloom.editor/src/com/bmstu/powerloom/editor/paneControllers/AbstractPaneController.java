/**
 *
 */
package com.bmstu.powerloom.editor.paneControllers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.bmstu.powerloom.editor.PrintService;

import javafx.fxml.FXML;

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

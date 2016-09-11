package de.rpi_controlcenter.AppServer.Controller.Automation.Operation;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.FileCreateOperation;

/**
 * Prüft ob die Automatisierungsoperationen zur Ausführung bereit sind
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class OperationController {

    public static boolean isSatisfies(FileCreateOperation operation) {

        //prüfen ob deaktiviert
        if (operation.isDisabled()) {

            return false;
        }

        return false;
    }
}

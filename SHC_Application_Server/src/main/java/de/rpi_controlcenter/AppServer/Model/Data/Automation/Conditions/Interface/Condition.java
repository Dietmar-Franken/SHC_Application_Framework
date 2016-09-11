package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;

/**
 * Schnittstelle Bedingung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface Condition extends AutomationElement {

    /**
     * gibt an ob die Bedingung Invertiert ist
     *
     * @return Invertiert
     */
    boolean isInvert();

    /**
     * setzt die Invertierung der Bedingung
     *
     * @param invert Invertiert
     */
    void setInvert(boolean invert);
}

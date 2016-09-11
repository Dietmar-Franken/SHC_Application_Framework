package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AbstractAutomationElement;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractCondition extends AbstractAutomationElement implements Condition {

    /**
     * gibt an ob die Bedingung invertiert ist
     */
    private boolean invert = false;

    /**
     * @param id ID
     * @param name Name
     */
    public AbstractCondition(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * gibt an ob die Bedingung Invertiert ist
     *
     * @return Invertiert
     */
    @Override
    public boolean isInvert() {
        return invert;
    }

    /**
     * setzt die Invertierung der Bedingung
     *
     * @param invert Invertiert
     */
    @Override
    public void setInvert(boolean invert) {
        this.invert = invert;
    }
}

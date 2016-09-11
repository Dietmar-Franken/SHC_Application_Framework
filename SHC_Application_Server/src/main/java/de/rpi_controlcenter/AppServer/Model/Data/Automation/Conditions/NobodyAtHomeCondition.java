package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;

/**
 * Bedingung Niemand zu Hause
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class NobodyAtHomeCondition extends AbstractCondition {

    private Type type = Type.CONDITION_NOBODY_AT_HOME;

    /**
     * @param id ID
     * @param name Name
     */
    public NobodyAtHomeCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    @Override
    public Type getType() {
        return type;
    }
}

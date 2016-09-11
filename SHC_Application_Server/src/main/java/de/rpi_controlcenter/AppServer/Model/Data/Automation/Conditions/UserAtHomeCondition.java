package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;

import java.util.HashSet;
import java.util.Set;

/**
 * Bedingung Benutzer zu Hause
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UserAtHomeCondition extends AbstractCondition {

    private Type type = Type.CONDITION_USER_AT_HOME;

    /**
     * liste mit allen Sensoren die überwacht werden sollen
     */
    private final Set<String> userAtHomeList = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public UserAtHomeCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Sensoren Liste zurück
     *
     * @return Sensoren Liste
     */
    public Set<String> getUserAtHomeList() {
        return userAtHomeList;
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

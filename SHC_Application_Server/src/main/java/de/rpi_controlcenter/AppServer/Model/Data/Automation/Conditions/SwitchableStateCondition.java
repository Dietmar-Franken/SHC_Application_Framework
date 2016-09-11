package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Bedingung schaltbare Elemente
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SwitchableStateCondition extends AbstractCondition {

    private Type type = Type.CONDITION_SWITCHABLE_STATE;

    /**
     * liste mit allen schaltbaren ELementen die überwacht werden sollen
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10202, message = "Es muss mindestens ein schaltbares Element überwacht werden")
    private final Set<String> switchableList = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public SwitchableStateCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Sensoren Liste zurück
     *
     * @return Sensoren Liste
     */
    public Set<String> getSwitchableList() {
        return switchableList;
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

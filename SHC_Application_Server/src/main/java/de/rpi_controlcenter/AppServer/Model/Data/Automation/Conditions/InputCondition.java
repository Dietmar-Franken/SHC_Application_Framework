package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Bedingung Eingang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class InputCondition extends AbstractCondition {

    private Type type = Type.CONDITION_INPUT;

    /**
     * Liste mit allen Sensoren die überwacht werden sollen
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10201, message = "Es muss mindestens ein Sensor überwacht werden")
    private final Set<String> sensorList = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public InputCondition(String id, String name, Type type) {
        super(id, name);
        this.type = type;
    }

    /**
     * gibt die Liste der zu überwachenden Sensoren zurück
     *
     * @return Liste der Sensoren
     */
    public Set<String> getSensorList() {
        return sensorList;
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

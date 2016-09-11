package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Bedingung Luftfeuchte
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class HumidityCondition extends AbstractCondition {

    private Type type = Type.CONDITION_HUMIDITY;

    /**
     * Liste mit allen Sensoren die überwacht werden sollen
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10201, message = "Es muss mindestens ein Sensor überwacht werden")
    private final Set<String> sensorList = new HashSet<>();

    /**
     * Grenzwert
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10203, message = "Ungültiger Prozent Wert %s%%")
    @ValidateMax(value = 100, errorCode = 10203, message = "Ungültiger Prozent Wert %s%%")
    private double limit = 0.0;

    /**
     * @param id ID
     * @param name Name
     */
    public HumidityCondition(String id, String name) {
        super(id, name);
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
     * gibt den Grenzwert zurück
     *
     * @return Grenzwert
     */
    public double getLimit() {
        return limit;
    }

    /**
     * setzt den Grenzwert
     *
     * @param limit Grenzwert
     */
    public void setLimit(double limit) {

        this.limit = limit;
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

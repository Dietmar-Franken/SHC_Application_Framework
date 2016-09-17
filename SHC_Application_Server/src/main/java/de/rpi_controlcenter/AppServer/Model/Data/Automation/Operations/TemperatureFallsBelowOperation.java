package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ereignis Temperatur fällt
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class TemperatureFallsBelowOperation extends AbstractOperation {

    private Type type = Type.OPERATION_TEMPERATURE_FALLS_BELOW;

    /**
     * Liste der zu überwachenden Sensoren
     */
    @ValidateSize(min = 1, errorCode = 102008, message = "Es muss mindestens ein Sensorwert eingetragen sein")
    private Set<String> sensors = new HashSet<>();

    /**
     * Grenzwert
     */
    @ValidateMin(value = -273.15, errorCode =  10203, message = "Ungültiges Limit")
    @ValidateMax(value = 100_000, errorCode =  10203, message = "Ungültiges Limit")
    private double limit = 0.0;

    /**
     * Status Map
     */
    private Map<String, Double> stateMap = new HashMap<>();

    /**
     * @param id ID
     * @param name Name
     */
    public TemperatureFallsBelowOperation(String id, String name) {

        super(id, name);
    }

    /**
     * gibt die Liste der überachten Sensoren zurück
     *
     * @return Liste der überachte Sensoren
     */
    public Set<String> getSensors() {
        return sensors;
    }

    /**
     * gibt das Limit zurück
     *
     * @return Limit
     */
    public double getLimit() {
        return limit;
    }

    /**
     * setzt das Limit
     *
     * @param limit Limit
     */
    public void setLimit(double limit) {
        this.limit = limit;
    }

    /**
     * gibt die Status Map zurück
     *
     * @return Status Map
     */
    public Map<String, Double> getStateMap() {
        return stateMap;
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

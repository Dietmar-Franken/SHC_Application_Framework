package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface;

import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractVirtualSensorValue extends AbstractSensorValue implements VirtualSensorValue {

    /**
     * überwachte Sensoren
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10204, message = "Es muss mindestens ein Sensorwert vorhanden sein")
    private final Set<String> sensorValues = new HashSet<>();

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public AbstractVirtualSensorValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Liste der überwachten Sensoren zurück
     *
     * @return Liste der überwachten Sensoren
     */
    public Set<String> getSensorValues() {
        return sensorValues;
    }
}

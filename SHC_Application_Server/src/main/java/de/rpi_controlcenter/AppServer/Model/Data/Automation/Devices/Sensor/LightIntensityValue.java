package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Lichtstärke
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class LightIntensityValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_LIGHT_INTENSITY;

    /**
     * Lichtstärke in Prozent
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double lightIntensity = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public LightIntensityValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Lichtstärke zurück
     *
     * @return Lichtstärke
     */
    public double getLightIntensity() {
        return lightIntensity;
    }

    /**
     * setzt die Lichtstärke
     *
     * @param lightIntensity Lichtstärke
     */
    public void setLightIntensity(double lightIntensity) {

        this.lightIntensity = lightIntensity;
    }

    /**
     * fügt die Lichtstärke hinzu
     *
     * @param lightIntensity Lichtstärke
     */
    public void pushLightIntensity(double lightIntensity) {

        setLightIntensity(lightIntensity);
        setLastPushTime(LocalDateTime.now());
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

package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Virtual;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractVirtualSensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.LightIntensityValue;

import java.util.Set;

/**
 * Virtuelle Lichtstärke
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualLightIntensityValue extends AbstractVirtualSensorValue {

    private Type type = Type.VIRTUALSENSORVALUE_LIGHT_INTENSITY;

    /**
     * Statistische Werte
     */
    private double average = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public VirtualLightIntensityValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * ermittelt aus den Sensorwerten die Statistischen Werte
     *
     * @param sensorValues Sensorwerte
     */
    @Override
    public void processValues(Set<SensorValue> sensorValues) {

        int count = 0;
        double sum = 0;
        for(SensorValue sensorValue : sensorValues) {

            if(sensorValue instanceof LightIntensityValue) {

                LightIntensityValue lightIntensityValue = (LightIntensityValue) sensorValue;
                sum += lightIntensityValue.getLightIntensity();
                count++;
            }
        }

        this.average = sum / count;
    }

    /**
     * gibt den Mittelwert zurück
     *
     * @return Mittelwert
     */
    public double getAverage() {
        return average;
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

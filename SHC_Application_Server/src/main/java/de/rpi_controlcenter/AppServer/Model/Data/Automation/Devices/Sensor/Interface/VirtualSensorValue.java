package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface;

import java.util.Set;

/**
 * Virtueller Sensorwert
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface VirtualSensorValue extends SensorValue {

    /**
     * gibt die Liste der überwachten Sensoren zurück
     *
     * @return Liste der überwachten Sensoren
     */
    Set<String> getSensorValues();

    /**
     * ermittelt aus den Sensorwerten die Statistischen Werte
     *
     * @param sensorValues Sensorwerte
     */
    void processValues(Set<SensorValue> sensorValues);
}

package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Virtual;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractVirtualSensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.TemperatureValue;

import java.util.Set;

/**
 * Virtuelle Temperatur
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualTemperatureValue extends AbstractVirtualSensorValue {

    private Type type = Type.VIRTUALSENSORVALUE_TEMPERATURE;

    /**
     * Statistische Werte
     */
    private double average = 0.0;
    private double sum = 0.0;
    private double min = -100000000;
    private double max = -100000000;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public VirtualTemperatureValue(String id, String identifier, String name) {
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
        for(SensorValue sensorValue : sensorValues) {

            if(sensorValue instanceof TemperatureValue) {

                TemperatureValue temperatureValue = (TemperatureValue) sensorValue;
                this.sum += temperatureValue.getTemperatureWithOffset();
                count++;

                if(this.min == -100000000 || temperatureValue.getTemperatureWithOffset() < this.min) {

                    this.min = temperatureValue.getTemperatureWithOffset();
                }
                if(this.max == -100000000 || temperatureValue.getTemperatureWithOffset() > this.max) {

                    this.max = temperatureValue.getTemperatureWithOffset();
                }
            }
        }

        this.average = this.sum / count;
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
     * gibt die Summe zurück
     *
     * @return Summe
     */
    public double getSum() {
        return sum;
    }

    /**
     * gibt den Minimalwert zurück
     *
     * @return Minimalwert
     */
    public double getMin() {
        return min;
    }

    /**
     * gibt den Maximalwert zurück
     *
     * @return Maximalwert
     */
    public double getMax() {
        return max;
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

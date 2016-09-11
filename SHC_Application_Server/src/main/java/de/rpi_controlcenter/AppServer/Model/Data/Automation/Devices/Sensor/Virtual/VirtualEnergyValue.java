package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Virtual;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.EnergyValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractVirtualSensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;

import java.util.Set;

/**
 * Virtueller Energiesensor
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualEnergyValue extends AbstractVirtualSensorValue {

    private Type type = Type.VIRTUALSENSORVALUE_ENERGY;

    /**
     * Statistische Werte
     */
    private double average = 0.0;
    private double sum = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public VirtualEnergyValue(String id, String identifier, String name) {
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

            if(sensorValue instanceof EnergyValue) {

                EnergyValue energyValue = (EnergyValue) sensorValue;
                this.sum += energyValue.getEnergy();
                count++;
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
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    @Override
    public Type getType() {
        return type;
    }
}

package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Virtual;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractVirtualSensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.WaterAmountValue;

import java.util.Set;

/**
 * Virtueller Wasserzähler
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualWaterAmountValue extends AbstractVirtualSensorValue {

    private Type type = Type.VIRTUALSENSORVALUE_WATER_AMOUNT;

    /**
     * Statistische Werte
     */
    private double sum = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public VirtualWaterAmountValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * ermittelt aus den Sensorwerten die Statistischen Werte
     *
     * @param sensorValues Sensorwerte
     */
    @Override
    public void processValues(Set<SensorValue> sensorValues) {

        for(SensorValue sensorValue : sensorValues) {

            if(sensorValue instanceof WaterAmountValue) {

                WaterAmountValue waterAmountValue = (WaterAmountValue) sensorValue;
                this.sum += waterAmountValue.getWaterAmount();
            }
        }
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

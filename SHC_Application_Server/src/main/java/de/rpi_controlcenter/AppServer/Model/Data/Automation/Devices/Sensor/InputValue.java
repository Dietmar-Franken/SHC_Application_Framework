package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;

import java.time.LocalDateTime;

/**
 * Eingang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class InputValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_INPUT;

    /**
     * Status
     */
    private boolean state = false;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public InputValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt an ob der Eingang aktiviert ist
     *
     * @return true wenn aktiviert
     */
    public boolean getState() {
        return state;
    }

    /**
     * setzt den Status
     *
     * @param state Status
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * fügt einen neuen Status ein
     *
     * @param state Status
     */
    public void pushState(boolean state) {

        setState(state);
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

package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Lebenszeichen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class LiveBitValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_LIVE_BIT;

    /**
     * Status
     */
    private boolean state = false;

    /**
     * Timeout in ms
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 100, errorCode = 10005, message = "Ungültiger Timeout")
    @ValidateMax(value = 86_400_000, errorCode = 10005, message = "Ungültiger Timeout")
    private int timeout = 10_000;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public LiveBitValue(String id, String identifier, String name) {
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
     * gibt den Timeout zurück
     *
     * @return Timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * setzt den Timeout
     *
     * @param timeout Timeout
     */
    public void setTimeout(int timeout) {

        this.timeout = timeout;
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

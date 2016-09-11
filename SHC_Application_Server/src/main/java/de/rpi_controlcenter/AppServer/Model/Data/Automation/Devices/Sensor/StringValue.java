package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.*;

import java.time.LocalDateTime;

/**
 * Zeichenkette
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class StringValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_STRING;

    /**
     * Zeichenkette
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateNotEmpty(errorCode = 10205, message = "Ungültiger Sensorwert")
    protected String string = "";

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public StringValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * setzt die Zeichenkette
     *
     * @return Zeichenkette
     */
    public String getString() {
        return string;
    }

    /**
     * gibt die Zeichenkette zurück
     *
     * @param string Zeichenkette
     */
    public void setString(String string) {

        this.string = string;
    }

    /**
     * fügt die Zeichenkette hinzu
     *
     * @param string Zeichenkette
     */
    public void pushString(String string) {

        setString(string);
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

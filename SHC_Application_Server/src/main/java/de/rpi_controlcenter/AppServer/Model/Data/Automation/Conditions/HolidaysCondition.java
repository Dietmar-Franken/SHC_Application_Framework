package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.DateTime.Holidays.GermanHolidays;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Bedingung Feiertage
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class HolidaysCondition extends AbstractCondition {

    private Type type = Type.CONDITION_HOLIDAYS;

    /**
     * Feiertage
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10200, message = "Es muss mindestens ein Feiertag eingetragen werden")
    private final Set<GermanHolidays.Holidays> holidays = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public HolidaysCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Feiertage zurück
     *
     * @return Feiertage
     */
    public Set<GermanHolidays.Holidays> getHolidays() {
        return holidays;
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

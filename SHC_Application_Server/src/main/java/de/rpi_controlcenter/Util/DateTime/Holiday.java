package de.rpi_controlcenter.Util.DateTime;

import de.rpi_controlcenter.Util.DateTime.Holidays.GermanHolidays;

import java.time.Year;

/**
 * Verwaltung der Feiertage
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class Holiday {

    /**
     * Deutsche feiertage
     *
     * @return Deutsche Feiertage
     */
    public static GermanHolidays getGermanHolidays() {

        return new GermanHolidays(Year.now().getValue());
    }
}

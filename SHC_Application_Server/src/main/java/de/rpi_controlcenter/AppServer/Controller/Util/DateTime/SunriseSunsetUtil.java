package de.rpi_controlcenter.AppServer.Controller.Util.DateTime;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Editor.SettingsEditor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Sonnenaufgang/Sonnenuntergang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class SunriseSunsetUtil {

    /**
     * gibt die Zeit des heutigen Sonnenaufganges zurück
     *
     * @return Zeit Sonnenaufgang
     */
    public static LocalTime getTodaySunriseTime() {

        SettingsEditor settings = AppServer.getInstance().getSettings();

        //Längen/Breitengrad
        double latitude = settings.getDoubleSetting(SettingsEditor.LATITUDE).getValue();
        double longitude = settings.getDoubleSetting(SettingsEditor.LONGITUDE).getValue();

        //LocalTime erstellen
        Location location = new Location(latitude, longitude);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/Berlin");
        Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
        LocalTime sunrise = LocalDateTime.ofInstant(officialSunrise.toInstant(), ZoneId.of("Europe/Berlin")).toLocalTime();

        //Offset
        int offset = settings.getIntegerSetting(SettingsEditor.SUNRISE_OFFSET).getValue();
        return sunrise.plusMinutes(offset);
    }

    /**
     * gibt die Zeit des heutigen Sonnenunterganges zurück
     *
     * @return Zeit Sonnenuntergang
     */
    public static LocalTime getTodaySunsetTime() {

        SettingsEditor settings = AppServer.getInstance().getSettings();

        //Längen/Breitengrad
        double latitude = settings.getDoubleSetting(SettingsEditor.LATITUDE).getValue();
        double longitude = settings.getDoubleSetting(SettingsEditor.LONGITUDE).getValue();

        //LocalTime erstellen
        Location location = new Location(latitude, longitude);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/Berlin");
        Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
        LocalTime sunset = LocalDateTime.ofInstant(officialSunset.toInstant(), ZoneId.of("Europe/Berlin")).toLocalTime();

        //Offset
        int offset = settings.getIntegerSetting(SettingsEditor.SUNSET_OFFSET).getValue();
        return sunset.plusMinutes(offset);
    }
}

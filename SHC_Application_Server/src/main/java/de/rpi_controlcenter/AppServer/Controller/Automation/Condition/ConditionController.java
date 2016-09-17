package de.rpi_controlcenter.AppServer.Controller.Automation.Condition;

import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.Util.DateTime.SunriseSunsetUtil;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.Condition;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.Switchable;
import de.rpi_controlcenter.Util.DateTime.Holiday;
import de.rpi_controlcenter.Util.DateTime.Holidays.GermanHolidays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * Prüft ob die zu prüfende Bedingung wahr oder Falsch ist
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class ConditionController {

    public static boolean isSatisfies(Condition condition) {

        switch(condition.getType()) {

            case CONDITION_CALENDAR_WEEK:

                return isSatisfies((CalendarWeekCondition) condition);
            case CONDITION_DATE:

                return isSatisfies((DateCondition) condition);
            case CONDITION_DAY_OF_WEEK:

                return isSatisfies((DayOfWeekCondition) condition);
            case CONDITION_FILE:

                return isSatisfies((FileCondition) condition);
            case CONDITION_HOLIDAYS:

                return isSatisfies((HolidaysCondition) condition);
            case CONDITION_HUMIDITY:

                return isSatisfies((HumidityCondition) condition);
            case CONDITION_INPUT:

                return isSatisfies((InputCondition) condition);
            case CONDITION_LIGHT_INTENSITY:

                return isSatisfies((LightIntensityCondition) condition);
            case CONDITION_MOISTURE:

                return isSatisfies((MoistureCondition) condition);
            case CONDITION_NOBODY_AT_HOME:

                return isSatisfies((NobodyAtHomeCondition) condition);
            case CONDITION_DAY:

                return isSatisfies((DayCondition) condition);
            case CONDITION_NIGHT:

                return isSatisfies((NightCondition) condition);
            case CONDITION_SWITCHABLE_STATE:

                return isSatisfies((SwitchableStateCondition) condition);
            case CONDITION_TEMPERATURE:

                return isSatisfies((TemperatureCondition) condition);
            case CONDITION_TIME:

                return isSatisfies((TimeCondition) condition);
            case CONDITION_USER_AT_HOME:

                return isSatisfies((UserAtHomeCondition) condition);
            default:
                throw new IllegalArgumentException("Unbekannter Typ");
        }
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(CalendarWeekCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int week = LocalDate.now().get(weekFields.weekOfWeekBasedYear());
        if (!condition.isInvert()) {

            //Gerade Kalenderwoche
            if (week % 2 == 0) {

                return true;
            }
        } else {

            //Ungerade Kalenderwoche
            if (week % 2 != 0) {

                return true;
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(DateCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        MonthDay startDate = condition.getStartDate();
        MonthDay endDate = condition.getEndDate();
        if (startDate != null && endDate != null) {

            //Test Modus
            MonthDay today = MonthDay.now();

            //Datumsbereich prüfen
            if (startDate.isBefore(endDate)) {

                //start vor ende
                if ((today.isAfter(startDate) && today.isBefore(endDate)) || today.equals(startDate) || today.equals(endDate)) {

                    return true;
                }
            } else {

                //ende vor start
                if ((today.isBefore(startDate) && today.isBefore(endDate)) || today.equals(startDate) || today.equals(endDate)) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(DayCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        LocalTime sunrise = SunriseSunsetUtil.getTodaySunriseTime();
        LocalTime sunset = SunriseSunsetUtil.getTodaySunsetTime();
        LocalTime now = LocalTime.now();

        return (now.isAfter(sunrise) && now.isBefore(sunset)) || now.equals(sunrise);
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(DayOfWeekCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        LocalDate today = LocalDate.now();
        AutomationElement.Weekdays startDay = condition.getStartDay();
        AutomationElement.Weekdays endDay = condition.getEndDay();

        if (startDay.getValue() < endDay.getValue()) {

            //start Tag vor dem End Tag
            LocalDate startDayDate = today.with(TemporalAdjusters.previousOrSame(getDayOfWeek(startDay)));
            LocalDate endDayDate = today.with(TemporalAdjusters.previousOrSame(getDayOfWeek(endDay)));
            if (endDayDate.isBefore(startDayDate)) {

                endDayDate = today.with(TemporalAdjusters.nextOrSame(getDayOfWeek(endDay)));
            }

            if ((today.isAfter(startDayDate) && today.isBefore(endDayDate)) || today.equals(startDayDate) || today.equals(endDayDate)) {

                return true;
            }

        } else if (startDay.getValue() > endDay.getValue()) {

            //start Tag nach dem End Tag
            LocalDate startDayDate = today.with(TemporalAdjusters.previousOrSame(getDayOfWeek(startDay)));
            LocalDate endDayDate = today.with(TemporalAdjusters.nextOrSame(getDayOfWeek(endDay)));
            if (endDayDate.isAfter(today.plusDays(7 - startDay.getValue()))) {

                endDayDate = today.with(TemporalAdjusters.previousOrSame(getDayOfWeek(endDay)));
            }

            if ((today.isAfter(startDayDate) && today.isBefore(endDayDate)) || today.equals(startDayDate) || today.equals(endDayDate)) {

                return true;
            }
        } else {

            //Start Tag und end Tag gleich
            if (today.getDayOfWeek().equals(getDayOfWeek(startDay))) {

                return true;
            }
        }
        return false;
    }

    /**
     * gibt das Wochentags Objekt zurück
     *
     * @param day Tag
     * @return Wochentags Objekt
     */
    private static DayOfWeek getDayOfWeek(AutomationElement.Weekdays day) {

        switch (day) {

            case MONDAY:

                return DayOfWeek.MONDAY;
            case TUESDAY:

                return DayOfWeek.TUESDAY;
            case WEDNESDAY:

                return DayOfWeek.WEDNESDAY;
            case THURSDAY:

                return DayOfWeek.THURSDAY;
            case FRIDAY:

                return DayOfWeek.FRIDAY;
            case SATURDAY:

                return DayOfWeek.SATURDAY;
            case SUNDAY:

                return DayOfWeek.SUNDAY;
        }
        return DayOfWeek.MONDAY;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(FileCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        String file = condition.getFile();
        if (file != null) {

            Path path = Paths.get(file);
            if (!condition.isInvert()) {

                //prüfen ob Datei vorhanden
                if (Files.exists(path)) {

                    if (condition.isDeleteFileIfExist()) {

                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                        }
                    }

                    return true;
                }
            } else {

                //prüfen ob Datei nicht vorhanden
                if (!Files.exists(path)) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(HolidaysCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        Set<GermanHolidays.Holidays> holidays = condition.getHolidays();
        if (holidays.size() > 0) {

            LocalDate today = LocalDate.now();
            GermanHolidays germanHolidays = Holiday.getGermanHolidays();
            for (GermanHolidays.Holidays holiday : holidays) {

                if (germanHolidays.getHolidayDate(holiday).isEqual(today)) {

                    return !condition.isInvert();
                }
            }
        }
        return condition.isInvert();
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(HumidityCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSensorList().size() > 0) {

            for (String sensorId : condition.getSensorList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof HumidityValue) {

                    HumidityValue sensor = (HumidityValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (sensor.getHumidity() > condition.getLimit()) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (sensor.getHumidity() < condition.getLimit()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(InputCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSensorList().size() > 0) {

            for (String sensorId : condition.getSensorList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof InputValue) {

                    InputValue sensor = (InputValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //auf Status "1" prüfen
                        if (sensor.getState()) {

                            return true;
                        }
                    } else {

                        //auf Status "0" prüfen
                        if (!sensor.getState()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(LightIntensityCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSensorList().size() > 0) {

            for (String sensorId : condition.getSensorList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof LightIntensityValue) {

                    LightIntensityValue sensor = (LightIntensityValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (sensor.getLightIntensity() > condition.getLimit()) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (sensor.getLightIntensity() < condition.getLimit()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(MoistureCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSensorList().size() > 0) {

            for (String sensorId : condition.getSensorList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof MoistureValue) {

                    MoistureValue sensor = (MoistureValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (sensor.getMoisture() > condition.getLimit()) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (sensor.getMoisture() < condition.getLimit()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(NightCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        LocalTime sunrise = SunriseSunsetUtil.getTodaySunriseTime();
        LocalTime sunset = SunriseSunsetUtil.getTodaySunsetTime();
        LocalTime now = LocalTime.now();

        return !((now.isAfter(sunrise) && now.isBefore(sunset)) || now.equals(sunrise));
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(NobodyAtHomeCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        List<SensorValue> usersAtHome = AppServer.getInstance().getSensorValues().filterByType(UserAtHomeValue.class);
        if (usersAtHome.size() > 0) {

            for (SensorValue sensorValue : usersAtHome) {

                UserAtHomeValue userAtHomeValue = (UserAtHomeValue) sensorValue;
                if (userAtHomeValue.isDisabled()) {

                    continue;
                }

                //prüfen ob zu Hause
                if (userAtHomeValue.isAtHome()) {

                    return false;
                }
            }
        }
        //keine Beutzer zu Hause definiert -> damit auch keiner da
        return true;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(SwitchableStateCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSwitchableList().size() > 0) {

            for (String switchableId : condition.getSwitchableList()) {

                Optional<Switchable> sv = AppServer.getInstance().getSwitchables().getById(switchableId);
                if (sv.isPresent() && sv.get() instanceof MoistureValue) {

                    Switchable switchable = (Switchable) sv.get();

                    //Deaktivierte überspringen
                    if (switchable.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (switchable.getState() == AutomationElement.State.ON) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (switchable.getState() == AutomationElement.State.OFF) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(TemperatureCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getSensorList().size() > 0) {

            for (String sensorId : condition.getSensorList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof TemperatureValue) {

                    TemperatureValue sensor = (TemperatureValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (sensor.getTemperatureWithOffset() > condition.getLimit()) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (sensor.getTemperatureWithOffset() < condition.getLimit()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(TimeCondition condition) {

        //prüfen ob deaktiviert
        if(condition.isDisabled()) {

            return true;
        }

        LocalTime now = LocalTime.now();
        LocalTime startTime = condition.getStartTime();
        LocalTime endTime = condition.getEndTime();
        if(startTime.isBefore(endTime)) {

            //start vor ende
            if((now.isAfter(startTime) && now.isBefore(endTime)) || now.equals(startTime) || now.equals(endTime)) {

                return true;
            }
        } else {

            //ende vor start
            if((now.isBefore(endTime) && now.isBefore(startTime)) || now.equals(startTime) || now.equals(endTime)) {

                return true;
            }
        }
        return false;
    }

    /**
     * prüft on die Bedingun zutrifft
     *
     * @param condition Bedingung
     * @return wahr oder falsch
     */
    public static boolean isSatisfies(UserAtHomeCondition condition) {

        //prüfen ob deaktiviert
        if (condition.isDisabled()) {

            return true;
        }

        if (condition.getUserAtHomeList().size() > 0) {

            for (String sensorId : condition.getUserAtHomeList()) {

                Optional<SensorValue> sv = AppServer.getInstance().getSensorValues().getById(sensorId);
                if (sv.isPresent() && sv.get() instanceof UserAtHomeValue) {

                    UserAtHomeValue sensor = (UserAtHomeValue) sv.get();

                    //Deaktivierte überspringen
                    if (sensor.isDisabled()) {

                        continue;
                    }

                    if (!condition.isInvert()) {

                        //größer als
                        if (sensor.isAtHome()) {

                            return true;
                        }
                    } else {

                        //Kleiner als
                        if (!sensor.isAtHome()) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

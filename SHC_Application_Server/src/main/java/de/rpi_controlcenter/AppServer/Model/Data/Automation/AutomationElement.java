package de.rpi_controlcenter.AppServer.Model.Data.Automation;

import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Element.Element;

/**
 * Automatisierungselement
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface AutomationElement extends Element {

    enum Type {

        @SerializedName("CONDITION_CALENDAR_WEEK")
        CONDITION_CALENDAR_WEEK,
        @SerializedName("CONDITION_DATE")
        CONDITION_DATE,
        @SerializedName("CONDITION_DAY_OF_WEEK")
        CONDITION_DAY_OF_WEEK,
        @SerializedName("CONDITION_FILE")
        CONDITION_FILE,
        @SerializedName("CONDITION_HOLIDAYS")
        CONDITION_HOLIDAYS,
        @SerializedName("CONDITION_HUMIDITY")
        CONDITION_HUMIDITY,
        @SerializedName("CONDITION_INPUT")
        CONDITION_INPUT,
        @SerializedName("CONDITION_LIGHT_INTENSITY")
        CONDITION_LIGHT_INTENSITY,
        @SerializedName("CONDITION_MOISTURE")
        CONDITION_MOISTURE,
        @SerializedName("CONDITION_NOBODY_AT_HOME")
        CONDITION_NOBODY_AT_HOME,
        @SerializedName("CONDITION_DAY")
        CONDITION_DAY,
        @SerializedName("CONDITION_NIGHT")
        CONDITION_NIGHT,
        @SerializedName("CONDITION_SWITCHABLE_STATE")
        CONDITION_SWITCHABLE_STATE,
        @SerializedName("CONDITION_TEMPERATURE")
        CONDITION_TEMPERATURE,
        @SerializedName("CONDITION_TIME")
        CONDITION_TIME,
        @SerializedName("CONDITION_USER_AT_HOME")
        CONDITION_USER_AT_HOME,

        @SerializedName("SWITCHABLE_AVM_SOCKET")
        SWITCHABLE_AVM_SOCKET,
        @SerializedName("SWITCHABLE_EDIMAX_SOCKET")
        SWITCHABLE_EDIMAX_SOCKET,
        @SerializedName("SWITCHABLE_FRITZ_BOX_WLAN")
        SWITCHABLE_FRITZ_BOX_WLAN,
        @SerializedName("SWITCHABLE_FRITZ_BOX_REBOOT_RECONNECT")
        SWITCHABLE_FRITZ_BOX_REBOOT_RECONNECT,
        @SerializedName("SerializedName")
        SWITCHABLE_OUTPUT,
        @SerializedName("SWITCHABLE_RADIO_SOCKET")
        SWITCHABLE_RADIO_SOCKET,
        @SerializedName("SWITCHABLE_REBOOT_SHUTDOWN")
        SWITCHABLE_REBOOT_SHUTDOWN,
        @SerializedName("SWITCHABLE_SCRIPT_SINGLE")
        SWITCHABLE_SCRIPT_SINGLE,
        @SerializedName("SWITCHABLE_SCRIPT_DOUBLE")
        SWITCHABLE_SCRIPT_DOUBLE,
        @SerializedName("SWITCHABLE_WAKE_ON_LAN")
        SWITCHABLE_WAKE_ON_LAN,
        @SerializedName("SWITCHABLE_VIRTUAL_SOCKET")
        SWITCHABLE_VIRTUAL_SOCKET,

        @SerializedName("SENSORVALUE_INPUT")
        SENSORVALUE_INPUT,
        @SerializedName("SENSORVALUE_USER_AT_HOME")
        SENSORVALUE_USER_AT_HOME,
        @SerializedName("SENSORVALUE_LIVE_BIT")
        SENSORVALUE_LIVE_BIT,
        @SerializedName("SENSORVALUE_ACTUAL_POWER")
        SENSORVALUE_ACTUAL_POWER,
        @SerializedName("SENSORVALUE_AIR_PRESSURE")
        SENSORVALUE_AIR_PRESSURE,
        @SerializedName("SENSORVALUE_ALTITUDE")
        SENSORVALUE_ALTITUDE,
        @SerializedName("SENSORVALUE_BATTERY_LEVEL")
        SENSORVALUE_BATTERY_LEVEL,
        @SerializedName("SENSORVALUE_DISTANCE")
        SENSORVALUE_DISTANCE,
        @SerializedName("SENSORVALUE_DURATION")
        SENSORVALUE_DURATION,
        @SerializedName("SENSORVALUE_ENERGY")
        SENSORVALUE_ENERGY,
        @SerializedName("SENSORVALUE_GAS_AMOUNT")
        SENSORVALUE_GAS_AMOUNT,
        @SerializedName("SENSORVALUE_HUMIDITY")
        SENSORVALUE_HUMIDITY,
        @SerializedName("SENSORVALUE_LIGHT_INTENSITY")
        SENSORVALUE_LIGHT_INTENSITY,
        @SerializedName("SENSORVALUE_MOISTURE")
        SENSORVALUE_MOISTURE,
        @SerializedName("SENSORVALUE_STRING")
        SENSORVALUE_STRING,
        @SerializedName("SENSORVALUE_TEMPERATURE")
        SENSORVALUE_TEMPERATURE,
        @SerializedName("SENSORVALUE_WATER_AMOUNT")
        SENSORVALUE_WATER_AMOUNT,
        @SerializedName("SENSORVALUE_VOLTAGE")
        SENSORVALUE_VOLTAGE,
        @SerializedName("SENSORVALUE_CURRENT")
        SENSORVALUE_CURRENT,

        @SerializedName("VIRTUALSENSORVALUE_ACTUAL_POWER")
        VIRTUALSENSORVALUE_ACTUAL_POWER,
        @SerializedName("VIRTUALSENSORVALUE_ENERGY")
        VIRTUALSENSORVALUE_ENERGY,
        @SerializedName("VIRTUALSENSORVALUE_GAS_AMOUNT")
        VIRTUALSENSORVALUE_GAS_AMOUNT,
        @SerializedName("VIRTUALSENSORVALUE_LIGHT_INTENSITY")
        VIRTUALSENSORVALUE_LIGHT_INTENSITY,
        @SerializedName("VIRTUALSENSORVALUE_WATER_AMOUNT")
        VIRTUALSENSORVALUE_WATER_AMOUNT,
        @SerializedName("VIRTUALSENSORVALUE_TEMPERATURE")
        VIRTUALSENSORVALUE_TEMPERATURE,

        @SerializedName("OPERATION_FILE_CREATE")
        OPERATION_FILE_CREATE,
        @SerializedName("OPERATION_FILE_DELETE")
        OPERATION_FILE_DELETE,
        @SerializedName("OPERATION_HUMIDITY_CLIMB_OVER")
        OPERATION_HUMIDITY_CLIMB_OVER,
        @SerializedName("OPERATION_HUMIDITY_FALLS_BELOW")
        OPERATION_HUMIDITY_FALLS_BELOW,
        @SerializedName("OPERATION_INPUT_HIGH")
        OPERATION_INPUT_HIGH,
        @SerializedName("OPERATION_INPUT_LOW")
        OPERATION_INPUT_LOW,
        @SerializedName("OPERATION_LIGHT_INTENSITY_CLIMB_OVER")
        OPERATION_LIGHT_INTENSITY_CLIMB_OVER,
        @SerializedName("OPERATION_LIGHT_INTENSITY_FALLS_BELOW")
        OPERATION_LIGHT_INTENSITY_FALLS_BELOW,
        @SerializedName("OPERATION_MOISTURE_CLIMB_OVER")
        OPERATION_MOISTURE_CLIMB_OVER,
        @SerializedName("OPERATION_MOISTURE_FALLS_BELOW")
        OPERATION_MOISTURE_FALLS_BELOW,
        @SerializedName("OPERATION_SUNRISE")
        OPERATION_SUNRISE,
        @SerializedName("OPERATION_SUNSET")
        OPERATION_SUNSET,
        @SerializedName("OPERATION_TEMPERATURE_CLIMB_OVER")
        OPERATION_TEMPERATURE_CLIMB_OVER,
        @SerializedName("OPERATION_TEMPERATURE_FALLS_BELOW")
        OPERATION_TEMPERATURE_FALLS_BELOW,
        @SerializedName("OPERATION_USER_COME_HOME")
        OPERATION_USER_COME_HOME,
        @SerializedName("OPERATION_USER_LEAVE_HOME")
        OPERATION_USER_LEAVE_HOME
    }

    enum State {

        @SerializedName("ON")
        ON,
        @SerializedName("OFF")
        OFF
    }

    enum Weekdays {

        @SerializedName("MONDAY")
        MONDAY(1),
        @SerializedName("TUESDAY")
        TUESDAY(2),
        @SerializedName("WEDNESDAY")
        WEDNESDAY(3),
        @SerializedName("THURSDAY")
        THURSDAY(4),
        @SerializedName("FRIDAY")
        FRIDAY(5),
        @SerializedName("SATURDAY")
        SATURDAY(6),
        @SerializedName("SUNDAY")
        SUNDAY(7);

        private final int value;

        Weekdays(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //Wochentage
    int MONDAY = 1;
    int TUESDAY = 2;
    int WEDNESDAY = 3;
    int THURSDAY = 4;
    int FRIDAY = 5;
    int SATURDAY = 6;
    int SUNDAY = 7;

    /**
     * gibt an ob das ELement deaktiviert ist
     *
     * @return true wenn deaktiviert
     */
    boolean isDisabled();

    /**
     * aktiviert/deaktiviert das Element
     *
     * @param disabled aktiviert/deaktiviert
     */
    void setDisabled(boolean disabled);

    /**
     * gibt den Kommentar zurück
     *
     * @return Kommentar
     */
    String getComment();

    /**
     * setzt den Kommentar
     *
     * @param comment Kommentar
     */
    void setComment(String comment);

    /**
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    Type getType();
}

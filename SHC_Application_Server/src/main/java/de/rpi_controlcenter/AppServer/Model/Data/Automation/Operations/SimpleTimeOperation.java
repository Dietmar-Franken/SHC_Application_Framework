package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Ereignis Einfacher Schaltpunkt
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SimpleTimeOperation extends AbstractOperation {

    public enum Weekdays {

        WEEKDAY,
        WEEKEND,
        FULL_WEEK
    }

    private AutomationElement.Type type = Type.OPERATION_SIMPLE_TIME;

    /**
     * Wochentag
     */
    @ValidateNotNull
    private Weekdays weekdays = Weekdays.FULL_WEEK;

    /**
     * Stunde
     */
    @ValidateMin(value = 0, errorCode =  10209, message = "Ungültige Zeitangabe")
    @ValidateMax(value = 23, errorCode =  10209, message = "Ungültige Zeitangabe")
    private int hour = 0;

    /**
     * Minute
     */
    @ValidateMin(value = 0, errorCode =  10209, message = "Ungültige Zeitangabe")
    @ValidateMax(value = 59, errorCode =  10209, message = "Ungültige Zeitangabe")
    private int minute = 0;

    /**
     * nächste Ausführungszeit
     */
    private LocalDateTime nextExecutionTime;

    /**
     * @param id ID
     * @param name Name
     */
    public SimpleTimeOperation(String id, String name) {

        super(id, name);
        setBlockingTime(60);
    }

    /**
     * gibt die Wochentage zurück
     *
     * @return Wochentage
     */
    public Weekdays getWeekdays() {
        return weekdays;
    }

    /**
     * setzt die Wochentage
     *
     * @param weekdays Wochentage
     */
    public void setWeekdays(Weekdays weekdays) {
        this.weekdays = weekdays;
    }

    /**
     * gibt die Stunde zurück
     *
     * @return Stunde
     */
    public int getHour() {
        return hour;
    }

    /**
     * setzt die Stunde
     *
     * @param hour Stunde
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * gibt die Minute zurück
     *
     * @return Minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * setzt die Minute
     *
     * @param minute Minute
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * gibt die nächste Ausführungszeit zurück
     *
     * @return nächste Ausführungszeit
     */
    public LocalDateTime getNextExecutionTime() {
        return nextExecutionTime;
    }

    /**
     * setzt die nächste Ausführungszeit
     *
     * @param nextExecutionTime nächste Ausführungszeit
     */
    public void setNextExecutionTime(LocalDateTime nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
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

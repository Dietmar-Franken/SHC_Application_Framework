package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Ereignis Zeitpunkt wird erreicht
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class TimeOperation extends AbstractOperation {

    private AutomationElement.Type type = Type.OPERATION_TIME;

    /**
     * Zeiteinstellungen
     */
    private SortedSet<Integer> month = new TreeSet<>();
    private SortedSet<Integer> weekday = new TreeSet<>();
    private SortedSet<Integer> day = new TreeSet<>();
    private SortedSet<Integer> hour = new TreeSet<>();
    private SortedSet<Integer> minute = new TreeSet<>();

    /**
     * nächste Ausführungszeit
     */
    private LocalDateTime nextExecutionTime;

    /**
     * @param id ID
     * @param name Name
     */
    public TimeOperation(String id, String name) {

        super(id, name);
        setBlockingTime(60);
    }

    /**
     * gibt die Liste der Tage zurück
     *
     * @return Liste der Tage
     */
    public SortedSet<Integer> getDay() {
        return day;
    }

    /**
     * gibt die Liste der Monate zurück
     *
     * @return Liste der Monate
     */
    public SortedSet<Integer> getMonth() {
        return month;
    }

    /**
     * gibt die Liste der Wochentage zurück
     *
     * @return Liste der Wochentage
     */
    public SortedSet<Integer> getWeekday() {
        return weekday;
    }

    /**
     * gibt die Liste der Stunden zurück
     *
     * @return Liste der Stunden
     */
    public SortedSet<Integer> getHour() {
        return hour;
    }

    /**
     * gibt die Liste der Minuten zurück
     *
     * @return Liste der Minuten
     */
    public SortedSet<Integer> getMinute() {
        return minute;
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

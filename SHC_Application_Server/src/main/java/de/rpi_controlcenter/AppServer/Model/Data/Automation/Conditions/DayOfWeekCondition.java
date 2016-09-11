package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

/**
 * Bedingung Tag der Woche
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DayOfWeekCondition extends AbstractCondition {

    private Type type = Type.CONDITION_DAY_OF_WEEK;

    /**
     * start und end Tag
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private Weekdays startDay = Weekdays.MONDAY;
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private Weekdays endDay = Weekdays.FRIDAY;

    /**
     * @param id ID
     * @param name Name
     */
    public DayOfWeekCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt den start Tag zurück
     *
     * @return start Tag
     */
    public Weekdays getStartDay() {
        return startDay;
    }

    /**
     * setzt den start Tag
     *
     * @param startDay start Tag
     */
    public void setStartDay(Weekdays startDay) {

        Preconditions.checkNotNull(startDay);
        this.startDay = startDay;
    }

    /**
     * gibt den end Tag zurück
     *
     * @return end Tag
     */
    public Weekdays getEndDay() {
        return endDay;
    }

    /**
     * setzt den end Tag
     *
     * @param endDay end Tag
     */
    public void setEndDay(Weekdays endDay) {

        Preconditions.checkNotNull(endDay);
        this.endDay = endDay;
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

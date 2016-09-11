package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.MonthDay;

/**
 * Bedingung Datum
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DateCondition extends AbstractCondition {

    private Type type = Type.CONDITION_DATE;

    /**
     * start Datum
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private MonthDay startDate;

    /**
     * end Datum
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private MonthDay endDate;

    /**
     * @param id ID
     * @param name Name
     */
    public DateCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt das start Datum zurück
     *
     * @return start Datum
     */
    public MonthDay getStartDate() {
        return startDate;
    }

    /**
     * setzt das start Datum
     *
     * @param startDate start Datum
     */
    public void setStartDate(MonthDay startDate) {

        Preconditions.checkNotNull(startDate);
        this.startDate = startDate;
    }

    /**
     * gibt das end Datum zurück
     *
     * @return end Datum
     */
    public MonthDay getEndDate() {
        return endDate;
    }

    /**
     * setzt das end Datum
     *
     * @param endDate end Datum
     */
    public void setEndDate(MonthDay endDate) {

        Preconditions.checkNotNull(endDate);
        this.endDate = endDate;
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

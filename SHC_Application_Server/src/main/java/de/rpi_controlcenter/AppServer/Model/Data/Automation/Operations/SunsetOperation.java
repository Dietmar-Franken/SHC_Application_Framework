package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;

/**
 * Ereignis Sonnenuntergang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SunsetOperation extends AbstractOperation {

    private AutomationElement.Type type = Type.OPERATION_SUNSET;

    /**
     * @param id ID
     * @param name Name
     */
    public SunsetOperation(String id, String name) {

        super(id, name);
        setBlockingTime(120);
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

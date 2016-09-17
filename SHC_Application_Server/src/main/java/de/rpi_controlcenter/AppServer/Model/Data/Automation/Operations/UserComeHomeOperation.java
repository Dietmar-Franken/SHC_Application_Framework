package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Benutzer kommt nach Hause
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UserComeHomeOperation extends AbstractOperation {

    private AutomationElement.Type type = Type.OPERATION_USER_LEAVE_HOME;

    /**
     * Liste der zu überwachenden Sensoren
     */
    @ValidateSize(min = 1, errorCode = 102008, message = "Es muss mindestens ein Benutzer eingetragen sein")
    private Set<String> users = new HashSet<>();

    /**
     * Status Map
     */
    private Map<String, Boolean> stateMap = new HashMap<>();

    /**
     * @param id ID
     * @param name Name
     */
    public UserComeHomeOperation(String id, String name) {

        super(id, name);
    }

    /**
     * gibt die Liste der überachten Sensoren zurück
     *
     * @return Liste der überachte Sensoren
     */
    public Set<String> getUsers() {
        return users;
    }

    /**
     * gibt die Status Map zurück
     *
     * @return Status Map
     */
    public Map<String, Boolean> getStateMap() {
        return stateMap;
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

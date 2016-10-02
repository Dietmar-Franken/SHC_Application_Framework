package de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements;

import de.rpi_controlcenter.AppServer.Model.Data.Room.AbstractRoomElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Butron
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ButtonElement extends AbstractRoomElement {

    /**
     * Liste der schaltbaren Elemente
     */
    private Set<String> switchables = new HashSet<>();

    /**
     * Zeit bis zum automatischen Ausschalten in 100ms Schritten
     * (wenn 0 normaler Button)
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10300, message = "Ungültiger Intervall")
    @ValidateMax(value = 86_400_000, errorCode = 10300, message = "Ungültiger Intervall")
    private long intervall = 0;

    private Type type = Type.BUTTON;

    /**
     * gibt die Liste mit den Schalbaren Elementen zurück
     *
     * @return liste der schaltbaren Elemente
     */
    public Set<String> getSwitchables() {
        return switchables;
    }

    /**
     * gibt den Intervall zurück
     *
     * @return Intervall
     */
    public long getIntervall() {
        return intervall;
    }

    /**
     * setzt den Intervall
     *
     * @param intervall Intervall
     */
    public void setIntervall(long intervall) {
        this.intervall = intervall;
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

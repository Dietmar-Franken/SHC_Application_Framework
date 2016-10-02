package de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements;

import de.rpi_controlcenter.AppServer.Model.Data.Room.AbstractRoomElement;
import de.rpi_controlcenter.AppServer.Model.Data.Room.ViewElement;

/**
 * Trennlinie
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SeparatorElement extends AbstractRoomElement {

    private Type type = Type.SEPARATOR;

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

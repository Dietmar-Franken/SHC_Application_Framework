package de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements;

import de.rpi_controlcenter.AppServer.Model.Data.Room.AbstractRoomElement;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualSensorElement extends AbstractRoomElement {

    /**
     * Hash des Virtuellen Sensorwertes
     */
    private String virtualSensorValueId = "";

    private Type type = Type.VIRTUAL_SENSOR;

    /**
     * gibt den Hash des Virtuellen Sensowertes zurück
     *
     * @return Hash
     */
    public String getVirtualSensorValueId() {
        return virtualSensorValueId;
    }

    /**
     * setzt den Hash des Virtuellen Sensowertes
     *
     * @param virtualSensorValueId Hash
     */
    public void setVirtualSensorValueId(String virtualSensorValueId) {
        this.virtualSensorValueId = virtualSensorValueId;
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

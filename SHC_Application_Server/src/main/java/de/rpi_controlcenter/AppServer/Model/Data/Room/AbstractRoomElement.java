package de.rpi_controlcenter.AppServer.Model.Data.Room;

/**
 * Standard Raum Element
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractRoomElement extends AbstractViewElement implements RoomElement {

    /**
     * schaltlogik umkehren
     */
    private boolean inverse = false;

    /**
     * Sicherheitsabfrage anzeigen
     */
    private boolean showSafetyRequest = false;

    /**
     * gibt an ob das Element in umgekehrter Logik schalten soll
     *
     * @return true wenn umgekehrte Logik
     */
    @Override
    public boolean isInverse() {
        return inverse;
    }

    /**
     * aktiviert/deaktiviert das umkehren der Logik
     *
     * @param inverse aktiviert/deaktiviert
     */
    @Override
    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    /**
     * gibt an ob eine Sicherheitsabfrage angezeigt werden soll
     *
     * @return Sicherheitsabfrage anzeigen
     */
    @Override
    public boolean isShowSafetyRequest() {
        return showSafetyRequest;
    }

    /**
     * aktiviert/deaktiviert das Anzeigen einer Sicherheitsabfrage
     *
     * @param showSafetyRequest aktiviert/deaktiviert
     */
    @Override
    public void setShowSafetyRequest(boolean showSafetyRequest) {
        this.showSafetyRequest = showSafetyRequest;
    }
}

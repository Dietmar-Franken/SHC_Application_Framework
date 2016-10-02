package de.rpi_controlcenter.AppServer.Model.Data.Room;

/**
 * Raum Element
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface RoomElement extends ViewElement {

    /**
     * gibt an ob das Element in umgekehrter Logik schalten soll
     *
     * @return true wenn umgekehrte Logik
     */
    boolean isInverse();

    /**
     * aktiviert/deaktiviert das umkehren der Logik
     *
     * @param inverse aktiviert/deaktiviert
     */
    void setInverse(boolean inverse);

    /**
     * gibt an ob eine Sicherheitsabfrage angezeigt werden soll
     *
     * @return Sicherheitsabfrage anzeigen
     */
    boolean isShowSafetyRequest();
    /**
     * aktiviert/deaktiviert das Anzeigen einer Sicherheitsabfrage
     *
     * @param showSafetyRequest aktiviert/deaktiviert
     */
    void setShowSafetyRequest(boolean showSafetyRequest);
}

package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Automatisierungsoperation
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface Operation extends AutomationElement {

    /**
     * gibt den letzten Status zurück
     *
     * @return letzter Status
     */
    boolean isLastState();

    /**
     * setzt den letzen Status
     *
     * @param lastState letzter Status
     */
    void setLastState(boolean lastState);

    /**
     * gibt den aktuellen Status zurück
     *
     * @return aktualler Status
     */
    boolean isState();

    /**
     * setzt den aktuellen Status
     *
     * @param state aktueller Status
     */
    void setState(boolean state);

    /**
     * gibt den Zeitunkt der letzten Ausführung zurück
     *
     * @return Zeitpunkt
     */
    LocalDateTime getLastExecuteTime();

    /**
     * setzt den Zeitunkt der letzten Ausführung
     *
     * @param lastExecuteTime Zeitpunkt
     */
    void setLastExecuteTime(LocalDateTime lastExecuteTime);

    /**
     * gibt die Blockierungszeit zurück
     *
     * @return Blockierungszeit
     */
    int getBlockingTime();

    /**
     * setzt die Blockierungszeit
     *
     * @param blockingTime Blockierungszeit
     */
    void setBlockingTime(int blockingTime);

    /**
     * gibt den Zeitpunkt des Endes der Blockierungszeit zurück
     *
     * @return Zeitpunkt des Endes der Blockierungszeit
     */
    LocalDateTime getBlockingTimeEnd();

    /**
     * setzt den Zeitpunkt des Endes der Blockierungszeit
     *
     * @param blockingTimeEnd Zeitpunkt des Endes der Blockierungszeit
     */
    void setBlockingTimeEnd(LocalDateTime blockingTimeEnd);

    /**
     * gibt die Liste der Bedingungen zurück
     *
     * @return Liste der Bedingungen
     */
    Set<String> getConditions();

    /**
     * gibt die Liste der schaltbaren Elemente zurück
     *
     * @return Liste der schaltbaren Elemente
     */
    Set<String> getSwitchables();
}

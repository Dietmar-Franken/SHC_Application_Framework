package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AbstractAutomationElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Automatisierungsoperation
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractOperation extends AbstractAutomationElement implements Operation {

    /**
     * letzter Status
     */
    private boolean lastState = false;

    /**
     * aktueller Status
     */
    private boolean state = false;

    /**
     * Zeitpunkt der letzten Ausführung
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private LocalDateTime lastExecuteTime;

    /**
     * Zeit die nach einer Ausführung gewartet werden muss bevor die Operation erneut ausgeführt wrid
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private int blockingTime;

    /**
     * Zeitpunkt des Endes der Sperrzeit (null wenn Sperrzeit nicht aktiv)
     */
    private LocalDateTime blockingTimeEnd;

    /**
     * Liste der Bedingungen
     */
    private Set<String> conditions = new HashSet<>();

    /**
     * Liste der schaltbaren Elemente
     */
    //TODO Durch Command Objekte ersetzen (Switchable + Befehl)
    @ValidateSize(min = 1, errorCode = 10207, message = "Es muss mindestens ein schaltbares Element vorhanden sein")
    private Set<String> switchables = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public AbstractOperation(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * gibt den letzten Status zurück
     *
     * @return letzter Status
     */
    public boolean isLastState() {
        return lastState;
    }

    /**
     * setzt den letzen Status
     *
     * @param lastState letzter Status
     */
    public void setLastState(boolean lastState) {
        this.lastState = lastState;
    }

    /**
     * gibt den aktuellen Status zurück
     *
     * @return aktualler Status
     */
    public boolean isState() {
        return state;
    }

    /**
     * setzt den aktuellen Status
     *
     * @param state aktueller Status
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * gibt den Zeitunkt der letzten Ausführung zurück
     *
     * @return Zeitpunkt
     */
    public LocalDateTime getLastExecuteTime() {
        return lastExecuteTime;
    }

    /**
     * setzt den Zeitunkt der letzten Ausführung
     *
     * @param lastExecuteTime Zeitpunkt
     */
    public void setLastExecuteTime(LocalDateTime lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    /**
     * gibt die Blockierungszeit zurück
     *
     * @return Blockierungszeit
     */
    public int getBlockingTime() {
        return blockingTime;
    }

    /**
     * setzt die Blockierungszeit
     *
     * @param blockingTime Blockierungszeit
     */
    public void setBlockingTime(int blockingTime) {
        this.blockingTime = blockingTime;
    }

    /**
     * gibt den Zeitpunkt des Endes der Blockierungszeit zurück
     *
     * @return Zeitpunkt des Endes der Blockierungszeit
     */
    public LocalDateTime getBlockingTimeEnd() {
        return blockingTimeEnd;
    }

    /**
     * setzt den Zeitpunkt des Endes der Blockierungszeit
     *
     * @param blockingTimeEnd Zeitpunkt des Endes der Blockierungszeit
     */
    public void setBlockingTimeEnd(LocalDateTime blockingTimeEnd) {
        this.blockingTimeEnd = blockingTimeEnd;
    }

    /**
     * gibt die Liste der Bedingungen zurück
     *
     * @return Liste der Bedingungen
     */
    public Set<String> getConditions() {
        return conditions;
    }

    /**
     * gibt die Liste der schaltbaren Elemente zurück
     *
     * @return Liste der schaltbaren Elemente
     */
    public Set<String> getSwitchables() {
        return switchables;
    }
}

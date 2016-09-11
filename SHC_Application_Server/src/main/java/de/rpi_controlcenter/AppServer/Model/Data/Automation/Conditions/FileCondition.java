package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class FileCondition extends AbstractCondition {

    private Type type = Type.CONDITION_FILE;

    /**
     * soll die Datei nach dem Prüfen gelöscht werden?
     */
    private boolean deleteFileIfExist = false;

    /**
     * Datei welche überwacht werden soll
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private String file;

    /**
     * @param id ID
     * @param name Name
     */
    public FileCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt an ob die Datei nach dem prüfen gelöscht werden soll
     *
     * @return true wenn aktiviert
     */
    public boolean isDeleteFileIfExist() {
        return deleteFileIfExist;
    }

    /**
     * aktiviert/deaktiviert das löschen der Datei nach dem prüfen
     *
     * @param deleteFileIfExist aktiviert/deaktiviert
     */
    public void setDeleteFileIfExist(boolean deleteFileIfExist) {
        this.deleteFileIfExist = deleteFileIfExist;
    }

    /**
     * gibt die Datei die von der Bedingung überwacht wird zurück
     *
     * @return Datei
     */
    public String getFile() {
        return file;
    }

    /**
     * setzt die Datei die von der Bedingung überwacht werden soll
     *
     * @param file Datei
     */
    public void setFile(String file) {

        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.length() >=3, "Ungültige Datei %s", file);
        this.file = file;
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

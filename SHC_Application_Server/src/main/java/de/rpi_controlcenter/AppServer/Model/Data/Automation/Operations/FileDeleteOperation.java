package de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.AbstractOperation;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotEmpty;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

/**
 * Ereignis Datei wird erstellt
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class FileDeleteOperation extends AbstractOperation {

    private Type type = Type.OPERATION_FILE_DELETE;

    /**
     * zu überwachender Dateipfad
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateNotEmpty(errorCode = 10007, message = "Ungültige länge der Zeichenkette")
    private String filePath = "";

    /**
     * @param id ID
     * @param name Name
     */
    public FileDeleteOperation(String id, String name) {

        super(id, name);
    }

    /**
     * gibt den zu überwachenden Dateipfad zurück
     *
     * @return zu überwachender Dateipfad
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * setzt den überwachenden Dateipfad
     *
     * @param filePath überwachender Dateipfad
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

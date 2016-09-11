package de.rpi_controlcenter.AppServer.Model.Data.Automation;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractAutomationElement extends AbstractElement implements AutomationElement {

    /**
     * deaktiviert
     */
    private boolean disabled = false;

    /**
     * Kommentar
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private String comment = "";

    /**
     * gibt an ob das ELement deaktiviert ist
     *
     * @return true wenn deaktiviert
     */
    public boolean isDisabled() {
        return this.disabled;
    }

    /**
     * aktiviert/deaktiviert das Element
     *
     * @param disabled aktiviert/deaktiviert
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * gibt den Kommentar zurück
     *
     * @return Kommentar
     */
    public String getComment() {
        return comment;
    }

    /**
     * setzt den Kommentar
     *
     * @param comment Kommentar
     */
    public void setComment(String comment) {

        Preconditions.checkNotNull(comment);
        this.comment = comment;
    }
}

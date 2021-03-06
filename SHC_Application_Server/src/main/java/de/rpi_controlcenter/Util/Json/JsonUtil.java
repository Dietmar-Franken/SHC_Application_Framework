package de.rpi_controlcenter.Util.Json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.rpi_controlcenter.Util.Validation.ValidationError;

import java.util.List;

/**
 * Hilfsfunktionen für die Json verarbeitung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class JsonUtil {

    /**
     * erstellt einen Json String aus den übergebenen Daten
     *
     * @param code Fehlercode
     * @param message Fehlermeldung
     * @return Json String
     */
    public static String errorMessage(int code, String message) {

        JsonObject jo = new JsonObject();
        jo.addProperty("code", code);
        jo.addProperty("message", message);
        return jo.toString();
    }

    /**
     * erstellt einen Json String aus den übergebenen Daten
     *
     * @param code Fehlercode
     * @param errors Fehlerhafte Felder
     * @return Json String
     */
    public static String errorMessage(int code, List<ValidationError> errors) {

        JsonArray ja = new JsonArray();
        for(ValidationError error : errors) {

            JsonObject errorJo = new JsonObject();
            errorJo.addProperty("code", error.getErrorCode());
            errorJo.addProperty("field", error.getFieldName());
            errorJo.addProperty("message", error.getMessage());
            ja.add(errorJo);
        }

        JsonObject jo = new JsonObject();
        jo.addProperty("code", code);
        jo.addProperty("message", "ungültige Daten");
        jo.add("errors", ja);
        return jo.toString();
    }
}

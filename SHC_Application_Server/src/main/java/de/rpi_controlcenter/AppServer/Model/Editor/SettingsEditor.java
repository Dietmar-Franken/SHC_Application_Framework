package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Database.DatabaseEditor;
import de.rpi_controlcenter.Util.Settings.*;
import de.rpi_controlcenter.Util.Settings.Interface.Setting;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Verwaltung der Einstellungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SettingsEditor implements DatabaseEditor {

    /**
     * Lock objekt
     */
    private volatile ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final String DATABASE_KEY = "shc:setting:settings";

    //Application REST Service (SSL)
    public static final String APPLICATION_SERVER_ADDRESS = "APPLICATION_SERVER_ADDRESS";
    public static final String APPLICATION_SERVER_PORT = "APPLICATION_SERVER_PORT";
    public static final String APPLICATION_SERVER_CERTIFICATE_PASSWORD = "APPLICATION_SERVER_CERTIFICATE_PASSWORD";

    //Sensor REST Service (unverschlüsselt)
    public static final String SENSOR_SERVER_ADDRESS = "SENSOR_SERVER_ADDRESS";
    public static final String SENSOR_SERVER_PORT = "SENSOR_SERVER_PORT";

    //Daten für den Sonnenaufgang/untergang
    public static final String SUNRISE_OFFSET = "SUNRISE_OFFSET";
    public static final String SUNSET_OFFSET = "SUNSET_OFFSET";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";

    //Fritz!Box
    public static final String FB_ACTIVE = "FB_ACTIVE";
    public static final String FB_ADDRESS = "FB_ADDRESS";
    public static final String FB_USER = "FB_USER";
    public static final String FB_PASSWORD = "FB_PASSWORD";
    public static final String FB_5GHZ_WLAN_ENABLED = "FB_5GHZ_WLAN_ENABLED";

    //Energiedaten
    public static final String ENERGY_ELECTRIC_PRICE = "ENERGY_ELECTRIC_PRICE";
    public static final String ENERGY_WATER_PRICE = "ENERGY_WATER_PRICE";
    public static final String ENERGY_GAS_PRICE = "ENERGY_GAS_PRICE";

    //Sonstiges
    public static final String STATE_LED_PIN = "STATE_LED_PIN";

    /**
     * Einstellungen
     */
    private List<Setting> settings = new ArrayList<>();

    /**
     * Bekannte Einstellungen
     */
    private List<Setting> knownSettings = new ArrayList<>();

    public SettingsEditor() {

        //Application REST Service (SSL)
        StringSetting applicationServerAddress = new StringSetting(APPLICATION_SERVER_ADDRESS, "localhost", "localhost");
        knownSettings.add(applicationServerAddress);
        IntegerSetting applicationServerPort = new IntegerSetting(APPLICATION_SERVER_PORT, 8081, 8081);
        knownSettings.add(applicationServerPort);
        StringSetting applicationServercertificatePassword = new StringSetting(APPLICATION_SERVER_CERTIFICATE_PASSWORD, "password", "password");
        knownSettings.add(applicationServercertificatePassword);

        //Sensor REST Service (unverschlüsselt)
        StringSetting sensorServerAddress = new StringSetting(SENSOR_SERVER_ADDRESS, "localhost", "localhost");
        knownSettings.add(sensorServerAddress);
        IntegerSetting sensorServerPort = new IntegerSetting(SENSOR_SERVER_PORT, 8080, 8080);
        knownSettings.add(sensorServerPort);

        //Daten für den Sonnenaufgang/untergang
        IntegerSetting sunriseOffset = new IntegerSetting(SUNRISE_OFFSET, 0, 0);
        knownSettings.add(sunriseOffset);
        IntegerSetting sunsetOffset = new IntegerSetting(SUNSET_OFFSET, 0, 0);
        knownSettings.add(sunsetOffset);
        DoubleSetting latitude = new DoubleSetting(LATITUDE, 50.0, 50.0);
        knownSettings.add(latitude);
        DoubleSetting longitude = new DoubleSetting(LONGITUDE, 12.0, 12.0);
        knownSettings.add(longitude);

        //Fritz!Box
        BooleanSetting fritzboxActive = new BooleanSetting(FB_ACTIVE, false, false);
        knownSettings.add(fritzboxActive);
        StringSetting fritzboxAddress = new StringSetting(FB_ADDRESS, "fritz.box", "fritz.box");
        knownSettings.add(fritzboxAddress);
        StringSetting fritzboxUser = new StringSetting(FB_USER, "", "");
        knownSettings.add(fritzboxUser);
        StringSetting fritzboxPassowrd = new StringSetting(FB_PASSWORD, "", "");
        knownSettings.add(fritzboxPassowrd);
        BooleanSetting fritzbox5GHzWlan = new BooleanSetting(FB_5GHZ_WLAN_ENABLED, false, false);
        knownSettings.add(fritzbox5GHzWlan);

        //Energiedaten
        DoubleSetting energyElectricPrice = new DoubleSetting(ENERGY_ELECTRIC_PRICE, 0.0, 0.0);
        knownSettings.add(energyElectricPrice);
        DoubleSetting energyWaterPrice = new DoubleSetting(ENERGY_WATER_PRICE, 0.0, 0.0);
        knownSettings.add(energyWaterPrice);
        DoubleSetting energyGasPrice = new DoubleSetting(ENERGY_GAS_PRICE, 0.0, 0.0);
        knownSettings.add(energyGasPrice);

        //Sonstiges
        IntegerSetting stateLedPin = new IntegerSetting(STATE_LED_PIN, -1, -1);
        knownSettings.add(stateLedPin);
    }

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        JsonParser jp = new JsonParser();
        Gson gson = AppServer.getInstance().getGson();

        List<String> settingsList = db.lrange(DATABASE_KEY, 0, -1);
        settings.clear();
        for(String settingJson : settingsList) {

            JsonObject jo = jp.parse(settingJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            Setting setting = (Setting) gson.fromJson(settingJson, clazz);
            settings.add(setting);
        }

        //mit bekannten Einstellungen falls nötig auffüllen
        for(Setting knownSetting : knownSettings) {

            String knownSettingName = knownSetting.getName();
            boolean found = false;
            for(Setting setting : settings) {

                if(setting.getName().equalsIgnoreCase(knownSettingName)) {

                    found = true;
                }
            }
            if(!found) {

                settings.add(knownSetting);
            }
        }
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public Optional<Setting> getSetting(String name) {

        return settings.stream().filter(setting -> setting.getName().equals(name)).findFirst();
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public StringSetting getStringSetting(String name) {

        Optional<Setting> setting = getSetting(name);
        if(setting.isPresent() && setting.get() instanceof StringSetting) {

            return (StringSetting) setting.get();
        }
        throw new IllegalArgumentException("Die Einstellung \"" + name + "\" ist nicht vorhanden");
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public IntegerSetting getIntegerSetting(String name) {

        Optional<Setting> setting = getSetting(name);
        if(setting.isPresent() && setting.get() instanceof IntegerSetting) {

            return (IntegerSetting) setting.get();
        }
        throw new IllegalArgumentException("Die Einstellung \"" + name + "\" ist nicht vorhanden");
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public DoubleSetting getDoubleSetting(String name) {

        Optional<Setting> setting = getSetting(name);
        if(setting.isPresent() && setting.get() instanceof DoubleSetting) {

            return (DoubleSetting) setting.get();
        }
        throw new IllegalArgumentException("Die Einstellung \"" + name + "\" ist nicht vorhanden");
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public BooleanSetting getBooleanSetting(String name) {

        Optional<Setting> setting = getSetting(name);
        if(setting.isPresent() && setting.get() instanceof BooleanSetting) {

            return (BooleanSetting) setting.get();
        }
        throw new IllegalArgumentException("Die Einstellung \"" + name + "\" ist nicht vorhanden");
    }

    /**
     * gibt eine Einstellung zurück
     *
     * @param name Name der Einstellung
     * @return Einstellung
     */
    public ListSetting getListSetting(String name) {

        Optional<Setting> setting = getSetting(name);
        if(setting.isPresent() && setting.get() instanceof ListSetting) {

            return (ListSetting) setting.get();
        }
        throw new IllegalArgumentException("Die Einstellung \"" + name + "\" ist nicht vorhanden");
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();

        Pipeline pipeline = db.pipelined();
        pipeline.del(DATABASE_KEY);
        for(Setting setting : settings) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(setting));
        }
        pipeline.sync();
    }

    /**
     * gibt das Lockobjekt zurück
     *
     * @return Lockobjekt
     */
    @Override
    public ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    /**
     * gibt das Class Objekt zum Typ zurück
     *
     * @param typeStr Typ String
     * @return Class Objekt
     */
    public Class getTypeClass(String typeStr) {

        Setting.Type type = Setting.Type.valueOf(typeStr);
        switch(type) {

            case BOOLEAN:

                return BooleanSetting.class;
            case DOUBLE:

                return DoubleSetting.class;
            case INTEGER:

                return IntegerSetting.class;
            case LIST:

                return ListSetting.class;
            case STRING:

                return StringSetting.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}

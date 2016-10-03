package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Device.ClientDevice;
import de.rpi_controlcenter.AppServer.Model.Database.DatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DeviceEditor implements DatabaseEditor {

    /**
     * Lock objekt
     */
    private volatile ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final String DATABASE_KEY = "shc:device:devices";

    /**
     * Liste der bekannten Geräte
     */
    private List<ClientDevice> devices = new ArrayList<>();

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        List<String> deviceList = db.lrange(DATABASE_KEY, 0, -1);
        devices.clear();
        for(String deviceJson : deviceList) {

            devices.add(gson.fromJson(deviceJson, ClientDevice.class));
        }
    }

    /**
     * gibt die Liste aller Geräte zurück
     *
     * @return Liste aller Geräte
     */
    public List<ClientDevice> getData() {

        return devices;
    }

    /**
     * gibt das Client Gerät zurück
     *
     * @param clientHash Hash
     * @return Client Gerät
     */
    public Optional<ClientDevice> getByClientHash(String clientHash) {

        return devices.stream().filter(c -> c.getClientHash().equals(clientHash)).findFirst();
    }

    /**
     * gibt das Client Gerät zurück
     *
     * @param userAgent Hash
     * @return Client Gerät
     */
    public Optional<ClientDevice> getByUserAgent(String userAgent) {

        return devices.stream().filter(c -> c.getUserAgent().equals(userAgent)).findFirst();
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Pipeline pipeline = db.pipelined();
        Gson gson = AppServer.getInstance().getGson();

        pipeline.del(DATABASE_KEY);
        for(ClientDevice clientDevice : devices) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(clientDevice));
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
     * gibt ein Lock Objekt zum erlangen des Leselock zurück
     *
     * @return Lock Objekt
     */
    @Override
    public ReentrantReadWriteLock.ReadLock readLock() {
        return readWriteLock.readLock();
    }

    /**
     * gibt ein Lock Objekt zum erlangen des Schreiblock zurück
     *
     * @return Lock Objekt
     */
    @Override
    public ReentrantReadWriteLock.WriteLock writeLock() {
        return readWriteLock.writeLock();
    }
}

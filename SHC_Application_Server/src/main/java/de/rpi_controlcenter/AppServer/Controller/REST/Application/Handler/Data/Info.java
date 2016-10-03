package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.management.OperatingSystemMXBean;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.REST.PermissionCheck;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.management.ManagementFactory;
import java.util.Map;

/**
 * Info Anzeige
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
@Path("/info")
public class Info {

    /**
     * @api {get} /info Server Informationen
     * @apiName getInfo
     * @apiGroup Info
     * @apiVersion 1.0.0
     * @apiPermission ENTER_ACP
     * @apiDescription Gibt Informationen zu folgenden Bereiche zurück:
     *      SHC (Allgemein),
     *      System (Laufzeitinformationen),
     *      Java Virtual Maschine (Laufzeitinformationen),
     *      Datenbank (Laufzeitinformationen)
     *
     * @apiSuccess (200) {Object} shc SHC Informationen
     * @apiSuccess (200) {String} shc.version Version
     * @apiSuccess (200) {Number} shc.apiLevel API Level
     * @apiSuccess (200) {String[]} shc.libarys verwendete Libarys
     * @apiSuccess (200) {Object} system System Informationen
     * @apiSuccess (200) {Number} system.cpuLoad CPU Auslastung
     * @apiSuccess (200) {Number} system.freeMemory freier Arbeitsspeicher
     * @apiSuccess (200) {Number} system.totalMemory gesamter Arbeitsspeicher
     * @apiSuccess (200) {Object} jvm JVM Informationen
     * @apiSuccess (200) {String} jvm.version Java Version
     * @apiSuccess (200) {Number} jvm.availableProcessors Anzahl der Prozessoren die der JVM zur verfügung stehen
     * @apiSuccess (200) {Number} jvm.cpuLoad CPU Auslastung der JVM
     * @apiSuccess (200) {Number} jvm.maxMemory Maximaler Arbeitsspeicher der JVM
     * @apiSuccess (200) {Number} jvm.freeMemory freier Arbeitsspeicher der JVM
     * @apiSuccess (200) {Number} jvm.totalMemory reservierter Arbeitsspeicher der JVM
     * @apiSuccess (200) {Number} jvm.runningThreads Anzahl der laufenden Threads in der JVM
     * @apiSuccess (200) {Object} db Datenbank (Redis) Informationen
     * @apiSuccess (200) {String} db.version Version
     * @apiSuccess (200) {String} db.mode Modus
     * @apiSuccess (200) {Number} db.uptime Laufzeit
     * @apiSuccess (200) {String} db.configFile Konfigurationsdatei
     * @apiSuccess (200) {Number} db.usedMemory benötigter Arbeitsspeicher
     * @apiSuccess (200) {Number} db.usedMemoryPeak benötigter Arbeitsspeicher (Spitze)
     * @apiSuccess (200) {Number} db.lastSaveTime Zeitstempel der letzten Sicherung der Datenbank
     * @apiSuccess (200) {String} db.lastSaveState Status der letzten Sicherung der Datenbank
     * @apiSuccess (200) {Number} db.totalBytesInput Empfangene Datenmenge in Bytes
     * @apiSuccess (200) {Number} db.totalBytesOutput Gesendete Datenmenge in Bytes
     *
     * @apiUse AuthenticationError
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.ENTER_ACP)
    public Response getInfo() {

        //Vorbereitung
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        Map<String, String> dbInfo = AppServer.getInstance().getDatabaseManager().getServerInfoForCurrentConnection();

        JsonObject jo = new JsonObject();

        //SHC Infos
        JsonObject shc = new JsonObject();
        shc.addProperty("version", AppServer.VERSION);
        shc.addProperty("apiLevel", AppServer.API_LEVEL);
        JsonArray ja = new JsonArray();
        ja.add("Google Gson");
        ja.add("Google Guava");
        ja.add("Jedis");
        ja.add("SunriseSunsetCalculator");
        ja.add("Grizzly");
        ja.add("Jersey");
        shc.add("libarys", ja);
        jo.add("shc", shc);

        //System
        JsonObject system = new JsonObject();
        system.addProperty("cpuLoad", osBean.getSystemCpuLoad());
        system.addProperty("freeMemory", osBean.getFreePhysicalMemorySize());
        system.addProperty("totalMemory", osBean.getTotalPhysicalMemorySize());
        jo.add("system", system);

        //JVM Laufzeit Informationen
        JsonObject jvm = new JsonObject();
        jvm.addProperty("version", System.getProperty("java.version"));
        jvm.addProperty("availableProcessors", Runtime.getRuntime().availableProcessors());
        jvm.addProperty("cpuLoad", osBean.getProcessCpuLoad());
        jvm.addProperty("maxMemory", Runtime.getRuntime().maxMemory());
        jvm.addProperty("freeMemory", Runtime.getRuntime().freeMemory());
        jvm.addProperty("totalMemory", Runtime.getRuntime().totalMemory());
        jvm.addProperty("runningThreads", Thread.activeCount());
        jo.add("jvm", jvm);

        //Datenbank Informationen
        JsonObject db = new JsonObject();
        db.addProperty("version", dbInfo.containsKey("redis_version") ? dbInfo.get("redis_version") : "unbekannt");
        db.addProperty("mode", dbInfo.containsKey("redis_mode") ? dbInfo.get("redis_mode") : "unbekannt");
        db.addProperty("uptime", dbInfo.containsKey("uptime_in_seconds") ? dbInfo.get("uptime_in_seconds") : "unbekannt");
        db.addProperty("configFile", dbInfo.containsKey("config_file") ? dbInfo.get("config_file") : "unbekannt");
        db.addProperty("usedMemory", dbInfo.containsKey("used_memory") ? dbInfo.get("used_memory") : "unbekannt");
        db.addProperty("usedMemoryPeak", dbInfo.containsKey("used_memory_peak") ? dbInfo.get("used_memory_peak") : "unbekannt");
        db.addProperty("lastSaveTime", dbInfo.containsKey("rdb_last_save_time") ? dbInfo.get("rdb_last_save_time") : "unbekannt");
        db.addProperty("lastSaveState", dbInfo.containsKey("rdb_last_bgsave_status") ? dbInfo.get("rdb_last_bgsave_status") : "unbekannt");
        db.addProperty("totalBytesInput", dbInfo.containsKey("total_net_input_bytes") ? dbInfo.get("total_net_input_bytes") : "unbekannt");
        db.addProperty("totalBytesOutput", dbInfo.containsKey("total_net_output_bytes") ? dbInfo.get("total_net_output_bytes") : "unbekannt");
        jo.add("db", db);

        return Response.status(Response.Status.OK).entity(jo.toString()).build();
    }

    /**
     * @api {put} /info Datenbak speichern
     * @apiName dumpDatabase
     * @apiGroup Info
     * @apiVersion 1.0.0
     * @apiPermission ENTER_ACP
     * @apiDescription fordert den Datenbankserver auf die Datenbank zu speichern auf
     *
     * @apiSuccess (200) {String} state Status Information
     *
     * @apiUse AuthenticationError
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.ENTER_ACP)
    public Response dumpDatabase() {

        Jedis jedis = AppServer.getInstance().getDatabaseConnection();
        String state = jedis.bgsave();

        JsonObject jo = new JsonObject();
        jo.addProperty("state", state);
        return Response.status(Response.Status.OK).entity(jo.toString()).build();
    }
}

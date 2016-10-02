package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.management.OperatingSystemMXBean;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
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
     * @apiDescription Gibt Informationen zu folgenden Bereiche zurück:
     *      SHC (Allgemein),
     *      System (Laufzeitinformationen),
     *      Java Virtual Maschine (Laufzeitinformationen),
     *      Datenbank (Laufzeitinformationen)
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *          "shc": {
     *              "version": "0.1.0",
     *              "apiLevel": 1,
     *              "libarys": [
     *                  "Google Gson",
     *                  "Google Guava",
     *                  "Jedis",
     *                  "SunriseSunsetCalculator",
     *                  "Grizzly",
     *                  "Jetty",
     *                  "Jersey"
     *              ]
     *          },
     *          "system": {
     *              "cpuLoad": 0.08325074331020813,
     *              "freeMemory": 8381874176,
     *              "totalMemory": 16634654720
     *          },
     *          "jvm": {
     *              "version": "1.8.0_101",
     *              "availableProcessors": 4,
     *              "cpuLoad": 0.000023374641490796785,
     *              "maxMemory": 3698851840,
     *              "freeMemory": 248953432,
     *              "totalMemory": 677380096,
     *              "runningThreads": 28
     *          },
     *          "db": {
     *              "version": "3.0.6",
     *              "mode": "standalone",
     *              "uptime": "24512",
     *              "configFile": "/etc/redis/redis.conf",
     *              "usedMemory": "637416",
     *              "usedMemoryPeak": "637440",
     *              "lastSaveTime": "1475402534",
     *              "lastSaveState": "ok",
     *              "totalBytesInput": "559269",
     *              "totalBytesOutput": "1184094"
     *          }
     *     }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
        ja.add("Jetty");
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
     * @apiDescription fordert den Datenbankserver auf die Datenbank zu speichern auf
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *          "state": "Background saving started"
     *     }
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response dumpDatabase() {

        Jedis jedis = AppServer.getInstance().getDatabaseConnection();
        String state = jedis.bgsave();

        JsonObject jo = new JsonObject();
        jo.addProperty("state", state);
        return Response.status(Response.Status.OK).entity(jo.toString()).build();
    }
}

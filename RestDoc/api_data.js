define({ "api": [
  {
    "type": "put",
    "url": "/info",
    "title": "Datenbak speichern",
    "name": "dumpDatabase",
    "group": "Info",
    "version": "1.0.0",
    "description": "<p>fordert den Datenbankserver auf die Datenbank zu speichern auf</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n     \"state\": \"Background saving started\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/info",
    "title": "Server Informationen",
    "name": "getInfo",
    "group": "Info",
    "version": "1.0.0",
    "description": "<p>Gibt Informationen zu folgenden Bereiche zurück: SHC (Allgemein), System (Laufzeitinformationen), Java Virtual Maschine (Laufzeitinformationen), Datenbank (Laufzeitinformationen)</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n     \"shc\": {\n         \"version\": \"0.1.0\",\n         \"apiLevel\": 1,\n         \"libarys\": [\n             \"Google Gson\",\n             \"Google Guava\",\n             \"Jedis\",\n             \"SunriseSunsetCalculator\",\n             \"Grizzly\",\n             \"Jetty\",\n             \"Jersey\"\n         ]\n     },\n     \"system\": {\n         \"cpuLoad\": 0.08325074331020813,\n         \"freeMemory\": 8381874176,\n         \"totalMemory\": 16634654720\n     },\n     \"jvm\": {\n         \"version\": \"1.8.0_101\",\n         \"availableProcessors\": 4,\n         \"cpuLoad\": 0.000023374641490796785,\n         \"maxMemory\": 3698851840,\n         \"freeMemory\": 248953432,\n         \"totalMemory\": 677380096,\n         \"runningThreads\": 28\n     },\n     \"db\": {\n         \"version\": \"3.0.6\",\n         \"mode\": \"standalone\",\n         \"uptime\": \"24512\",\n         \"configFile\": \"/etc/redis/redis.conf\",\n         \"usedMemory\": \"637416\",\n         \"usedMemoryPeak\": \"637440\",\n         \"lastSaveTime\": \"1475402534\",\n         \"lastSaveState\": \"ok\",\n         \"totalBytesInput\": \"559269\",\n         \"totalBytesOutput\": \"1184094\"\n     }\n}",
          "type": "json"
        }
      ]
    },
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info"
  }
] });

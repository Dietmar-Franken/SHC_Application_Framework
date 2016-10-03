define({ "api": [
  {
    "type": "put",
    "url": "/info",
    "title": "Datenbak speichern",
    "name": "dumpDatabase",
    "group": "Info",
    "version": "1.0.0",
    "permission": [
      {
        "name": "ENTER_ACP"
      }
    ],
    "description": "<p>fordert den Datenbankserver auf die Datenbank zu speichern auf</p>",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "state",
            "description": "<p>Status Information</p>"
          }
        ]
      }
    },
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info",
    "error": {
      "fields": {
        "4xx": [
          {
            "group": "4xx",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>Fehlercode</p>"
          },
          {
            "group": "4xx",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>Fehlermeldung</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/info",
    "title": "Server Informationen",
    "name": "getInfo",
    "group": "Info",
    "version": "1.0.0",
    "permission": [
      {
        "name": "ENTER_ACP"
      }
    ],
    "description": "<p>Gibt Informationen zu folgenden Bereiche zurück: SHC (Allgemein), System (Laufzeitinformationen), Java Virtual Maschine (Laufzeitinformationen), Datenbank (Laufzeitinformationen)</p>",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "shc",
            "description": "<p>SHC Informationen</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "shc.version",
            "description": "<p>Version</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "shc.apiLevel",
            "description": "<p>API Level</p>"
          },
          {
            "group": "200",
            "type": "String[]",
            "optional": false,
            "field": "shc.libarys",
            "description": "<p>verwendete Libarys</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "system",
            "description": "<p>System Informationen</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "system.cpuLoad",
            "description": "<p>CPU Auslastung</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "system.freeMemory",
            "description": "<p>freier Arbeitsspeicher</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "system.totalMemory",
            "description": "<p>gesamter Arbeitsspeicher</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "jvm",
            "description": "<p>JVM Informationen</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "jvm.version",
            "description": "<p>Java Version</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.availableProcessors",
            "description": "<p>Anzahl der Prozessoren die der JVM zur verfügung stehen</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.cpuLoad",
            "description": "<p>CPU Auslastung der JVM</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.maxMemory",
            "description": "<p>Maximaler Arbeitsspeicher der JVM</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.freeMemory",
            "description": "<p>freier Arbeitsspeicher der JVM</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.totalMemory",
            "description": "<p>reservierter Arbeitsspeicher der JVM</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "jvm.runningThreads",
            "description": "<p>Anzahl der laufenden Threads in der JVM</p>"
          },
          {
            "group": "200",
            "type": "Object",
            "optional": false,
            "field": "db",
            "description": "<p>Datenbank (Redis) Informationen</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "db.version",
            "description": "<p>Version</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "db.mode",
            "description": "<p>Modus</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.uptime",
            "description": "<p>Laufzeit</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "db.configFile",
            "description": "<p>Konfigurationsdatei</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.usedMemory",
            "description": "<p>benötigter Arbeitsspeicher</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.usedMemoryPeak",
            "description": "<p>benötigter Arbeitsspeicher (Spitze)</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.lastSaveTime",
            "description": "<p>Zeitstempel der letzten Sicherung der Datenbank</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "db.lastSaveState",
            "description": "<p>Status der letzten Sicherung der Datenbank</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.totalBytesInput",
            "description": "<p>Empfangene Datenmenge in Bytes</p>"
          },
          {
            "group": "200",
            "type": "Number",
            "optional": false,
            "field": "db.totalBytesOutput",
            "description": "<p>Gesendete Datenmenge in Bytes</p>"
          }
        ]
      }
    },
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info",
    "error": {
      "fields": {
        "4xx": [
          {
            "group": "4xx",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": "<p>Fehlercode</p>"
          },
          {
            "group": "4xx",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>Fehlermeldung</p>"
          }
        ]
      }
    }
  }
] });

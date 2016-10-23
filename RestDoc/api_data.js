define({ "api": [
  {
    "type": "post",
    "url": "/user?t=:token",
    "title": "erstellen",
    "name": "createUser",
    "group": "Benutzer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>erstellt einen neuen Benutzer</p>",
    "examples": [
      {
        "title": "Request:",
        "content": " {\n    \"id\": \"\",\n    \"name\": \"test\",\n    \"passwordHash\": \"bla\",\n    \"originator\": true,\n    \"locale\": \"de\",\n    \"userGroups\": [\n        \"1d9920835958d3061252c02f9030dfada370a772\"\n    ]\n}",
        "type": "json"
      },
      {
        "title": "Response:",
        "content": "HTTP/1.1 201 Created\nLocation http://localhost:8081/user/820694213e14dc9a3932953aee832da29c2f1e43",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Users.java",
    "groupTitle": "Benutzer",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "delete",
    "url": "/user/:id?t=:token",
    "title": "löschen",
    "name": "deleteUser",
    "group": "Benutzer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>löscht einen Benutzer</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID des Benutzers</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 204 No Content",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Users.java",
    "groupTitle": "Benutzer",
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/user/:id?t=:token",
    "title": "suchen",
    "name": "findUser",
    "group": "Benutzer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>sucht nach dem Benutzer mit der ID und gibt ihn falls vorhanden zurück</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID des Benutzers</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n{\n    \"id\": \"bc8fea1dc1ab01832e4bf166b4a83964685744c1\",\n    \"name\": \"test\",\n    \"passwordHash\": \"bla\",\n    \"originator\": false,\n    \"locale\": \"de\",\n    \"userGroups\": [\n        \"1d9920835958d3061252c02f9030dfada370a772\"\n    ]\n}",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Users.java",
    "groupTitle": "Benutzer",
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "/group?t=:token",
    "title": "erstellen",
    "name": "createGroup",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>erstellt eine neue Benutzergruppe</p>",
    "examples": [
      {
        "title": "Request:",
        "content": " {\n    \"descripion\": \"Benutzer können die Funktionen des SHC nutzen\",\n    \"systemGroup\": true,\n    \"permissions\": [\n        \"SWITCH_ELEMENTS\"\n    ],\n    \"id\": \"\",\n    \"name\": \"Test\"\n}",
        "type": "json"
      },
      {
        "title": "Response:",
        "content": "HTTP/1.1 201 Created\nLocation http://localhost:8081/group/97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "delete",
    "url": "/group/:id?t=:token",
    "title": "löschen",
    "name": "deleteGroup",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>löscht eine Benutzergruppe</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID der Benutzergruppe</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 204 No Content",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/group/:id?t=:token",
    "title": "suchen",
    "name": "findGroup",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>sucht nach der Benutzergruppe mit der ID und gibt ihn falls vorhanden zurück</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID der Benutzergruppe</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n{\n    \"descripion\": \"Administratoren können das SHC Verwalten\",\n    \"systemGroup\": true,\n    \"permissions\": [\n        \"MANAGE_KNOWN_DEVICES\",\n        \"MANAGE_SETTINGS\",\n        \"MANAGE_ROOMS\",\n        \"MANAGE_USERS\",\n        \"SWITCH_ELEMENTS\",\n        \"ENTER_ACP\",\n        \"MANAGE_ELEMENTS\"\n    ],\n    \"id\": \"1d9920835958d3061252c02f9030dfada370a772\",\n    \"name\": \"Administratoren\"\n}",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/group?t=:token",
    "title": "Liste aller Benutzergruppen",
    "name": "listGroups",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>gibt eine Liste mit allen Benutzergruppen zurück</p>",
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n[\n    {\n        \"descripion\": \"Benutzer können die Funktionen des SHC nutzen\",\n        \"systemGroup\": true,\n        \"permissions\": [\n            \"SWITCH_ELEMENTS\"\n        ],\n        \"id\": \"b16ff48f87d644f487e8f0e518fa3fe0c878c64c\",\n        \"name\": \"Benutzer\"\n    },\n    {\n        \"descripion\": \"Administratoren können das SHC Verwalten\",\n        \"systemGroup\": true,\n        \"permissions\": [\n            \"MANAGE_KNOWN_DEVICES\",\n            \"MANAGE_SETTINGS\",\n            \"MANAGE_ROOMS\",\n            \"MANAGE_USERS\",\n            \"SWITCH_ELEMENTS\",\n            \"ENTER_ACP\",\n            \"MANAGE_ELEMENTS\"\n        ],\n        \"id\": \"1d9920835958d3061252c02f9030dfada370a772\",\n        \"name\": \"Administratoren\"\n    }\n]",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/group/permissions?t=:token",
    "title": "Liste aller Berechtigungen",
    "name": "listPermissions",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>gibt eine Liste mit allen Berechtigungen zurück</p>",
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n[\n    \"ENTER_ACP\",\n    \"MANAGE_USERS\",\n    \"MANAGE_KNOWN_DEVICES\",\n    \"MANAGE_SETTINGS\",\n    \"MANAGE_ELEMENTS\",\n    \"MANAGE_ROOMS\",\n    \"SWITCH_ELEMENTS\"\n]",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "put",
    "url": "/group?t=:token",
    "title": "bearbeiten",
    "name": "updateGroup",
    "group": "Benutzergruppen",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>bearbeitet eine bestehende Benutzergruppe</p>",
    "examples": [
      {
        "title": "Request:",
        "content": " {\n    \"descripion\": \"Benutzer können die Funktionen des SHC nutzen\",\n    \"systemGroup\": true,\n    \"permissions\": [\n        \"SWITCH_ELEMENTS\",\n        \"ENTER_ACP\"\n    ],\n    \"id\": \"97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c\",\n    \"name\": \"Test123\"\n}",
        "type": "json"
      },
      {
        "title": "Response:",
        "content": "HTTP/1.1 201 Created\nLocation http://localhost:8081/group/97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/UserGroups.java",
    "groupTitle": "Benutzergruppen",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/user?t=:token",
    "title": "Liste aller Benutzer",
    "name": "listUsers",
    "group": "Benutzer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>gibt eine Liste mit allen Benutzern zurück</p>",
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n[\n    {\n        \"id\": \"bc8fea1dc1ab01832e4bf166b4a83964685744c1\",\n        \"name\": \"test\",\n        \"passwordHash\": \"bla\",\n        \"originator\": false,\n        \"locale\": \"de\",\n        \"userGroups\": [\n            \"1d9920835958d3061252c02f9030dfada370a772\"\n        ]\n    },\n    {\n        \"id\": \"74ceb62861983f7170818eb07bf2001c7c9a9cdd\",\n        \"name\": \"admin\",\n        \"passwordHash\": \"admin\",\n        \"originator\": true,\n        \"locale\": \"de\",\n        \"userGroups\": [\n            \"1d9920835958d3061252c02f9030dfada370a772\"\n        ]\n    }\n]",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Users.java",
    "groupTitle": "Benutzer",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "put",
    "url": "/user?t=:token",
    "title": "bearbeiten",
    "name": "updateUser",
    "group": "Benutzer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "MANAGE_USERS"
      }
    ],
    "description": "<p>bearbeitet einen bestehenden Benutzer</p>",
    "examples": [
      {
        "title": "Request:",
        "content": " {\n    \"id\": \"820694213e14dc9a3932953aee832da29c2f1e43\",\n    \"name\": \"test123\",\n    \"passwordHash\": \"bla\",\n    \"originator\": true,\n    \"locale\": \"de\",\n    \"userGroups\": [\n        \"1d9920835958d3061252c02f9030dfada370a772\"\n    ]\n}",
        "type": "json"
      },
      {
        "title": "Response:",
        "content": "HTTP/1.1 201 Created\nLocation http://localhost:8081/user/820694213e14dc9a3932953aee832da29c2f1e43",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Users.java",
    "groupTitle": "Benutzer",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "put",
    "url": "/info?t=:token",
    "title": "Datenbak speichern",
    "name": "dumpDatabase",
    "group": "Info",
    "version": "1.0.0",
    "permission": [
      {
        "name": "ENTER_ACP"
      }
    ],
    "description": "<p>fordert den Datenbankserver auf die Datenbank zu speichern</p>",
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
    "examples": [
      {
        "title": "Response:",
        "content": "HTTP/1.1 200 OK\n{\n   \"state\": \"Background saving started\"\n}",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/info?t=:token",
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
    "examples": [
      {
        "title": "Response:",
        "content": " HTTP/1.1 200 OK\n {\n    \"shc\": {\n        \"version\": \"0.1.0\",\n        \"apiLevel\": 1,\n        \"libarys\": [\n            \"Google Gson\",\n            \"Google Guava\",\n            \"Jedis\",\n            \"SunriseSunsetCalculator\",\n            \"Grizzly\",\n            \"Jersey\"\n        ]\n    },\n    \"system\": {\n        \"cpuLoad\": 0.14192629846795876,\n        \"freeMemory\": 3788398592,\n        \"totalMemory\": 16770277376\n    },\n    \"jvm\": {\n        \"version\": \"1.8.0_101\",\n        \"availableProcessors\": 8,\n        \"cpuLoad\": 0.000057742121130185896,\n        \"maxMemory\": 3728736256,\n        \"freeMemory\": 416402800,\n        \"totalMemory\": 870318080,\n        \"runningThreads\": 28\n    },\n    \"db\": {\n        \"version\": \"3.2.1\",\n        \"mode\": \"standalone\",\n        \"uptime\": \"11780\",\n        \"configFile\": \"/etc/redis/redis.conf\",\n        \"usedMemory\": \"815552\",\n        \"usedMemoryPeak\": \"857328\",\n        \"lastSaveTime\": \"1477116824\",\n        \"lastSaveState\": \"ok\",\n        \"totalBytesInput\": \"19820\",\n        \"totalBytesOutput\": \"126002\"\n    }\n}",
        "type": "json"
      }
    ],
    "filename": "SHC_Application_Server/src/main/java/de/rpi_controlcenter/AppServer/Controller/REST/Application/Handler/Data/Info.java",
    "groupTitle": "Info",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>Autorisierungstoken</p>"
          }
        ]
      }
    },
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
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"code\": 10103,\n  \"message\": \"ungültiger Token\"\n}",
          "type": "json"
        }
      ]
    }
  }
] });

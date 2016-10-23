package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Data;

import com.google.gson.JsonParseException;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.REST.PermissionCheck;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.AppServer.Model.Editor.UserGroupsEditor;
import de.rpi_controlcenter.Util.Json.JsonUtil;
import de.rpi_controlcenter.Util.Validation.ValidationSupport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * Benutzergruppen Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
@Path("/group")
public class UserGroups {

    /**
     * @api {get} /group/permissions?t=:token Liste aller Berechtigungen
     * @apiName listPermissions
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription gibt eine Liste mit allen Berechtigungen zurück
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *     [
        "ENTER_ACP",
        "MANAGE_USERS",
        "MANAGE_KNOWN_DEVICES",
        "MANAGE_SETTINGS",
        "MANAGE_ELEMENTS",
        "MANAGE_ROOMS",
        "SWITCH_ELEMENTS"
    ]
     */
    @GET
    @Path("/permissions")
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response listPermissions() {

        String json = AppServer.getInstance().getGson().toJson(Permission.values());;
        return Response.ok().entity(json).build();
    }

    /**
     * @api {get} /group?t=:token Liste aller Benutzergruppen
     * @apiName listGroups
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription gibt eine Liste mit allen Benutzergruppen zurück
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *     [
        {
            "descripion": "Benutzer können die Funktionen des SHC nutzen",
            "systemGroup": true,
            "permissions": [
                "SWITCH_ELEMENTS"
            ],
            "id": "b16ff48f87d644f487e8f0e518fa3fe0c878c64c",
            "name": "Benutzer"
        },
        {
            "descripion": "Administratoren können das SHC Verwalten",
            "systemGroup": true,
            "permissions": [
                "MANAGE_KNOWN_DEVICES",
                "MANAGE_SETTINGS",
                "MANAGE_ROOMS",
                "MANAGE_USERS",
                "SWITCH_ELEMENTS",
                "ENTER_ACP",
                "MANAGE_ELEMENTS"
            ],
            "id": "1d9920835958d3061252c02f9030dfada370a772",
            "name": "Administratoren"
        }
    ]
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response listGroups() {

        UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
        Lock lock = userGroupsEditor.readLock();
        lock.lock();

        String json = "";
        try {

            json = AppServer.getInstance().getGson().toJson(userGroupsEditor.getData());
        } finally {

            lock.unlock();
        }
        return Response.ok().entity(json).build();
    }

    /**
     * @api {get} /group/:id?t=:token suchen
     * @apiName findGroup
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription sucht nach der Benutzergruppe mit der ID und gibt ihn falls vorhanden zurück
     *
     * @apiParam {String} id ID der Benutzergruppe
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *     {
        "descripion": "Administratoren können das SHC Verwalten",
        "systemGroup": true,
        "permissions": [
            "MANAGE_KNOWN_DEVICES",
            "MANAGE_SETTINGS",
            "MANAGE_ROOMS",
            "MANAGE_USERS",
            "SWITCH_ELEMENTS",
            "ENTER_ACP",
            "MANAGE_ELEMENTS"
        ],
        "id": "1d9920835958d3061252c02f9030dfada370a772",
        "name": "Administratoren"
    }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response findGroup(@PathParam("id") final String id) {

        UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
        Lock lock = userGroupsEditor.readLock();
        lock.lock();

        String json = "";
        try {

            Optional<UserGroup> userGroupOptional = userGroupsEditor.getById(id);
            if(userGroupOptional.isPresent()) {

                json = AppServer.getInstance().getGson().toJson(userGroupOptional.get());
                return Response.ok().entity(json).build();
            }
        } finally {

            lock.unlock();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * @api {post} /group?t=:token erstellen
     * @apiName createGroup
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription erstellt eine neue Benutzergruppe
     *
     * @apiUse Authentication
     *
     * @apiExample Request:
     *      {
        "descripion": "Benutzer können die Funktionen des SHC nutzen",
        "systemGroup": true,
        "permissions": [
            "SWITCH_ELEMENTS"
        ],
        "id": "",
        "name": "Test"
    }
     * @apiExample Response:
     *     HTTP/1.1 201 Created
     *     Location http://localhost:8081/group/97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response createGroup(String jsonRequest) {

        try {

            UserGroup userGroup = AppServer.getInstance().getGson().fromJson(jsonRequest, UserGroup.class);
            userGroup.setId(UserGroupsEditor.createId());

            ValidationSupport validationSupport = new ValidationSupport();
            if(validationSupport.validate(userGroup)) {

                UserGroup newUserGroup = new UserGroup(userGroup.getId(), userGroup.getName());
                newUserGroup.setDescripion(userGroup.getDescripion());
                newUserGroup.clearPermissions();
                for(Permission permission : userGroup.getPermissions()) {

                    newUserGroup.addPermission(permission);
                }
                newUserGroup.setName(userGroup.getName());

                UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
                Lock lock = userGroupsEditor.writeLock();
                lock.lock();
                try {

                    if(!userGroupsEditor.getByName(newUserGroup.getName()).isPresent()) {

                        userGroupsEditor.getData().add(newUserGroup);
                        return Response.created(URI.create("/group/" + newUserGroup.getId())).build();
                    } else {

                        return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10502, "die Benutzergruppe existiert bereits")).build();
                    }
                } finally {

                    lock.unlock();
                }
            } else {

                return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10501, validationSupport.getValidationErrors())).build();
            }
        } catch (JsonParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10500, e.getLocalizedMessage())).build();
        }
    }

    /**
     * @api {put} /group?t=:token bearbeiten
     * @apiName updateGroup
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription bearbeitet eine bestehende Benutzergruppe
     *
     * @apiUse Authentication
     *
     * @apiExample Request:
     *      {
        "descripion": "Benutzer können die Funktionen des SHC nutzen",
        "systemGroup": true,
        "permissions": [
            "SWITCH_ELEMENTS",
            "ENTER_ACP"
        ],
        "id": "97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c",
        "name": "Test123"
    }
     * @apiExample Response:
     *     HTTP/1.1 201 Created
     *     Location http://localhost:8081/group/97e2eb81ce8f2b4162a89de33e4ebfc01ad3da8c
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response updateGroup(String jsonRequest) {

        try {

            UserGroup userGroup = AppServer.getInstance().getGson().fromJson(jsonRequest, UserGroup.class);

            ValidationSupport validationSupport = new ValidationSupport();
            if(validationSupport.validate(userGroup)) {


                UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
                Lock lock = userGroupsEditor.writeLock();
                lock.lock();
                try {

                    Optional<UserGroup> userGroupOptional = userGroupsEditor.getById(userGroup.getId());
                    if(userGroupOptional.isPresent()) {

                        UserGroup knownUserGroup = userGroupOptional.get();

                        //prüfen ob der Gruppenname nschon vergene ist
                        if(!userGroup.getName().equalsIgnoreCase(knownUserGroup.getName())) {

                            if(userGroupsEditor.getByName(userGroup.getName()).isPresent()) {

                                return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10504, "Der Name ist bereits vergeben")).build();
                            }
                        }

                        knownUserGroup.setDescripion(userGroup.getDescripion());
                        knownUserGroup.clearPermissions();
                        for(Permission permission : userGroup.getPermissions()) {

                            knownUserGroup.addPermission(permission);
                        }
                        knownUserGroup.setName(userGroup.getName());
                        return Response.created(URI.create("/group/" + knownUserGroup.getId())).build();
                    } else {

                        return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10503, "Der Datensatz konnte nicht gefunden werden")).build();
                    }
                } finally {

                    lock.unlock();
                }
            } else {

                return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10501, validationSupport.getValidationErrors())).build();
            }
        } catch (JsonParseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10500, e.getLocalizedMessage())).build();
        }
    }

    /**
     * @api {delete} /group/:id?t=:token löschen
     * @apiName deleteGroup
     * @apiGroup Benutzergruppen
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription löscht eine Benutzergruppe
     *
     * @apiParam {String} id ID der Benutzergruppe
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 204 No Content
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response deleteGroup(@PathParam("id") final String id) {

        UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
        Lock lock = userGroupsEditor.writeLock();
        lock.lock();

        try {

            Optional<UserGroup> userGroupOptional = userGroupsEditor.getById(id);
            if(userGroupOptional.isPresent()) {

                //prüfen ob Hauptbenutzer
                if(!userGroupOptional.get().isSystemGroup()) {

                    if(userGroupsEditor.getData().remove(userGroupOptional.get())) {

                        return Response.noContent().build();
                    }
                } else {

                    return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10505, "Eine Systemgruppe kann nicht gelöscht werden")).build();
                }
            }
        } finally {

            lock.unlock();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

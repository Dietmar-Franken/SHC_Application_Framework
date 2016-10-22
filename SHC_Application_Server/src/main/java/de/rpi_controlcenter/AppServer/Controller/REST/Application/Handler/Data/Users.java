package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Data;

import com.google.gson.JsonParseException;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.REST.PermissionCheck;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Editor.UsersEditor;
import de.rpi_controlcenter.Util.Json.JsonUtil;
import de.rpi_controlcenter.Util.Validation.ValidationSupport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * Benutzer Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
@Path("/user")
public class Users {

    /**
     * @api {get} /user?t=:token Liste aller Benutzer
     * @apiName listUsers
     * @apiGroup Benutzer
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription gibt eine Liste mit allen Benutzern zurück
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *     [
        {
            "id": "bc8fea1dc1ab01832e4bf166b4a83964685744c1",
            "name": "test",
            "passwordHash": "bla",
            "originator": false,
            "locale": "de",
            "userGroups": [
                "1d9920835958d3061252c02f9030dfada370a772"
            ]
        },
        {
            "id": "74ceb62861983f7170818eb07bf2001c7c9a9cdd",
            "name": "admin",
            "passwordHash": "admin",
            "originator": true,
            "locale": "de",
            "userGroups": [
                "1d9920835958d3061252c02f9030dfada370a772"
            ]
        }
    ]
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response listUsers() {

        UsersEditor usersEditor = AppServer.getInstance().getUsers();
        Lock lock = usersEditor.readLock();
        lock.lock();

        String json = "";
        try {

            json = AppServer.getInstance().getGson().toJson(usersEditor.getData());
        } finally {

            lock.unlock();
        }
        return Response.ok().entity(json).build();
    }

    /**
     * @api {get} /user/:id?t=:token suchen
     * @apiName findUser
     * @apiGroup Benutzer
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription sucht nach dem Benutzer mit der ID und gibt ihn falls vorhanden zurück
     *
     * @apiParam {String} id ID des Benutzers
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *     {
        "id": "bc8fea1dc1ab01832e4bf166b4a83964685744c1",
        "name": "test",
        "passwordHash": "bla",
        "originator": false,
        "locale": "de",
        "userGroups": [
            "1d9920835958d3061252c02f9030dfada370a772"
        ]
    }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response findUser(@PathParam("id") final String id) {

        UsersEditor usersEditor = AppServer.getInstance().getUsers();
        Lock lock = usersEditor.readLock();
        lock.lock();

        String json = "";
        try {

            Optional<de.rpi_controlcenter.AppServer.Model.Data.User.User> userOptional = usersEditor.getById(id);
            if(userOptional.isPresent()) {

                json = AppServer.getInstance().getGson().toJson(userOptional.get());
                return Response.ok().entity(json).build();
            }
        } finally {

            lock.unlock();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * @api {post} /user?t=:token erstellen
     * @apiName createUser
     * @apiGroup Benutzer
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription erstellt einen neuen Benutzer
     *
     * @apiUse Authentication
     *
     * @apiExample Request:
     *      {
        "id": "",
        "name": "test",
        "passwordHash": "bla",
        "originator": true,
        "locale": "de",
        "userGroups": [
            "1d9920835958d3061252c02f9030dfada370a772"
        ]
    }
     * @apiExample Response:
     *     HTTP/1.1 201 Created
     *     Location http://localhost:8081/user/820694213e14dc9a3932953aee832da29c2f1e43
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response createUser(String jsonRequest) {

        try {

            User user = AppServer.getInstance().getGson().fromJson(jsonRequest, User.class);
            user.setId(UsersEditor.createId());

            ValidationSupport validationSupport = new ValidationSupport();
            if(validationSupport.validate(user)) {

                User newUser = new User(user.getId(), user.getName());
                newUser.setPassword(user.getPassword());
                newUser.setLocale(user.getLocale());
                newUser.getUserGroups().addAll(user.getUserGroups());

                UsersEditor usersEditor = AppServer.getInstance().getUsers();
                Lock lock = usersEditor.writeLock();
                lock.lock();
                try {

                    if(!usersEditor.getByName(newUser.getName()).isPresent()) {

                        usersEditor.getData().add(newUser);
                        return Response.created(URI.create("/user/" + newUser.getId())).build();
                    } else {

                        return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10502, "der Benutzer existiert bereits")).build();
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
     * @api {put} /user?t=:token bearbeiten
     * @apiName updateUser
     * @apiGroup Benutzer
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription bearbeitet einen bestehenden Benutzer
     *
     * @apiUse Authentication
     *
     * @apiExample Request:
     *      {
        "id": "820694213e14dc9a3932953aee832da29c2f1e43",
        "name": "test123",
        "passwordHash": "bla",
        "originator": true,
        "locale": "de",
        "userGroups": [
            "1d9920835958d3061252c02f9030dfada370a772"
        ]
    }
     * @apiExample Response:
     *     HTTP/1.1 201 Created
     *     Location http://localhost:8081/user/820694213e14dc9a3932953aee832da29c2f1e43
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.MANAGE_USERS)
    public Response updateUser(String jsonRequest) {

        try {

            User user = AppServer.getInstance().getGson().fromJson(jsonRequest, User.class);

            ValidationSupport validationSupport = new ValidationSupport();
            if(validationSupport.validate(user)) {

                UsersEditor usersEditor = AppServer.getInstance().getUsers();
                Lock lock = usersEditor.writeLock();
                lock.lock();
                try {

                    Optional<User> userOptional = usersEditor.getById(user.getId());
                    if(userOptional.isPresent()) {

                        User knownUser = userOptional.get();

                        //prüfen ob der Benutzername schon vergeben ist
                        if(!user.getName().equalsIgnoreCase(knownUser.getName())) {

                            if(usersEditor.getByName(user.getName()).isPresent()) {

                                return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10504, "Der Name ist bereits vergeben")).build();
                            }
                        }

                        knownUser.setName(user.getName());
                        knownUser.setPassword(user.getPassword());
                        knownUser.setLocale(user.getLocale());
                        knownUser.getUserGroups().clear();
                        knownUser.getUserGroups().addAll(user.getUserGroups());
                        return Response.created(URI.create("/user/" + knownUser.getId())).build();
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
     * @api {delete} /user/:id?t=:token löschen
     * @apiName deleteUser
     * @apiGroup Benutzer
     * @apiVersion 1.0.0
     * @apiPermission MANAGE_USERS
     * @apiDescription löscht einen Benutzer
     *
     * @apiParam {String} id ID des Benutzers
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
    public Response deleteUser(@PathParam("id") final String id) {

        UsersEditor usersEditor = AppServer.getInstance().getUsers();
        Lock lock = usersEditor.writeLock();
        lock.lock();

        try {

            Optional<User> userOptional = usersEditor.getById(id);
            if(userOptional.isPresent()) {

                //prüfen ob Hauptbenutzer
                if(!userOptional.get().isOriginator()) {

                    if(usersEditor.getData().remove(userOptional.get())) {

                        return Response.noContent().build();
                    }
                } else {

                    return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.errorMessage(10505, "Ein Systembenutzer kann nicht gelöscht werden")).build();
                }
            }
        } finally {

            lock.unlock();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

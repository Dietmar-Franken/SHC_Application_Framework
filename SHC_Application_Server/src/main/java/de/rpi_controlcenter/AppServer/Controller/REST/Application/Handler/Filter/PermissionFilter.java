package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Filter;

import com.google.gson.JsonObject;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.REST.PermissionCheck;
import de.rpi_controlcenter.AppServer.Model.Data.Session.Session;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * Prüft die Benutzer Berechtigungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
@Provider
public class PermissionFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        //prüfen ob die Rechteprüfung aktiv ist (per Annotation)
        final Method method = resourceInfo.getResourceMethod();
        if(method.isAnnotationPresent(PermissionCheck.class)) {

            Response response = null;
            PermissionCheck permissionCheck = method.getAnnotation(PermissionCheck.class);
            Permission requiredPermission = permissionCheck.value();
            MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();
            if(queryParameters.containsKey("t")) {

                //Token prüfen
                String token = queryParameters.getFirst("t");
                if(token.matches("^[a-fA-F0-9]{40}$")) {

                    //Token i.O. -> Sitzung laden und Benutzer ID der Sitzung ermitteln
                    Optional<String> userIdOptional = Optional.empty();
                    Lock lock = AppServer.getInstance().getSessions().readLock();
                    lock.lock();
                    try {

                        Optional<Session> sessionOptional = AppServer.getInstance().getSessions().getSessionById(token);
                        if(sessionOptional.isPresent()) {

                            userIdOptional = sessionOptional.get().getUser();
                            if(userIdOptional.isPresent()) {

                                //letzten zugriff aktualisieren
                                sessionOptional.get().updateExpire();
                            } else {

                                //nicht Autorisiert da kein User angemeldet
                                JsonObject jo = new JsonObject();
                                jo.addProperty("code", 10104);
                                jo.addProperty("message", "kein Benutzer angemeldet");
                                response = Response.status(Response.Status.FORBIDDEN).entity(jo.toString()).build();
                            }
                        } else {

                            //nicht Authentifiziert//ungültiger Token = Bad Request
                            JsonObject jo = new JsonObject();
                            jo.addProperty("code", 10103);
                            jo.addProperty("message", "ungültiger Token");
                            response = Response.status(Response.Status.UNAUTHORIZED).entity(jo.toString()).build();
                        }
                    } finally {
                         lock.unlock();
                    }

                    //Benutzer laden und Berechtigungen prüfen
                    Lock lock1 = AppServer.getInstance().getUsers().readLock();
                    lock1.lock();
                    try {

                        Optional<User> userOptional = AppServer.getInstance().getUsers().getById(userIdOptional.get());
                        if(userOptional.isPresent()) {

                            if(!userOptional.get().checkPermission(requiredPermission)) {

                                //nicht Autorisiert da User die Rechte nicht besesitzt
                                JsonObject jo = new JsonObject();
                                jo.addProperty("code", 10105);
                                jo.addProperty("message", "der Benutzer besitzt nicht die nötigen Rechte");
                                response = Response.status(Response.Status.FORBIDDEN).entity(jo.toString()).build();
                            }
                        } else {

                            //nicht Autorisiert da kein User angemeldet
                            JsonObject jo = new JsonObject();
                            jo.addProperty("code", 10104);
                            jo.addProperty("message", "kein Benutzer angemeldet");
                            response = Response.status(Response.Status.FORBIDDEN).entity(jo.toString()).build();
                        }
                    } finally {

                        lock1.unlock();
                    }
                } else {

                    //ungültiger Token = Bad Request
                    JsonObject jo = new JsonObject();
                    jo.addProperty("code", 10103);
                    jo.addProperty("message", "ungültiger Token");
                    response = Response.status(Response.Status.BAD_REQUEST).entity(jo.toString()).build();
                }
            } else {

                //kein Token = Bad Request
                JsonObject jo = new JsonObject();
                jo.addProperty("code", 10102);
                jo.addProperty("message", "kein Token");
                response = Response.status(Response.Status.BAD_REQUEST).entity(jo.toString()).build();
            }
            if(response != null) {
                requestContext.abortWith(response);
            }
        }
    }
}

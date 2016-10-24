package de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler.Data;

import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.REST.PermissionCheck;
import de.rpi_controlcenter.AppServer.Model.Data.Icon.Icon;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Editor.IconEditor;
import de.rpi_controlcenter.Util.Image.ImageUtil;
import de.rpi_controlcenter.Util.Json.JsonUtil;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * Icon Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
@Path("/icon")
public class Icons {

    /**
     * @api {get} /icon Liste aller Icons
     * @apiName listIcons
     * @apiGroup Icon
     * @apiVersion 1.0.0
     * @apiDescription gibt eine Liste mit allen Icons zurück
     *
     * @apiExample Response:
     *      HTTP/1.1 200 OK
     *      [
        {
            "availableSize": [
                48,
                96,
                72
            ],
            "baseFileName": "test_1",
            "basePath": "/media/Daten/Projekte/Java/SHC_Application_Framework/Icons/test_1",
            "id": "e1426ac3841b2ac0277b5d70147cf168d1da35c5",
            "name": "test_1"
        },
        {
            "availableSize": [
                48,
                96,
                72
            ],
            "baseFileName": "test_2",
            "basePath": "/media/Daten/Projekte/Java/SHC_Application_Framework/Icons/test_2",
            "id": "c8fe01a4a927bcc428b3e24bbb3b2829aae715e8",
            "name": "test_2"
        }
    ]
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listIcons() {

        IconEditor iconEditor = AppServer.getInstance().getIcons();
        Lock lock = iconEditor.readLock();
        lock.lock();

        String json = "";
        try {

            json = AppServer.getInstance().getGson().toJson(iconEditor.getData());
        } finally {

            lock.unlock();
        }
        return Response.ok().entity(json).build();
    }

    /**
     * @api {put} /icon einlesen
     * @apiName updateIcons
     * @apiGroup Icon
     * @apiVersion 1.0.0
     * @apiDescription fordert den Server auf die Icons neu ein zu lesen
     *
     * @apiExample Response:
     *     HTTP/1.1 200 OK
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIcons() {

        IconEditor iconEditor = AppServer.getInstance().getIcons();
        Lock lock = iconEditor.readLock();
        lock.lock();

        try {

            if(iconEditor.updateIconsFromFileSystem()) {

                return Response.ok().build();
            }
            return Response.serverError().build();
        } finally {

            lock.unlock();
        }
    }

    /**
     * @api {get} /icon/:iconName/:size download
     * @apiName loadIcon
     * @apiGroup Icon
     * @apiVersion 1.0.0
     * @apiDescription download eines Icons
     *
     * @apiParam {String} iconName Name des Icons
     * @apiParam {Number} size Größe des Icons (wenn nicht vorhanden wird es scaliert)
     *
     * @apiExample Response:
     *      HTTP/1.1 200 OK
     *      ~~Binärdaten~~
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 204 No Content
     */
    @GET
    @Path("/{name}/{size}")
    @Produces("image/png")
    public Response loadIcon(
            @PathParam("name") final String name,
            @PathParam("size") final int size
    ) {

        IconEditor iconEditor = AppServer.getInstance().getIcons();
        Lock lock = iconEditor.readLock();
        lock.lock();

        try {

            //Icon suchen
            Optional<Icon> iconOptional = iconEditor.getByName(name);
            if(iconOptional.isPresent()) {

                //angeforderte größe prüfen
                Icon icon = iconOptional.get();
                if(icon.getAvailableSize().contains(size)) {

                    //größe vorhanden
                    StreamingOutput fileStream = new StreamingOutput() {

                        @Override
                        public void write(OutputStream output) throws IOException, WebApplicationException {

                            try {

                                byte[] data = Files.readAllBytes(Paths.get(icon.getBasePath(), size + ".png"));
                                output.write(data);
                                output.flush();
                            } catch (Exception e) {

                                throw new WebApplicationException("File Not Found!");
                            }
                        }
                    };
                    return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                            .header("content-disposition","attachment; filename = " + icon.getBaseFileName() + "_" + size + ".png")
                            .build();
                } else {

                    //skalieren
                    int maxSize = icon.getAvailableSize().stream().max(Integer::compare).get();
                    StreamingOutput fileStream = new StreamingOutput() {

                        @Override
                        public void write(OutputStream output) throws IOException, WebApplicationException {

                            try {

                                //Bild skalieren
                                BufferedImage resizedCopy = ImageUtil.createResizedCopy(
                                        ImageIO.read(Paths.get(icon.getBasePath(), maxSize + ".png").toFile()),
                                        size,
                                        size
                                );

                                //in Byte Array umwandeln
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageIO.write(resizedCopy, "png", baos);
                                baos.flush();
                                byte[] data = baos.toByteArray();
                                baos.close();

                                //in die Ausgabe schreiben
                                output.write(data);
                                output.flush();
                            } catch (Exception e) {

                                throw new WebApplicationException("File Not Found!");
                            }
                        }
                    };
                    return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                            .header("content-disposition","attachment; filename = " + icon.getBaseFileName() + "_" + size + ".png")
                            .build();
                }
            }
            //Icon nicht gefunden
            return Response.noContent().build();
        } finally {

            lock.unlock();
        }
    }

    /**
     * @api {delete} /icon/:id?t=:token löschen
     * @apiName deleteIcon
     * @apiGroup Icon
     * @apiVersion 1.0.0
     * @apiPermission ENTER_ACP
     * @apiDescription löscht ein Icon
     *
     * @apiParam {String} id ID des Icons
     *
     * @apiUse Authentication
     *
     * @apiExample Response:
     *     HTTP/1.1 204 No Content
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionCheck(Permission.ENTER_ACP)
    public Response deleteIcon(@PathParam("id") final String id) {

        IconEditor iconEditor = AppServer.getInstance().getIcons();
        Lock lock = iconEditor.writeLock();
        lock.lock();

        try {

            Optional<Icon> iconOptional = iconEditor.getById(id);
            if(iconOptional.isPresent()) {

                if(iconEditor.getData().remove(iconOptional.get())) {

                    Icon icon = iconOptional.get();
                    for(int size : icon.getAvailableSize()) {

                        Files.deleteIfExists(Paths.get(icon.getBasePath(), size + ".png"));
                    }
                    Files.deleteIfExists(Paths.get(icon.getBasePath()));
                    return Response.noContent().build();
                }
            }
        } catch (IOException e) {

            return Response.serverError().build();
        } finally {

            lock.unlock();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    //TODO Upload Funktion implementieren
/*    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadIcon(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail
    ) {

        System.out.println("HALLO " + fileDetail.getFileName());
        return Response.ok().build();
    }

    @GET
    @Path("/upload")
    @Produces("text/html")
    public Response getUploadHtml() {

        String html =
                "<html>\n" +
                "<body>\n" +
                "\t<h1>Upload Icon File</h1>\n" +
                "\n" +
                "\t<form action=\"upload\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "\n" +
                "\t   <p>\n" +
                "\t\tSelect a file : <input type=\"file\" name=\"file\" size=\"50\" />\n" +
                "\t   </p>\n" +
                "\n" +
                "\t   <input type=\"submit\" value=\"Upload It\" />\n" +
                "\t</form>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return Response.ok().entity(html).build();
    }
*/
}

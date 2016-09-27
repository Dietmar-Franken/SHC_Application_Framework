package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Icon.Icon;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Verwaltung der Icons
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class IconEditor extends AbstractDatabaseEditor<Icon> {

    private static Logger logger = LoggerUtil.getLogger(IconEditor.class);

    private static final String DATABASE_KEY = "shc:icon:icons";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        List<Icon> data = getData();
        List<String> iconList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String iconJson : iconList) {

            data.add(gson.fromJson(iconJson, Icon.class));
        }
    }

    /**
     * gibt das Icon mit dem übergebenen Basisnamen zurück
     *
     * @param baseName Basisname
     * @return Icon
     */
    public Optional<Icon> getIconByBaseName(String baseName) {

        return getData().stream().filter(icon -> icon.getBaseFileName().equalsIgnoreCase(baseName)).findFirst();
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Pipeline pipeline = db.pipelined();
        Gson gson = AppServer.getInstance().getGson();
        List<Icon> data = getData();

        pipeline.del(DATABASE_KEY);
        for(Icon icon : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(icon));
        }
        pipeline.sync();
    }

    public boolean updateIconsFromFileSystem() {

        boolean success = true;
        Path iconDirectory = Paths.get("Icons");
        if(Files.exists(iconDirectory)) {

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(iconDirectory)) {

                for(Path baseName : stream) {

                    if(Files.isDirectory(baseName)) {

                        //prüfen ob das Icon schon bekannt ist
                        String baseNameStr = baseName.toString();
                        Optional<Icon> iconOptional = getIconByBaseName(baseNameStr);
                        Icon icon = null;
                        if(iconOptional.isPresent()) {

                            //Bereits bekannt
                            icon = iconOptional.get();
                        } else {

                            //neues Icon
                            icon = new Icon(createId(), baseNameStr);
                            icon.setBaseFileName(baseNameStr);
                            icon.setBasePath(iconDirectory.resolve(baseName).toAbsolutePath().toString());
                            getData().add(icon);
                        }

                        //verfügbare größen auslesen
                        try (DirectoryStream<Path> stream1 = Files.newDirectoryStream(iconDirectory.resolve(baseName), "*.png")) {

                            for(Path iconFile : stream1) {

                                String fileName = iconFile.getFileName().toString().toLowerCase();
                                int size = 0;
                                size = Integer.parseInt(fileName.replace(".png", ""));
                                if(size > 0) {

                                    icon.getAvailableSize().add(size);
                                } else {

                                    success = false;
                                    logger.warning("Ungültige Icon größe");
                                }
                            }
                        } catch (IOException e) {

                            success = false;
                            logger.warning("Einlesen der Icon Dateien fehlgeschlagen");
                        }
                    }
                }
            } catch (IOException e) {

                success = false;
                logger.warning("Einlesen der Icon Ordner fehlgeschlagen");
            }
        }
        return success;
    }
}

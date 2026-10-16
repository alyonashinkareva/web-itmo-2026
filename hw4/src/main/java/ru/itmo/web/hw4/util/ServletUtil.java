package ru.itmo.web.hw4.util;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServletUtil {

    public static File getFile(String uri, ServletContext context){
        String appRoot = "src/main/webapp";
        File file = Paths.get(appRoot, uri).normalize().toFile();
        if (!file.isFile()) {
            file = new File(context.getRealPath(uri));
        }
        Path path = file.toPath();
        if (!path.startsWith(appRoot) && !path.startsWith(context.getRealPath(""))){
            return null;
        }
        return file;
    }
}

package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String uri = request.getRequestURI();
        final String[] uriArray = uri.split("\\+");
        File source = getSourceDirectory();
        final File[] files = new File[uriArray.length];
        for (int i = 0; i < uriArray.length; i++){
            String root = source != null ? source.getPath() : null;
            File file = root != null ? new File(root, uriArray[i]) : null;
            if (file != null && isUnLegalPath(root, file)){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (file == null || !file.isFile()) {
                root = getServletContext().getRealPath("/static");
                if (root == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                file = new File(root, uriArray[i]);
                if (isUnLegalPath(root, file)) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
            if (!file.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            files[i] = file;
        }
        response.setContentType(getServletContext().getMimeType(files[0].getName()));
        try (OutputStream outputStream = response.getOutputStream()) {
            for (File file : files) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }
    private File getSourceDirectory() {
        String path = getServletContext().getRealPath("/static");
        if (path == null){
            return null;
        }
        File dir = new File(path);
        while (dir != null) {
            File source = new File(dir, "src/main/webapp/static");
            if (source.isDirectory()) return source;
            dir = dir.getParentFile();
        }
        return null;
    }

    private boolean isUnLegalPath(final String root, File file) throws IOException {
        return !file.getCanonicalFile().toPath().startsWith(new File(root).getCanonicalFile().toPath());
    }
}

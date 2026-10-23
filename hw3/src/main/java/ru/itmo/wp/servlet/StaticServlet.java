package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private File getFileIfInDir(File directory, String uri) throws IOException {
        File canonicalDirectory = directory.getCanonicalFile();
        File file = new File(canonicalDirectory, uri).getCanonicalFile();
        if (file.isFile() && file.toPath().startsWith(canonicalDirectory.toPath())) {
            return file;
        }
        return null;
    }

    private File getFile(String uri) throws IOException {
        File file = getFileIfInDir(new File("src/main/webapp/static"), uri);

        if (file == null) {
            String realPath = getServletContext().getRealPath("/static");
            if (realPath != null) {
                file = getFileIfInDir(new File(realPath), uri);
            }
        }
        return file;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] uriSplit = uri.split("\\+");

        File[] files = new File[uriSplit.length];

        for (int i = 0; i < files.length; ++i) {
            files[i] = getFile(uriSplit[i]);
            if (files[i] == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        response.setContentType(getServletContext().getMimeType(files[0].getName()));
        try (OutputStream outputStream = response.getOutputStream()) {
            for (int i = 0; i < files.length; ++i) {
                Files.copy(files[i].toPath(), outputStream);
            }
        }
    }
}

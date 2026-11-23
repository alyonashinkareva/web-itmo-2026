package ru.itmo.wp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

public class StaticServlet extends HttpServlet {
    private Set<String> WHITELIST;

    @Override
    public void init() throws ServletException {
        super.init();
        WHITELIST = Set.of(
                Objects.requireNonNull(getSourceRoot()).getAbsolutePath(),
                getServletContext().getRealPath("/static")
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        String[] parts = uri.split("\\+");
        File[] files = new File[parts.length];

        for (int i = 0; i < files.length; i++) {
            if (parts[i].isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            files[i] = findFile(parts[i]);
            if (files[i] == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        String mime = getServletContext().getMimeType(files[0].getName());
        if (mime != null) {
            response.setContentType(mime);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            for (File file : files) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }

    private boolean checkUri(File file) {
        String uri;
        try {
            uri = file.getCanonicalPath();
        } catch (final IOException ignored) {
            return false;
        }

        for (String whitelist : WHITELIST) {
            if (uri.startsWith(whitelist)) {
                return true;
            }
        }
        return false;
    }

    private File findFile(String uri) {
        File file = findInSrcDir(uri);
        if (file == null || !file.exists() || !file.isFile()) {
            file = new File(getServletContext().getRealPath("/static"), uri);
        }

        if (!file.exists() || !file.isFile() || !checkUri(file)) {
            return null;
        }

        return file;
    }

    private File findInSrcDir(String uri) {
        File root = getSourceRoot();
        if (root == null || !root.exists() || !root.isDirectory()) {
            return null;
        }
        if (!root.isDirectory()) {
            return null;
        }

        return new File(root, uri);
    }

    private File getSourceRoot() {
        try {
            File dir = new File(StaticServlet.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());

            if (dir.isFile()) {
                dir = dir.getParentFile();
            }

            while (dir != null) {
                File src = new File(dir, "src/main/webapp");
                if (src.isDirectory()) {
                    return new File(dir, "src/main/webapp/static");
                }
                dir = dir.getParentFile();
            }
        } catch (final URISyntaxException ignored) {
            // ignored
        }
        return null;
    }
}

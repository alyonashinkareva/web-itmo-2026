package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    private static final Path SOURCE_STATIC_DIRECTORY = Paths.get(
            "src", "main", "webapp", "static").toAbsolutePath().normalize();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        String[] files = uri.split("\\+", -1);

        Path firstPath = resolveInside(SOURCE_STATIC_DIRECTORY, files[0]);
        Path firstFile = firstPath != null && Files.isRegularFile(firstPath)
                ? firstPath
                : deployedFile(files[0]);

        if (firstFile == null || !Files.isRegularFile(firstFile)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(getServletContext().getMimeType(firstFile.getFileName().toString()));

        try (OutputStream outputStream = response.getOutputStream()) {
            for (String fileUri : files) {
                fileUri = fileUri.startsWith("/") ? fileUri : "/" + fileUri;
                Path path = resolveInside(SOURCE_STATIC_DIRECTORY, fileUri);
                if (path == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                Path file = Files.isRegularFile(path) ? path : deployedFile(fileUri);
                if (file != null && Files.isRegularFile(file)) {
                    Files.copy(file, outputStream);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
        }
    }

    private Path deployedFile(String uri) {
        String realPath = getServletContext().getRealPath("/static" + uri);
        String staticDir = getServletContext().getRealPath("/static");

        if (realPath == null || staticDir == null) {
            return null;
        }
        return resolveInside(
                Paths.get(staticDir).toAbsolutePath().normalize(),
                Paths.get(realPath).toAbsolutePath().normalize()
        );
    }

    private Path resolveInside(Path directory, String uri) {
        return resolveInside(directory, directory.resolve(uri.startsWith("/") ? uri.substring(1) : uri)
                .toAbsolutePath().normalize());
    }

    private Path resolveInside(Path directory, Path file) {
        return file.startsWith(directory) ? file : null;
    }
}

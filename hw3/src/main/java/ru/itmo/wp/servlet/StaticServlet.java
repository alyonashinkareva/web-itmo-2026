package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ServletUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uriRequest = request.getRequestURI();
        List<String> paths = Arrays.stream(uriRequest.split("\\+")).toList();
        String mimeType = "";
        List<File> files = new ArrayList<>();
        ServletContext context = getServletContext();
        for (String uri : paths){
            File file = ServletUtils.getFile(uri, context);
            if (file != null && file.isFile()) {
                files.add(file);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        response.setContentType(getServletContext().getMimeType(mimeType));
        try (OutputStream outputStream = response.getOutputStream()) {
            for (File file : files){
                Files.copy(file.toPath(), outputStream);
            }
        }
    }
}

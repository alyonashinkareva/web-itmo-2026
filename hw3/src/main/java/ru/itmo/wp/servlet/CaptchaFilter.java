package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;
import ru.itmo.wp.util.ServletUtils;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();

        if (uri.endsWith(".css")
                || uri.endsWith(".png")
                || uri.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();

        if (Boolean.TRUE.equals(session.getAttribute("captcha_accepted"))) {
            chain.doFilter(request, response);
            return;
        }

        String result = (String) session.getAttribute("result");
        String userResult = request.getParameter("userResult");

        if ("POST".equals(request.getMethod())
                && result != null
                && userResult != null
                && result.equals(userResult.strip())) {
            session.setAttribute("captcha_accepted", true);
            session.removeAttribute("result");

            String target = uri;
            String query = request.getQueryString();

            if (query != null) {
                target += "?" + query;
            }

            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
            response.setHeader("Location", target);
            return;
        }

        result = Integer.toString(100 + new Random().nextInt(900));
        session.setAttribute("result", result);

        writeResponse(response, result, session.getId());
    }

    private void writeResponse(HttpServletResponse response, String captchaNumber, String id) throws IOException {
        response.setContentType("text/html");
        Path path = Paths.get("src/main/webapp/static/img/" + id + "/captcha.png");
        Files.createDirectories(path.getParent());
        Files.write(path, ImageUtils.toPng(captchaNumber));
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(String.format(Files.readString(Paths.get("src/main/webapp/static/captcha.html")), id).getBytes());
        }
    }
}

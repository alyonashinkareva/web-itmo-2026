package ru.itmo.web.hw4.web;

import freemarker.template.*;
import ru.itmo.web.hw4.util.DataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerServlet extends HttpServlet {
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String DEBUG_TEMPLATES_PATH = "../../src/main/webapp/WEB-INF/templates";
    private static final String TEMPLATES_PATH = "/WEB-INF/templates";

    private final Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_31);

    @Override
    public void init() throws ServletException {
        File dir = new File(getServletContext().getRealPath("."), DEBUG_TEMPLATES_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir = new File(getServletContext().getRealPath(TEMPLATES_PATH));
        }

        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(dir);
        } catch (IOException e) {
            throw new ServletException("Unable to set template directory [dir=" + dir + "].", e);
        }

        freemarkerConfiguration.setDefaultEncoding(UTF_8);
        freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        freemarkerConfiguration.setLogTemplateExceptions(false);
        freemarkerConfiguration.setWrapUncheckedExceptions(true);
        freemarkerConfiguration.setFallbackOnNullLoopVariable(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().isEmpty() || request.getRequestURI().equals("/")){
            response.sendRedirect("/index");
            return;
        }
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        Template template;
        Map<String, Object> data = new HashMap<>();
        try {
            getData(request, data);
            template = freemarkerConfiguration.getTemplate(URLDecoder.decode(request.getRequestURI(), UTF_8) + ".ftlh");
        } catch (TemplateNotFoundException ignored) {
            template = getErrorTemplate(HttpServletResponse.SC_NOT_FOUND, "No such page.", response, data);
        } catch (IllegalArgumentException e) {
            template = getErrorTemplate(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), response, data);
        }

        response.setContentType("text/html");
        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getData(HttpServletRequest request, Map<String, Object> data) {
        DataUtil.addData(request, data);
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            if (e.getValue() != null && e.getValue().length == 1) {
                String key = e.getKey();
                String value = e.getValue()[0];
                if (key.endsWith("_id") && isCorrectId(key, value)){
                    data.put(key, Long.valueOf(value));
                }else{
                    data.put(key, value);
                }
            }
        }

        data.put("uri", request.getRequestURI());
    }

    private boolean isCorrectId(String name, String value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must be not null");
        }

        try {
            if (Long.parseLong(value) >= 0){
                return true;
            }else{
                throw new IllegalArgumentException(name + " can't be " + value +
                        ", correct id must be non-negative.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " can't be " + value +
                    ", correct id must be number less " + Long.MAX_VALUE + ".");
        }
    }

    private Template getErrorTemplate(int statusCode, String message, HttpServletResponse response, Map<String, Object> data) throws IOException {
        response.setStatus(statusCode);
        data.put("status_code", statusCode);
        data.put("error_message", message);
        return freemarkerConfiguration.getTemplate("/error.ftlh");
    }
}

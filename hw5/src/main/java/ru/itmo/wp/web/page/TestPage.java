package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TestPage extends ParentTestPage {
    private void action(Map<String, Object> view, HttpServletRequest request) {
        view.put("method", "Action");
    }
}

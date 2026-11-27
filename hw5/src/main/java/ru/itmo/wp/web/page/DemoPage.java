package ru.itmo.wp.web.page;

import java.util.Map;

public class DemoPage extends DemoParent {
    private void action(Map<String, Object> view) {
        view.put("message", "Method from page");
    }
}
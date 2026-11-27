package ru.itmo.wp.web.page;

import java.util.Map;

public class DemoParent implements DemoActions {
    protected void fromParent(Map<String, Object> view) {
        view.put("message", "Method from parent");
    }
}
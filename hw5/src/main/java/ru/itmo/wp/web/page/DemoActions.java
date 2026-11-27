package ru.itmo.wp.web.page;

import java.util.Map;

public interface DemoActions {
    default void fromInterface(Map<String, Object> view) {
        view.put("message", "Method from interface");
    }
}
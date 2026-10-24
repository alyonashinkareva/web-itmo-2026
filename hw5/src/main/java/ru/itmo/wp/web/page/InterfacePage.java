package ru.itmo.wp.web.page;

import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public interface InterfacePage {
    default void interfaceAction(Map<String, Object> view) {
        view.put("message", "InterfacePage");
    }
}

package ru.itmo.wp.web.page;

import java.util.Map;

public interface InterfaceTestPage {
    default void interfaceAction(Map<String, Object> view) {
        view.put("method", "Interface");
    }
}

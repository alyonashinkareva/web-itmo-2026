package ru.itmo.wp.web.page;

import java.util.Map;

public interface BasicPage {
    default void action(Map<String, Object> view) {
        view.put("hi", "Hello World!");
    }
}

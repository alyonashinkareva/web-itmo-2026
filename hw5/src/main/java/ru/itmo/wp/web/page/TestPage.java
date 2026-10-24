package ru.itmo.wp.web.page;

import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TestPage extends ParentTestPage implements InterfacePage {
    public void action(Map<String, Object> view) {
        view.put("message", "TestPage");
    }
}

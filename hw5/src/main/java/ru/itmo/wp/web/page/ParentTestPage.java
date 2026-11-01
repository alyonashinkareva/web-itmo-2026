package ru.itmo.wp.web.page;

import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class ParentTestPage {
    public void parentAction(Map<String, Object> view) {
        view.put("message", "ParentPage");
    }
}

package ru.itmo.wp.web.page;

import java.util.Map;

public class ParentTestPage implements InterfaceTestPage {
    protected void parentAction(Map<String, Object> view) {
        view.put("method", "Parent");
    }
}

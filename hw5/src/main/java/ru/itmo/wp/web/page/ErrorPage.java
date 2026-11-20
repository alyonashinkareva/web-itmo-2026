package ru.itmo.wp.web.page;

import java.util.Map;

public abstract class ErrorPage {
    protected int code;

    public ErrorPage(int code) {
        this.code = code;
    }

    protected void action(Map<String, Object> view) {
        view.put("code", code);
    }
}

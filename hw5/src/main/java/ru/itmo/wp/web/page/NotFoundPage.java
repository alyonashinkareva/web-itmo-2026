package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class NotFoundPage extends ErrorPage{
    public NotFoundPage() {
        super(404);
    }
}

package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class NoticeForm {
    @NotBlank(message = "Content is required")
    @Size(max = 1000, message = "Content can't be longer than 1000 characters")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

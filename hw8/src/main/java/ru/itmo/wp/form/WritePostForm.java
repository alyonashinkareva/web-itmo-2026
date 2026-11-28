package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class WritePostForm {
    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 65000)
    private String text;

    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

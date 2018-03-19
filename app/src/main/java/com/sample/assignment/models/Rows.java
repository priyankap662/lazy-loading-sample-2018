package com.sample.assignment.models;

import java.io.Serializable;

public class Rows implements Serializable {

    private String title;
    private String description;
    private String imageHref;

    public Rows() {
    }

    public Rows(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rows{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imageHref=").append(imageHref);
        sb.append('}');
        return sb.toString();
    }
}

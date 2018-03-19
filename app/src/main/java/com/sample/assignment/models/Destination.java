package com.sample.assignment.models;

import java.io.Serializable;
import java.util.List;

public class Destination implements Serializable {

    private String title;
    private List<Rows> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Destination{");
        sb.append("title=").append(title);
        sb.append("rows=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}

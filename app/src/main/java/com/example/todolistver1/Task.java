package com.example.todolistver1;

import java.io.Serializable;

public class Task implements Serializable {

    private String name;
    private String detail;

    public Task(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

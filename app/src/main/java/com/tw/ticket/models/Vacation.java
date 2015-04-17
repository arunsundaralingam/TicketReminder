package com.tw.ticket.models;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Vacation {
    public Vacation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField(index = true)
    String name;
    @DatabaseField
    Date date;

    public Vacation(String name, Date date) {
        this.name = name;
        this.date = date;
    }

}

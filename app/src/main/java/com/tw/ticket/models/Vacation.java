package com.tw.ticket.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Vacation {
    @DatabaseField(id=true, columnName = "name")
    private String name;
    @DatabaseField(dataType = DataType.DATE_LONG, format = "dd-MMM-yyyy")
    private Date date;

    public Vacation() {
    }

    public Vacation(String name, Date date) {
        this.name = name;
        this.date = date;
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
}

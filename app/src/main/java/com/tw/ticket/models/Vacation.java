package com.tw.ticket.models;

import java.util.Date;

public class Vacation {
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

    String name;
    Date date;

    public Vacation(String name, Date date) {
        this.name = name;
        this.date = date;
    }

}

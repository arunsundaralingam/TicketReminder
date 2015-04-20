package com.tw.ticket.models;

import com.j256.ormlite.field.DatabaseField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String formattedDateAsString = format.format(date);
        try {
            this.date = format.parse(formattedDateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(index = true)
    private String name;
    @DatabaseField
    private Date date;

    public Vacation(String name, Date date) {
        this.name = name;
        this.date = date;
    }

}

package com.tw.ticket.models;

import com.j256.ormlite.field.DatabaseField;

public class Reminder {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(index = true)
    private String name;
    @DatabaseField
    private int daysBefore;
    @DatabaseField
    private int hours;
    @DatabaseField
    private int minutes;

    public Reminder() {
    }

    public Reminder(String name, int daysBefore, int hours, int minutes) {
        this.name = name;
        this.daysBefore = daysBefore;
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}

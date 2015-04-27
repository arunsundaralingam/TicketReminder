package com.tw.ticket.models;

import com.j256.ormlite.field.DatabaseField;

public class VacationReminder {
    public final static String vacation_FIELD_NAME = "vacation_id";
    public final static String reminder_FIELD_NAME = "reminder_id";
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, columnName = vacation_FIELD_NAME)
    private Vacation vacation;
    @DatabaseField(foreign = true, columnName = reminder_FIELD_NAME)
    private Reminder reminder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public VacationReminder() {
    }

    public VacationReminder(Vacation vacation, Reminder reminder) {
        this.vacation = vacation;
        this.reminder = reminder;
    }
}

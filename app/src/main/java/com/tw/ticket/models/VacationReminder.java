package com.tw.ticket.models;

import com.j256.ormlite.field.DatabaseField;

public class VacationReminder {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private Vacation vacation_id;
    @DatabaseField(foreign = true)
    private Reminder reminder_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vacation getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(Vacation vacation_id) {
        this.vacation_id = vacation_id;
    }

    public Reminder getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(Reminder reminder_id) {
        this.reminder_id = reminder_id;
    }

    public VacationReminder() {
    }

    public VacationReminder(Vacation vacation_id, Reminder reminder_id) {
        this.vacation_id = vacation_id;
        this.reminder_id = reminder_id;
    }
}

package com.tw.ticket.migration;

public class Version4 implements Patch {
    // creates VacationReminder table
    @Override
    public String onUpgrade() {
        return "create table if not exists VacationReminder (id integer primary key autoincrement not null," +
                " vacation_id integer, reminder_id integer);";
    }
}

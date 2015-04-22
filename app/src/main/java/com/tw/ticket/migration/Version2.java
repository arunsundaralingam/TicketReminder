package com.tw.ticket.migration;

public class Version2 implements Patch {
// creates Reminder table
    @Override
    public String onUpgrade() {
        return "create table if not exists Reminder (id integer primary key autoincrement not null," +
                " name text, daysBefore integer, hours integer, minutes integer);";
    }
}

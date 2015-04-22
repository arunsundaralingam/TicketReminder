package com.tw.ticket.migration;

public class Version1 implements Patch {
//creates Vacation table
    @Override
    public String onUpgrade() {
        return "create table if not exists Vacation (id integer primary key autoincrement not null," +
                " name text not null, date integer);";
    }
}

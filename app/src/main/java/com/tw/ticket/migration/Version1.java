package com.tw.ticket.migration;

public class Version1 implements Patch {

    @Override
    public String onUpgrade() {
        return "create table if not exists Vacation (name text primary key not null, date integer);";
    }
}

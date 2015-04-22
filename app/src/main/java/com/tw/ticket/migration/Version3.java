package com.tw.ticket.migration;

public class Version3 implements Patch {
// insert initial Reminders
    @Override
    public String onUpgrade() {
        return "insert into Reminder(name, daysBefore, hours, minutes) values" +
                "('irctc booking', 120, 7, 0)," +
                "('SETC booking', 90, 8, 0);";
    }
}

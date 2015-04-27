package com.tw.ticket.db;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.tw.ticket.models.Reminder;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.models.VacationReminder;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            Vacation.class,
            Reminder.class,
            VacationReminder.class
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}

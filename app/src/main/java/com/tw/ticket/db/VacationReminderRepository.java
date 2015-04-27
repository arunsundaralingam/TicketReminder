package com.tw.ticket.db;

import android.content.Context;
import com.tw.ticket.models.Reminder;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.models.VacationReminder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacationReminderRepository {
    BaseRepository<Vacation> vacationRepository;
    BaseRepository<Reminder> reminderRepository;
    BaseRepository<VacationReminder> vacationReminderRepository;
    ManyToManyRepository<Vacation, Reminder, VacationReminder> vacationForRemindersRepository;

    public VacationReminderRepository(Context context) {
        vacationRepository = new BaseRepository<Vacation>(context, Vacation.class);
        reminderRepository = new BaseRepository<Reminder>(context, Reminder.class);
        vacationReminderRepository = new BaseRepository<VacationReminder>(context, VacationReminder.class);
        vacationForRemindersRepository = new ManyToManyRepository<Vacation, Reminder, VacationReminder>(
                context,
                Vacation.class,
                Reminder.class,
                VacationReminder.class);
    }

    public void saveVacation(Vacation vacation) {
        vacationRepository.addOrUpdate(vacation);
    }

    public void saveReminder(Reminder reminder) {
        reminderRepository.addOrUpdate(reminder);
    }

    public void saveVacationReminder(VacationReminder vacationReminder) {
        vacationReminderRepository.addOrUpdate(vacationReminder);
    }

    public List<Vacation> readAllVacations() {
        return vacationRepository.readAll();
    }

    public List<Reminder> readAllReminders() {
        return reminderRepository.readAll();
    }

    public List<VacationReminder> readAllVacationReminders() {
        return vacationReminderRepository.readAll();
    }

    public List<Reminder> getRemindersForVacation(Vacation vacation) {
        List<Reminder> reminders = new ArrayList<Reminder>();
        try {
            reminders = vacationForRemindersRepository.lookupTargetForSource(vacation,
                    VacationReminder.vacation_FIELD_NAME,
                    VacationReminder.reminder_FIELD_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

    public void close() {
        vacationRepository.close();
        reminderRepository.close();
        vacationReminderRepository.close();
        vacationForRemindersRepository.close();
    }
}

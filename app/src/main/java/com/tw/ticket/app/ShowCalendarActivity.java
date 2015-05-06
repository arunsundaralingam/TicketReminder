package com.tw.ticket.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.tw.ticket.db.VacationReminderRepository;
import com.tw.ticket.models.Reminder;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.util.DateUtil;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ShowCalendarActivity extends ActionBarActivity {
    CaldroidFragment calendarView;
    View buttonView;
    TextView vacationInfoView;
    long selectedDate;
    private Context applicationContext;
    private VacationReminderRepository vacationReminderRepository;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getApplicationContext();
        if (vacationReminderRepository == null) {
            vacationReminderRepository = new VacationReminderRepository(applicationContext);
        }
        setContentView(R.layout.activity_show_calendar);
        registerComponents();
        registerAddButtonListener();
        Calendar c = Calendar.getInstance();
        selectedDate = c.get(Calendar.SECOND);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_calendar, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vacationReminderRepository.close();
    }

    private final CaldroidListener caldroidListener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            selectedDate= new DateTime(date).withTimeAtStartOfDay().getMillis();
            calendarView.setTextColorForDate(R.color.GREEN_COLOR, date);
            calendarView.refreshView();
            List<Vacation> vacations = vacationReminderRepository.readAllVacations();
            String displayString = "<<";
            List<Reminder> reminders;
            for (Vacation vacation : vacations) {
                reminders = vacationReminderRepository.getRemindersForVacation(vacation);
                for (Reminder reminder : reminders) {
                    displayString = displayString + (DateUtil.formatDateString(
                            vacation.getDate()) + "--" +
                            reminder.getName());
                }
            }
            vacationInfoView.setText(displayString + ">>");
        }
    };

    private void registerAddButtonListener() {
        buttonView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(applicationContext, AddVacationActivity.class);
                intent.putExtra("vacationDate", selectedDate);
                startActivity(intent);
            }
        });
    }

    private void registerComponents() {
        vacationInfoView = (TextView) findViewById(R.id.vacationInfo);
        buttonView = findViewById(R.id.vacationButton);
        prepareCalendarView();
    }

    private void prepareCalendarView() {
        calendarView = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        calendarView.setArguments(args);
        calendarView.setCaldroidListener(caldroidListener);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, calendarView);
        t.commit();
    }
}

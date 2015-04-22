package com.tw.ticket.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import com.tw.ticket.db.BaseRepository;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.util.DateUtil;

import java.util.Calendar;
import java.util.List;


public class ShowCalendarActivity extends ActionBarActivity {
    BaseRepository<Vacation> vacationRepository;
    CalendarView calendarView;
    View buttonView;
    TextView vacationInfoView;
    long selectedDate;
    private Context applicationContext;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getApplicationContext();
        if (vacationRepository == null) {
            vacationRepository = new BaseRepository<Vacation>(applicationContext, Vacation.class);
        }
        setContentView(R.layout.activity_show_calendar);
        registerComponents();
        registerCalendarListener();
        registerAddButtonListener();
        selectedDate = calendarView.getDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        vacationRepository.close();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void registerCalendarListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                setSelectedDate(year, month, dayOfMonth);
                List<Vacation> values = vacationRepository.readAll();
                String displayString = "<<";
                for(Vacation val : values){
                    displayString = displayString + DateUtil.formatDateString(val.getDate());
                }
                vacationInfoView.setText(displayString+">>");
            }
        });
    }

    private void setSelectedDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        DateUtil.resetTimeToMidnight(calendar);
        selectedDate = calendar.getTime().getTime();
    }


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
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        buttonView = findViewById(R.id.vacationButton);
    }
}

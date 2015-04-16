package com.tw.ticket.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import com.tw.ticket.db.DBManager;

import java.util.List;


public class ShowCalendarActivity extends ActionBarActivity {
    DBManager dbManager;
    CalendarView calendarView;
    View buttonView;
    TextView vacationInfoView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dbManager == null) dbManager = new DBManager(getApplicationContext());
        setContentView(R.layout.activity_show_calendar);
        registerComponents();
        registerCalendarListener();
        registerAddButtonListener();
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
        dbManager.close();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void registerCalendarListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                List<String> values = dbManager.readAllValues();
                String displayString = "<<";
                for(String val : values){
                    displayString += val;
                }
                vacationInfoView.setText(displayString+">>");
            }
        });
    }

    private void registerAddButtonListener() {
        buttonView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddVacationActivity.class);
                intent.putExtra("vacationDate", calendarView.getDate());
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

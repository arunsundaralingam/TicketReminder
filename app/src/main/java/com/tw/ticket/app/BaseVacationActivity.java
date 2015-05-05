package com.tw.ticket.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tw.ticket.db.VacationReminderRepository;
import com.tw.ticket.models.Reminder;

import java.util.List;


public class BaseVacationActivity extends ActionBarActivity {
    protected VacationReminderRepository vacationReminderRepository;
    protected TextView vacationDateTextView;
    protected View doneButtonView;
    protected EditText vacationNameText;
    protected Context applicationContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getApplicationContext();
        if (vacationReminderRepository == null) {
            vacationReminderRepository = new VacationReminderRepository(applicationContext);
        }
        setContentView(R.layout.activity_base_vacation);
        populateReminderCheckBoxes();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void populateReminderCheckBoxes() {
        List<Reminder> reminders = vacationReminderRepository.readAllReminders();
        Function<Reminder, String> reminderNamesFn = new Function<Reminder, String>() {
            public String apply(Reminder r) {
                return r.getName();
            }
        };

        List<String> reminderNames = Lists.transform(reminders, reminderNamesFn);
        ArrayAdapter<String> reminderNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, reminderNames);
        GridView remindersGrid = (GridView) findViewById(R.id.gridView);
        remindersGrid.setAdapter(reminderNameAdapter);
        remindersGrid.setChoiceMode(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base_vacation, menu);
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
        vacationReminderRepository.close();
    }
}

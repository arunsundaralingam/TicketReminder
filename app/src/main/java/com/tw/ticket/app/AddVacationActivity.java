package com.tw.ticket.app;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.tw.ticket.models.Reminder;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.models.VacationReminder;
import com.tw.ticket.util.DateUtil;
import com.tw.ticket.util.UIUtil;

import java.util.Date;

public class AddVacationActivity extends BaseVacationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerDoneButtonListener();
        loadDate();
    }

    private void loadDate() {
        String formattedDate = DateUtil.formatDateString(getVacationDate());
        vacationDateTextView = (TextView) findViewById(R.id.addOrEditTextView);
        vacationDateTextView.setText("Add vacation for date\n" + formattedDate);
    }

    private void registerDoneButtonListener() {
        doneButtonView = findViewById(R.id.button);
        doneButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vacationNameText = (EditText) findViewById(R.id.vacationText);
                GridView gridView = (GridView) findViewById(R.id.gridView);
                String vacationName = vacationNameText.getText().toString();
                if (notEmpty(gridView, vacationName)) {
                    Vacation vacation = new Vacation(vacationName, getVacationDate());
                    saveVacation(gridView, vacation);
                } else {
                    UIUtil.showToast(applicationContext, "Vacation Name must be specified");
                }
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private boolean notEmpty(GridView gridView, String vacationName) {
                return vacationName != null && !vacationName.equals("") && gridView.getCheckedItemCount() > 0;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void saveVacation(GridView gridView, Vacation vacation) {
        vacationReminderRepository.saveVacation(vacation);
        SparseBooleanArray checkedItemPositions = gridView.getCheckedItemPositions();
        for(int i = 0; i < gridView.getAdapter().getCount(); i++) {
            if(checkedItemPositions.get(i)){
                String reminderNameAtIndex = (String) gridView.getItemAtPosition(i);
                Reminder reminderForName = vacationReminderRepository.getReminderForName(reminderNameAtIndex);
                vacationReminderRepository.saveVacationReminder(new VacationReminder(vacation, reminderForName));
            }
        }
    }

    private Date getVacationDate() {
        Object longDate = getIntent().getExtras().get("vacationDate");
        return new Date((Long) longDate);
    }
}


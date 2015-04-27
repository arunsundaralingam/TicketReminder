package com.tw.ticket.app;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
                String vacationName = vacationNameText.getText().toString();
                if (vacationName != null && !vacationName.equals("")) {
                    Vacation vacation = new Vacation(vacationName, getVacationDate());
                    vacationReminderRepository.saveVacation(vacation);
                    Reminder reminder = new Reminder("Rem1", 4, 12, 0);
                    vacationReminderRepository.saveReminder(reminder);
                    vacationReminderRepository.saveVacationReminder(new VacationReminder(vacation, reminder));
                } else {
                    UIUtil.showToast(applicationContext, "Vacation Name must be specified");
                }
            }
        });
    }

    private Date getVacationDate() {
        Object longDate = getIntent().getExtras().get("vacationDate");
        return new Date((Long) longDate);
    }
}


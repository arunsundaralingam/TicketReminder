package com.tw.ticket.app;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tw.ticket.models.Vacation;
import com.tw.ticket.util.DateUtil;

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

    private Date getVacationDate() {
        Object longDate = getIntent().getExtras().get("vacationDate");
        return new Date((Long) longDate);
    }

    private void registerDoneButtonListener() {
        doneButtonView = findViewById(R.id.button);
        doneButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vacationNameText = (EditText) findViewById(R.id.vacationText);
                String vacationName = vacationNameText.getText().toString();
                if (vacationName != null && !vacationName.equals("")) {
                    dbManager.addOrUpdate(new Vacation(vacationName, getVacationDate()));
                } else {
                    showToast("Vacation Name must be specified");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}


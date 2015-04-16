package com.tw.ticket.app;


import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddVacationActivity extends BaseVacationActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDate();
    }

    private void loadDate() {
        final Object dateObject = getIntent().getExtras().get("vacationDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(new Date((Long)dateObject));
        vacationDateTextView = (TextView) findViewById(R.id.addOrEditTextView);
        vacationDateTextView.setText("Add vacation for date\n" + formattedDate);
    }
}


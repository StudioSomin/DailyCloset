package kr.ac.ssu.closet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DateActivity extends AppCompatActivity {
    DatePicker datePicker;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        datePicker = (DatePicker)findViewById(R.id.date_picker);
        editText = (EditText)findViewById(R.id.location);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Todo
                year = datePicker.getYear();
                monthOfYear = datePicker.getMonth() + 1;
                dayOfMonth = datePicker.getDayOfMonth();

                Toast.makeText(DateActivity.this, year + " " + monthOfYear + " " + dayOfMonth, Toast.LENGTH_SHORT).show();

                String loc = editText.;
            }
        });
    }
}

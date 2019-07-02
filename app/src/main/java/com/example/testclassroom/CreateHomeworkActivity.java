package com.example.testclassroom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateHomeworkActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView textViewDate , textViewShowDate;
    Button buttonCreate;
    int year, month, day;
    int hour, minute;
    int finalHour , finalMinute;
    int finalYear , finalMonth , finalDay;
    EditText editTextTitle , editTextDescription , editTextPoints ;
    Spinner spinnerTopic ;
    String request = "createHomework:" ;
    static String result = "";
    static String classCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_homework);

        textViewDate = findViewById(R.id.textViewTimePicker);
        textViewShowDate = findViewById(R.id.textViewShowDate);
        buttonCreate = findViewById(R.id.buttonCreateHomework);
        editTextTitle = findViewById(R.id.editTextHomeworkTitle);
        editTextDescription = findViewById(R.id.editTextHomeworkDescription);
        editTextPoints = findViewById(R.id.editTextHomeworkPoint) ;


        textViewDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateHomeworkActivity.this, CreateHomeworkActivity.this,year,month,day);
            datePickerDialog.show();
        });

        buttonCreate.setOnClickListener(v1 -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String points = editTextPoints.getText().toString();
            String topic = "firstTopic" ;
            String date = finalYear + "/" + finalMonth + "/" + finalDay ;
            String time =  finalHour + "/" + finalMinute ;

            request = request.concat(title + ":" + description + ":" + points + ":" + topic + ":" + date + ":" + time + ":" + ListOfHomeworkActivity.classCode);
            new TransferMessage().execute(request) ;
            Log.e("CreateHomework" , request);

//            if (result.equals("success")){
//                Log.e("resultHomework",request) ;
//                result = "" ;
                CreateHomeworkActivity.refreshHomework(ListOfHomeworkActivity.classCode);
                Intent intent = new Intent(CreateHomeworkActivity.this,ListOfHomeworkActivity.class);
                startActivity(intent);

//            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        finalYear = year;
        finalMonth = month;
        finalDay = dayOfMonth;
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateHomeworkActivity.this,CreateHomeworkActivity.this,hour,minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        finalHour = hourOfDay;
        finalMinute = minute;
        textViewShowDate.setText(finalYear + "/" + finalMonth + "/" + finalDay + "   " + finalHour + ":" + finalMinute);
    }

    public static void refreshHomework(String classCode){
        Log.e("refreshHomework",classCode) ;
        new TransferMessage().execute("homeworkList:" + classCode + ":" + WelcomeActivity.username );
    }
}
package com.example.asapa.terminarz;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTask extends AppCompatActivity {

    Calendar dateTime = Calendar.getInstance();
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    TextView txt;
    Button btnDate,btnTime, btnSave, btnCancel;
    DataManagerImpl dataManager;
    EditText taskName, taskDesc;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        txt = (TextView) findViewById(R.id.dueDateTV);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnTime = (Button) findViewById(R.id.btnTime);
        btnSave = (Button) findViewById(R.id.saveBtn);
        btnCancel = (Button) findViewById(R.id.cancelBtn);
        taskName = (EditText) findViewById(R.id.taskNameTV);
        taskDesc = (EditText) findViewById(R.id.taskDescTV);
        dataManager = new DataManagerImpl(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.priorities,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                String taskNameTmp=taskName.getText().toString();
                task.setName(taskNameTmp);
                task.setDesc(taskDesc.getText().toString());
                String text = spinner.getSelectedItem().toString();
                task.setPrio(text);
                task.setDue_date(txt.getText().toString());
                dataManager.saveTask(task);
                Intent intent = new Intent(AddTask.this,AllTasks.class);
               //запуск служюы отсчёта времени
                long howMutchToEvent=(dateTime.getTimeInMillis()-System.currentTimeMillis())/1000;
                if(howMutchToEvent>=0)
                startService(new Intent(AddTask.this,CheckService.class).putExtra("time", (int)howMutchToEvent).putExtra("eventTime",txt.getText().toString()).putExtra("eventName",taskNameTmp));

                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTask.this,AllTasks.class);
                startActivity(intent);
            }
        });

        updateDueDate();
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, day);
            updateDueDate();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute){
            dateTime.set(Calendar.HOUR_OF_DAY, hour);
            dateTime.set(Calendar.MINUTE, minute);
            updateDueDate();
        }
    };

    private void updateDueDate(){
        txt.setText(formatDateTime.format(dateTime.getTime()));
    }


}

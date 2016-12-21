package com.example.asapa.terminarz;

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

import java.text.DateFormat;
import java.util.Calendar;

public class EditTask extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit_task);

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

        final String name = getIntent().getStringExtra("name");
        final String desc = getIntent().getStringExtra("desc");
        final String prio = getIntent().getStringExtra("prio");
        final String due_date = getIntent().getStringExtra("due_date");
        Bundle bdl = getIntent().getExtras();
        final long id = bdl.getLong("ID");

        txt.setText(due_date);
        taskName.setText(name);
        taskDesc.setText(desc);
        if(!prio.equals(null)){
            int spinnerPosition = adapter.getPosition(prio);
            spinner.setSelection(spinnerPosition);
        }

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
                task.setId(id);
                task.setName(taskName.getText().toString());
                task.setDesc(taskDesc.getText().toString());
                String text = spinner.getSelectedItem().toString();
                task.setPrio(text);
                task.setDue_date(txt.getText().toString());
                dataManager.updateTask(task);
                Intent intent = new Intent(EditTask.this,AllTasks.class);
                startActivity(intent);
            }


        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTask.this,AllTasks.class);
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

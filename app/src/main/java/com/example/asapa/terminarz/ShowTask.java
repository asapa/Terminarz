package com.example.asapa.terminarz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowTask extends AppCompatActivity {

    TextView taskName,taskDesc,taskPrio,taskDueDate;
    Button btnEdit,btnDelete;
    DataManagerImpl dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        dataManager = new DataManagerImpl(this);
        taskName = (TextView) findViewById(R.id.taskNameTV);
        taskDesc = (TextView) findViewById(R.id.taskDescTV);
        taskPrio = (TextView) findViewById(R.id.prioTV);
        taskDueDate = (TextView) findViewById(R.id.dueDateTV);
        btnEdit = (Button) findViewById(R.id.editBtn);
        btnDelete = (Button) findViewById(R.id.deleteBtn);

        final String name = getIntent().getStringExtra("name");
        final String desc = getIntent().getStringExtra("desc");
        final String prio = getIntent().getStringExtra("prio");
        final String due_date = getIntent().getStringExtra("due_date");
        Bundle bdl = getIntent().getExtras();
        final long id = bdl.getLong("ID");

        taskName.setText(name);
        taskDesc.setText(desc);
        taskPrio.setText(prio);
        taskDueDate.setText(due_date);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.deleteTask(id);
                Intent intent = new Intent(ShowTask.this,AllTasks.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTask.this,EditTask.class);
                intent.putExtra("ID", id);
                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                intent.putExtra("prio",prio);
                intent.putExtra("due_date",due_date);
                startActivity(intent);
            }
        });

    }
}

package com.example.asapa.terminarz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class AllTasks extends AppCompatActivity {
    Button btn;
    public static DataManagerImpl dataManager;
    public static TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        btn = (Button) findViewById(R.id.btnAdd);
        final ListView listView = (ListView) findViewById(R.id.tasksLV);

        dataManager = new DataManagerImpl(this);

        List<Task> values = dataManager.getAllTasks();

        taskAdapter = new TaskAdapter(this,values);
        listView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllTasks.this,AddTask.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllTasks.this, ShowTask.class);
                long id = adapterView.getItemIdAtPosition(i);
                String name = dataManager.getTask(id).getName();
                String desc = dataManager.getTask(id).getDesc();
                String prio = dataManager.getTask(id).getPrio();
                String due_date = dataManager.getTask(id).getDue_date();
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

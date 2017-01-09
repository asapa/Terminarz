package com.example.asapa.terminarz;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class AllTasks extends AppCompatActivity {
    Button btn;
    Button btnSettings;
    public static DataManagerImpl dataManager;
    public static TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        btn = (Button) findViewById(R.id.btnAdd);
        btnSettings=(Button) findViewById(R.id.btnSettings);

        final ListView listView = (ListView) findViewById(R.id.tasksLV);

        dataManager = new DataManagerImpl(this);

        List<Task> values = dataManager.getAllTasks();

        taskAdapter = new TaskAdapter(this,values);
        listView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();


        //локальный ресивер
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isLowBattery = status == BatteryManager.BATTERY_STATUS_DISCHARGING;
        String textToToast=String.valueOf(listView.getCount());
        if(isLowBattery)
            Toast.makeText(this,getResources().getString(R.string.to_do) +textToToast, Toast.LENGTH_SHORT).show();


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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = adapterView.getItemIdAtPosition(i);
                dataManager.deleteTask(id);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            }
        });


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllTasks.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

}

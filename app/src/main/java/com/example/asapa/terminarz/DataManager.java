package com.example.asapa.terminarz;

import android.widget.ListView;

import java.util.List;

public interface DataManager {
    Task getTask(long id_task);
    List<Task> getAllTasks();
    long saveTask(Task task);
    boolean deleteTask(long id_task);
    int updateTask(Task task);
    long saveUser(User user);
    String searchPass(String login);
}

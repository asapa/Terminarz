package com.example.asapa.terminarz;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class DataManagerImpl implements DataManager {

    private Context context;
    private SQLiteDatabase db;
    private TaskDAO taskDAO;
    private UserDAO userDAO;
    private DBHelper dbHelper;
    private static final String TABLE_USERS = "users";
    public DataManagerImpl(Context context){
        this.context = context;

        dbHelper = new DBHelper(this.context);

        db = dbHelper.getWritableDatabase();

        taskDAO = new TaskDAO(db);
        userDAO = new UserDAO(db);
    }


    @Override
    public Task getTask(long id_task) {
        Task task = taskDAO.get(id_task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.getAll();
    }

    @Override
    public long saveTask(Task task) {
        long test = 0L;
        try{
            db.beginTransaction();
            test = taskDAO.save(task);
            db.setTransactionSuccessful();
            Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Log.e("1","Blad przy zapisie");
            test = 0L;
        }finally {
            db.endTransaction();
        }
        return test;
    }

    @Override
    public boolean deleteTask(long id_task) {
        boolean result = false;
        try {
            db.beginTransaction();
            Task task = getTask(id_task);
            taskDAO.delete(task);
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e("2","Blad przy usuwaniu");
        }finally {
            db.endTransaction();

        }
        return result;
    }

    @Override
    public int updateTask(Task task) {
        int test = 0;
        try{
            db.beginTransaction();
            test = taskDAO.update(task);
            db.setTransactionSuccessful();
            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Log.e("1","Blad przy zapisie");
            test = 0;
        }finally {
            db.endTransaction();
        }
        return test;
    }

    @Override
    public long saveUser(User user) {
        long test = 0L;
        try{
            db.beginTransaction();
            test = userDAO.save(user);
            db.setTransactionSuccessful();
            Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Log.e("1","Blad przy zapisie");
            test = 0L;
        }finally {
            db.endTransaction();
        }
        return test;
    }


    @Override
    public String searchPass(String login) {
        String query = "SELECT login,password from " + TABLE_USERS;
        Cursor c = db.rawQuery(query,null);
        String a,b;
        b = "Złe dane logowania";
        if(c.moveToFirst()){
            do{
                a = c.getString(0);

                if(a.equals(login)){
                    b = c.getString(1);
                    break;
                }
            }while(c.moveToNext());
        }
        return b;
    }

}

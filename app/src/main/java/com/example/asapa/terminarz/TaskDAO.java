package com.example.asapa.terminarz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;


public class TaskDAO implements DAO<Task>{

    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_TASK_ID= "id_task";
    private static final String KEY_TASK_NAME= "task_name";
    private static final String KEY_DESC_ = "desc";
    private static final String KEY_PRIO = "prio";
    private static final String KEY_DUE_DATE = "due_date";;

    private static final String INSERT =
            "insert into " + TABLE_TASKS
                    + "(" + KEY_TASK_NAME + ", "
                    + KEY_DESC_ + ", " + KEY_PRIO + ", " + KEY_DUE_DATE + ") values (?, ?, ?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public TaskDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(TaskDAO.INSERT);
    }

    @Override
    public long save(Task task) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, task.getName());
        insertStatement.bindString(2, task.getDesc());
        insertStatement.bindString(3, task.getPrio());
        insertStatement.bindString(4, task.getDue_date());
        return insertStatement.executeInsert();

    }

    @Override
    public int update(Task task) {
        final ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getName());
        values.put(KEY_DESC_, task.getDesc());
        values.put(KEY_PRIO, task.getPrio());
        values.put(KEY_DUE_DATE, task.getDue_date());

        return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?", new String[] { String.valueOf(task.getId()) });
    }

    @Override
    public void delete(Task task) {
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });

    }

    @Override
    public Task get(long id_task) {
        Task task = null;
        Cursor c =
                db.query(TABLE_TASKS,
                        new String[] {
                                KEY_TASK_ID, KEY_TASK_NAME,
                                KEY_DESC_, KEY_PRIO, KEY_DUE_DATE},
                        KEY_TASK_ID + " = ?", new String[] { String.valueOf(id_task) },
                        null, null, null, "1");
        if (c.moveToFirst()) {
            task = this.buildTaskFromCursor(c);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        List<Task> list = new ArrayList<Task>();
        Cursor c =
                db.query(TABLE_TASKS, new String[] {
                                KEY_TASK_ID, KEY_TASK_NAME,
                                KEY_DESC_, KEY_PRIO, KEY_DUE_DATE},
                        null, null, null, null, KEY_DUE_DATE, null);
        if (c.moveToFirst()) {
            do {
                Task task = this.buildTaskFromCursor(c);
                if (task != null) {
                    list.add(task);
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
    }

    private Task buildTaskFromCursor(Cursor c) {
        Task task = null;
        if (c != null) {
            task = new Task();
            task.setId(c.getLong(0));
            task.setName(c.getString(1));
            task.setDesc(c.getString(2));
            task.setPrio(c.getString(3));
            task.setDue_date(c.getString(4));
        }
        return task;
    }
}

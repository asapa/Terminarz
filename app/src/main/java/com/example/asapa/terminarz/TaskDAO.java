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
    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_DESC_ID = "id_desc";
    private static final String KEY_PRIO_ID = "id_prio";
    private static final String KEY_DUE_DATE = "due_date";

    private static final String INSERT =
            "insert into " + TABLE_TASKS
                    + "(" + KEY_TASK_ID + ", " + KEY_TASK_NAME + ", "
                    + KEY_USER_ID + ", " + KEY_DESC_ID + ", " + KEY_PRIO_ID + ", " + KEY_DUE_DATE + ") values (?, ?, ?, ?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public TaskDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(TaskDAO.INSERT);
    }

    @Override
    public long save(Task task, long id_user) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, task.get_task_name());
        insertStatement.bindLong(2, id_user);
        insertStatement.bindLong(3, task.get_desc_id());
        insertStatement.bindLong(4, task.get_prio_id());
        insertStatement.bindString(5, task.get_due_date());
        return insertStatement.executeInsert();

    }

    @Override
    public int update(Task task) {
        final ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.get_task_name());
        values.put(KEY_DESC_ID, task.get_desc_id());
        values.put(KEY_PRIO_ID, task.get_prio_id());
        values.put(KEY_DUE_DATE, task.get_due_date());

        return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?", new String[] { String.valueOf(task.get_task_id()) });
    }

    @Override
    public void delete(Task task) {
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.get_task_id()) });
        db.close();
    }

    @Override
    public Task get(long id_task) {
        Task task = null;
        Cursor c =
                db.query(TABLE_TASKS,
                        new String[] {
                                KEY_TASK_ID, KEY_TASK_NAME, KEY_USER_ID,
                                KEY_DESC_ID, KEY_PRIO_ID, KEY_DUE_DATE},
                        KEY_TASK_ID + " = ?", new String[] { String.valueOf(id_task) },
                        null, null, null, "1");
        if (c.moveToFirst()) {
            task = this.buildStudentFromCursor(c);
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
                                KEY_TASK_ID, KEY_TASK_NAME, KEY_USER_ID,
                                KEY_DESC_ID, KEY_PRIO_ID, KEY_DUE_DATE},
                        null, null, null, null, KEY_DUE_DATE, null);
        if (c.moveToFirst()) {
            do {
                Task task = this.buildStudentFromCursor(c);
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

    private Task buildStudentFromCursor(Cursor c) {
        Task task = null;
        if (c != null) {
            task = new Task();
            task.set_task_id(c.getLong(0));
            task.set_task_name(c.getString(1));
            task.set_user_id(c.getLong(2));
            task.set_desc_id(c.getLong(3));
            task.set_prio_id(c.getLong(4));
            task.set_due_date(c.getString(5));
        }
        return task;
    }
}

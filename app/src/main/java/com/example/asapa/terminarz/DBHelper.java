package com.example.asapa.terminarz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasksDB";

    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_TASK_ID= "id_task";
    private static final String KEY_TASK_NAME= "task_name";
    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_DESC_ID = "id_desc";
    private static final String KEY_PRIO_ID = "id_prio";
    private static final String KEY_DUE_DATE = "due_date";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " INTEGER," + KEY_TASK_NAME + " TEXT," +
                KEY_DESC_ID + " INTEGER," + KEY_PRIO_ID + " INTEGER" + KEY_DUE_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}

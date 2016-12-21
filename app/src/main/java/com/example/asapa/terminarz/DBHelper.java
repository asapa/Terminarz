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
    private static final String KEY_DESC_ = "desc";
    private static final String KEY_PRIO = "prio";
    private static final String KEY_DUE_DATE = "due_date";

    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_USER_LOGIN = "login";
    private static final String KEY_USER_PASS = "password";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TASK_NAME + " TEXT," +
                KEY_DESC_ + " TEXT," + KEY_PRIO + " TEXT," + KEY_DUE_DATE + " TEXT" + ")";
        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_USERS + "("
               + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_LOGIN + " TEXT," + KEY_USER_PASS + " TEXT" + ")";db.execSQL(CREATE_TABLE);
       db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}

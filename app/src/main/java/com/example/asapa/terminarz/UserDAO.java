package com.example.asapa.terminarz;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

public class UserDAO implements DAO<User> {

    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_USER_LOGIN = "login";
    private static final String KEY_USER_PASS = "password";


    private static final String INSERT =
            "insert into " + TABLE_USERS
                    + "(" + KEY_USER_LOGIN + ", "
                    + KEY_USER_PASS + ") values (?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(UserDAO.INSERT);
    }

    @Override
    public long save(User user) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, user.getLogin());
        insertStatement.bindString(2, user.getPassword());
        return insertStatement.executeInsert();
    }

    @Override
    public int update(User type) {
        return 0;
    }

    @Override
    public void delete(User type) {

    }

    @Override
    public User get(long id_task) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}

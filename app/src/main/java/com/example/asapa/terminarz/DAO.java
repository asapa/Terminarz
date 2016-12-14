package com.example.asapa.terminarz;


import java.util.List;

public interface DAO<T> {
    long save(T type, long id_user);
    int update(T type);
    void delete(T type);
    T get(long id_task);
    List<T> getAll();
}

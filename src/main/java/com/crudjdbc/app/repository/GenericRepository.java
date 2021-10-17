package com.crudjdbc.app.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T getById(ID id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void deleteById(ID id);
}

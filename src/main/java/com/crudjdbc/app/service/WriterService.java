package com.crudjdbc.app.service;

import com.crudjdbc.app.model.Writer;

import java.util.List;

public interface WriterService {
    Writer getById(int id);

    List<Writer> getAll();

    Writer save(Writer writer);

    Writer update(Writer writer);

    void deleteById(int id);
}

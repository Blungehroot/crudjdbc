package com.crudjdbc.app.service;

import com.crudjdbc.app.model.Label;

import java.util.List;

public interface LabelService {
    Label getById(int id);

    List<Label> getAll();

    Label save(Label label);

    Label update(Label label);

    void deleteById(int id);
}

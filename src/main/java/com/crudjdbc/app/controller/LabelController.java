/*
package com.crudjdbc.app.controller;

import com.crudjdbc.app.model.Label;

import java.util.List;

public class LabelController {

    public LabelController() {
        gsonLabelRepository = new GsonLabelRepositoryImpl();
    }

    public List<Label> getAll() {
        return gsonLabelRepository.getAll();
    }

    public Label getById(Long id) {
        return gsonLabelRepository.getById(id);
    }

    public Label create(String name) {
        Label label = new Label();
        label.setName(name);
        return gsonLabelRepository.save(label);
    }

    public Label update(Long id, String name) {
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        return gsonLabelRepository.update(label);
    }

    public void delete(Long id) {
        gsonLabelRepository.deleteById(id);
    }
}
*/

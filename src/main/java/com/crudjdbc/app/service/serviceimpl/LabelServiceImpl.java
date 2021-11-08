package com.crudjdbc.app.service.serviceimpl;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.repository.LabelRepository;
import com.crudjdbc.app.repository.jdbc.JdbcLabelRepositoryImpl;
import com.crudjdbc.app.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    public LabelServiceImpl() {
        this.labelRepository = new JdbcLabelRepositoryImpl();
    }

    @Override
    public Label getById(int id) {
        return labelRepository.getById(id);
    }

    @Override
    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    @Override
    public Label save(Label label) {
        return labelRepository.save(label);
    }

    @Override
    public Label update(Label label) {
        return labelRepository.update(label);
    }

    @Override
    public void deleteById(int id) {
        labelRepository.deleteById(id);
    }
}

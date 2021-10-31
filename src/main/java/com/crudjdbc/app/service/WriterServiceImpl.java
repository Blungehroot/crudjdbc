package com.crudjdbc.app.service;

import com.crudjdbc.app.model.Writer;
import com.crudjdbc.app.repository.WriterRepository;
import com.crudjdbc.app.repository.jdbc.JdbcWriterRepositoryImpl;

import java.util.List;

public class WriterServiceImpl implements WriterService {
    private final WriterRepository writerRepository;

    public WriterServiceImpl() {
        this.writerRepository = new JdbcWriterRepositoryImpl();
    }

    @Override
    public Writer getById(int id) {
        return writerRepository.getById(id);
    }

    @Override
    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    @Override
    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    @Override
    public void deleteById(int id) {
        writerRepository.deleteById(id);
    }
}

package com.crudjdbc.app.repository;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public Label getById(Integer id) {
        return null;
    }

    @Override
    public List<Label> getAll() {
        return null;
    }

    @Override
    public Label save(Label label) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into labels(name) values (?)");
            ps.setString(1, label.getName());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return label;
    }

    @Override
    public Label update(Label label) {
        return label;
    }

    @Override
    public void deleteById(Integer id) {

    }

    public static void main(String[] args) {
        Label label = new Label();
        label.setName("kek");
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        labelRepository.save(label);

    }
}

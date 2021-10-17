package com.crudjdbc.app.repository;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public Label getById(Integer id) {
        Label label = new Label();

        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from labels where ID=(?);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                label.setId(rs.getInt("ID"));
                label.setName(rs.getString("NAME"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from labels;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Label label = new Label();
                label.setId(rs.getInt("ID"));
                label.setName(rs.getString("NAME"));
                labels.add(label);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    @Override
    public void save(Label label) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into labels(name) values (?)");
            ps.setString(1, label.getName());
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Label is saved");
    }

    @Override
    public void update(Label label) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("update labels set NAME=(?) where ID=(?);");
            ps.setString(1, label.getName());
            ps.setInt(2, label.getId());
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Label is updated");
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from labels where ID=(?);");
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Label is deleted");
    }
}

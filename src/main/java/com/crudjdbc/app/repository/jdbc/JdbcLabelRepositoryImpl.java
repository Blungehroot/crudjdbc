package com.crudjdbc.app.repository.jdbc;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.repository.LabelRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    @Override
    public Label save(Label label) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into labels(name) values (?)");
            ps.setString(1, label.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("update labels set NAME=(?) where ID=(?);");
            ps.setString(1, label.getName());
            ps.setInt(2, label.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from labels where ID=(?);");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Label is deleted");
    }
}

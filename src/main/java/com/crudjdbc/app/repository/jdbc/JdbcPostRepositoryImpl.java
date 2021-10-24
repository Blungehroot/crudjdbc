package com.crudjdbc.app.repository.jdbc;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.PostRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {
    @Override
    public Post getById(Integer id) {
        Post post = new Post();

        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from posts where ID=(?);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                post.setId(rs.getInt("ID"));
                post.setName(rs.getString("NAME"));
                post.setContent(rs.getString("CONTENT"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from posts where ID=(?);");
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Post is deleted");
    }
}

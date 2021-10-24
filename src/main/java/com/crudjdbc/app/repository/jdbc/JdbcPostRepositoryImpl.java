package com.crudjdbc.app.repository.jdbc;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.PostRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Post> posts = new ArrayList<>();
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from posts;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("ID"));
                post.setName(rs.getString("NAME"));
                post.setContent(rs.getString("CONTENT"));
                posts.add(post);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post save(Post post) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into posts(NAME, CONTENT) values (?, ?)");
            ps.setString(1, post.getName());
            ps.setString(2, post.getContent());
            ps.executeUpdate();
            conn.setAutoCommit(false);
            int postId = getPostId(post);
            ps = conn.prepareStatement("insert into labels_posts values (?, ?)");
            List<Label> labels = new ArrayList<>();
            labels.addAll(post.getLabels());
            for (int i = 0; i < labels.size(); i++) {
                ps.setInt(1, labels.get(i).getId());
                ps.setInt(2, postId);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("update posts set NAME=(?), CONTENT=(?) where ID=(?);");
            ps.setString(1, post.getName());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
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

    private int getPostId(Post post) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from posts where NAME=(?);");
            ps.setString(1, post.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                post.setId(rs.getInt("ID"));
                post.setName(rs.getString("NAME"));
                post.setContent(rs.getString("CONTENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post.getId();
    }

    public static void main(String[] args) {
        JdbcLabelRepositoryImpl la = new JdbcLabelRepositoryImpl();
        Label label = la.getById(1);
        Label label1 = la.getById(5);
        List<Label> labels = new ArrayList<>();
        labels.add(label);
        labels.add(label1);
        Post post = new Post();
        post.setName("kek2");
        post.setContent("content");
        post.setLabels(labels);
        JdbcPostRepositoryImpl po = new JdbcPostRepositoryImpl();
        po.save(post);
    }
}

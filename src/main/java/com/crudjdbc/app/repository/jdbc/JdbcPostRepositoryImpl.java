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
import java.util.stream.Collectors;

public class JdbcPostRepositoryImpl implements PostRepository {

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

    @Override
    public Post getById(Integer id) {
        Post post = new Post();
        List<Label> labels = new ArrayList<>();
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps =
                    conn.prepareStatement("select p.ID, p.NAME, p.CONTENT, l.ID, l.NAME from posts p\n" +
                            "LEFT OUTER JOIN labels_posts lp on p.ID = lp.POST_ID\n" +
                            "LEFT OUTER JOIN labels l on lp.LABEL_ID = l.ID\n" +
                            "where p.ID=(?);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Label label = new Label();
                post.setId(rs.getInt("p.ID"));
                post.setName(rs.getString("p.NAME"));
                post.setContent(rs.getString("CONTENT"));
                if (rs.getInt("l.ID") > 0) {
                    label.setId(rs.getInt("l.ID"));
                    label.setName(rs.getString("l.NAME"));
                    labels.add(label);
                    post.setLabels(labels);
                }
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
        List<Label> labels = new ArrayList<>();
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select p.ID, p.NAME, p.CONTENT, l.ID, l.NAME from posts p\n" +
                    "LEFT OUTER JOIN labels_posts lp on p.ID = lp.POST_ID\n" +
                    "LEFT OUTER JOIN labels l on lp.LABEL_ID = l.ID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                Label label = new Label();
                post.setId(rs.getInt("p.ID"));
                post.setName(rs.getString("p.NAME"));
                post.setContent(rs.getString("CONTENT"));
                if (rs.getInt("l.ID") > 0) {
                    label.setId(rs.getInt("l.ID"));
                    label.setName(rs.getString("l.NAME"));
                    labels.add(label);
                    post.setLabels(labels);
                }
                posts.add(post);
                labels = new ArrayList<>();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts.stream().distinct().collect(Collectors.toList());
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
            PreparedStatement ps = conn.prepareStatement("update posts\n" +
                    "inner join labels_posts on labels_posts.POST_ID = posts.ID\n" +
                    "SET posts.NAME = (?), posts.CONTENT = (?)\n" +
                    "WHERE posts.ID = (?);");
            ps.setString(1, post.getName());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());
            ps.executeUpdate();
            int postId = getPostId(post);

            ps = conn.prepareStatement("delete from labels_posts where POST_ID=(?);");
            List<Label> labels = new ArrayList<>();
            ps.setInt(1, postId);
            ps.executeUpdate();

            conn.setAutoCommit(false);
            ps = conn.prepareStatement("insert into labels_posts  values (?, ?);");
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

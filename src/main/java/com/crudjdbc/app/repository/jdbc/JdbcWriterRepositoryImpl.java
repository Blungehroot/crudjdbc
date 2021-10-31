package com.crudjdbc.app.repository.jdbc;

import com.crudjdbc.app.driver.Connector;
import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.model.Writer;
import com.crudjdbc.app.repository.WriterRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private int getWriterId(Writer writer) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from writers where NAME=(?);");
            ps.setString(1, writer.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                writer.setId(rs.getInt("ID"));
                writer.setName(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer.getId();
    }

    @Override
    public Writer getById(Integer id) {
        Writer writer = new Writer();
        List<Post> posts = new ArrayList<>();
        List<Label> labels = new ArrayList<>();
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps =
                    conn.prepareStatement("select w.ID, w.NAME, p.ID, p.NAME, p.CONTENT, l.ID, l.NAME from writers w\n" +
                            "LEFT OUTER JOIN posts_writers pw on w.ID = pw.WRITER_ID\n" +
                            "LEFT OUTER JOIN posts p on pw.POST_ID = p.ID\n" +
                            "LEFT OUTER JOIN labels_posts lp on p.ID = lp.POST_ID\n" +
                            "LEFT OUTER JOIN labels l on lp.LABEL_ID = l.ID\n" +
                            "where w.ID=(?);");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                Label label = new Label();
                writer.setId(rs.getInt("w.ID"));
                writer.setName(rs.getString("w.NAME"));
                if (rs.getInt("p.ID") > 0) {
                    post.setId(rs.getInt("p.ID"));
                    post.setName(rs.getString("p.NAME"));
                    post.setContent(rs.getString("p.CONTENT"));
                    if (rs.getInt("l.ID") > 0) {
                        label.setId(rs.getInt("l.ID"));
                        label.setName(rs.getString("l.NAME"));
                        labels.add(label);
                        post.setLabels(labels);
                    }
                    posts.add(post);
                    writer.setPosts(posts.stream().distinct().collect(Collectors.toList()));
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<Label> labels = new ArrayList<>();
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps =
                    conn.prepareStatement("select w.ID, w.NAME, p.ID, p.NAME, p.CONTENT, l.ID, l.NAME from writers w\n" +
                            "LEFT OUTER JOIN posts_writers pw on w.ID = pw.WRITER_ID\n" +
                            "LEFT OUTER JOIN posts p on pw.POST_ID = p.ID\n" +
                            "LEFT OUTER JOIN labels_posts lp on p.ID = lp.POST_ID\n" +
                            "LEFT OUTER JOIN labels l on lp.LABEL_ID = l.ID;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Writer writer = new Writer();
                Post post = new Post();
                Label label = new Label();
                writer.setId(rs.getInt("w.ID"));
                writer.setName(rs.getString("w.NAME"));
                if (rs.getInt("p.ID") > 0) {
                    post.setId(rs.getInt("p.ID"));
                    post.setName(rs.getString("p.NAME"));
                    post.setContent(rs.getString("p.CONTENT"));
                    if (rs.getInt("l.ID") > 0) {
                        label.setId(rs.getInt("l.ID"));
                        label.setName(rs.getString("l.NAME"));
                        labels.add(label);
                    }
                }
                post.setLabels(labels.stream().distinct().collect(Collectors.toList()));
                posts.add(post);
                writer.setPosts(posts);
                writers.add(writer);
                posts = new ArrayList<>();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Writer save(Writer writer) {
        try {
            Connection conn = Connector.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into writers(NAME) values (?)");
            ps.setString(1, writer.getName());
            ps.executeUpdate();
            conn.setAutoCommit(false);
            int writerId = getWriterId(writer);
            ps = conn.prepareStatement("insert into posts_writers values (?, ?)");
            List<Post> posts = new ArrayList<>();
            posts.addAll(writer.getPosts());
            for (int i = 0; i < posts.size(); i++) {
                ps.setInt(1, posts.get(i).getId());
                ps.setInt(2, writerId);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public static void main(String[] args) {
        JdbcWriterRepositoryImpl wr = new JdbcWriterRepositoryImpl();
        JdbcPostRepositoryImpl pr = new JdbcPostRepositoryImpl();

        //Writer writer = new Writer();
        //Post post = new Post();
        //Post post1 = new Post();
        //List<Post> posts = new ArrayList<>();
        //post = pr.getById(20);
        //posts.add(post);

        //writer.setName("kik");
        //writer.setPosts(posts);
        // wr.save(writer);
        System.out.println(wr.getAll());
    }
}

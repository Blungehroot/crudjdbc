package com.crudjdbc.app.service.serviceimpl;

import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.PostRepository;
import com.crudjdbc.app.repository.jdbc.JdbcPostRepositoryImpl;
import com.crudjdbc.app.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl() {
        this.postRepository = new JdbcPostRepositoryImpl();
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }
}

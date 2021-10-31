package com.crudjdbc.app.service;

import com.crudjdbc.app.model.Post;

import java.util.List;

public interface PostService {
    Post getById(int id);

    List<Post> getAll();

    Post save(Post post);

    Post update(Post post);

    void deleteById(int id);
}

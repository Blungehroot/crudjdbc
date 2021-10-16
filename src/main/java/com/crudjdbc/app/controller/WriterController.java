package com.crudjdbc.app.controller;

import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.model.Writer;
import com.crudjdbc.app.repository.gson.GsonPostRepositoryImpl;
import com.crudjdbc.app.repository.gson.GsonWriterRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WriterController {
    private final GsonWriterRepositoryImpl gsonWriterRepository;
    private final GsonPostRepositoryImpl gsonPostRepository;

    private List<Post> getListPostsById(String ids) {
        List<Post> allExistPosts = gsonPostRepository.getAll();
        List<Post> temp;
        String[] idsArray = ids.split(",");
        int size = idsArray.length;
        Long[] intIds = new Long[size];

        for (int i = 0; i < size; i++) {
            intIds[i] = Long.parseLong(idsArray[i]);
        }

        List<Long> listOfIds = Arrays.asList(intIds);
        temp = allExistPosts
                .stream()
                .filter(post -> listOfIds.contains(post.getId()))
                .collect(Collectors.toList());
        return temp;
    }

    public WriterController() {
        gsonWriterRepository = new GsonWriterRepositoryImpl();
        gsonPostRepository = new GsonPostRepositoryImpl();
    }

    public List<Writer> getAll() {
        return gsonWriterRepository.getAll();
    }

    public Writer getById(Long id) {
        return gsonWriterRepository.getById(id);
    }

    public Writer create(String name, String postsIds) {
        Writer writer = new Writer();
        writer.setName(name);
        writer.setPosts(getListPostsById(postsIds));
        return gsonWriterRepository.save(writer);
    }

    public Writer update(Long id, String name, String postsIds) {
        Writer writer = new Writer();
        writer.setId(id);
        writer.setName(name);
        writer.setPosts(null);
        writer.setPosts(getListPostsById(postsIds));
        return gsonWriterRepository.update(writer);
    }

    public void delete(Long id) {
        gsonWriterRepository.deleteById(id);
    }
}

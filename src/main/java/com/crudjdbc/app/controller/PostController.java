/*
package com.crudjdbc.app.controller;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.gson.GsonLabelRepositoryImpl;
import com.crudjdbc.app.repository.gson.GsonPostRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostController {
    private final GsonPostRepositoryImpl gsonPostRepository;
    private final GsonLabelRepositoryImpl gsonLabelRepository;

    private List<Label> getListLabelsById(String ids) {
        List<Label> allExistLabels = gsonLabelRepository.getAll();
        List<Label> temp;
        String[] idsArray = ids.split(",");
        int size = idsArray.length;
        Long[] intIds = new Long[size];

        for (int i = 0; i < size; i++) {
            intIds[i] = Long.parseLong(idsArray[i]);
        }

        List<Long> listOfIds = Arrays.asList(intIds);
        temp = allExistLabels
                .stream()
                .filter(label -> listOfIds.contains(label.getId()))
                .collect(Collectors.toList());
        return temp;

    }

    public PostController() {
        gsonPostRepository = new GsonPostRepositoryImpl();
        gsonLabelRepository = new GsonLabelRepositoryImpl();
    }

    public List<Post> getAll() {
        return gsonPostRepository.getAll();
    }

    public Post getById(Long id) {
        return gsonPostRepository.getById(id);
    }

    public Post create(String name, String content, String labelsIds) {
        Post post = new Post();
        post.setName(name);
        post.setContent(content);
        post.setLabels(getListLabelsById(labelsIds));
        return gsonPostRepository.save(post);
    }

    public Post update(Long id, String name, String content, String labelsIds) {
        Post post = new Post();
        post.setId(id);
        post.setName(name);
        post.setContent(content);
        post.setLabels(null);
        post.setLabels(getListLabelsById(labelsIds));
        return gsonPostRepository.update(post);
    }

    public void delete(Long id) {
        gsonPostRepository.deleteById(id);
    }
}
*/

package com.crudjdbc.app.controller;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.service.LabelService;
import com.crudjdbc.app.service.serviceimpl.LabelServiceImpl;
import com.crudjdbc.app.service.PostService;
import com.crudjdbc.app.service.serviceimpl.PostServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostController {
    private final PostService postService;
    private final LabelService labelService;

    public PostController() {
        postService = new PostServiceImpl();
        labelService = new LabelServiceImpl();
    }


    private List<Label> getListLabelsById(String ids) {
        List<Label> allExistLabels = labelService.getAll();
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

    public List<Post> getAll() {
        return postService.getAll();
    }

    public Post getById(int id) {
        if (postService.getById(id).getId() == null) {
            try {
                throw new Exception("Post with id = " + id + " is not present");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postService.getById(id);
    }

    public Post create(String name, String content, String labelsIds) {
        Post post = new Post();
        post.setName(name);
        post.setContent(content);
        post.setLabels(getListLabelsById(labelsIds));
        return postService.save(post);
    }

    public Post update(int id, String name, String content, String labelsIds) {
        Post post = new Post();
        post.setId(id);
        post.setName(name);
        post.setContent(content);
        post.setLabels(null);
        post.setLabels(getListLabelsById(labelsIds));
        return postService.update(post);
    }

    public void delete(int id) {
        if (postService.getById(id).getId() == null) {
            try {
                throw new Exception("Post with id = " + id + " is not present and cannot be deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        postService.deleteById(id);
    }
}

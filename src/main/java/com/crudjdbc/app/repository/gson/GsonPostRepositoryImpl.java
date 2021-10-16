package com.crudjdbc.app.repository.gson;

import com.crudjdbc.app.helpers.FileHelpers;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class GsonPostRepositoryImpl implements PostRepository {
    private static final String POST_FILE = "posts.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Long generateMaxId(List<Post> allExistingPosts) {
        Long id = Collections.max(allExistingPosts, Comparator.comparing(Post::getId)).getId();
        return id + 1;
    }

    private List<Post> getPostsFromJson(String postsStrings) {
        return gson.fromJson(postsStrings, new TypeToken<List<Post>>() {
        }.getType());
    }

    public Post getById(Long id) {
        String postsStrings = FileHelpers.readFile(POST_FILE);
        return getPostsFromJson(postsStrings)
                .stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public List<Post> getAll() {
        String postsStrings = FileHelpers.readFile(POST_FILE);
        return getPostsFromJson(postsStrings);
    }

    public Post save(Post post) {
        String postsStrings = FileHelpers.readFile(POST_FILE);
        List<Post> posts = new ArrayList<>();
        if (postsStrings.isEmpty()) {
            post.setId(1L);
            posts.add(post);
        } else {
            posts = getPostsFromJson(postsStrings);
            post.setId(generateMaxId(posts));
            posts.add(post);
        }
        String jsonString = gson.toJson(posts);
        FileHelpers.writeInFile(jsonString, POST_FILE);
        return post;
    }

    public Post update(Post post) {
        String postsStrings = FileHelpers.readFile(POST_FILE);
        List<Post> posts = getPostsFromJson(postsStrings);
        Post post1 = posts.stream().filter(p -> p.getId().equals(post.getId())).findAny().orElse(null);
        post1.setName(post.getName());
        post1.setContent(post.getContent());
        post1.setLabels(post.getLabels());
        String jsonString = gson.toJson(posts);
        FileHelpers.writeInFile(jsonString, POST_FILE);
        return post1;
    }

    public void deleteById(Long id) {
        String postsStrings = FileHelpers.readFile(POST_FILE);
        List<Post> posts = getPostsFromJson(postsStrings);
        posts.removeIf(post -> post.getId().equals(id));
        String jsonString = gson.toJson(posts);
        FileHelpers.writeInFile(jsonString, POST_FILE);
    }
}

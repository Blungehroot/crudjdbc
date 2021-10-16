package com.crudjdbc.app.view;


import com.crudjdbc.app.controller.PostController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner sc;
    private final Gson gson;

    private final String menu = "Select action on posts:\n" +
            "1. Add new post\n" +
            "2. Display all posts\n" +
            "3. Display selected post\n" +
            "4. Delete post\n" +
            "5. Edit post\n" +
            "6. Exit";

    private static final String CREATE_MSG = "Creating post.\n";
    private static final String EDIT_MSG = "Edit post.\n";
    private static final String DISPLAY_ALL_MSG = "Display all posts\n";
    private static final String DISPLAY_TARGET_MSG = "Display selected post.\n";
    private static final String DELETE_MSG = "Delete post.\n";
    private static final String INPUT_NAME_MSG = "Input Post name";
    private static final String INPUT_ID_MSG = "Input Post id: ";
    private static final String INPUT_ID_TO_DELETE = "Input id to delete post: ";
    private static final String INPUT_CONTENT = "Input content: ";
    private static final String INPUT_IDS_LABELS = "Input id's labels: ";

    public PostView(PostController postController, Scanner sc) {
        this.postController = postController;
        this.sc = sc;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    void create() {
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(INPUT_CONTENT);
        String content = sc.next();
        System.out.println(INPUT_IDS_LABELS);
        String labelsIds = sc.next();
        System.out.println(CREATE_MSG);
        postController.create(name, content, labelsIds);
    }

    void update() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(INPUT_CONTENT);
        String content = sc.next();
        System.out.println(INPUT_IDS_LABELS);
        String labelsIds = sc.next();
        System.out.println(EDIT_MSG);
        postController.update(Long.valueOf(id), name, content, labelsIds);
    }

    void delete() {
        System.out.println(INPUT_ID_TO_DELETE);
        String id = sc.next();
        System.out.println(DELETE_MSG);
        postController.delete(Long.valueOf(id));
    }

    void printSelected() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(DISPLAY_TARGET_MSG);
        System.out.println(gson.toJson(postController.getById(Long.valueOf(id))));
    }

    void printAll() {
        System.out.println(DISPLAY_ALL_MSG);
        System.out.println(gson.toJson(postController.getAll()));
    }

    void show() {
        boolean isExit = false;
        System.out.println(menu);
        while (true) {
            String action = sc.next();
            switch (action) {
                case "1":
                    create();
                    System.out.println(menu);
                    break;
                case "2":
                    printAll();
                    System.out.println(menu);
                    break;
                case "3":
                    printSelected();
                    System.out.println(menu);
                    break;
                case "4":
                    delete();
                    System.out.println(menu);
                    break;
                case "5":
                    update();
                    System.out.println(menu);
                    break;
                case "6":
                    isExit = true;
                    break;
                default:
                    System.out.println("some error occurred");
                    break;
            }
            if (isExit)
                break;
        }
    }
}

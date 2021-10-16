/*
package com.crudjdbc.app.view;


import com.crudjdbc.app.controller.WriterController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner sc;
    private final Gson gson;

    private final String menu = "Select action on writers:\n" +
            "1. Add new writer\n" +
            "2. Display all writers\n" +
            "3. Display selected writer\n" +
            "4. Delete writer\n" +
            "5. Edit writer\n" +
            "6. Exit";

    private static final String CREATE_MSG = "Creating writer.\n";
    private static final String EDIT_MSG = "Edit writer.\n";
    private static final String DISPLAY_ALL_MSG = "Display all writers\n";
    private static final String DISPLAY_TARGET_MSG = "Display selected writer.\n";
    private static final String DELETE_MSG = "Delete writer.\n";
    private static final String INPUT_NAME_MSG = "Input Writer name";
    private static final String INPUT_ID_MSG = "Input Writer id: ";
    private static final String INPUT_ID_TO_DELETE = "Input id to delete writer: ";
    private static final String INPUT_IDS_POSTS = "Input id's posts: ";

    public WriterView(WriterController writerController, Scanner sc) {
        this.writerController = writerController;
        this.sc = sc;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    void create() {
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(INPUT_IDS_POSTS);
        String postsIds = sc.next();
        System.out.println(CREATE_MSG);
        writerController.create(name, postsIds);
    }

    void update() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(INPUT_IDS_POSTS);
        String postsIds = sc.next();
        System.out.println(EDIT_MSG);
        writerController.update(Long.valueOf(id), name, postsIds);
    }

    void delete() {
        System.out.println(INPUT_ID_TO_DELETE);
        String id = sc.next();
        System.out.println(DELETE_MSG);
        writerController.delete(Long.valueOf(id));
    }

    void printSelected() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(DISPLAY_TARGET_MSG);
        System.out.println(gson.toJson(writerController.getById(Long.valueOf(id))));
    }

    void printAll() {
        System.out.println(DISPLAY_ALL_MSG);
        System.out.println(gson.toJson(writerController.getAll()));
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
*/

package com.crudjdbc.app.view;


import com.crudjdbc.app.controller.LabelController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

public class LabelView {
    private final LabelController labelController;
    private final Scanner sc;
    private final Gson gson;

    private static final String menu = "Select action on label:\n" +
            "1. Add new label\n" +
            "2. Display all labels\n" +
            "3. Display selected label\n" +
            "4. Delete label\n" +
            "5. Edit label\n" +
            "6. Exit";

    private static final String CREATE_MSG = "Creating label.\n";
    private static final String EDIT_MSG = "Edit label.\n";
    private static final String DISPLAY_ALL_MSG = "Display all labels\n";
    private static final String DISPLAY_TARGET_MSG = "Display selected label.\n";
    private static final String DELETE_MSG = "Delete label.\n";
    private static final String INPUT_NAME_MSG = "Input label name";
    private static final String INPUT_ID_MSG = "Input label id: ";
    private static final String INPUT_ID_TO_DELETE = "Input id to delete label: ";

    public LabelView(LabelController labelController, Scanner sc) {
        this.labelController = labelController;
        this.sc = sc;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    void create() {
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(CREATE_MSG);
        labelController.create(name);
    }

    void update() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(INPUT_NAME_MSG);
        String name = sc.next();
        System.out.println(EDIT_MSG);
        labelController.update(Long.valueOf(id), name);
    }

    void delete() {
        System.out.println(INPUT_ID_TO_DELETE);
        String id = sc.next();
        System.out.println(DELETE_MSG);
        labelController.delete(Long.valueOf(id));
    }

    void printSelected() {
        System.out.println(INPUT_ID_MSG);
        String id = sc.next();
        System.out.println(DISPLAY_TARGET_MSG);
        System.out.println(gson.toJson(labelController.getById(Long.valueOf(id))));
    }

    void printAll() {
        System.out.println(DISPLAY_ALL_MSG);
        System.out.println(gson.toJson(labelController.getAll()));
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

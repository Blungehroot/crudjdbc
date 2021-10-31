package com.crudjdbc.app.view;

import com.crudjdbc.app.controller.LabelController;
import com.crudjdbc.app.controller.PostController;

import java.util.Scanner;

public class ConsoleRunner {
    private final LabelView labelView;
    private final PostView postView;
    //private final WriterView writerView;

    private Scanner sc = new Scanner(System.in);
    private static final String msg = "Select action:\n" +
            "1. Actions with labels.\n" +
            "2. Actions with posts.\n" +
            "3. Actions with writers.\n" +
            "4. Exit.";

    public ConsoleRunner() {
        LabelController labelController = new LabelController();
        labelView = new LabelView(labelController, sc);

        PostController postController = new PostController();
        postView = new PostView(postController, sc);

        /*WriterController writerController = new WriterController();
        writerView = new WriterView(writerController, sc);*/
    }

    public void run() {
        boolean isExit = false;
        System.out.println(msg);
        while (true) {
            String action = sc.next();
            switch (action) {
                case "1":
                    labelView.show();
                    System.out.println(msg);
                    break;
                case "2":
                    postView.show();
                    System.out.println(msg);
                    break;
                case "3":
                    //writerView.show();
                    System.out.println(msg);
                    break;
                case "4":
                    isExit = true;
                    break;
                default:
                    System.out.println("some error occurred");
                    break;
            }
            if (isExit)
                break;
        }
        sc.close();
    }
}

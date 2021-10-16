package com.crudjdbc.app.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Label implements Serializable {
    private Long id;
    private String name;

    public Label() {}
}

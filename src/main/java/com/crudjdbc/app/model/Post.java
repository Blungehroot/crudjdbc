package com.crudjdbc.app.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Post implements Serializable {
    private Long id;
    private String name;
    private String content;
    private List<Label> labels;
}

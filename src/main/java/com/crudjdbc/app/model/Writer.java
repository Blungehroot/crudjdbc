package com.crudjdbc.app.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Writer implements Serializable {
    private Integer id;
    private String name;
    private List<Post> posts;
}

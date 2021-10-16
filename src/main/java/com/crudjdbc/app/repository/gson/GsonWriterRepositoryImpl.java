package com.crudjdbc.app.repository.gson;

import com.crudjdbc.app.helpers.FileHelpers;
import com.crudjdbc.app.model.Writer;
import com.crudjdbc.app.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private static final String WRITER_FILE = "writers.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Long generateMaxId(List<Writer> allExistingWriters) {
        Long id = Collections.max(allExistingWriters, Comparator.comparing(Writer::getId)).getId();
        return id + 1;
    }

    private List<Writer> getWritersFromJson(String writersStrings) {
        return gson.fromJson(writersStrings, new TypeToken<List<Writer>>() {
        }.getType());
    }

    @Override
    public Writer getById(Long id) {
        String writersStrings = FileHelpers.readFile(WRITER_FILE);
        return getWritersFromJson(writersStrings)
                .stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        String writersStrings = FileHelpers.readFile(WRITER_FILE);
        return getWritersFromJson(writersStrings);
    }

    @Override
    public Writer save(Writer writer) {
        String writersStrings = FileHelpers.readFile(WRITER_FILE);
        List<Writer> writers = new ArrayList<>();
        if (writersStrings.isEmpty()) {
            writer.setId(1L);
            writers.add(writer);
        } else {
            writers = getWritersFromJson(writersStrings);
            writer.setId(generateMaxId(writers));
            writers.add(writer);
        }
        String jsonString = gson.toJson(writers);
        FileHelpers.writeInFile(jsonString, WRITER_FILE);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        String writersStrings = FileHelpers.readFile(WRITER_FILE);
        List<Writer> writers = getWritersFromJson(writersStrings);
        Writer writer1 = writers.stream().filter(w -> w.getId().equals(writer.getId())).findAny().orElse(null);
        writer1.setName(writer.getName());
        writer1.setPosts(writer.getPosts());
        String jsonString = gson.toJson(writers);
        FileHelpers.writeInFile(jsonString, WRITER_FILE);
        return writer1;
    }

    @Override
    public void deleteById(Long id) {
        String writersStrings = FileHelpers.readFile(WRITER_FILE);
        List<Writer> writers = getWritersFromJson(writersStrings);
        writers.removeIf(post -> post.getId().equals(id));
        String jsonString = gson.toJson(writers);
        FileHelpers.writeInFile(jsonString, WRITER_FILE);
    }
}

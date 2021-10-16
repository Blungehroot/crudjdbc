package com.crudjdbc.app.repository.gson;


import com.crudjdbc.app.helpers.FileHelpers;
import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class GsonLabelRepositoryImpl implements LabelRepository {
    private static final String LABEL_FILE = "labels.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Long generateMaxId(List<Label> allExistingLabels) {
        Long id = Collections.max(allExistingLabels, Comparator.comparing(l -> l.getId())).getId();
        return id + 1;
    }

    private List<Label> getLabelsFromJson(String labelStrings) {
        return gson.fromJson(labelStrings, new TypeToken<List<Label>>() {
        }.getType());
    }

    public Label getById(Long id) {
        String labelsStrings = FileHelpers.readFile(LABEL_FILE);
        return getLabelsFromJson(labelsStrings)
                .stream()
                .filter(l -> l.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public List<Label> getAll() {
        String labelsStrings = FileHelpers.readFile(LABEL_FILE);
        return getLabelsFromJson(labelsStrings);
    }

    public Label save(Label label) {
        String labelsStrings = FileHelpers.readFile(LABEL_FILE);
        List<Label> labels = new ArrayList<>();
        if (labelsStrings.isEmpty()) {
            label.setId(1L);
            labels.add(label);
        } else {
            labels = getLabelsFromJson(labelsStrings);
            label.setId(generateMaxId(labels));
            labels.add(label);
        }
        String jsonString = gson.toJson(labels);
        FileHelpers.writeInFile(jsonString, LABEL_FILE);
        return label;
    }

    public Label update(Label label) {
        String labelsStrings = FileHelpers.readFile(LABEL_FILE);
        List<Label> labels = getLabelsFromJson(labelsStrings);
        Label label1 = getLabelsFromJson(labelsStrings).stream().filter(l -> l.getId().equals(label.getId())).findAny().orElse(null);
        label1.setName(label.getName());
        String jsonString = gson.toJson(labels);
        FileHelpers.writeInFile(jsonString, LABEL_FILE);
        return label1;
    }

    public void deleteById(Long id) {
        String labelsStrings = FileHelpers.readFile(LABEL_FILE);
        List<Label> labels = getLabelsFromJson(labelsStrings);
        labels.removeIf(label -> label.getId().equals(id));
        String jsonString = gson.toJson(labels);
        FileHelpers.writeInFile(jsonString, LABEL_FILE);
    }
}

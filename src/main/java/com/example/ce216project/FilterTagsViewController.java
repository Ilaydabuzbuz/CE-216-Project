package com.example.ce216project;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class FilterTagsViewController {

    @FXML
    private VBox tagsVBox;

    private final Set<String> selectedTags = new HashSet<>();

    public void setAvailableTags(Set<String> tags) {
        tagsVBox.getChildren().clear();

        for (String tag : tags) {
            CheckBox checkBox = new CheckBox(tag);
            checkBox.getStyleClass().add("check-box");
            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    selectedTags.add(tag);
                    System.out.println("ADDED: " + tag);
                } else {
                    selectedTags.remove(tag);
                    System.out.println("REMOVED: " + tag);
                }
            });
            
            tagsVBox.getChildren().add(checkBox);
        }
    }

    public Set<String> getSelectedTags() {
        return selectedTags;
    }

    @FXML
    private void onApplyClicked() {
        tagsVBox.getScene().getWindow().hide();
    }
}

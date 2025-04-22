package com.example.ce216project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditArtifactViewController {
    private String selectedImagePath;

    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField civilizationField;
    @FXML private TextField locationField;
    @FXML private TextField compositionField;
    @FXML private DatePicker dateField;
    @FXML private TextField placeField;
    @FXML private TextField dimensionsField;
    @FXML private TextField weightField;
    @FXML private TextField tagsField;
    @FXML private Button imageButton;
    @FXML private ImageView imageView;

    private Artifacts artifact;

    public void setArtifact(Artifacts artifact) {
        this.artifact = artifact;
        if (artifact == null) return;

        nameField.setText(artifact.artifactname);
        categoryField.setText(artifact.category);
        civilizationField.setText(artifact.civilization);
        locationField.setText(artifact.discoveryLocation);
        compositionField.setText(artifact.composition);

        if (artifact.discoveryDate != null && !artifact.discoveryDate.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dateField.setValue(LocalDate.parse(artifact.discoveryDate, formatter));
            } catch (Exception e) {
                System.err.println("Invalid date format: " + artifact.discoveryDate);
            }
        }

        placeField.setText(artifact.currentPlace);
        dimensionsField.setText(artifact.dimensions);

        if (artifact.weight != null) {
            weightField.setText(String.valueOf(artifact.weight));
        }

        if (artifact.tags != null && !artifact.tags.isEmpty()) {
            tagsField.setText(String.join(", ", artifact.tags));
        }

        if (artifact.imagePath != null) {
            selectedImagePath = artifact.imagePath;
            imageView.setImage(new Image(new File(artifact.imagePath).toURI().toString()));
        }
    }

    @FXML
    private void onSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }
}

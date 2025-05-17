package com.example.ce216project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddArtifactViewController {
    private String selectedImagePath;
    @FXML
    private TextField nameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField civilizationField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField compositionField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField placeField;

    @FXML
    private TextField dimensionsField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField tagsField;

    @FXML
    private Button imageButton;

    @FXML
    private ImageView imageView;

    @FXML
    private void onCreateArtifact(ActionEvent event) {
        boolean allValid = true;

        if (selectedImagePath == null) {
            selectedImagePath = getClass().getResource("/com/example/ce216project/icons/default_icon.png").toString();
        }

        if (selectedImagePath.startsWith("file:/")) {
            selectedImagePath = selectedImagePath.substring(5).replace("/", "\\");
        }

        TextField[] requiredFields = {
                nameField, categoryField, civilizationField,
                locationField, compositionField, placeField,
                dimensionsField, weightField, tagsField
        };

        for (TextField field : requiredFields) {
            if (field.getText().trim().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
                allValid = false;
            } else {
                field.setStyle("");
            }
        }

        if (!allValid || dateField.getValue() == null || selectedImagePath.isEmpty()) {
            dateField.setStyle("-fx-border-color: red;");
            if (selectedImagePath.isEmpty()) {
                selectedImagePath = getClass().getResource("/com/example/ce216project/icons/default_icon.png").toString();
            }
            showError("Please fill in all required fields.");
            return;
        }

        Artifacts artifact = new Artifacts();
        artifact.setArtifactName(nameField.getText());
        artifact.setCategory(categoryField.getText());
        artifact.setCivilization(civilizationField.getText());
        artifact.setDiscoveryLocation(locationField.getText());
        artifact.setComposition(compositionField.getText());
        artifact.setCurrentPlace(placeField.getText());
        artifact.setDimensions(dimensionsField.getText());
        artifact.setWeight(Double.valueOf(weightField.getText()));
        // Tags formatting
        String tagInput = tagsField.getText();
        List<String> tagList = Arrays.stream(tagInput.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        artifact.setImagePath(selectedImagePath);
        artifact.setTags(tagList);
        // Date formatting
        LocalDate localDate = dateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDate.format(formatter);
        artifact.setDiscoveryDate(formattedDate);

        // Artifact ID formatting
        String date = String.valueOf(localDate.getYear());
        String place = artifact.discoveryLocation.toLowerCase().replaceAll("\\s+", "");
        String name = artifact.artifactname.length() >= 3 ? artifact.artifactname.substring(0, 3).toLowerCase() : artifact.artifactname.toLowerCase();
        String uniquePart = String.valueOf(System.currentTimeMillis() % 100000);
        String artifactId = date + place + name + uniquePart;
        artifact.setArtifactid(artifactId);

        // Create JSON file
        String filename = artifact.getArtifactid() + ".json";
        File file = new File(MainViewController.CONTENT_DIR, filename);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(artifact, writer);
            showInfo("Artifact Saved Successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            showError("Error saving artifact.");
        }
    }


    @FXML
    private void onSelectImage(ActionEvent event){
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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

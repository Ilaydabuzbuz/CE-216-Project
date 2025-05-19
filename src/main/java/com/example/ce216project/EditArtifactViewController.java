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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class EditArtifactViewController {
    private String selectedImagePath;
    private CatalogViewController catalogViewController; // Reference to CatalogViewController

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


    public void setCatalogViewController(CatalogViewController catalogViewController) {
        this.catalogViewController = catalogViewController;
    }

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
    private void onEditArtifact(ActionEvent event) {
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

        artifact.setArtifactName(nameField.getText());
        artifact.setCategory(categoryField.getText());
        artifact.setCivilization(civilizationField.getText());
        artifact.setDiscoveryLocation(locationField.getText());
        artifact.setComposition(compositionField.getText());
        artifact.setCurrentPlace(placeField.getText());
        artifact.setDimensions(dimensionsField.getText());
        artifact.setWeight(Double.valueOf(weightField.getText()));
        artifact.setTags(Collections.singletonList(tagsField.getText()));
        artifact.setImagePath(selectedImagePath);

        // Date formatting
        LocalDate localDate = dateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDate.format(formatter);
        artifact.setDiscoveryDate(formattedDate);

        //no artifactid formatting for editing the same json file

        //overriding json
        String filename = artifact.getArtifactid() + ".json";
        File file = new File(MainViewController.CONTENT_DIR, filename);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(artifact, writer);
            showInfo("Artifact Edited Successfully!");
            closeWindow();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            showError("Error editing artifact.");
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

    private void closeWindow() {
        if (catalogViewController != null) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    catalogViewController.loadArtifactsFromDirectory(Paths.get(MainViewController.CONTENT_DIR));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}

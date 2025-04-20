package com.example.ce216project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.Collections;

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
        Artifacts artifact = new Artifacts();
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

        //Date formatting
        LocalDate localDate = dateField.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDate.format(formatter);
        artifact.setDiscoveryDate(formattedDate);

        //Artifactid formatting
        String date = String.valueOf(localDate.getYear());
        String place = artifact.discoveryLocation.toLowerCase().replaceAll("\\s+", ""); // Remove spaces
        String name = artifact.artifactname.length() >= 3 ? artifact.artifactname.substring(0, 3).toLowerCase() : artifact.artifactname.toLowerCase();
        String artifactId = date + place + name;
        artifact.setArtifactid(artifactId);

        //Setting filename
        String filename = artifact.getArtifactid() + ".json";
        File file = new File(FileIOController.CONTENT_DIR, filename);

        //Creating file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(artifact, writer);
            System.out.println("Data saved to" + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
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
}

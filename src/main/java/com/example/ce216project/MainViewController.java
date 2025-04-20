package com.example.ce216project;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {

    @FXML
    private Button viewCatalogButton;

    @FXML
    private MenuItem importJsonMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private void onViewCatalog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("catalog-view.fxml"));
            Parent catalogRoot = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Artifact Catalog");
            stage.setScene(new Scene(catalogRoot));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load catalog view.");
        }
    }


    @FXML
    private void onImportJson(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Selected JSON: " + selectedFile.getAbsolutePath());
            //TODO: Burda seçilen JSONLA napacaz bilmiyom
        }
    }

    @FXML
    private void onAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About This Program");
        alert.setHeaderText("Historical Artifact Catalog");
        //TODO: şu açıklamayı bi ara yazarım
        alert.setContentText("Bu Uygulama şunu şunu yapar genel açıklaması.");
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

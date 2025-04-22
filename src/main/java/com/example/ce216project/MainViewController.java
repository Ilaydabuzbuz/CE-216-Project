package com.example.ce216project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainViewController {
    public static final String DOCUMENTS_DIR = System.getProperty("user.home") + File.separator + "Documents";
    public static final String ARTIFACTS_DIR = DOCUMENTS_DIR + File.separator + "artifacts";
    public static final String CONTENT_DIR = ARTIFACTS_DIR + File.separator + "content";
    public static final String IMAGE_DIR = ARTIFACTS_DIR + File.separator + "image";

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
    private void onAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About This Program");
        alert.setHeaderText("Historical Artifact Catalog");
        //TODO: complete about page
        alert.setContentText("This will be about page");
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void createDirectories() {
        new File(CONTENT_DIR).mkdirs();
        new File(IMAGE_DIR).mkdirs();
    }
}

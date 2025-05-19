package com.example.ce216project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainViewController {
    public static final String INSTALL_DIR = new File(System.getProperty("user.dir")).getParent();
    public static final String ARTIFACTS_DIR = INSTALL_DIR + File.separator + "artifacts";
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
            createDirectories();
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
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/ce216project/styles/delete-style.css").toExternalForm());

        String aboutText = "This application allows you to digitally catalog, manage, and explore a collection "
                + "of historical artifacts. You can create new entries, edit existing ones, filter or search "
                + "based on tags and attributes, and import/export the data in JSON format for easy sharing. ";

        Label label = new Label(aboutText);
        label.setWrapText(true);

        VBox content = new VBox(label);
        content.setPrefWidth(400);
        content.setPadding(new Insets(10));

        alert.getDialogPane().setContent(content);
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
        System.out.println("Creating directories:");
        System.out.println("→ " + ARTIFACTS_DIR);
        System.out.println("→ " + CONTENT_DIR);
        System.out.println("→ " + IMAGE_DIR);

        new File(ARTIFACTS_DIR).mkdirs();
        new File(CONTENT_DIR).mkdirs();
        new File(IMAGE_DIR).mkdirs();
    }
}

package com.example.ce216project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class ArtifactDetailViewController {
    @FXML private Label nameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label civilizationLabel;
    @FXML private Label discoveryLocationLabel;
    @FXML private Label compositionLabel;
    @FXML private Label discoveryDateLabel;
    @FXML private Label placeLabel;
    @FXML private Label dimensionsLabel;
    @FXML private Label weightLabel;
    @FXML private Label tagsLabel;
    @FXML private ImageView artifactImageView;

    private Artifacts artifact;

    public void setArtifact(Artifacts artifact) {
        this.artifact = artifact;

        if (artifact == null) return;

        nameLabel.setText(artifact.getArtifactName());
        categoryLabel.setText(artifact.getCategory());
        civilizationLabel.setText(artifact.getCivilization());
        discoveryLocationLabel.setText(artifact.getDiscoveryLocation());
        compositionLabel.setText(artifact.getComposition());
        discoveryDateLabel.setText(artifact.getDiscoveryDate());
        placeLabel.setText(artifact.getCurrentPlace());
        dimensionsLabel.setText(artifact.getDimensions());
        weightLabel.setText(String.valueOf(artifact.getWeight()));
        tagsLabel.setText(String.join(", ", artifact.getTags()));

        if (artifact.getImagePath() != null) {
            File imageFile = new File(artifact.getImagePath());
            if (imageFile.exists()) {
                artifactImageView.setImage(new Image(imageFile.toURI().toString()));
            }
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
    }
}
package com.example.ce216project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CatalogViewController {
    private static final String ARTIFACTS_DIRECTORY = MainViewController.CONTENT_DIR;
    public static List<Artifacts> artifactsList = new ArrayList<>();
    public static ObservableList<Artifacts> artifactObservableList;
    public static final Gson gson = new Gson();
    private final Set<String> allTags = new HashSet<>();
    @FXML
    private TextField searchField;
    @FXML
    private Button addNewArtifactButton;
    @FXML
    private Button filterByTagsButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button helpButton;
    @FXML
    private TableView<Artifacts> artifactsTableView;


    @FXML
    private TableColumn<Artifacts, String> col_image;
    @FXML
    private TableColumn<Artifacts, String> col_id;
    @FXML
    private TableColumn<Artifacts, String> col_name;
    @FXML
    private TableColumn<Artifacts, String> col_category;
    @FXML
    private TableColumn<Artifacts, String> col_civilization;
    @FXML
    private TableColumn<Artifacts, String> col_discovery;
    @FXML
    private TableColumn<Artifacts, String> col_composition;
    @FXML
    private TableColumn<Artifacts, String> col_date;
    @FXML
    private TableColumn<Artifacts, String> col_place;
    @FXML
    private TableColumn<Artifacts, String> col_dimensions;
    @FXML
    private TableColumn<Artifacts, String> col_weight;
    @FXML
    private TableColumn<Artifacts, String> col_tags;
    @FXML
    private TableColumn<Artifacts, String> col_update;

    public void initialize(){
        artifactObservableList = FXCollections.observableArrayList();

        artifactsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        col_id.setCellValueFactory(new PropertyValueFactory<>("artifactid"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("artifactName"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_civilization.setCellValueFactory(new PropertyValueFactory<>("civilization"));
        col_discovery.setCellValueFactory(new PropertyValueFactory<>("discoveryLocation"));
        col_composition.setCellValueFactory(new PropertyValueFactory<>("composition"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("discoveryDate"));
        col_place.setCellValueFactory(new PropertyValueFactory<>("currentPlace"));
        col_dimensions.setCellValueFactory(new PropertyValueFactory<>("dimensions"));
        col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        col_tags.setCellValueFactory(new PropertyValueFactory<>("tags"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        col_image.setCellFactory(column -> new TableCell<Artifacts, String>() {
        private final ImageView imageView = new ImageView();
        {
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            imageView.setPreserveRatio(true);
        }

        @Override
        protected void updateItem(String imagePath, boolean empty) {
            super.updateItem(imagePath, empty);
            if (empty || imagePath == null || imagePath.isEmpty()) {
                setGraphic(null);
            } else {
                try {
                    Image image = new Image(new File(imagePath).toURI().toString(), 60, 60, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + imagePath);
                    setGraphic(null);
                }
            }
        }
    });


        col_update.setCellFactory(column -> new TableCell<Artifacts, String>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox hbox = new HBox(10);

            {
                hbox.getChildren().addAll(editButton, deleteButton);

                editButton.setOnAction(event -> {
                    Artifacts selectedArtifact = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-artifact-view.fxml"));
                        Parent root = loader.load();

                        EditArtifactViewController controller = loader.getController();
                        controller.setArtifact(selectedArtifact);

                        Stage stage = new Stage();
                        stage.setTitle("Edit Artifact");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setOnHidden(e -> loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY)));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                deleteButton.setOnAction(event -> {
                    Artifacts artifact = getTableView().getItems().get(getIndex());
                    String filename = artifact.getArtifactid() + ".json";
                    File file = new File(MainViewController.CONTENT_DIR, filename);

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirm Deletion");
                    confirmationAlert.setHeaderText("Are you sure you want to delete this artifact?");
                    confirmationAlert.setContentText("Artifact ID: " + artifact.getArtifactid());

                    confirmationAlert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/ce216project/styles/delete-style.css").toExternalForm());

                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

                    confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            if (file.exists()) {
                                if (file.delete()) {
                                    loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY));
                                } else {
                                    showError("Failed to delete the file " + filename);
                                }
                            } else {
                                System.out.println("File not found: " + filename);
                            }
                        }
                    });
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });

        artifactsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Artifacts selectedArtifact = artifactsTableView.getSelectionModel().getSelectedItem();
                if (selectedArtifact != null) {
                    openArtifactDetailPage(selectedArtifact);
                }
            }
        });

        artifactsTableView.setItems(artifactObservableList);
        loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY));
    }

    private void openArtifactDetailPage(Artifacts artifact) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("artifact-detail-view.fxml"));
            Parent root = loader.load();

            ArtifactDetailViewController controller = loader.getController();
            controller.setArtifact(artifact);

            Stage stage = new Stage();
            stage.setTitle("Artifact Details");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load artifact detail view.");
        }
    }

    public void loadArtifactsFromDirectory(Path directoryPath) {
        artifactsList.clear();
        artifactObservableList.clear();
        allTags.clear();

        if (!Files.isDirectory(directoryPath)) {
            System.out.println("File path error");
            return;
        }
        System.out.println("Loading artifacts from: " + directoryPath.toAbsolutePath());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, "*.json")) {
            for (Path filePath : stream) {
                System.out.println("Processing file: " + filePath.getFileName());
                try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                    Artifacts artifact = gson.fromJson(reader, Artifacts.class);

                    if (artifact != null && artifact.getArtifactName() != null) {
                        artifactsList.add(artifact);
                        if (artifact.getTags() != null) {
                            allTags.addAll(artifact.getTags());
                        }
                    } else {
                        System.err.println("Warning: Invalid or incomplete artifact data in file: " + filePath.getFileName());
                    }

                } catch (JsonSyntaxException e) {
                    System.err.println("Error parsing JSON file: " + filePath.getFileName() + " - " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Error reading file: " + filePath.getFileName() + " - " + e.getMessage());
                }
            }
            artifactObservableList.setAll(artifactsList);
            artifactsTableView.setItems(artifactObservableList);


        } catch (IOException e) {
            System.out.println("Loading Error");
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddNewArtifact(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-artifact-view.fxml"));
            Parent catalogRoot = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Artifact");
            stage.setScene(new Scene(catalogRoot));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(e -> loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY)));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load add new artifact view.");
        }
    }

    @FXML
    private void onFilterTags(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("filter-tags-view.fxml"));
            Parent catalogRoot = loader.load();

            FilterTagsViewController controller = loader.getController();
            controller.setAvailableTags(allTags);

            Stage stage = new Stage();
            stage.setTitle("Pick Tags");
            stage.setScene(new Scene(catalogRoot));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            Set<String> selectedTags = controller.getSelectedTags();
            System.out.println("Selected Tags: " + selectedTags);

            if (!selectedTags.isEmpty()) {
                List<Artifacts> filtered = artifactsList.stream()
                    .filter(a -> a.getTags() != null && a.getTags().stream().anyMatch(selectedTags::contains))
                    .toList();
                artifactsTableView.setItems(FXCollections.observableArrayList(filtered));
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load filter by tags view.");
        }
    }


    @FXML
    private void onImportArtifact(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null && selectedFile.exists() && selectedFile.isFile()) {
            try {
                String content = Files.readString(selectedFile.toPath());

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(content, JsonObject.class);

                if (jsonObject.has("artifactid")) {
                    String artifactId = jsonObject.get("artifactid").getAsString();

                    MainViewController.createDirectories();

                    Path destinationPath = Path.of(MainViewController.CONTENT_DIR, artifactId + ".json");

                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File successfully imported and renamed to: " + destinationPath);
                    loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY));
                } else {
                    showError("Invalid file: artifactId not found.");
                }

            } catch (IOException | JsonSyntaxException e) {
                System.out.println("Error importing file: " + e.getMessage());
            }
        } else {
            showError("File didn't import.");
        }
    }


    public void exportArtifact(Artifacts artifact, File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(artifact);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            showError("Success");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error");
        }
    }

    @FXML
    private void onExportArtifact(ActionEvent event) {
        Artifacts selectedArtifact = artifactsTableView.getSelectionModel().getSelectedItem();
        if (selectedArtifact == null) {
            showError("You must select file to export");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Artifact as JSON");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            exportArtifact(selectedArtifact, file);
        }
    }

    @FXML
    private void onHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use This Program");
        alert.setHeaderText("Historical Artifact Catalog Help");

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/ce216project/styles/delete-style.css").toExternalForm());

        String helpText = "How to Use the Artifact Catalog Page\n\n" +
                "Search Box: Enter any keyword and click Search to filter artifacts across all fields.\n\n" +
                "Filter by Tags: Click to choose one or more tags and filter artifacts accordingly.\n\n" +
                "⟳ Clear All Filters: Reset the view to show all artifacts by clearing search and tag filters.\n\n" +
                "Add New Artifact: Click to enter detailed information and add a new artifact to the list.\n\n" +
                "Import ⬇: Load artifact data from a JSON file.\n\n" +
                "Export ⬆: First, select an artifact from the table, then click Export to save that single artifact into a JSON file.\n\n" +
                "Edit/Delete: Update or remove a specific artifact from the catalog.\n\n" +
                "Double-click: Double-click on an artifact to view its details in a new window.";

        Label label = new Label(helpText);
        label.setWrapText(true);
        label.setFont(Font.font("System", 14));

        VBox content = new VBox(label);
        content.setPrefWidth(600);
        content.setPadding(new Insets(10));

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }




    @FXML
    private void onRefresh(ActionEvent event) {
        loadArtifactsFromDirectory(Paths.get(ARTIFACTS_DIRECTORY));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void search() {
        String query = searchField.getText().toLowerCase().trim();
        ObservableList<Artifacts> filteredList = FXCollections.observableArrayList();

        if(query.isEmpty()){
            filteredList.addAll(artifactsList);
        } else {
            for (Artifacts artifact : artifactsList) {
                if (artifact.getArtifactName().toLowerCase().contains(query) ||
                        artifact.getCategory().toLowerCase().contains(query) ||
                        artifact.getArtifactid().toLowerCase().contains(query) ||
                        artifact.getCivilization().toLowerCase().contains(query) ||
                        artifact.getComposition().toLowerCase().contains(query) ||
                        artifact.getDiscoveryLocation().toLowerCase().contains(query) ||
                        artifact.getCurrentPlace().toLowerCase().contains(query) ||
                        artifact.getLength().toString().contains(query) ||
                        artifact.getWidth().toString().contains(query) ||
                        artifact.getHeight().toString().contains(query) ||
                        artifact.getDiscoveryDate().toLowerCase().contains(query) ||
                        artifact.getWeight().toString().contains(query) ||
                        artifact.getTags().contains(query)) {
                    filteredList.add(artifact);
                }
            }
        }
        //artifactsTableView.setPlaceholder(new Label("No results found")); // may be handled differently
        artifactsTableView.setItems(filteredList);
    }

    @FXML
    private void onSearch(ActionEvent event) {
        search();
    }
}

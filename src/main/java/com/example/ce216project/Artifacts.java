package com.example.ce216project;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Artifacts {
    // Artifact attributes
    public String artifactid;
    public String artifactname;
    public String category;
    public String civilization;
    public String discoveryLocation;
    public String composition;
    public String discoveryDate;
    public String currentPlace;
    public Double length;
    public Double width;
    public Double height;
    public Double weight;
    public List<String> tags;
    public String imagePath;


    // Getters and setters
    public String getArtifactid() {
        return artifactid;
    }

    public void setArtifactid(String artifactid) {
        this.artifactid = artifactid;
    }

    public String getArtifactName() {
        return artifactname;
    }

    public void setArtifactName(String artifactname) {
        this.artifactname = artifactname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCivilization() {
        return civilization;
    }

    public void setCivilization(String civilization) {
        this.civilization = civilization;
    }

    public String getDiscoveryLocation() {
        return discoveryLocation;
    }

    public void setDiscoveryLocation(String discoveryLocation) {
        this.discoveryLocation = discoveryLocation;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public String getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(String currentPlace) {
        this.currentPlace = currentPlace;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDimensions() {
        return String.format("%.2f*%.2f*%.2f", length, width, height);
    }


    public Artifacts(){}

    public void ArtifactInfo(){
        this.artifactid = " ";
        this.artifactname = " ";
        this.category = " ";
        this.civilization = " ";
        this.discoveryLocation = " ";
        this.composition = " ";
        this.discoveryDate = " ";
        this.currentPlace = " ";
        this.length = 0.0;
        this.width = 0.0;
        this.height = 0.0;
        this.weight = 0.0;
        this.tags = Collections.singletonList(" ");
        this.imagePath = " ";
    }

    //THIS FUNCTION IS PROTOTYPE. MIGHT CHANGE IN THE FUTURE
//    public static String isValid(String jsonFilePath) {
//        try (JsonReader reader = new JsonReader(new FileReader(jsonFilePath))) {
//            Gson gson = new Gson();
//            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
//
//            boolean isValid = true;
//            StringBuilder validationErrors = new StringBuilder();
//
//            if (!jsonObject.has("artifactid") || jsonObject.get("artifactid").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty artifactid. ");
//            }
//            if (!jsonObject.has("artifactname") || jsonObject.get("artifactname").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty artifactName. ");
//            }
//            if (!jsonObject.has("category") || jsonObject.get("category").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty category. ");
//            }
//            if (!jsonObject.has("civilization") || jsonObject.get("civilization").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty civilization. ");
//            }
//            if (!jsonObject.has("discoveryLocation") || jsonObject.get("discoveryLocation").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty discoveryLocation. ");
//            }
//            if (!jsonObject.has("composition") || jsonObject.get("composition").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty composition. ");
//            }
//            if (!jsonObject.has("currentPlace") || jsonObject.get("currentPlace").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty currentPlace. ");
//            }
//            if (!jsonObject.has("dimensions") || jsonObject.get("dimensions").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty dimensions. ");
//            }
//            if (!jsonObject.has("imagePath") || jsonObject.get("imagePath").getAsString().isEmpty()) {
//                isValid = false;
//                validationErrors.append("Missing or empty imagePath. ");
//            }
//
//            if (!jsonObject.has("weight") || !jsonObject.get("weight").isJsonPrimitive() || !jsonObject.get("weight").getAsJsonPrimitive().isNumber()) {
//                isValid = false;
//                validationErrors.append("Invalid or missing weight. ");
//            }
//
//            if (jsonObject.has("discoveryDate")) {
//                try {
//                    long timestamp = jsonObject.get("discoveryDate").getAsLong();
//                    Date discoveryDate = new Date(timestamp);
//                    if (discoveryDate.after(new Date())) {
//                        isValid = false;
//                        validationErrors.append("Discovery date cannot be in the future. ");
//                    }
//                } catch (Exception e) {
//                    isValid = false;
//                    validationErrors.append("Invalid discoveryDate format. ");
//                }
//            } else {
//                isValid = false;
//                validationErrors.append("Missing discoveryDate. ");
//            }
//
//            String jsonContent = gson.toJson(jsonObject);
//            return jsonContent + "\n\nValid: " + isValid + (isValid ? "" : "\nErrors: " + validationErrors.toString());
//
//        } catch (IOException e) {
//            return "Error reading file: " + e.getMessage();
//        }
//    }

}

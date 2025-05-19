CE-216 Historical_Artifact_Catalog_Project Team#10
#JavaFX Desktop Application
The Historical Artifact Catalog is a Windows based desktop application designed to organize and manage historical artifacts efficiently. Built using JavaFX for a user-friendly interface, this application allows users to add, edit, delete, search, and filter artifacts based on various attributes such as civilization, category, composition, and discovery location.

📦 Application Features
Developed with JavaFX for a modern, interactive GUI
Uses Gradle for dependency management and project structure
Reads and writes inside JSON files to store artifacts
Ready-to-run executable file included

🔎 Notes for usage:

Examle Json Format:
{ "artifacts": [ { 
"artifactId": 2025sladjsad37798, // Unique ID of the artifact 
"artifactName": "String", // Name of the artifact 
"category": "String", // Category or type, e.g., "Painting" 
"civilization": "String", // The civilization artifact belongs to 
"discoveryLocation": "String", // Place where it was discovered 
"composition": "String", // Materials used 
"discoveryDate": "YYYY-MM-DD", // Date it was discovered 
"currentPlace": "String", // Where it is currently located 
"width": Number, // Width in centimeters 
"length": Number, // Length in centimeters 
"height": Number, // Height in centimeters 
"weight": Number, // Weight in kilograms 
"tags": ["String", "..."] // List of identifier tags } ] }

Field Description:
- Tags :This is an array (list) of strings, typically used for search/filter features.
- User is able to filter tags after added to the system

💻 System Requirements:
All java requirements and dependencies included in the installer.

🚀 How to Run
Simply double click the ArtifactCatalog.bat file to start the application.

📦 Installation Steps

1.Run the ArtifactCatalog_Installer.exe file

2.Follow the setup wizard instructions

3.(Optional) A desktop shortcut will be created after installation

4.Launch the application via the shortcut or from the installation folder

🔧 Notes
*Installer built with Inno Setup
*The installer includes the ArtifactCatalog.exe generated
*Default installation directory is: C:\Program Files\ArtifactCatalog (modifiable during setup)
*No need to install Java separately — the runtime is bundled with the application

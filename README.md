# CE-216 Historical_Artifact_Catalog_Project Team 10
# Historical Artifact Catalog
The Historical Artifact Catalog is a Windows based desktop application designed to organize and manage historical artifacts efficiently. Built using JavaFX for a user-friendly interface, this application allows users to add, edit, delete, search, and filter artifacts based on various attributes such as civilization, category, composition, and discovery location.

## Prerequisites

**Operating System:** Windows  
No need to install Java manually — all required dependencies and runtime are bundled with the application.

## Features and Usage

- **Modern GUI**: Built with JavaFX to provide an interactive and responsive interface.
- **Artifact Management**: Add, update, and remove artifacts with detailed attributes.
- **Search and Filter**: Quickly locate artifacts using category, civilization, tags, and more.
- **Data Storage**: Artifacts are stored in a JSON file to allow persistent local storage.
- **Executable Installer**: Comes with a setup wizard and desktop shortcut.

## Example JSON Format

```json
{
  "artifacts": [
    {
      "artifactId": 2025,
      "artifactName": "String",
      "category": "String",
      "civilization": "String",
      "discoveryLocation": "String",
      "composition": "String",
      "discoveryDate": "YYYY-MM-DD",
      "currentPlace": "String",
      "width": Number,
      "length": Number,
      "height": Number,
      "weight": Number,
      "tags": ["String", "..."]
    }
  ]
}
```
### Field Descriptions

- **Tags**: A list of strings to help classify and search for artifacts.
- **All fields** are editable via the GUI and saved automatically to the JSON file.

## User Interface

### Artifact Management Panel

Allows users to add, edit, delete, and view artifact entries.  

### Tag Filtering Panel

Users can filter artifacts dynamically by selecting available tags.

## System Requirements

- JavaFX (bundled within the installer)  
- Compatible with Windows 10/11  
- JSON-based storage; no external database needed

## How to Run

1. Run `ArtifactCatalog_Installer.exe`.
2. Follow the installation wizard.
3. Launch the app via:
   - `ArtifactCatalog.bat` file  
   **or**  
   - The desktop shortcut created by the installer.

## File Structure

- **Installer**: Built using Inno Setup  
- **Executable**: `ArtifactCatalog.exe` is placed in `C:\Program Files\ArtifactCatalog` by default  
- **Data Storage**: JSON file is used for artifact persistence  
- **No additional setup required** — Java Runtime is bundled.

## Project Management

We used **Trello** to manage and track the project's progress and tasks throughout development.  
Project board link:
https://trello.com/b/reT5fU6K/kanban-template

## Support

For support or contributions, please contact the development team or refer to the system documentation.

package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class StudentInfoController {
    public TextField studentID;
    public TextField midtermGrade;
    public TextField assignmentGrade;
    public TextField finalExamGrade;
    public Button button;
    @FXML
    private AnchorPane aPane;
    @FXML
    public MenuBar menuBar;
    @FXML
    private TableView<StudentRecord> tabView;
    @FXML
    private TableColumn<Object, Object> sID;
    @FXML
    private TableColumn<Object, Object> midterm;
    @FXML
    private TableColumn<Object, Object> assignment;
    @FXML
    private TableColumn<Object, Object> finalExam;
    @FXML
    private TableColumn<Object, Object> finalMark;
    @FXML
    private TableColumn<Object, Object> letter;
    @FXML
    private MenuItem mnuNew;
    @FXML
    private MenuItem mnuOpen;
    @FXML
    private MenuItem mnuSave;
    @FXML
    private MenuItem mnuSaveAs;
    @FXML
    private MenuItem mnuExit;

    private File currentFilename;


    @FXML
    public void handleButtonAction() {
        ObservableList<StudentRecord> newInfo = tabView.getItems();
        newInfo.add(new StudentRecord(
                studentID.getText(),
                Double.parseDouble(midtermGrade.getText()),
                Double.parseDouble(assignmentGrade.getText()),
                Double.parseDouble(finalExamGrade.getText())));
        tabView.setItems(newInfo);
        studentID.clear();
        midtermGrade.clear();
        assignmentGrade.clear();
        finalExamGrade.clear();
    }

    public static class StudentRecord {
        private final String studentID;
        private final double midtermGrade;
        private final double assignmentGrade;
        private final double finalExamGrade;
        private final double finalMarkGrade;
        private final String letterGrade;

        public StudentRecord(String studentID, double midterm, double assignment, double finalExam) {
            this.studentID = studentID;
            this.midtermGrade = midterm;
            this.assignmentGrade = assignment;
            this.finalExamGrade = finalExam;
            this.finalMarkGrade = ((0.2*assignment)+(0.3*midterm)+(0.5*finalExam));
            if (finalMarkGrade < 50){
                this.letterGrade = "F";
            }
            else if (finalMarkGrade >= 50 && finalMarkGrade < 60){
                this.letterGrade = "D";
            }
            else if (finalMarkGrade >= 60 && finalMarkGrade < 70){
                this.letterGrade = "C";
            }
            else if (finalMarkGrade >= 70 && finalMarkGrade < 80){
                this.letterGrade = "B";
            }
            else{
                this.letterGrade = "A";
            }
        }
        public String getLetterGrade() {
            return letterGrade;
        }

        public double getFinalMarkGrade() {
            return finalMarkGrade;
        }

        public double getFinalExamGrade() {
            return finalExamGrade;
        }

        public double getMidtermGrade() {
            return midtermGrade;
        }

        public double getAssignmentGrade() {
            return assignmentGrade;
        }

        public String getStudentID() {
            return studentID;
        }
    }

    public static class DataSource {
        public static ObservableList<StudentRecord> getAllMarks() {
            ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
            // Student ID, Assignments, Midterm, Final exam
            marks.add(new StudentRecord("100100100", 75.0f, 68.0f, 54.25f));
            marks.add(new StudentRecord("100100101", 70.0f, 69.25f, 51.5f));
            marks.add(new StudentRecord("100100102", 100.0f, 97.0f, 92.5f));
            marks.add(new StudentRecord("100100103", 90.0f, 88.5f, 68.75f));
            marks.add(new StudentRecord("100100104", 72.25f, 74.75f, 58.25f));
            marks.add(new StudentRecord("100100105", 85.0f, 56.0f, 62.5f));
            marks.add(new StudentRecord("100100106", 70.0f, 66.5f, 61.75f));
            marks.add(new StudentRecord("100100107", 55.0f, 47.0f, 50.5f));
            marks.add(new StudentRecord("100100109", 82.5f, 77.0f, 74.25f));

            return marks;
        }
    }

    public void initialize(){
        sID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midtermGrade"));
        assignment.setCellValueFactory(new PropertyValueFactory<>("assignmentGrade"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExamGrade"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMarkGrade"));
        letter.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tabView.setItems(DataSource.getAllMarks());
        currentFilename = null;
    }

    @FXML
    private void onNewClick(ActionEvent e){
        tabView.getItems().clear();
    }

    @FXML
    private void onOpenClick(ActionEvent e){
        chooseFile();
        load();
    }

    @FXML
    private void onSaveClick(ActionEvent e){
        save();
    }

    @FXML
    private void onSaveAsClick(ActionEvent e){
        chooseFile();
        save();
    }

    @FXML
    private void onExitClick(ActionEvent e){
        Platform.exit();
    }

    @FXML
    private void chooseFile(){
        Stage stage = (Stage) aPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        currentFilename = fileChooser.showOpenDialog(stage);
    }

    private void load(){
        ObservableList<StudentRecord> tabData = FXCollections.observableArrayList();
        try {
            BufferedReader r = new BufferedReader(new FileReader(currentFilename));

            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);

                StudentRecord record = new StudentRecord(fields[0], Double.parseDouble(fields[1]), Double.parseDouble(fields[2]),
                        Double.parseDouble(fields[3]));
                tabData.add(record);
                tabView.setItems(tabData);
            }
        } catch(Exception err){
            err.printStackTrace();
        }
    }

    private void save(){
        Writer w = null;
        try{
            w = new BufferedWriter(new FileWriter(currentFilename));
            for (StudentRecord student: tabView.getItems()){
                String line = student.getStudentID() + "," + student.getMidtermGrade() + "," + student.getAssignmentGrade() + "," + student.getFinalExamGrade() + "\n";
                w.write(line);
            }
        } catch(Exception err){
            err.printStackTrace();
        }
        finally {
            try {
                if (w != null) {
                    w.flush();
                    w.close();
                }
            } catch (IOException ioErr) {
                ioErr.printStackTrace();
            }
        }
    }
}



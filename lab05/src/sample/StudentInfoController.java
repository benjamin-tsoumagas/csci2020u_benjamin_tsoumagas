package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentInfoController {
    public TextField studentID;
    public TextField midtermGrade;
    public TextField assignmentGrade;
    public TextField finalExamGrade;
    public Button button;
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
    public void handleButtonAction(ActionEvent e) {
        DataSource.getAllMarks().add(new StudentRecord(
                studentID.getText(),
                Double.parseDouble(midtermGrade.getText()),
                Double.parseDouble(assignmentGrade.getText()),
                Double.parseDouble(finalExamGrade.getText())));
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
    }
}

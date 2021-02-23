package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 04 Solution");

        /* Username, Password, Full name, E-mail Address,
        * Phone number (format: 000-000-0000)
        * Date of Birth ( date ) */

        //Grid formatting
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Username (String)
        Label username = new Label("Username: ");
        grid.add(username, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        userTextField.setPromptText("Enter your username");

        //Password (String)
        Label password = new Label("Password: ");
        grid.add(password, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox,1,2);
        pwBox.setPromptText("Enter your password");

        //Full name (String)
        Label fullName = new Label("Full name: ");
        grid.add(fullName, 0, 3);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 3);
        nameTextField.setPromptText("Enter your full name");

        //E-mail address (String)
        Label address = new Label("E-mail address: ");
        grid.add(address, 0, 4);

        TextField addressTextField = new TextField();
        grid.add(addressTextField, 1, 4);
        addressTextField.setPromptText("Enter your E-mail address");

        //Phone Number (String (000-000-0000))
        Label phoneNumber = new Label("Phone Number: ");
        grid.add(phoneNumber, 0, 5);

        TextField numberTextField = new TextField();
        grid.add(numberTextField, 1, 5);
        numberTextField.setPromptText("Enter your phone number");

        //Date of Birth ( Date )
        Label dateOfBirth = new Label("Date of Birth: ");
        grid.add(dateOfBirth, 0, 6);

        DatePicker dob = new DatePicker();
        grid.add(dob, 1, 6);
        dob.setPromptText("Select your date of birth");

        //Register
        Button register = new Button("Register");
        grid.add(register, 0, 7);
        register.setOnAction(arg0 -> {
            System.out.println("Username: " + userTextField.getText());
            System.out.println("Password: " + pwBox.getText());
            System.out.println("Full name: " + nameTextField.getText());
            Alert errorEmail = new Alert(Alert.AlertType.ERROR);
            if (!isValidAddress(addressTextField.getText())) {
                errorEmail.setContentText("Invalid e-mail address");
                errorEmail.showAndWait();
                addressTextField.setText(null);
            }
            else {
                System.out.println("E-mail address: " + addressTextField.getText());
            }
            if (!isValidNumber(numberTextField.getText())) {
                errorEmail.setContentText("Invalid phone number");
                errorEmail.showAndWait();
                numberTextField.setText(null);
            }
            else {
                System.out.println("Phone Number: " + numberTextField.getText());
            }
        });

        Scene scene = new Scene(grid, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidAddress(String text) {
        return text.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidNumber(String text) {
        return text.matches("^(\\d{3}[-]?){2}\\d{4}$");
    }


    public static void main(String[] args) { launch(args); }
}

package Main.UI;

import Main.Controller.Controller;
import Main.Model.*;
import Main.Repository.CourseSqlRepository;
import Main.Repository.StudentSqlRepository;
import Main.Repository.TeacherSqlRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class GUIStudentView extends Application {

    Controller controller;
    Student userStudent;

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        String connURL="jdbc:mysql://localhost:3306/mapsqlproject";
        String connUser="root";
        String connPassword="1234";
        TeacherSqlRepository tr = new TeacherSqlRepository(connURL,connUser,connPassword);
        CourseSqlRepository cr = new CourseSqlRepository(connURL,connUser,connPassword);
        StudentSqlRepository sr = new StudentSqlRepository(connURL,connUser,connPassword);

        controller = new Controller(cr,tr,sr);


        GridPane layout = new GridPane();
        Scene sceneLogin = new Scene(layout,300,250);
        primaryStage.setScene(sceneLogin);
        layout.setHgap(10);
        layout.setVgap(10);
        primaryStage.setTitle("Student Manager");
        TextField loginIdField = new TextField();
        Button buttonLogin = new Button();
        buttonLogin.setText("Login");
        layout.add(loginIdField,1,1);
        layout.add(buttonLogin,1,2);

        buttonLogin.setOnAction(e-> {
            try {
                userStudent = controller.getStudent(parseInt(loginIdField.getText()));
            } catch (Exception ex) {
                userStudent = null;
            }
            System.out.println(userStudent);
        });
        primaryStage.show();
    }
}

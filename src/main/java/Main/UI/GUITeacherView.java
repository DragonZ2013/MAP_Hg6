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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class GUITeacherView extends Application {

    Controller controller;
    Teacher userTeacher;

    public static void main(String[] args){

        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
        String connURL="jdbc:mysql://localhost:3306/mapsqlproject";
        String connUser="root";
        String connPassword="1234";
        TeacherSqlRepository tr = new TeacherSqlRepository(connURL,connUser,connPassword);
        CourseSqlRepository cr = new CourseSqlRepository(connURL,connUser,connPassword);
        StudentSqlRepository sr = new StudentSqlRepository(connURL,connUser,connPassword);

        controller = new Controller(cr,tr,sr);


        GridPane layoutLogin = new GridPane();
        Scene sceneLogin = new Scene(layoutLogin,300,250);
        primaryStage.setScene(sceneLogin);

        layoutLogin.setHgap(10);
        layoutLogin.setVgap(10);
        primaryStage.setTitle("Teacher Manager");
        Label loginLabel = new Label("Teacher Id:");
        TextField loginIdField = new TextField();
        Button buttonLogin = new Button();
        buttonLogin.setText("Login");
        layoutLogin.add(loginLabel,1,1);
        layoutLogin.add(loginIdField,2,1);
        layoutLogin.add(buttonLogin,1,2);
        GridPane layoutTeacher = new GridPane();
        layoutTeacher.setHgap(10);
        layoutTeacher.setVgap(10);
        Scene sceneTeacher = new Scene(layoutTeacher,1280,720);
        Button buttonRefresh = new Button();
        buttonRefresh.setText("Refresh");
        layoutTeacher.add(buttonRefresh,2,1);


        buttonLogin.setOnAction(e-> {
            try {
                userTeacher = controller.getTeacher(parseInt(loginIdField.getText()));
            } catch (Exception ex) {
                userTeacher = null;
            }
            if(userTeacher!=null){
                Label userInfo = new Label("Teacher Id: "+userTeacher.getTeacherId()+" First Name: "+userTeacher.getFirstName()+" Last Name: " + userTeacher.getLastName());
                layoutTeacher.add(userInfo,1,1);
                primaryStage.setScene(sceneTeacher);}
        });
        primaryStage.show();

    }
}

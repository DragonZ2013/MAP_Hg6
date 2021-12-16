package Main.UI;

import Main.Controller.Controller;
import Main.Model.*;
import Main.Repository.CourseSqlRepository;
import Main.Repository.StudentSqlRepository;
import Main.Repository.TeacherSqlRepository;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Integer.parseInt;

public class GUITeacherView extends Application {

    Controller controller;
    Teacher userTeacher;
    ObservableList<Pair<Integer,Integer>> enrollments;

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
        AtomicReference<ArrayList<Pair<Integer, Integer>>> enrolledStudents= new AtomicReference<>(new ArrayList<>());
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
        buttonRefresh.setText("Update");
        layoutTeacher.add(buttonRefresh,2,2);
        TextField courseIdField = new TextField();
        layoutTeacher.add(courseIdField,1,2);

        ListView<Student> listViewStudents = new ListView();
        listViewStudents.setPrefWidth(600);
        listViewStudents.setPrefHeight(800);
        layoutTeacher.add(listViewStudents,1,3);



        buttonLogin.setOnAction(e-> {
            try {
                userTeacher = controller.getTeacher(parseInt(loginIdField.getText()));
            } catch (Exception ex) {
                userTeacher = null;
            }
            if(userTeacher!=null){
                Label userInfo = new Label("Teacher Id: "+userTeacher.getTeacherId()+" First Name: "+userTeacher.getFirstName()+" Last Name: " + userTeacher.getLastName());
                layoutTeacher.add(userInfo,1,1);
                primaryStage.setScene(sceneTeacher);
                buttonRefresh.setOnAction(e2->{
                    listViewStudents.getItems().clear();
                    if(!courseIdField.getText().equals(""))
                        if(enrolledStudents(parseInt(courseIdField.getText()),userTeacher.getTeacherId())!=null)
                            for(int s: enrolledStudents(parseInt(courseIdField.getText()),userTeacher.getTeacherId())) {
                                try {
                                    listViewStudents.getItems().add(controller.getStudent(s));
                                } catch (SQLException ex) {
                                    listViewStudents.getItems().clear();
                                }
                            }

                    });
            }
        });
        primaryStage.show();

    }


    public List<Integer> enrolledStudents(int courseId,int teacherId){

        Course c = null;
        try{
            c = controller.getCourse(courseId);
        } catch (SQLException throwables) {
            return null;
        }
        if(c==null)
            return null;
        if(c.getTeacher()!=teacherId)
            return null;
        return c.getStudentsEnrolled();

    }

}

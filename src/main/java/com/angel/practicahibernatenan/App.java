package com.angel.practicahibernatenan;

import com.angel.practicahibernatenan.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HibernateUtil.getSessionFactory();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Equipos.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Gestión Liga Baloncesto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
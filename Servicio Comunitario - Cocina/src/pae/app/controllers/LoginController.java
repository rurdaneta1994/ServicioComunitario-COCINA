package pae.app.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import pae.dbconnections.PostgresDbConnection;

import java.io.IOException;

public class LoginController {

    @FXML JFXButton loginButton;

    //Carga la vista AbastecerController.fxml
    public void ingresarCocina(ActionEvent event) throws IOException {
        Parent abastecer_cocina= FXMLLoader.load(getClass().getResource("/pae/app/views/HomeView.fxml"));
        Scene abastecer_cocina_scene=new Scene(abastecer_cocina);
        Stage app_stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(abastecer_cocina_scene);
        app_stage.show();
    }
}
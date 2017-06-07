package pae.app.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import pae.alimentos.controllers.AbastecerController;

public class HomeController {

    @FXML JFXButton abastecerButton;

    public void abastecer(ActionEvent event) throws Exception {

        /*Parent abastecer_cocina= FXMLLoader.load(getClass().getResource("/pae/alimentos/views/AbastecerView.fxml"));
        Scene abastecer_cocina_scene=new Scene(abastecer_cocina);
        Stage app_stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(abastecer_cocina_scene);
        app_stage.show();*/
        AbastecerController abastecerController = new AbastecerController();
        abastecerController.init();
        abastecerController.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}
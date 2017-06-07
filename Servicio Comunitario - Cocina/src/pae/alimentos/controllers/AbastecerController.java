package pae.alimentos.controllers;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import pae.dbconnections.DbException;
import pae.dbconnections.PostgresDbConnection;
import pae.alimentos.dbconnections.AlimentoDbAdapter;
import pae.alimentos.models.Alimento;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AbastecerController extends Application implements Initializable{

    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtCantidad;
    @FXML private Label txtDisponible;
    @FXML private JFXTextField txtCantidadNueva;
    @FXML private JFXTextField txtDescripcion;
    @FXML private JFXComboBox<String> txtComboBox = new JFXComboBox<String>();

    static String db = "Alimentos_AndresBello";
    static String user = "postgres";
    static String pass = "zulay2612";
    static String url = "jdbc:postgresql://localhost:5432/" + db;
    static PostgresDbConnection conn;
    private List<Alimento> alimentos;

    @Override
    public void start(Stage primaryStage) throws DbException, IOException {

        System.out.println("Soy Abastecer desde Abastecer");
        conn = new PostgresDbConnection (url, db, user, pass);
        conn.open(true);

        Parent abastecer_cocina= FXMLLoader.load(getClass().getResource("/PAE/alimentos/views/AbastecerView.fxml"));
        Scene abastecer_cocina_scene=new Scene(abastecer_cocina);
        primaryStage.setScene(abastecer_cocina_scene);
        primaryStage.show();

    }


    @Override
    public void initialize (URL location, ResourceBundle resource){
        AlimentoDbAdapter ad = new AlimentoDbAdapter();
        alimentos = ad.getList(conn, null);

        txtComboBox.getItems().removeAll(txtComboBox.getItems());
        txtComboBox.getItems().addAll(getNames(alimentos));
        txtComboBox.getSelectionModel().select(0);

        txtDisponible.setText(Double.toString(
                alimentos.get(txtComboBox.getSelectionModel().getSelectedIndex()).getCantidad()));

        txtComboBox.setOnAction(e -> txtDisponible.setText(Double.toString(
                alimentos.get(txtComboBox.getSelectionModel().getSelectedIndex()).getCantidad())));
    }

    private List<String> getNames(List<Alimento> alimentos){
        List<String> aNames = new ArrayList<>();
        for (Alimento a: alimentos){
            aNames.add(a.getNombre());
        }
        return  aNames;
    }
           //Metodo que activa el boton crear insumo correspondiente .....
    public void crearInsumo(ActionEvent event){
        AlimentoDbAdapter ad = new AlimentoDbAdapter();

        Alimento alim = new Alimento(txtNombre.getText(),
                Double.parseDouble(txtCantidadNueva.getText()),
                txtDescripcion.getText());

        ad.insertRecord(conn, alim, null);
        txtComboBox.getItems().addAll(alim.getNombre());
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCantidadNueva.setText("");
        alimentos.add(alim);

    }


    public void actualizarInsumo(ActionEvent event){
        String nombre=txtComboBox.getSelectionModel().getSelectedItem();
        conn = new PostgresDbConnection (url, db, user, pass);
        double cantidad=Double.parseDouble(txtCantidad.getText());
         int indice=txtComboBox.getSelectionModel().getSelectedIndex();
            AlimentoDbAdapter ad=new AlimentoDbAdapter();
         double nueva_cantidad_disponible=0.0;
            double cantidad_disponible=0.0;
            if(indice>=0){
                if(cantidad>0){
                   Alimento alimento=new Alimento(nombre,cantidad,"");
                    cantidad_disponible=ad.getCantidad(conn,null,alimento);
                    ad.updateInsumo(conn,alimento,null,cantidad_disponible);
                    txtCantidad.setText("");
                   alimentos=ad.getList(conn,null);
                   txtDisponible.setText(Double.toString(alimentos.get(txtComboBox.getSelectionModel().getSelectedIndex()).getCantidad()));
                    alertas("Actualización de Insumos","Se ha actualizado la cantidad disponible de "+alimento.getNombre()+"");
                }
            }

    }





           //todo tipo de alertas necesarias solo pasarle el titulo y el mensaje que llevará
    public static void alertas(String title,String mensaje){
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Macharia
 */
public class LoginController implements Initializable {
    @FXML private VBox parentPane;
    @FXML private VBox contentPane;
    
    @FXML
    private void logInAction(ActionEvent event) {
        try
        {
            Stage stage = (Stage)((Scene)((Button)event.getSource()).getScene()).getWindow();
        
            Parent root = FXMLLoader.load( getClass().getResource("home.fxml"));

            Scene scene = new Scene( root );

            stage.setScene( scene );
            
            stage.show();
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @FXML
    private void signUpAction(ActionEvent event) {
       try
        {
            Stage stage = (Stage)((Scene)((Button)event.getSource()).getScene()).getWindow();
        
            Parent root = FXMLLoader.load( getClass().getResource("employee_signup.fxml"));

            Scene scene = new Scene( root );

            stage.setScene( scene );
            
            stage.show();
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try
        {
            parentPane.setId("parentPane");
            contentPane.setId("contentPane");
        }catch(Exception e)
        {
            UtilityClass.alert("Error: " + e.getMessage());
        } 
    }    
    
}

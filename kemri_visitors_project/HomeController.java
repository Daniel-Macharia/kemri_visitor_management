/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Daniel Macharia
 */
public class HomeController implements Initializable {
    @FXML AnchorPane anchor;
    @FXML TabPane parentPane;
    @FXML Button logout;
    @FXML Tab registrationTab, checkInTab, CheckOutTab, reportTab;
    @FXML private VBox contentPane;
    @FXML private HBox registerBox, checkInBox, checkOutBox, getReportBox, generateQRBox;
    @FXML ScrollPane report_scroll_pane;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Node node = FXMLLoader.load( getClass().getResource("registerVisitor.fxml"));
            contentPane.getChildren().add(node);
            resetBoxes();
            UtilityClass.inform("finished resetting boxes");
            registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            
        } catch (IOException e) {
            UtilityClass.alert("IO Error initializing home screen: " + e);
        }catch (Exception e) {
            UtilityClass.alert("Error initalizing home screen: " + e);
        }

    }
    
    @FXML
    public void handleVisitorCheckInAction(ActionEvent event)
    {
        try
        {
            Parent node = FXMLLoader.load( getClass().getResource("visitorCheckIn.fxml"));

            contentPane.getChildren().remove( 0 );
            contentPane.getChildren().add(node);
            resetBoxes();
            checkInBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );

        }catch(Exception e)
        {
            UtilityClass.alert("Error loading resource: " + e);
        }
    }
    
    @FXML
    private void handleLogOutAction( ActionEvent event )
    {
        try
        {
            Stage stage = (Stage) ( (Scene)( ( (Button)event.getSource() ).getScene() ) ).getWindow();
            Parent root = FXMLLoader.load( getClass().getResource("Login.fxml") );
            
            Scene scene = new Scene( root );
            stage.setScene(scene);
            stage.show();
        }catch(Exception e)
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    private void resetBoxes()
    {
        try
        {
            registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#dddddd"), null, null))  );
            checkInBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#dddddd"), null, null))  );
            checkOutBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#dddddd"), null, null))  );
            getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#dddddd"), null, null))  );
            generateQRBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#dddddd"), null, null))  );
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @FXML
    private void handleGenerateQRCodeAction(ActionEvent event)
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("generateQRCode.fxml"));

            contentPane.getChildren().remove( 0 );
            contentPane.getChildren().add(node);
            resetBoxes();
            generateQRBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );

        }catch(Exception e)
        {
            UtilityClass.alert("Error loading resource: " + e);
        }
    }
    
    @FXML
    private void handleGetVisitReportAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("visitReport.fxml"));

            contentPane.getChildren().remove( 0 );
            contentPane.getChildren().add(node);
            resetBoxes();
            getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @FXML
    private void handleCheckOutAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("visitorCheckOut.fxml"));

            contentPane.getChildren().remove( 0 );
            contentPane.getChildren().add(node);
            resetBoxes();
            checkOutBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @FXML
    private void handleVisitorRegistrationAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("registerVisitor.fxml") );
            
            contentPane.getChildren().remove( 0 );
            contentPane.getChildren().add(node);

            resetBoxes();
            registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );

        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
}
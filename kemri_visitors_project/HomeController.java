
package kemri_visitors_project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Daniel Macharia
 */
public class HomeController implements Initializable {
    @FXML private Button menuItem, registerButton, checkInButton, checkOutButton, getReportButton;
    @FXML private Button checkInOrOutButton, getStaffReportButton, generateQRCodeButton;
    @FXML private VBox homeContentPane, staffHomeContentPane;
    @FXML private HBox registerBox, checkInBox, checkOutBox, getReportBox, generateQRBox;

    public static String securityNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Node node = FXMLLoader.load( getClass().getResource("registerVisitor.fxml"));
            homeContentPane.getChildren().add(node);
            
            registerButton.setTextFill( Paint.valueOf("#408ae4"));

            Node staffNode = FXMLLoader.load( getClass().getResource("staffCheckInOrOut.fxml"));
            staffHomeContentPane.getChildren().add(staffNode);
            
            checkInOrOutButton.setTextFill( Paint.valueOf("#408ae4"));
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

            homeContentPane.getChildren().remove( 0 );
            homeContentPane.getChildren().add(node);
            resetBoxes();
            
            checkInButton.setTextFill( Paint.valueOf("#408ae4"));
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
            /*registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            checkInBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            checkOutBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );*/
            //generateQRBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            registerButton.setTextFill( Paint.valueOf("#b51b72"));
            checkInButton.setTextFill( Paint.valueOf("#b51b72"));
            checkOutButton.setTextFill( Paint.valueOf("#b51b72"));
            getReportButton.setTextFill( Paint.valueOf("#b51b72"));
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    @FXML
    private void handleGetVisitReportAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("visitReport.fxml"));

            homeContentPane.getChildren().remove( 0 );
            homeContentPane.getChildren().add(node);
            resetBoxes();
            //getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            getReportButton.setTextFill( Paint.valueOf("#408ae4"));
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

            homeContentPane.getChildren().remove( 0 );
            homeContentPane.getChildren().add(node);
            resetBoxes();
            //checkOutBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            checkOutButton.setTextFill( Paint.valueOf("#408ae4"));
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
            
            homeContentPane.getChildren().remove( 0 );
            homeContentPane.getChildren().add(node);

            resetBoxes();
            //registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            registerButton.setTextFill( Paint.valueOf("#408ae4"));
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

    //methods handling staff section
    private void resetStaffButtons()
    {
        try
        {
            //registerBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            //checkInBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            /*checkInOrOutBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );
            generateQRBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("transparent"), null, null))  );*/
            checkInOrOutButton.setTextFill( Paint.valueOf("#b51b72"));
            getStaffReportButton.setTextFill( Paint.valueOf("#b51b72"));
            generateQRCodeButton.setTextFill( Paint.valueOf("#b51b72"));
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

            staffHomeContentPane.getChildren().remove( 0 );
            staffHomeContentPane.getChildren().add(node);
            resetStaffButtons();
            //generateQRBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            //generateQRCodeButton.setBackground( new Background( new BackgroundFill( Paint.valueOf("#22bae5"), null, null))  );
            generateQRCodeButton.setTextFill( Paint.valueOf("#408ae4"));
        }catch(Exception e)
        {
            UtilityClass.alert("Error loading resource: " + e);
        }
    }

    @FXML
    private void handleGetStaffVisitReportAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("staffVisitReport.fxml"));

            staffHomeContentPane.getChildren().remove( 0 );
            staffHomeContentPane.getChildren().add(node);
            resetStaffButtons();
            //getReportBox.setBackground( new Background( new BackgroundFill( Paint.valueOf("#ffffff"), null, null))  );
            //getReportButton.setBackground( new Background( new BackgroundFill( Paint.valueOf("#22bae5"), null, null))  );
            getStaffReportButton.setTextFill( Paint.valueOf("#408ae4"));
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

    @FXML
    private void handleCheckInOrOutAction( ActionEvent event )
    {
        try
        {
            Node node = FXMLLoader.load( getClass().getResource("staffCheckInOrOut.fxml"));

            staffHomeContentPane.getChildren().remove( 0 );
            staffHomeContentPane.getChildren().add(node);
            resetStaffButtons();
            //checkInOrOutButton.setBackground( new Background( new BackgroundFill( Paint.valueOf("#22bae5"), null, null))  );
            checkInOrOutButton.setTextFill( Paint.valueOf("#408ae4"));

        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

}
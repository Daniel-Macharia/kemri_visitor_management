/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Daniel Macharia
 */
public class VisitorCheckInController implements Initializable {

    @FXML
    private TextField visitor_id;
    @FXML
    private TextField visitor_origin;
    @FXML
    private ComboBox<String> visitor_destination;
    @FXML
    private TextField visit_reason;
    @FXML
    private RadioButton visit_on_foot;
    @FXML
    private RadioButton visit_by_vehicle;
    @FXML
    private Label number_plate_label;
    @FXML
    private TextField number_plate_input;
    
    private ToggleGroup foot_or_vehicle;
    
    private final Destination destinationList = new Destination();
    private ObservableList<String> items;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        foot_or_vehicle = new ToggleGroup();
        visit_on_foot.setToggleGroup(foot_or_vehicle);
        visit_by_vehicle.setToggleGroup(foot_or_vehicle);
        initDestination();
    }    
    
    @FXML
    private void handleVisitorCheckInAction(ActionEvent event)
    {
        try{
            String id = visitor_id.getText();
            String origin = visitor_origin.getText();
            String destination = visitor_destination.getValue();
            String reason = visit_reason.getText();
            
            
            String numberPlate = null;
            
            if( visit_by_vehicle.isSelected() )
            {
                numberPlate = number_plate_input.getText();
            }
            else if( visit_on_foot.isSelected() )
            {
                numberPlate = "NULL";
            }
            
            if( validCheckInDetails( id, origin, reason, numberPlate ) )
            {
                checkInVisitor( HomeController.securityNumber, id, origin, destination, reason, numberPlate );
            }
            
        }catch( Exception e )
        {
            System.out.println("Error: " + e);
        }
    }
    
    
    private void initDestination()
    {
        try
        {
            String sql = "SELECT * FROM DESTINATION";
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement(sql);
            
            ResultSet result = state.executeQuery();
            
            while( result.next() )
            {
                destinationList.add( result.getString(1), result.getString(2) );
            }
            
            items = FXCollections.observableArrayList(destinationList.getNames());
            
            visitor_destination.setItems(items);
            visitor_destination.setValue( new String( items.get(0) ) );
            
        }catch( Exception e )
        {
            System.out.println("Error: " + e);
        }
    }
    
    @FXML
    private void onFootAction( ActionEvent event )
    {
        showNumberPlateField(false);
    }
    
    @FXML
    private void byVehicleAction( ActionEvent event )
    {
        showNumberPlateField(true);
    }
    
    private void showNumberPlateField(boolean byVehicle)
    {
        try
        {
            
            number_plate_label.setVisible(byVehicle);
            number_plate_input.setVisible(byVehicle);
            
        }catch( Exception e )
        {
            System.out.println("Error: " + e);
        }
            
    }
    
    private void clearCheckInDetails()
    {
        visitor_id.setText("");
        visitor_origin.setText("");
        visitor_destination.setValue( new String( items.get(0) ) );
        visit_reason.setText("");
        
        visit_on_foot.setSelected(false);
        visit_by_vehicle.setSelected(false);
        number_plate_input.setText("");
        showNumberPlateField(false);
        
    }
    
    private boolean validCheckInDetails( String id, String origin, String reason, String numberPlate)
    {
        if( !UtilityClass.isValidIdNumber( id ) )
        {
            UtilityClass.alert("Invalid ID number.\nId number contains 8 digits.");
            return false;
        }
        
        if( !UtilityClass.isValidOrigin( origin ) )
        {
            UtilityClass.alert("Origin may contain only characters and digits");
            return false;
        }
        
        if( !UtilityClass.isValidUsername(reason) )
        {
            UtilityClass.alert("Reason may contain only characters, a space and an apostrophe.");
            return false;
        }
        
        if( numberPlate == null )
        {
            UtilityClass.alert("Please select means!");
            return false;
        }
        else if( !numberPlate.equals("NULL") )
        {
            if( !UtilityClass.isValidNumberPlate( numberPlate ) )
            {
                UtilityClass.alert("Invalid number plate!\nNumber plate contains 3 letters followed by a space then \n 3 digits followed by a character e.g XYZ 222H");
                return false;
            }
        }
        
        return true;
    }
    
    private void checkInVisitor( String securityNumber, String id, String origin, String destination, String reason, String numberPlate )
    {
        try
        {
            Date date = new Date();
            Scanner s = new Scanner( date.toString() );
        
            String y, m, d;

            s.next();
            m = getMonth( s.next() ) + "";
            d = s.next();
            s.next();
            s.next();
            y = s.next();

            String dateString = y + "-" + m + "-" + d;
            UtilityClass.inform("Date: " + dateString);
            String time_out = "";
            String time_in = getTimeString();
            
            String sql = "INSERT INTO VISIT(VISIT_DATE,"
                    + "VISIT_NUMBER,"
                    + "ID_NUMBER,"
                    + "SECURITY_NUMBER,"
                    + "VISITOR_ORIGIN,"
                    + "VISITOR_DESTINATION_ID,"
                    + "VISIT_REASON,"
                    + "VISITOR_TIME_IN,"
                    + "VISITOR_TIME_OUT,"
                    + "VEHICLE_NUMBER_PLATE) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement( sql );
            
            state.setString(1, dateString);
            state.setInt(2, UtilityClass.visitCount + 1);
            state.setString(3, id);
            state.setString(4, securityNumber);
            state.setString(5, origin);
            state.setString(6, destinationList.getId( destination ) );
            state.setString(7, reason);
            state.setString(8, time_in);
            state.setString(9, time_out);
            state.setString(10, numberPlate);
            
            int rows = state.executeUpdate();
            
            if( rows == 1 )
            {
                DateNumber.incrementDBCount(false);
                clearCheckInDetails();
                UtilityClass.inform("Successfully checked in\n" + "Your visit number is\n\n" + (UtilityClass.visitCount) );
                SendSms sms = new SendSms(getPhoneNumber("VISITOR", id), "Dear visitor, your number is " + UtilityClass.visitCount );
                sms.send();
            }
            else
            {
                UtilityClass.alert("Failed to check in!");
            }
            
        }catch(MySQLIntegrityConstraintViolationException e )
        {
            UtilityClass.alert("Visitor not Registered!\nPlease Register first." + "\nVisit Number: " + UtilityClass.visitCount);
        }
        catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    private String getPhoneNumber(String tableName, String idNumber)
    {
        String phone = null;
        
        try
        {
            String sql = "SELECT PHONE_NUMBER FROM " + tableName + " WHERE ID_NUMBER = ? ";
            
            Connection con = UtilityClass.getDatabaseConnection();
            PreparedStatement state = con.prepareStatement(sql);
            state.setString(1, idNumber);
            
            ResultSet result = state.executeQuery();
            
            if( result.first() )
            {
                phone = result.getString( 1 );
            }
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
        
        return phone;
    }
    
    private int getMonth( String month )
    {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for( int i = 0; i < months.length; i++ )
        {
            if( month.equals( months[i] ) )
                return i + 1;
        }
        
        return 0;
    }
    
    private String getTimeString()
    {
        Date date = new Date();
        Scanner s = new Scanner( date.toString() );

        String hr, min, sec;
        
        for( int i = 0; i < 3; i++ )
            s.next();
        
        String timeString = s.next();
        
        
        return timeString;
    }
    
}

class Destination
{
    private ArrayList<String> idList;
    private ArrayList<String> nameList;
    
    public Destination( )
    {
        idList = new ArrayList<String>();
        nameList = new ArrayList<String>();
    }
    
    public void add( String id, String name )
    {
        idList.add( new String(id) );
        nameList.add( new String( name ) );
    }
    
    public String getId( int index )
    {
        return idList.get( index );
    }
    
    public String getId( String name )
    {
        for( int i = 0; i < nameList.size(); i++ )
        {
            if( name.matches( nameList.get( i) ) )
                return idList.get(i);
        }
        
        return null;
    }
    
    public String getName( int index )
    {
        return nameList.get( index );
    }
    
    public ArrayList<String> getNames()
    {
        ArrayList<String> items = new ArrayList<>();
        
        for( String name : nameList )
            items.add( new String( name ) );
        
        return items;
    }
}
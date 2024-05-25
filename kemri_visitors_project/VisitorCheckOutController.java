/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Daniel Macharia
 */
public class VisitorCheckOutController implements Initializable {

    @FXML
    private TextField visitor_number_or_ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void handleVisitorCheckOutAction( ActionEvent event )
    {
        try
        {
            String number = visitor_number_or_ID.getText();
            
            //UtilityClass.alert("Number: " + number);
            
            if( isValidCheckOutId( number ) )
            {
                checkOutVisitor( number );
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    private boolean isValidCheckOutId( String idNumber )
    {
        if( !UtilityClass.isValidCheckInId( idNumber ) )
        {
            UtilityClass.alert("Please enter a valid id or visit number.");
            return false;
        }
        
        return true;
    }
    
    private void checkOutVisitor( String idNumber )
    {
        try
        {
            Date date = new Date();
            Scanner s = new Scanner( date.toString() );
        
            String y, m, d, time_out;

            s.next();
            m = getMonth( s.next() ) + "";
            d = s.next();
            time_out = s.next();
            s.next();
            y = s.next();

            String dateString = y + "-" + m + "-" + d;
            
            
            String sql = "UPDATE VISIT SET VISITOR_TIME_OUT = ? "
                    + "WHERE (VISIT_DATE = ? AND VISIT_NUMBER = ?) OR "
                    + "(VISIT_DATE = ? AND ID_NUMBER = ? )";
            
            Connection con = UtilityClass.getDatabaseConnection();
            PreparedStatement state = con.prepareStatement(sql);
            
            state.setString( 1, time_out);
            state.setString(2, dateString);
            state.setString(3, idNumber);
            state.setString(4, dateString);
            state.setString(5, idNumber);
            
            int rows = state.executeUpdate();
            
            if( rows == 1 )
            {
                clearCheckOutDetails();
                UtilityClass.inform("Successfully checked out!");
            }
            else
            {
                UtilityClass.alert("Failed to check out!\nConfirm the entered ID or visit number.");
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    private int getMonth( String month )
    {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for( int i = 0; i < months.length; i++ )
        {
            if( month.matches( months[i] ) )
                return i + 1;
        }
        
        return 0;
    }
    
    private void clearCheckOutDetails()
    {
        visitor_number_or_ID.setText("");
    }
}

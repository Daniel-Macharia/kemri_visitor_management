/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 *
 * @author Daniel Macharia
 */
public class RegisterVisitorController implements Initializable {
    @FXML private TextField visitor_ID_number, visitor_name, visitor_phone_number;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML 
    private void handleRegisterAction(ActionEvent event)
    {
        try
        {
            String id_number = visitor_ID_number.getText();
            String name = visitor_name.getText();
            String phone = visitor_phone_number.getText();
            
            if( validVisitorRegistrationDetails( id_number, name, phone ) )
            {
                registerNewVisitor( id_number, name, phone );
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
     private boolean validVisitorRegistrationDetails( String idNumber, String name, String phone )
    {
        if( !UtilityClass.isValidIdNumber( idNumber ) )
        {
            UtilityClass.alert("Invalid ID number!\nAn id number may contain only 8 digits.");
            return false;
        }
        
        if( !UtilityClass.isValidUsername( name ) )
        {
            UtilityClass.alert("Invalid name.\nA name may contain characters, a space or an apostrophe.");
            return false;
        }
        
        if( !UtilityClass.isValidPhoneNumber( phone ) )
        {
            UtilityClass.alert("Invalid phone number.\nEnter a valid Kenyan phone number\n e.g 07... or 01... and must be 10 digits long.");
            return false;
        }
        
        
        return true;
    }
     
     private void registerNewVisitor( String idNumber, String name, String phoneNumber )
    {
        try
        {
            String sql = "INSERT INTO VISITOR VALUES( ?, ?, ?)";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement(sql);
            
            state.setString(1, idNumber);
            state.setString(2, name);
            state.setString(3, phoneNumber);
            
            int rows = state.executeUpdate();
            
            if( rows == 1 )
            {
                UtilityClass.inform("Visitor registered successfully!");
                clearRegistrationDetails();
            }
            else
            {
                UtilityClass.alert("Fialed to register visitor!");
            }
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
     
    private void clearRegistrationDetails()
    {
        visitor_ID_number.setText("");
        visitor_name.setText("");
        visitor_phone_number.setText("");
    }
    
}

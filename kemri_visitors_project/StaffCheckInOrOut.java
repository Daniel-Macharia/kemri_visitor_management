package kemri_visitors_project;

import com.mysql.jdbc.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StaffCheckInOrOut implements Initializable {
    public static int visitCount = 0;
    @FXML private TextField staffNumber;
    @Override
    public void initialize(URL url, ResourceBundle bundle )
    {
        //init class members
    }

    @FXML
    public void handleStaffCheckInOrOutAction(ActionEvent event)
    {
        try
        {
            String number = staffNumber.getText();

            if( isValidStaffNumber( number ) )
            {
                if( isCheckedIn( number ) )
                {
                    checkOut( number );
                }
                else
                {
                    checkIn( number );
                }
            }
            
            clearStaffNumber();
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    private void clearStaffNumber()
    {
        staffNumber.clear();
    }

    private boolean isValidStaffNumber( String staffNumber )
    {
        if( !UtilityClass.isValidStaffNumber( staffNumber ) )
        {
            UtilityClass.alert("Invalid staff number!");
            return false;
        }

        return true;
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

    private String getDateString()
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

        return dateString;
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

    private boolean isCheckedIn( String staffNumber )
    {
        try{
            String dateString = getDateString();
            String sql = "SELECT STAFF_CHECK_IN_TIME, STAFF_CHECK_OUT_TIME " +
                    "FROM STAFF_VISIT " +
                    "WHERE VISIT_DATE=? AND STAFF_ID=?";
            Connection con = UtilityClass.getDatabaseConnection();
            PreparedStatement state = con.prepareStatement( sql );
            state.setString(1, dateString);
            state.setString(2, staffNumber);

            ResultSet result = state.executeQuery();

            while( result.next() )//an entry exists
            {
                if( result.getString(2).equals("") )
                {
                    return true;
                }
            }
        }catch ( Exception e )
        {
            UtilityClass.alert("Error: " + e );
        }
        return false;
    }

    private void checkIn( String staffNumber )
    {
        try
        {
            
            String time_out = "";
            String dateString = getDateString();
            String check_in_time = getTimeString();
            
            String sql = "INSERT INTO STAFF_VISIT VALUES (?, ?, ?, ?, ?, ?)";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement(sql);
            
            state.setString(1, dateString);
            state.setInt(2, StaffCheckInOrOut.visitCount + 1);
            state.setString(3, staffNumber);
            state.setString(4, "KEMRI");
            state.setString(5, check_in_time);
            state.setString(6, time_out);
            
            
            int rows = state.executeUpdate();
            
            if( rows == 1 )
            {
                UtilityClass.inform("Successfully checked in.");
                DateNumber.incrementDBCount(true);
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

    private void checkOut( String staffNumber )
    {
        try
        {
            String dateString = getDateString();
            String outTime = getTimeString();

            String sql = "UPDATE STAFF_VISIT " +
                    "SET STAFF_CHECK_OUT_TIME=? " +
                    "WHERE VISIT_DATE=? AND STAFF_ID=?";
            
            Connection con = UtilityClass.getDatabaseConnection();
            PreparedStatement state = con.prepareStatement( sql );
            state.setString(1, outTime);
            state.setString(2, dateString );
            state.setString(3, staffNumber);

            int rows = state.executeUpdate();

            if( rows > 0 )
            {
                UtilityClass.inform("Successfully checked out.");
            }
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

}

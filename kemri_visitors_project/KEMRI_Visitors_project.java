/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Daniel Macharia
 */
public class KEMRI_Visitors_project extends Application {
    
    @Override
    public void start(Stage stage) {
        
        try
        {
            DateNumber n = new DateNumber();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            
            stage.getIcons().add( new Image( getClass().getResourceAsStream("assets/KEMRI.png") ) );
            stage.setScene(scene);
            stage.setTitle("KENYA MEDICAL RESEARCH INSTITUTE VISITOR MANAGEMENT");

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());

            stage.show();
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}


class DateNumber
{
    private Date d;
    private int date, year;
    private int month;
    
    public DateNumber()
    {
        d = new Date();
        parse();
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
    
    private void parse()
    {
        Scanner s = new Scanner( this.d.toString() );
        
        String y, m, d;
        
        s.next();
        m = s.next();
        d = s.next();
        s.next();
        s.next();
        y = s.next();
        
        String date = y + " " + m + " " + d;
        year = Integer.parseInt( y );
        month = getMonth( m );
        this.date = Integer.parseInt( d );
        
        if( greaterThan( getStoredDate(false) ) )
        {
            //update date and init UtilityClass.visitCount to 0
            updateDBDate();
        }
        
    }
    
    public static void incrementDBCount( Boolean staff_count)
    {
        try
        {
            Connection con;
            PreparedStatement state;
            String sql;
            if( staff_count )
            {
                sql = "UPDATE DATE_NUMBER SET CURRENT_COUNT = ? WHERE IS_STAFF_COUNT='YES'";
                StaffCheckInOrOut.visitCount++;
                con = UtilityClass.getDatabaseConnection();

                state = con.prepareStatement(sql);

                state.setInt(1, StaffCheckInOrOut.visitCount );
            }
            else
            {
                sql = "UPDATE DATE_NUMBER SET CURRENT_COUNT = ? WHERE IS_STAFF_COUNT='NO'";
                UtilityClass.visitCount++;
                con = UtilityClass.getDatabaseConnection();

                state = con.prepareStatement(sql);

                state.setInt(1, UtilityClass.visitCount );
            }


            
            int rows = state.executeUpdate();
            
            if( rows != 1 )
            {
                UtilityClass.alert("Could not update number!");
            }
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }

    private boolean greaterThan( String storedDate )
    {
        String y, m, d;
        
        if( storedDate != null )
        {
            Scanner s = new Scanner( storedDate );
        
            y = s.next();
            m = s.next();
            d = s.next();
            
            int yr, mn, dt;
            yr = Integer.parseInt(y);
            mn = Integer.parseInt( m );
            dt = Integer.parseInt( d );
            
            if( yr < year )
            {
                return true;
            }
            else//check month
            {
                if( mn < month )
                {
                    return true;
                }
                else//check date
                {
                    if( dt < date )
                    {
                        return true;
                    }
                }
            }
        }
        
        
        
        return false;
    }
    
    private String getStoredDate( boolean isStaff)
    {
        try
        {
            String sql;
            if( isStaff )
            {
                sql = "SELECT * FROM DATE_NUMBER WHERE IS_STAFF_COUNT='YES'";
            }
            else
            {
                sql = "SELECT * FROM DATE_NUMBER WHERE IS_STAFF_COUNT='NO'";
            }
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement( sql );
            
            ResultSet result = state.executeQuery();
            if( result.first() )
            {
                String num, date;
                
                date = result.getString(2);
                UtilityClass.visitCount = result.getInt(3);
                
                return date;
            }
            
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
        return null;
    }
    
    private void updateDBDate()
    {
        try
        {
            String sql = "UPDATE DATE_NUMBER SET DATE_TODAY = ?,  CURRENT_COUNT = ?";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement(sql);
            
            state.setString( 1, year + " " + month + " " + date );
            state.setInt( 2, 0);
            
            int rows = state.executeUpdate();
            
            if( rows != 2 )
            {
                UtilityClass.alert("Invalid Date!");
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
}

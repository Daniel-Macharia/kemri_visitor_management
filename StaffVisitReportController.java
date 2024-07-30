/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author AdminAttachee
 */
public class StaffVisitReportController implements Initializable {

    @FXML private DatePicker date_to_report;
    @FXML private ListView report_list;
    
    private ArrayList<GridPane> reportList = new ArrayList<>();
    
    private int number = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initReportList();
        updateList();
    }
    
    
    @FXML
    private void handleGenerateVisitReportAction(ActionEvent event )
    {
        try
        {
            String date = date_to_report.getValue().toString();
            getReport(date, false);
        }catch( NullPointerException e )
        {
            UtilityClass.inform("No date selected!");
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    private void initReportList()
    {
        GridPane titles = getGridPane( true, "Number", "Staff ID",
                        "Staff Name",
                        "Staff phone",
                        "Station",
                        "Time in",
                        "Time out" );
        
        reportList.add(titles);
        //updateList();
    }
    
    private void updateList()
    {
        ObservableList<GridPane> visitReport = FXCollections.observableArrayList( reportList );
        report_list.setItems(visitReport);
    }
    
    private GridPane getGridPane(boolean isFirstRow, String s_Num, String s_id, String s_name, String s_phone, String s_station, String v_time_in, String v_time_out)
    {
        GridPane pane = new GridPane();
        
        Label sNumLabel = new Label(s_Num);
        sNumLabel.setMinWidth(80.0);
        sNumLabel.setMaxWidth(80.0);
        
        Label sIDLabel = new Label(s_id);
        sIDLabel.setMinWidth(120.0);
        sIDLabel.setMaxWidth(120.0);
        
        Label sNameLabel = new Label(s_name);
        sNameLabel.setMinWidth(180.0);
        sNameLabel.setMaxWidth(180.0);
        
        Label sPhoneLabel = new Label(s_phone);
        sPhoneLabel.setMinWidth(120.0);
        sPhoneLabel.setMaxWidth(120.0);
        
        Label sStationLabel = new Label(s_station);
        sStationLabel.setMinWidth(120.0);
        sStationLabel.setMaxWidth(120.0);
        
        Label sTimeInLabel = new Label(v_time_in);
        sTimeInLabel.setMinWidth(180.0);
        sTimeInLabel.setMaxWidth(180.0);
        
        Label sTimeOutLabel = new Label(v_time_out);
        sTimeOutLabel.setMinWidth(100.0);
        sTimeOutLabel.setMaxWidth(100.0);
        
        
        if( isFirstRow )
        {
            String paint = "#b51b72";
            sNumLabel.setTextFill(Paint.valueOf(paint));
            sIDLabel.setTextFill(Paint.valueOf(paint));
            sNameLabel.setTextFill(Paint.valueOf(paint));
            sPhoneLabel.setTextFill(Paint.valueOf(paint));
            sStationLabel.setTextFill(Paint.valueOf(paint));
            
            sTimeInLabel.setTextFill(Paint.valueOf(paint));
            sTimeOutLabel.setTextFill(Paint.valueOf(paint));
        }
        
        pane.add( sNumLabel, 0,0);
        pane.add( sIDLabel, 1, 0);
        pane.add( sNameLabel, 2, 0);
        pane.add( sPhoneLabel, 3, 0);
        pane.add( sStationLabel, 4, 0);
        pane.add( sTimeInLabel, 5, 0);
        pane.add( sTimeOutLabel, 6, 0);
        
        return pane;
    }
    
    private void getReport(String date, boolean download)
    {
        try
        {
            number = 0;
            reportList = null;
            reportList = new ArrayList<>();
            initReportList();
            
            String sql = "SELECT VISIT_NUMBER, STAFF_VISIT.STAFF_ID, STAFF_NAME, STAFF_PHONE, CHECK_IN_STATION, STAFF_CHECK_IN_TIME, STAFF_CHECK_OUT_TIME "
                    + "FROM STAFF_VISIT JOIN STAFF ON STAFF_VISIT.STAFF_ID=STAFF.STAFF_ID "
                    + "WHERE VISIT_DATE = ? "
                    + "ORDER BY VISIT_NUMBER";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement( sql );
            
            
            state.setString(1, date);
            
            ResultSet result = state.executeQuery();
            
            
            if( download )
            {
                new Thread( new Runnable(){
                
                    @Override
                    public void run()
                    {
                        try{
                            WriteToSpreadSheet sheet = new WriteToSpreadSheet( date, " Staff Visit Report", true);
                
                            while( result.next() )
                            {
                                sheet.insertStaffVisitRecord( result.getInt(1) + "",
                                    result.getString(2),
                                    result.getString(3),
                                    result.getString(4),
                                    result.getString(5),
                                    result.getString(6),
                                    result.getString(7));

                                number++;
                            }
                        }catch( Exception e )
                        {
                            UtilityClass.alert("Error: " + e);
                        }
                    }
                }).start();
                
            }
            else
            {
                while( result.next() )
                {
                    reportList.add( getGridPane( false, result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7) ) );

                    number++;
                }
            }
            
            if( download )
            {
                File f = new File( "assets/" + date + " Staff Visit Report.xlsx" );
                 
                if( f.exists() )
                {
                    Runtime.getRuntime().exec("explorer.exe /select," + f.getAbsolutePath());
                    //Process p = new ProcessBuilder("explorer.exe", "/select,'" + f.getAbsolutePath() + "'").start();
                }
                else
                {
                    UtilityClass.inform("Could not download report!");
                }
            }
            else
            {
                updateList();
            }
            
        }catch( Exception e )
        {
            UtilityClass.alert("Error: "+ e);
        }
    }
    
    @FXML
    private void handleDownloadVisitReportAction( ActionEvent event )
    {
        try
        {
            String date = date_to_report.getValue().toString();
            getReport(date, true);
        }catch( NullPointerException e )
        {
            UtilityClass.inform("No date selected!");
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
}

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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Daniel Macharia
 */
public class VisitReportController implements Initializable {
    
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
        GridPane titles = getGridPane( true, "Number", "Visitor ID",
                        "Visitor name",
                        "Visitor phone",
                        "Visitor`s origin",
                        "Visitor`s destination",
                        "Reason for visit",
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
    
    private GridPane getGridPane(boolean isFirstRow, String v_Num, String v_id, String v_name, String v_phone, String v_origin, String v_destination, String v_reason, String v_time_in, String v_time_out)
    {
        GridPane pane = new GridPane();
        
        Label vNumLabel = new Label(v_Num);
        vNumLabel.setMinWidth(80.0);
        vNumLabel.setMaxWidth(80.0);
        
        Label vIDLabel = new Label(v_id);
        vIDLabel.setMinWidth(120.0);
        vIDLabel.setMaxWidth(120.0);
        
        Label vNameLabel = new Label(v_name);
        vNameLabel.setMinWidth(180.0);
        vNameLabel.setMaxWidth(180.0);
        
        Label vPhoneLabel = new Label(v_phone);
        vPhoneLabel.setMinWidth(120.0);
        vPhoneLabel.setMaxWidth(120.0);
        
        Label vOriginLabel = new Label(v_origin);
        vOriginLabel.setMinWidth(120.0);
        vOriginLabel.setMaxWidth(120.0);
        
        Label vDestinationLabel = new Label(v_destination);
        vDestinationLabel.setMinWidth(180.0);
        vDestinationLabel.setMaxWidth(180.0);
        
        Label vReasonLabel = new Label(v_reason);
        vReasonLabel.setMinWidth(160.0);
        vReasonLabel.setMaxWidth(160.0);
        
        Label vTimeInLabel = new Label(v_time_in);
        vTimeInLabel.setMinWidth(180.0);
        vTimeInLabel.setMaxWidth(180.0);
        
        Label vTimeOutLabel = new Label(v_time_out);
        vTimeOutLabel.setMinWidth(100.0);
        vTimeOutLabel.setMaxWidth(100.0);
        
        
        if( isFirstRow )
        {
            String paint = "#b51b72";
            vNumLabel.setTextFill(Paint.valueOf(paint));
            vIDLabel.setTextFill(Paint.valueOf(paint));
            vNameLabel.setTextFill(Paint.valueOf(paint));
            vPhoneLabel.setTextFill(Paint.valueOf(paint));
            vOriginLabel.setTextFill(Paint.valueOf(paint));
            vDestinationLabel.setTextFill(Paint.valueOf(paint));
            vReasonLabel.setTextFill(Paint.valueOf(paint));
            vTimeInLabel.setTextFill(Paint.valueOf(paint));
            vTimeOutLabel.setTextFill(Paint.valueOf(paint));
        }
        
        pane.add( vNumLabel, 0,0);
        pane.add( vIDLabel, 1, 0);
        pane.add( vNameLabel, 2, 0);
        pane.add( vPhoneLabel, 3, 0);
        pane.add( vOriginLabel, 4, 0);
        pane.add( vDestinationLabel, 5, 0);
        pane.add( vReasonLabel, 6, 0);
        pane.add( vTimeInLabel, 7, 0);
        pane.add( vTimeOutLabel, 8, 0);
        
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
            
            String sql = "SELECT VISIT_NUMBER, VISIT.ID_NUMBER, VISITOR_NAME, PHONE_NUMBER,"
                    + "VISITOR_ORIGIN, DESTINATION_NAME, VISIT_REASON, VISITOR_TIME_IN, VISITOR_TIME_OUT "
                    + "FROM VISIT JOIN VISITOR ON VISIT.ID_NUMBER=VISITOR.ID_NUMBER "
                    + "JOIN DESTINATION ON VISIT.VISITOR_DESTINATION_ID=DESTINATION.DESTINATION_ID"
                    + " WHERE VISIT_DATE = ? "
                    + " ORDER BY VISIT.VISIT_NUMBER";
            
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
                        try
                        {
                            WriteToSpreadSheet sheet = new WriteToSpreadSheet( date, " Visitor report");
                
                            while( result.next() )
                            {
                                sheet.insert( result.getInt(1),
                                    result.getString(2),
                                    result.getString(3),
                                    result.getString(4),
                                    result.getString(5),
                                    result.getString(6),
                                    result.getString(7),
                                    result.getString(8),
                                    result.getString(9));

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
                        result.getString(7),
                        result.getString(8),
                        result.getString(9)) );

                    number++;
                }
            }
            
            if( download )
            {
                File f = new File( "assets/" + date + " Visitor Report.xlsx" );
                 
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


class WriteToSpreadSheet
{
    private String name;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private OutputStream out;
    private int count;
    
    public WriteToSpreadSheet( String date, String title )
    {
        this.name = new String( date + title + ".xlsx");
        workbook = new XSSFWorkbook();
        createSheet();
        count = 0;
    }

    private XSSFWorkbook getWorkbook(){ return this.workbook; }
    private XSSFSheet getSheet(){return this.sheet; }
    private String getName(){ return this.name; }
    
    private void createSheet()
    {
        try
        {
            File directory = new File("assets");
            if( ! directory.exists() )
                directory.mkdir();
            
            File sheet = new File("assets/" + getName() );
            
            if( !sheet.exists() )
                sheet.createNewFile();
            
            out = new FileOutputStream( sheet.getPath() );
            
            this.sheet = workbook.createSheet("visit report");
            workbook.write(out);
            
            
            insert(0, "ID Number", "Name", "Phone", "Origin", "Destination", "Reason", "Time In", "Time out");
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
    public void insert( int visitNumber, String idNumber, String visitorName, String phoneNumber,
            String visitorOrigin, String visitorDestination, String visitReason,
            String timeIn, String timeOut )
    {
        try
        {
            File f = new File( "assets/" + getName() );
            out = new FileOutputStream( f.getPath() );
            
            XSSFRow row = getSheet().createRow(count);
            
            getSheet().autoSizeColumn(0);
            XSSFCell cell1 = row.createCell(0);
            
            getSheet().autoSizeColumn(1);
            XSSFCell cell2 = row.createCell(1);
            
            getSheet().autoSizeColumn(2);
            XSSFCell cell3 = row.createCell(2);
            
            getSheet().autoSizeColumn(3);
            XSSFCell cell4 = row.createCell(3);
            
            getSheet().autoSizeColumn(4);
            XSSFCell cell5 = row.createCell(4);
            
            getSheet().autoSizeColumn(5);
            XSSFCell cell6 = row.createCell(5);
            
            getSheet().autoSizeColumn(6);
            XSSFCell cell7 = row.createCell(6);
            
            getSheet().autoSizeColumn(7);
            XSSFCell cell8 = row.createCell(7);
            
            getSheet().autoSizeColumn(8);
            XSSFCell cell9 = row.createCell(8);
            
            cell1.setCellValue(visitNumber);
            cell2.setCellValue(idNumber);
            cell3.setCellValue(visitorName);
            cell4.setCellValue(phoneNumber);
            cell5.setCellValue(visitorOrigin);
            cell6.setCellValue(visitorDestination);
            cell7.setCellValue(visitReason);
            cell8.setCellValue(timeIn);
            cell9.setCellValue(timeOut);
            
            workbook.write( out );
            count++;
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    } 
    
    public void insertStaffVisitRecord( int visitNumber, String idNumber, String staffName, String staffPhoneNumber,
            String staffStation, String timeIn, String timeOut )
    {
        try
        {
            File f = new File( "assets/" + getName() );
            out = new FileOutputStream( f.getPath() );
            
            XSSFRow row = getSheet().createRow(count);
            
            getSheet().autoSizeColumn(0);
            XSSFCell cell1 = row.createCell(0);
            
            getSheet().autoSizeColumn(1);
            XSSFCell cell2 = row.createCell(1);
            
            getSheet().autoSizeColumn(2);
            XSSFCell cell3 = row.createCell(2);
            
            getSheet().autoSizeColumn(3);
            XSSFCell cell4 = row.createCell(3);
            
            getSheet().autoSizeColumn(4);
            XSSFCell cell5 = row.createCell(4);
            
            getSheet().autoSizeColumn(5);
            XSSFCell cell6 = row.createCell(5);
            
            getSheet().autoSizeColumn(6);
            XSSFCell cell7 = row.createCell(6);
            
            cell1.setCellValue(visitNumber);
            cell2.setCellValue(idNumber);
            cell3.setCellValue(staffName);
            cell4.setCellValue(staffPhoneNumber);
            cell5.setCellValue(staffStation);
            cell6.setCellValue(timeIn);
            cell7.setCellValue(timeOut);
            
            workbook.write( out );
            count++;
        }catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    } 
}
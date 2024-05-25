/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemri_visitors_project;

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
            getReport(date);
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
    
    private void getReport(String date)
    {
        try
        {
            number = 0;
            reportList = null;
            reportList = new ArrayList<>();
            initReportList();
            
            String sql = "SELECT VISIT.ID_NUMBER, VISITOR_NAME, PHONE_NUMBER,"
                    + "VISITOR_ORIGIN, DESTINATION_NAME, VISIT_REASON, VISITOR_TIME_IN, VISITOR_TIME_OUT "
                    + "FROM VISIT JOIN VISITOR ON VISIT.ID_NUMBER=VISITOR.ID_NUMBER "
                    + "JOIN DESTINATION ON VISIT.VISITOR_DESTINATION_ID=DESTINATION.DESTINATION_ID"
                    + " WHERE VISIT_DATE = ? "
                    + " ORDER BY VISIT.VISIT_NUMBER";
            
            Connection con = UtilityClass.getDatabaseConnection();
            
            PreparedStatement state = con.prepareStatement( sql );
            state.setString(1, date);
            
            ResultSet result = state.executeQuery();
            
            while( result.next() )
            {
                reportList.add( getGridPane( false, "" + (number + 1), result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8)) );
                number++;
            }
            
            updateList();
        }catch( Exception e )
        {
            UtilityClass.alert("Error: "+ e);
        }
    }
}

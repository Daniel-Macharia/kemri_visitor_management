package kemri_visitors_project;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Daniel Macharia
 */
public class GenerateQRCodeController implements Initializable {
    
    @FXML private ImageView qrImage;
    @FXML private TextField ID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void handleGenerateQRCodeAction( ActionEvent event )
    {
        try
        {
            String val = ID.getText();
            if( val == null || val.equals("") )
            {
                UtilityClass.inform("Please enter a valid id!");
                return;
            }
            
            BitMatrix bm = new MultiFormatWriter().encode( val, BarcodeFormat.QR_CODE, 600, 600);
            
            File assetsDir = new File("assets");
            
            if( !assetsDir.exists() )
            {
                assetsDir.mkdir();
            }
            File outputImageFile = new File( "assets/qrCodeImage.jpg");
            
            if( ! outputImageFile.exists() )
            {
                outputImageFile.createNewFile();
            }
            
            Path path = Paths.get( outputImageFile.toURI() );
            MatrixToImageWriter.writeToPath(bm, "jpg", path );
            
            Image img = new Image( outputImageFile.toURI().toURL().toExternalForm() );
            qrImage.setImage( img );
        }
        catch( NullPointerException e )
        {
            UtilityClass.alert("Null Pointer Error: " + e);
        }
        catch( FileNotFoundException e )
        {
            UtilityClass.alert("File Not Found Error: " + e);
        }
        catch( IOException e )
        {
            UtilityClass.alert("IO Error: " + e);
        }
        catch( Exception e )
        {
            UtilityClass.alert("Error: " + e);
        }
    }
    
}

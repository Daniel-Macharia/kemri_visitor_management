package kemri_visitors_project;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SendSms
{
    private String message;
    private String mobileNumber;
    
    public SendSms(String mobileNumber, String message)
    {
        this.mobileNumber = mobileNumber;
        this.message = message;
    }
	public void send()
    {
        try
        {
            new Thread( new Runnable(){
            
                @Override
                public void run()
                {
                    try
                    {
                        String id = "\"senderID\"";
                        String idVal = "\"MOBILESASA\"";
                        String m = "\"message\"";
                        StringBuilder mVal = new StringBuilder().append("\"").append(getMessage()).append("\"");
                        String p = "\"phone\"";
                        StringBuilder pVal = new StringBuilder().append("\"").append(getPhoneNumber()).append("\"");

                        String url = "https://api.mobilesasa.com/v1/send/message";
                        StringBuilder data = new StringBuilder();
                        data.append( "{" ).append( id ).append(": ").append(idVal).append(",")
                                        .append(m).append(": ").append(mVal.toString()).append(",") 
                                        .append(p).append(": ").append(pVal.toString()).append("}");


                        URL object = new URI(url).toURL();

                        HttpURLConnection connection = (HttpURLConnection) object.openConnection();

                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Authorization", "Bearer npY2qbtIXcD7H3uRCcwgVwJShof0x9yeAr3ma7RM4C8TjuvewaB29bLeODRm");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setRequestProperty("Content-Type", "application/json");

                        DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                        os.writeBytes( data.toString() );
                        os.flush();

                        int responseCode = connection.getResponseCode();

                        if( responseCode == HttpURLConnection.HTTP_OK )
                        {
                                StringBuilder response = new StringBuilder();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;

                                while( (line = reader.readLine() ) != null )
                                {
                                        response.append(line);
                                }

                                System.out.println("Response: " + response.toString());
                        }
                        else
                        {
                                System.out.println("No reponse received.");
                                connection.disconnect();
                        }
                    }catch( Exception e )
                    {
                        System.out.println("Error: " + e);
                    }
                }
            }).start();

        }catch( Exception e )
        {
            System.out.println("Error: " + e);
        }
    }
        
    private String getMessage(){ return this.message; }
    private String getPhoneNumber(){ return this.mobileNumber; }
}

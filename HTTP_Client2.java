package netzwerkhölle_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTP_Client2 {
	public static final String HOST = "localhost";
	public static final int PORT = 80;
	public static final String RESSOURCE = "/'";
	
	private void doRequest()
	{
		try
		{
			HttpURLConnection uconn = (HttpURLConnection) new URL("http://"+HOST+RESSOURCE).openConnection();
				
			BufferedReader br = new BufferedReader( new InputStreamReader( uconn.getInputStream()));
			
			System.out.println("Server response code: " + uconn.getResponseCode());
			
			for(String line = br.readLine(); line != null; line = br.readLine())
			{
				System.out.println("Server antwortet: "+line);
			}
		}
			
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args)
	{
		(new HTTP_Client2()).doRequest();
	}
}


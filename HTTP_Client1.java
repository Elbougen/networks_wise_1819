package netzwerkhölle_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
/*
 * Replace all images with smileys (<IMG Tags)
 * Add expression (yeah!) to MMIX, Java, Computer, RISC, CISC, Debugger, Informatik, Student, Studentin, 
 * Studierende, Windows, Linux, Software, InformatikerInnen, Informatiker, Informatikerin
 */
public class HTTP_Client1 {
	public static final String HOST = "localhost";
	public static final int PORT = 8082;
	public static final String RESSOURCE = "/'";
	
	void doRequest()
	{
		try (
				Socket s = new Socket(HOST, PORT);
				BufferedWriter bw = new BufferedWriter (new OutputStreamWriter(s.getOutputStream()) );
				BufferedReader br = new BufferedReader (new InputStreamReader(s.getInputStream()) );
			)
		{
			bw.write("GET " + RESSOURCE + " HTTP/1.0\r\n");
			bw.write("Host: " + HOST + "\r\n");
			bw.write("\r\n");
			bw.flush();
			
			for(String line = br.readLine(); line != null; line = br.readLine())
			{
				System.out.println("Server antwortet: "+line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		(new HTTP_Client1()).doRequest();
	}
}

package netzwerkhölle_2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HTTPServer {
	public static String targetHost = "";
	public static final int TARGET_PORT = 80;
	public static final int PORT = 8082;
	public static final String BODY = "Hello Client!";
	
	public static void startServer()
	{
		try(ServerSocket servSock = new ServerSocket(PORT);)
		{
			while(true)
			{
				System.out.println("Server is waiting for clients...");
				Socket cs = servSock.accept();
				System.out.println("Server has connection to client'!");
				
				try(Socket ts = new Socket(targetHost, TARGET_PORT);
					BufferedReader br1 = new BufferedReader( new InputStreamReader( ts.getInputStream()));
					BufferedWriter bw1 = new BufferedWriter( new OutputStreamWriter( ts.getOutputStream())); 
					BufferedReader br2 = new BufferedReader( new InputStreamReader( cs.getInputStream()));
					BufferedWriter bw2 = new BufferedWriter( new OutputStreamWriter( cs.getOutputStream())); )
				{
							
					for(String l = br2.readLine(); !l.isEmpty(); l = br2.readLine())
					{
						System.out.println("Client says: "+l);
							if(l.startsWith("GET")) {
								bw1.write(l + "\r\n");
								break;
							}
					}
					
					String hostLine = String.format("Host: %s:%d\r\n", targetHost, TARGET_PORT);
					bw1.write(hostLine);
					bw1.write("\r\n");
					bw1.flush();
					
					// <img.*src="([A-z.]+)".*> replace img
					// https://upload.wikimedia.org/wikipedia/commons/8/8d/Smiley_head_happy.svg
					boolean header = true;
					for(String l = br1.readLine(); l != null; l = br1.readLine())
					{
						System.out.println("Target says: "+l);
						bw2.write(header ? l + "\r\n" : l);
						if(l.isEmpty())  {
							header = false;
						}
					}
					bw2.flush();
					
//					bw2.write("HTTP/1.1 200 OK\r\n");
//					bw2.write("Content-Length: "+BODY.length()+"\r\n");
//					bw2.write("\r\n");
//					bw2.write(BODY);
//					bw2.flush();
				}
				cs.close();
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args)
	{
		// create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);

	    //  prompt for the TargetHost name
	    System.out.print("Enter TargetHost name: ");

	    // get their input as a String
	    targetHost = scanner.next();
	    
	    scanner.close();
	    
		HTTPServer.startServer();
	}
}


package Game;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {

	public static ObjectOutputStream outputToClient;
	public static ObjectInputStream inputFromClient;
	public static ArrayList<Player> players = new ArrayList<Player>();
	int currentPlayer = 0;
	public Server() throws IOException {


		while (true) {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(7000);

			// Listen for a new connection request
			Socket socket = serverSocket.accept();
			//System.out.println("connect ");
			//outputToClient = new ObjectOutputStream(socket.getOutputStream());
			// Create an input stream from the socket
			//			inputFromClient = new ObjectInputStream(socket.getInputStream());

			//			ServerSocket serverSocket = new ServerSocket(portNumber);
			//		    Socket clientSocket = serverSocket.accept();
			//		    PrintWriter out =
			//		        new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.println("111");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
			System.out.println("222");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("333");
			while(true)  {
			
				String command = in.readLine();				
				
				if(command!=null){

					if(command.equals("moveUp")){
						players.get(currentPlayer).moveNorth();
						out.write("("+players.get(currentPlayer).xpos+","+players.get(currentPlayer).ypos+")");
						System.out.println("server moved the player up");
					}
				}
			}
			// Write to the file
			//outputToFile.writeObject(object);

		}
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
}

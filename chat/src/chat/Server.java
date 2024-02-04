package chat;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
public class Server{
	private ServerSocket serversocket;
	public Server(ServerSocket sc){
		this.serversocket = sc;
	}
	public void startserver(){
		try{
			while (!serversocket.isClosed()){
				Socket socket=serversocket.accept();
				System.out.println("a new client has connected!");
				ClientHundler clienthundler = new ClientHundler(socket);
				Thread thread = new Thread(clienthundler);
				thread.start();
			}
		}
		catch(IOException e ){
			
		}
	
	}
public void closseeserver() {
	try { 
		if (serversocket != null) {
			serversocket.close();
		}
	}catch(IOException e) {
		e.printStackTrace();
	}
}
	
public static void main(String[]args) throws IOException {
	ServerSocket serversocket = new ServerSocket(2002);
	Server server = new Server(serversocket);
	server.startserver();
}}
	
	
	

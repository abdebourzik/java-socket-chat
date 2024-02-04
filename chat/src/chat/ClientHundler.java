package chat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class ClientHundler implements Runnable{
	public static ArrayList<ClientHundler> clienthundlers =new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedreader;
	private BufferedWriter bufferedwriter;
	private String clientusername;
	public ClientHundler(Socket socket) { 
		try{
			this.socket=socket;
			this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.clientusername = bufferedreader.readLine();
			clienthundlers.add(this);
			brodcastmessage("Server: "+clientusername+"has entred the groupe!");
		}catch(IOException e) {
		closeeverything(socket,bufferedreader,bufferedwriter);
	}}
	@Override
	public void run() {
		String messagefromclient;
		while(socket.isConnected()) {
			try {
				messagefromclient = bufferedreader.readLine();
				brodcastmessage(messagefromclient);
			}catch(IOException e){
				closeeverything(socket,bufferedreader,bufferedwriter);
				break;}
		}
		
	}
	public void brodcastmessage(String message) {
		for(ClientHundler clienthundler : clienthundlers) {
			try {
				if(!clienthundler.clientusername.equals(clientusername)) {
					
					clienthundler.bufferedwriter.write(message);
					clienthundler.bufferedwriter.newLine();
					clienthundler.bufferedwriter.flush();
					
				}
			}catch(IOException e) {
				closeeverything(socket,bufferedreader,bufferedwriter);
			}
		}
	}
	public void removeclienthundler() {
		clienthundlers.remove(this);
		brodcastmessage("Server: "+this.clientusername+" has left the groupe!");
	}
	public void closeeverything(Socket socket,BufferedReader bufferedreader,BufferedWriter bufferedwriter) {
		removeclienthundler();
		try {
			if(bufferedreader!=null) {
				bufferedreader.close();
			}
			if(bufferedwriter!=null) {
				bufferedwriter.close();
			}
			if(socket!=null) {
				socket.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	}

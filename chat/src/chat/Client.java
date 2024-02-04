package chat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {
	
	private Socket socket;
	private BufferedReader bufferedreader;
	private BufferedWriter bufferedwriter;
	private String username;
	
	public Client (Socket socket, String username) {
		
		try {
			this.socket=socket;
			this.username=username;
			this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(IOException e) {
			closeeverything(socket,bufferedreader,bufferedwriter);
		}
	
	
	}
	public void sendmesssage() {
		try {
			bufferedwriter.write(username);
			bufferedwriter.newLine();
			bufferedwriter.flush();
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				String messagetosend = scanner.nextLine();
				bufferedwriter.write(username+ ": " + messagetosend);
				bufferedwriter.newLine();
				bufferedwriter.flush();
			}scanner.close();}catch(IOException e) {
				closeeverything(socket,bufferedreader,bufferedwriter);
				
			}
			
		}
	public void lestentomessage() { 
		new Thread(new Runnable() {

			@Override
			public void run() {
				String messagefromgroup;
				while(socket.isConnected()) {
					try {
					messagefromgroup = bufferedreader.readLine();
					System.out.println(messagefromgroup);
				}catch(IOException e) {
					closeeverything(socket,bufferedreader,bufferedwriter);
				}
				
			
			
				}}}).start();
	}
	public void closeeverything(Socket socket,BufferedReader bufferedreader,BufferedWriter bufferedwriter) {
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
	public static void main(String[]args) throws UnknownHostException, IOException {
		 
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your username to connect to groupe chat: ");
		String username = scanner.nextLine();
		Socket socket=new Socket("localhost",2002);
		Client client= new Client(socket,username);
		client.lestentomessage();
		client.sendmesssage();
		scanner.close();
		
	}
}

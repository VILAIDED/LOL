package baiSock;

import java.net.ServerSocket;
import java.net.Socket;

public class CheckIPClient {
	public static void main(String[] args) {
		new CheckIPClient();
	}
	public CheckIPClient() {
		ServerSocket server;
		try {
			server = new ServerSocket(5000);
			System.out.println("Server is running.....!");
			while(true) {
				Socket sock = server.accept();
				System.out.println(sock.getInetAddress());
			}
			
		}catch(Exception ex) {}
		
	}
}

package baiSock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	public static void main(String[] args) {
		new ChatClient();
	}
	public ChatClient() {
		try {
		Socket sock = new Socket("42.116.159.207",80);
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		DataInputStream dis = new DataInputStream(sock.getInputStream());
		dos.writeUTF("Manivong litar");
		new ClientXuly(sock).start();
		Scanner sc = new Scanner(System.in);
	
		while(true) {
			String msg = sc.nextLine();
			dos.writeUTF(msg);
		}
		}catch(Exception ex) {
			
		}
	}
}
class ClientXuly extends Thread{
	Socket sock;
	public ClientXuly(Socket sock) {
		this.sock = sock;	
	}
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			while(true) {
				System.out.println(dis.readUTF());
			}
		}catch(Exception ex) {
			
		}
	}
	
}

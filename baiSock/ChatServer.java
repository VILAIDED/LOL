package Bai5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatRoomServer {
	public static void main(String[] args) {
		new ChatRoomServer();
	}
	
	static Vector<ChatXuly> clients = new Vector<ChatXuly>();

	public ChatRoomServer() {
		try {
			ServerSocket server = new ServerSocket(80);
			while (true) {
				Socket soc = server.accept();
				ChatXuly c = new ChatXuly(soc);
				clients.add(c);
				c.start();
			}
		} catch (Exception e) {
		}
	}
}

class ChatXuly extends Thread {
	Socket soc;
	DataInputStream dis;
	DataOutputStream dos;
	String name;
	public ChatXuly(Socket soc) {
		try {
			this.soc = soc;
			dis = new DataInputStream(soc.getInputStream());
			dos = new DataOutputStream(soc.getOutputStream());
		} catch (Exception e) {

		}
	}
	public void run() {
		try {
			name = dis.readUTF();
			while (true) {
				String msg = dis.readUTF();
				for (ChatXuly c : ChatRoomServer.clients) {
					c.dos.writeUTF(name+soc.getInetAddress()+">"+msg);
				}
			}
		}catch(Exception e) {
		}
	}
}

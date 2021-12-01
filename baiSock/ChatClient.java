package Bai5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatRoomClient {

	public static void main(String[] args) {
		new ChatRoomClient();
	}

	public ChatRoomClient() {
		try {
			Socket soc = new Socket("localhost", 80);
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			DataInputStream dis = new DataInputStream(soc.getInputStream());
			dos.writeUTF("Pham Minh Tuan");
			new ClientXuly(soc).start();
			Scanner sc = new Scanner(System.in);
			while (true) {
				// Gui msg cho server
				String msg = sc.nextLine();
				dos.writeUTF(msg);
				// Code sai!!!
				// System.out.println(dis.readUTF());
			}
		} catch (Exception e) {
		}
	}
}

class ClientXuly extends Thread {
	Socket soc;

	public ClientXuly(Socket soc) {
		this.soc = soc;
	}

	public void run() {
		try {
			DataInputStream dis = new DataInputStream(soc.getInputStream());
			while (true) {
				System.out.println(dis.readUTF());
			}
		} catch (Exception e) {

		}
	}
}

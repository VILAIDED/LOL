package baiSock;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connect2Server {
	public static void main(String[] args) {
		new Connect2Server();
	}
	public Connect2Server() {
		try {
			@SuppressWarnings("resource")
			Socket sock = new Socket("localhost",5000);
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			dos.writeUTF("Litar : Minh la xinh nhat lop");
			System.out.println(dis.readUTF());
		}catch(Exception ex) {
			
		}
	}
}

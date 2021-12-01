package baiSock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SendMsgServer {
	public static void main(String[] args) {
		new SendMsgServer();
	}
	public SendMsgServer() {
		try {
			ServerSocket server = new ServerSocket(5000 );
		//	new ResetIPCount().start();
			System.out.println("Server is Running....!");
			while(true) {	
				Socket sock = server.accept();
			    new Xuly(sock).start();
			}
		}catch(Exception ex) {
			
		}
	}
}
class ResetIPCount extends Thread{
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				Xuly.IPCount = new HashMap<String,Integer>();
			}catch(Exception ex) {
				
			}
		}
	}
}
class Xuly extends Thread{
	static Set<String>  bl = new HashSet<String>();
	static Map<String,Integer> IPCount = new HashMap<String,Integer>();
	Socket sock;
	public Xuly(Socket sock) {
		this.sock = sock;
	}
	public void run() {
		try {
			String ip = sock.getInetAddress().toString();
			Integer c = IPCount.get(ip);
			if(c== null) {
				IPCount.put(ip, 1);
			}else 
				IPCount.put(ip, c+1);
			if(c > 10) bl.add(ip);
			
			if(bl.contains(ip)) return;
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			
			//Thread.sleep(1000);
			String msg = dis.readUTF();
			dos.writeUTF(msg);
			sock.close();
		}catch(Exception ex) {
			
		}
	}
}

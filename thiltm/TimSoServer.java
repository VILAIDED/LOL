package thiltm;

import java.awt.Point;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import thi.CaroServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class TimSoServer {
	int n = 5;
	int s = 100;
	int os = 50;
	static List<Point> dadanh = new ArrayList<Point>();
	public static Set<String> ip_port = new HashSet<String>();
	public static Set<String> aliveIP = new HashSet<String>();
	static Vector<String> clients = new Vector<String>();
	DatagramSocket soc;
	byte buf[] = new byte[1000];
	void send(String ip_port,String msg) throws Exception {
		String stl[] = ip_port.split(",");
		String ip = stl[0];
		String port = stl[1];
		System.out.println(ip_port);
		DatagramPacket seP = new DatagramPacket(msg.getBytes(), 
				msg.length(), InetAddress.getByName(ip),Integer.parseInt(port));
		soc.send(seP);
	}
	public  static void main(String[] args) {
		new TimSoServer();
	}
	public TimSoServer() {
		try {
			soc = new DatagramSocket(5000);
			 loop :while (true) {
				try {
					DatagramPacket reP = new DatagramPacket(buf, buf.length);
					soc.receive(reP);
					String str = new String(reP.getData()).substring(0, reP.getLength());
				    String msg = "";
				    String user_ip = reP.getAddress().getHostAddress() + "," + reP.getPort();
				    System.out.print(str);
					if (str.equals("want to join")) {
						if(clients.size() < 4) {
							msg = "You are a player";
							clients.add(user_ip);
						}else {
							msg = "You are a viewer";
						}
						DatagramPacket seP = new DatagramPacket(msg.getBytes(), msg.length(), reP.getAddress(),
								reP.getPort());
						soc.send(seP);
						ip_port.add(user_ip);
					}
					else {
						String stl[] = str.split(",");
						if(clients.contains(user_ip)) {
						int ix = Integer.parseInt(stl[0]);
						int iy = Integer.parseInt(stl[1]);
						
						if (clients.size() < 4)
							continue;
						if (ix < 0 || ix >= n || iy < 0 || iy >= n)
							continue;
						for (Point p : dadanh) {
							if (p.x == ix && p.y == iy)
								continue loop;
						}
						dadanh.add(new Point(ix, iy));
						for(String ip : ip_port) {
							int index = clients.indexOf(user_ip);
							msg = index + "," + ix + "," + iy;
							send(ip,msg);
						}
	             }
					}
				}catch (Exception e1) {
				}
}
		}catch (Exception eX) {
}
		}
}

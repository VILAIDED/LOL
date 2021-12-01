 package thi;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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


public class CaroServer {

	public static void main(String[] args) {
		new CaroServer();
	}

	static int n = 12;
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
		DatagramPacket seP = new DatagramPacket(msg.getBytes(), 
				msg.length(), InetAddress.getByName(ip),Integer.parseInt(port));
		soc.send(seP);
	}
	public CaroServer() {
		try {
			soc = new DatagramSocket(5000);
			new Xuly().start();
			loop : while (true) {
				try {
					DatagramPacket reP = new DatagramPacket(buf, buf.length);
					soc.receive(reP);
					String str = new String(reP.getData()).substring(0, reP.getLength());
				    String msg = "";
				    String user_ip = reP.getAddress().getHostAddress() + "," + reP.getPort();
				    
					if (str.equals("want to join")) {
						if(clients.size() < 2) {
							msg = "You are a player";
							clients.add(user_ip);
						}else {
							msg = "You are a viewer";
						}
						
						DatagramPacket seP = new DatagramPacket(msg.getBytes(), msg.length(), reP.getAddress(),
								reP.getPort());
						soc.send(seP);
						ip_port.add(user_ip);
						aliveIP.add(user_ip);
					} else if (str.equals("I am alive")){
						aliveIP.add(user_ip);
					}
					else {
						String stl[] = str.split(",");
						if(CaroServer.clients.contains(user_ip)) {
						int ix = Integer.parseInt(stl[0]);
						int iy = Integer.parseInt(stl[1]);
						
						if (CaroServer.clients.size() < 2)
							continue;
						// 2. Kiem tra luot danh hop le hay khong
						if (user_ip == CaroServer.clients.get(0) && CaroServer.dadanh.size() % 2 != 0)
							continue;
						if (user_ip == CaroServer.clients.get(1) && CaroServer.dadanh.size() % 2 != 1)
							continue;
						if (ix < 0 || ix >= CaroServer.n || iy < 0 || iy >= CaroServer.n)
							continue;
						for (Point p : CaroServer.dadanh) {
							if (p.x == ix && p.y == iy)
								continue loop;
						}
						CaroServer.dadanh.add(new Point(ix, iy));
						for(String ip : ip_port) {
							msg = ix + "," + iy;
							send(ip,msg);
						}
						}else {
							send(user_ip,"You are a viewer");
						}
					}
				} catch (Exception e1) {
				}
			}
		} catch (Exception e) {
		}
	}
}
class Xuly extends Thread {
	public void run() {
		while(true) {
		try {
			Thread.sleep(10000);
			
			Set<String> delList = new HashSet<String>();
			for(String str : CaroServer.ip_port) {
			    if(!CaroServer.aliveIP.contains(str)) {
			    	delList.add(str);
			    }
			    System.out.println(str);
			}
			CaroServer.ip_port.removeAll(delList);
			System.out.println("       ");
			CaroServer.aliveIP = new HashSet<String>();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
	}


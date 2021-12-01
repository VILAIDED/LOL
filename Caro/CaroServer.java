 package Caro;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CaroServer {
	public static void main(String[] args) {
		new CaroServer();
	}

	static List<Point> dadanh = new ArrayList<Point>();
	static int n = 15;
	static Vector<Xuly> clients = new Vector<Xuly>();

	public CaroServer() {
		try {
			ServerSocket server = new ServerSocket(80);
lll:		while (true) {
				Socket soc = server.accept();
				System.out.println(soc.getInetAddress() + " Join!!");
				Xuly p = new Xuly(soc);
				if (clients.size() <= 2) {
					clients.add(p);
					p.start();
				} else {
					for (int i = 0; i < 2; i++) {
						Xuly c = clients.get(i);
						if (!c.connecting && c.soc.getInetAddress().equals(soc.getInetAddress())) {
							clients.remove(i);
							clients.add(i,p);
							p.start();
							continue lll;
						}
					}
					clients.add(p);
				}
			}
		} catch (Exception e) {
		}
	}
}

class Xuly extends Thread {
	Socket soc;
	DataOutputStream dos;
	DataInputStream dis;
	boolean connecting = true;

	public Xuly(Socket soc) {
		try {
			this.soc = soc;
			dos = new DataOutputStream(soc.getOutputStream());
			dis = new DataInputStream(soc.getInputStream());
			for (Point p : CaroServer.dadanh) {
				dos.writeUTF(p.x + "");
				dos.writeUTF(p.y + "");
			}
		} catch (Exception e) {
			connecting = false;
		}
	}

	public void run() {
		try {
			loop: while (true) {
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				// Can nhung xu ly gi???
				if (CaroServer.clients.size() < 2)
					continue;
				// 2. Kiem tra luot danh hop le hay khong
				if (this == CaroServer.clients.get(0) && CaroServer.dadanh.size() % 2 != 0)
					continue;
				if (this == CaroServer.clients.get(1) && CaroServer.dadanh.size() % 2 != 1)
					continue;

				System.out.println(soc.getInetAddress());
				// 3. Kiem tra hop toa do le hay khong
				if (ix < 0 || ix >= CaroServer.n || iy < 0 || iy >= CaroServer.n)
					continue;
				for (Point p : CaroServer.dadanh) {
					if (p.x == ix && p.y == iy)
						continue loop;
				}

				CaroServer.dadanh.add(new Point(ix, iy));
				// 4.
				for (Xuly p : CaroServer.clients) {
					try {
						p.dos.writeUTF(ix + "");
						p.dos.writeUTF(iy + "");
					} catch (Exception e) {
						p.connecting = false;
					}
				}
			}
		} catch (Exception e) {
			connecting = false;
		}
	}
}


package thi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class CaroClient extends JFrame implements MouseListener, Runnable {

	public static void main(String[] args) {
		new Thread(new CaroClient()).start();
	}

	List<Point> dadanh = new ArrayList<Point>();
	int n = 12;
	int s = 40;
	int os = 50;
	DatagramSocket soc;
	byte buf[] = new byte[1000];
    String role = "";
	void send(String msg) throws Exception {
		DatagramPacket seP = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("localhost"), 5000);
		soc.send(seP);
	}

	String receive() throws Exception {
		DatagramPacket reP = new DatagramPacket(buf, buf.length);
		soc.receive(reP);
		String str = new String(reP.getData()).substring(0, reP.getLength());
		return str;
	}

	public CaroClient() {
		this.setTitle("Co CARO!!");
		this.setSize(n * s + 2 * os, n * s + 2 * os);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		try {
			soc = new DatagramSocket();
			String str = "want to join";
			send(str);
		} catch (Exception e) {
		}
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, os + i * s, os + s * n, os + i * s);
			g.drawLine(os + i * s, os, os + i * s, os + s * n);
		}
		g.setFont(new Font("arial", Font.BOLD, s));
		for (int i = 0; i < dadanh.size(); i++) {
			int ix = dadanh.get(i).x;
			int iy = dadanh.get(i).y;
			int x = ix * s + os + s - s / 2 - s / 4;
			int y = iy * s + os + s - s / 2 + s / 4;
			String str = "x";
			if (i % 2 == 1)
				str = "o";
			g.drawString(str, x, y);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x < os || x >= os + n * s)
			return;
		if (y < os || y >= os + n * s)
			return;
		int ix = (x - os) / s;
		int iy = (y - os) / s;
		
		try {
			send(ix + "," + iy);
		} catch (Exception e1) {
		}
	}

	@Override
	public void run() {
		byte buf[] = new byte[1000];
		try {
			while (true) {
				String str = receive();
				
				if(str.contains("You are a")) {
					role = str.substring(9);
					System.out.println(str);
				}else{
				String stl[] = str.split(",");
				int ix = Integer.parseInt(stl[0]);
				int iy = Integer.parseInt(stl[1]);
				dadanh.add(new Point(ix, iy));
				send("I am alive");
				this.repaint();
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
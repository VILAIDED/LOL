package thiltm;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

import thi.CaroClient;
public class TimSoClient extends JFrame implements MouseListener,Runnable{
	public static void main(String[] args) {
		new Thread(new TimSoClient()).start();
	}
	int n = 5;
	int s = 100;
	int os = 50;
	int matran[][] = new int[n][n];
	Random rand = new Random();
	List<Point> dadanh = new ArrayList<Point>();
	DatagramSocket soc;
	List<Integer> indexCol = new ArrayList<Integer>();
	byte buf[] = new byte[1000];
    String role = "";
    String receive() throws Exception {
		DatagramPacket reP = new DatagramPacket(buf, buf.length);
		soc.receive(reP);
		String str = new String(reP.getData()).substring(0, reP.getLength());
		return str;
	}
	void send(String msg) throws Exception {
		DatagramPacket seP = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("localhost"), 5000);
		soc.send(seP);
	}
	
	public TimSoClient() {
		this.setSize(n * s + 2 * os, n * s + 2 * os);
		this.setTitle("Caro");
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matran[i][j] = i * n + j + 1;
			}
		}
		for (int r = 0; r < n * n; r++) {
			int i1 = rand.nextInt(n);
			int j1 = rand.nextInt(n);
			int i2 = rand.nextInt(n);
			int j2 = rand.nextInt(n);
			int tmp = matran[i1][j1];
			matran[i1][j1] = matran[i2][j2];
			matran[i2][j2] = tmp;
		}
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

		for (int i = 0; i < dadanh.size(); i++) {
			int color = indexCol.get(i);
			if(color == 0) {
				g.setColor(Color.GREEN);
			}else if (color == 1) {
				g.setColor(Color.RED);
			}else if (color == 2) {
				g.setColor(Color.YELLOW);
			}else if (color == 3) {
				g.setColor(Color.BLUE);
			}
			int ix = dadanh.get(i).x;
			int iy = dadanh.get(i).y;
			int x = os + ix * s;
			int y = os + iy * s;
			g.fillRect(x, y, s, s);;
		}
		
		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, os + i * s, os + n * s, os + i * s);
			g.drawLine(os + i * s, os, os + i * s, os + n * s);
		}
		g.setFont(new Font("arial", Font.BOLD, s/3));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				String str = matran[i][j]+"";
				if (matran[i][j]<10) str = "00" + str;
				else if (matran[i][j]<100) str = "0" + str;
				int x = os + i * s + s / 2 - s / 4;
				int y = os + j * s + s / 2 + s / 4 - s/8;
				g.drawString(str, x, y);
			}
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
				}else{
					String stl[] = str.split(",");
					indexCol.add(Integer.parseInt(stl[0]));
					int ix = Integer.parseInt(stl[1]);
					int iy = Integer.parseInt(stl[2]);
					dadanh.add(new Point(ix, iy));
					this.repaint();
				}
				}
		}catch(Exception ex) {
			
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

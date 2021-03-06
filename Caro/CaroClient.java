package Caro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class CaroClient extends JFrame implements MouseListener,Runnable {
	public static void main(String[] args) {
		new Thread(new CaroClient()).start();
	}

	int n = 15;
	int s = 40;
	int os = 50;
	List<Point> dadanh = new ArrayList<Point>();
	DataOutputStream dos;
	DataInputStream dis;

	public CaroClient() {
		this.setTitle("Co Caro");
		this.setSize(n * s + 2 * os, n * s + 2 * os);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		try {
			Socket soc = new Socket("localhost", 80);
			dos = new DataOutputStream(soc.getOutputStream());
			dis = new DataInputStream(soc.getInputStream());
		} catch (Exception e) {

		}
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, os + i * s, os + n * s, os + i * s);
			g.drawLine(os + i * s, os, os + i * s, os + n * s);
		}
		g.setFont(new Font("arial", Font.BOLD, s));
		for (int i = 0; i < dadanh.size(); i++) {
			Point p = dadanh.get(i);
			int ix = p.x;
			int iy = p.y;
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
		if (x < os || x >= os + n * s || y < os || y >= os + n * s)
			return;
		int ix = (x - os) / s;
		int iy = (y - os) / s;
		// Gui toa do cho server
		try {
			dos.writeUTF(ix + "");
			dos.writeUTF(iy + "");
		} catch (Exception e1) {
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				dadanh.add(new Point(ix,iy));
				this.repaint();
			}
		}catch(Exception e) {	
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

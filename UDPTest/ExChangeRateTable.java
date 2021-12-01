package UDPTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class ExChangeRateTable extends JFrame implements Runnable {

	public static void main(String[] args) {
		new Thread(new ExChangeRateTable()).start();
	}
	List<Double> tygia = new ArrayList<Double>();
	BufferedImage img;
	Graphics g;
	public ExChangeRateTable() {
		this.setTitle("Exchange Rate Table");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(3);
		img = new BufferedImage(400, 300,BufferedImage.TYPE_4BYTE_ABGR);
		g = img.getGraphics();
		
		this.setVisible(true);
	}
	
	public void paint(Graphics g1) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.YELLOW);
		g.fillRect(50, 50, 300, 200);
		
		g.setColor(Color.BLUE);
		for (int t = 1; t<tygia.size();t++) {
			int x1 = t*3 + 50;
			int y1 = (int)(250 - tygia.get(t));
			int x2 = (t-1)*3 + 50;
			int y2 = (int)(250 - tygia.get(t-1));
			g.drawLine(x1, y1, x2, y2);
		}
		g1.drawImage(img, 0, 0, null);
	}

	@Override
	public void run() {
		try {
			DatagramSocket soc = new DatagramSocket();
			while (true) {
				try {
					String str = "ExchangeRateUSDtoJPY";
					DatagramPacket seP = new DatagramPacket(str.getBytes(), str.length(),
							InetAddress.getByName("localhost"), 5000);
					soc.send(seP);
					byte buf[] = new byte[1000];
					DatagramPacket reP = new DatagramPacket(buf, buf.length);
					soc.receive(reP);
					String restr = new String(reP.getData()).substring(0,reP.getLength());
					tygia.add(Double.parseDouble(restr));
					if  (tygia.size()>100)
						tygia.remove(0);
					this.repaint();
					Thread.sleep(100);
				} catch (Exception e1) {
				}
			}
		} catch (Exception e) {

		}
	}

}
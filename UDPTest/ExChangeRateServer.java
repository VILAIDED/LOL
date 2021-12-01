package UDPTest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExChangeRateServer extends Thread{

	public static void main(String[] args) {
		new ExChangeRateServer();
	}

	Map<String,Double> tygia = new HashMap<String, Double>();
	Random rand = new Random();
	public void run() {
		while (true) {
			for (String key : tygia.keySet()) {
				double tg = tygia.get(key);
				tg = tg*(1+rand.nextDouble()*0.1 - 0.05);
				tygia.put(key, tg);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public ExChangeRateServer() {
		tygia.put("VND", 1d);
		tygia.put("JPY", 240d);
		tygia.put("USD", 23000d);
		tygia.put("TBZ", 50d);
		this.start();
		try {
			DatagramSocket soc = new DatagramSocket(5000);
			while (true) {
				try {
					byte buf[] = new byte[1000];
					DatagramPacket reP = new DatagramPacket(buf, buf.length);
					soc.receive(reP);
					String str = new String(reP.getData()).substring(0, reP.getLength());
					// ExchangeRateUDStoVND
					String cmd = str.substring(0, 12);
					String m1 = str.substring(12, 15);
					String to = str.substring(15, 17);
					String m2 = str.substring(17, 20);
					if (!cmd.equals("ExchangeRate"))
						continue;
					if (!to.equals("to"))
						continue;
					double tg = tygia.get(m1) / tygia.get(m2);
					String stg = tg + "";
					DatagramPacket seP = new DatagramPacket(stg.getBytes(), stg.length(), reP.getAddress(),
							reP.getPort());
					soc.send(seP);
				} catch (Exception e1) {

				}
			}
		} catch (Exception e) {
		}
	}

}


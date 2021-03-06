package Caro;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

	private InetAddress host;
	private int port;
	private Scanner sc = new Scanner(System.in);
	public Client(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@SuppressWarnings("deprecation")
	private void execute() throws IOException {
		DatagramSocket client = new DatagramSocket();
		client.send(createPacket("connect"));
		String menu = receiveData(client);
		
		Alive alive = new Alive(client, host, port);
		alive.start();
		while (true) {
			System.out.println(menu);
			int choose = input("Nhập vào tùy chọn của bạn: ");
			switch (choose) {
			case 1:
				client.send(createPacket(-1,1));
				//Nháº­n dá»¯ liá»‡u vá»�
				System.out.println("DHCP cua ban la: "+ receiveData(client));
				break;
			case 2:{
				boolean flag = true;
				do {
					int number = input("Nhap dhcp ban muon: ");
					client.send(createPacket(number,2));
					int result = Integer.parseInt(receiveData(client));
					if(result == -1) {
						System.out.println("Số DHCP phải nằm trong khoảng (0-255). Vui lòng chọn số DHCP khác!");
					}
					else if(result == -2) {
						System.out.println("Số DHCP này đã được sử dụng. Vui lòng chọn số DHCP khác!");
					}
					else if(result == -3) {
						System.out.println("DHCP của bạn là : " + number);
						flag = false;
					}					
				}while(flag);
				break;
			}
			case 3:
				client.send(createPacket(-1,3)); // key ==3 danh dau gui menu tuy chon 3
				alive.stop();
				System.out.println("Ket thuc");
				break;
			default: 
				System.out.println("Vui long chi nhap so trong menu");
				break;
			}
			if(choose == 3)
				break;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client(InetAddress.getByName("118.71.240.113"), 15797);
		client.execute();
	}
	private int input(String request) {
		int number = 0;
		boolean flag = true;
		do {
			try {
				System.out.println(request);
				number = Integer.parseInt(sc.nextLine());
				flag = false;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}while(flag);
		return number;
	}
	
	
	private String receiveData(DatagramSocket server) throws IOException {
		byte[] temp = new byte[1024];
		DatagramPacket receive_Packet = new DatagramPacket(temp,temp.length);
		server.receive(receive_Packet);
		return new String(receive_Packet.getData()).trim();
	}
	
	private DatagramPacket createPacket(String value) {
		byte[] arrData = new byte[1024];
		return new DatagramPacket(arrData, arrData.length, host, port);
	}
	
	private DatagramPacket createPacket(int value, int index) {
		String str = String.valueOf(value) + "_" + index; 
		byte[] arrData = str.getBytes();
		return new DatagramPacket(arrData, arrData.length, host, port);
	}

}


class Alive extends Thread{
	private DatagramSocket client;
	private InetAddress host;
	private int port;
	
	public Alive(DatagramSocket client, InetAddress host, int port) {
		this.client = client;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			try {
				client.send(createPacket("alive"));
				Thread.sleep(10000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private DatagramPacket createPacket(String value) {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length, host, port);
	}
}

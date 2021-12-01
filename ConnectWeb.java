import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConnectWeb {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String domain = sc.next();
		int port = 5000;
		
		try {
			Socket s = new Socket(InetAddress.getByName(domain),port);
			
			System.out.println(s.getLocalAddress());
			System.out.println(s.getInetAddress());
			
		}catch(Exception e) {
			
		}
	}
}

import java.net.ServerSocket;
import java.net.Socket;

public class Serverr {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			while(true) {
				Socket s = server.accept();
				System.out.println(s.getInetAddress());
				s.close();
			}
		}catch(Exception e) {
			
		}
	}
}

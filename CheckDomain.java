import java.net.InetAddress;
import java.util.Scanner;

public class CheckDomain {
	public static void main(String[] args) {
		
		try {
			Scanner sc = new Scanner(System.in);
			String domain = sc.next();
			InetAddress ia[] = InetAddress.getAllByName(domain);
			for(InetAddress i : ia) {
				System.out.println(i.getHostAddress());
				System.out.println(i.getHostName());
			}
			
		}catch(Exception e) {
			
		}
	}
}

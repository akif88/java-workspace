import java.io.PrintWriter;
import java.util.Scanner;


public class PrintWrite {


	public static void main(String[] args) {
		
		try {
			
			PrintWriter out = new PrintWriter("/home/akif/hel");
			
			out.println("Hello...");	
			
			Scanner in = new Scanner(System.in);
			String str = in.nextLine();
			
			while(!str.equals("exit")){
				out.println(str);
				str = in.nextLine();
			}
			
			in.close();
			out.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		

	}

}

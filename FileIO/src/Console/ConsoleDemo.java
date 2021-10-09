package Console;

import java.io.Console;

public class ConsoleDemo {

	public static void main(String[] args) {
		String str;
		Console con;
		
		con = System.console();
		
		if(con == null) {
			System.out.println("console not Found");
			return;
		}
		
		str = con.readLine("Enter a string: ");
		con.printf("Here is a string: %s\n", str);

	}

}

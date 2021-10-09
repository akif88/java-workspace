import java.io.File;
import java.util.Scanner;

public class ScannerFile {

	public static void main(String[] args) {
		
		Scanner scan = null;
		
		try{
			scan = new Scanner(new File("test.txt"));
			
			while(scan.hasNextLine())
				System.out.println(scan.nextLine());
			
		}
		catch (Exception e) {
			System.out.println(e);
		}finally {
			if(scan != null)
				scan.close();
		}
		
		
	}

}

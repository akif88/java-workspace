package ThreadExamples;

public class NewThreadWithExtends extends Thread {
	
	public NewThreadWithExtends() {
		
		super("Demo Thread");
		System.out.println("Child Thread: " + this);
		start();//start Thread		
	}
	
	@Override
	public void run() {
		try {
			
			for(int i = 5; i > 0 ; i--){
				System.out.println("Child Thread: " +i);
				Thread.sleep(500);
			}
			
		} catch (InterruptedException e) {
			System.out.println("Child Interruted");
		}
		finally {
			System.out.println("Exiting child thread");
		}
		
	}//end run
	
	
}//end this class



class ExtendThread{
	
	
	public static void main(String[] args) {
		new NewThreadWithExtends();
		
		try {
			
			for(int i = 5; i > 0 ; i--){
				System.out.println("Main Thread: " + i);
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO: handle exception
			System.out.println("Main Thread Interrupted");
		}
		finally {
			System.out.println("Main Thread exiting");
		}
		
		
	}//end main
	
	
}//end class



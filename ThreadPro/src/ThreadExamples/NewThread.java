package ThreadExamples;

public class NewThread  implements Runnable{
	
	Thread t;
	
	public NewThread() {
		
		//create new Thread
		t = new Thread(this, "Demo Thread");
		System.out.println("Child Thread: " + t);
		t.start();
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
		
	}//end run method	

}//end newThread class


class ThreadDemo{
	
	public static void main(String[] args) {
		new NewThread(); //create new child thread
		
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
	
	
}//end demo class

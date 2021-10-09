package ThreadExamples;

public class CurrentThreadDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Thread t = Thread.currentThread();
		
		System.out.println("Current Thread: " + t.getName());
		
		//change name for thread
		t.setName("My Thread");
		System.out.println("After Change Name: " + t.getName());
		
		try {
			
			for(int i = 5; i > 0 ; i--){
				System.out.println(i);
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO: handle exception
			System.out.println("Main Thread Interrupted");
		}
		

	}

}

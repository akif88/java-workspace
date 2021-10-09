
public class ThreadDemoExtends extends Thread {

	private Thread thrd;
	private String threadName;
	
	public ThreadDemoExtends(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}
	
	@Override
	public void run(){
		System.out.println("Running " + threadName);
		
		try {
			
			for(int i = 400000; i > 0; i--){
				System.out.println("Thread: " + threadName + "," + i);
				
				Thread.sleep(0);				
			}
			
		} 
		catch (InterruptedException e) {
			System.out.println("Thread " +  threadName + " interrupted.");			
		}
		finally {
			System.out.println("Thread " +  threadName + " exiting");		
		}		
	}
	
	@Override
	public void start(){
		System.out.println("Starting " +  threadName );
		
		if(thrd==null){
			thrd = new Thread(this, threadName);
			thrd.start();
		}
		
	}
	
	
	
	
}

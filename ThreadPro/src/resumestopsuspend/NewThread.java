package resumestopsuspend;

public class NewThread implements Runnable {

	String name;
	Thread t;
	boolean suspendFlag;
	
	public NewThread(String threadName) {
		name = threadName;
		t = new Thread(this, name);
		System.out.println("NewThread: " + t);
		suspendFlag = false;
		t.start();
	}
	
	@Override
	public void run() {
		try {
			for(int i = 15; i > 0; i--){
				System.out.println(name + ": " + i);
				Thread.sleep(2000);
				synchronized(this){
					while (suspendFlag) {
						wait();
					}
				}
			}
			
			
		} catch (InterruptedException e) {
				System.out.println(name + " interrupted");
		}
		
	}//end run

	
	
	
	void mysuspend(){
		suspendFlag = true;
	}
	
	
	synchronized void myresume(){
		suspendFlag = false;
		notify();
	}
	
		
}

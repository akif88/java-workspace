package ThreadExamples;

public class Clicker implements Runnable {

	long click = 0;
	Thread t;
	private volatile boolean running = true;
	
	
	public Clicker(int p) {
		t = new Thread(this);
		t.setPriority(p);
	}

	@Override
	public void run() {
		while(running){
			click ++;
		}
		
	}
	
	public void stop(){
		running = false;
	}
	
	public void start(){
		t.start();
	}
	
}//end class


class HighLowPriority{
	
	
	public static void main(String[] args) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Clicker highPri = new Clicker(Thread.NORM_PRIORITY + 4);
		Clicker lowPri = new Clicker(Thread.NORM_PRIORITY - 4);
		
		lowPri.start();
		highPri.start();
		try{
			Thread.sleep(30000);
		}catch (InterruptedException e) {
			System.out.println("Main Thread interrupted");
		}
		
		lowPri.stop();
		highPri.stop();
		
		try {
			highPri.t.join();
			lowPri.t.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		System.out.println("Low Priority Thread: " + lowPri.click);
		System.out.println("High Priority Thread: " + highPri.click);
		
	}
	
	
}


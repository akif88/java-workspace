package ThreadExamples;

public class SimpleThreads {
	
	
	static void threadMessage(String message){
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s\n", threadName, message);
	}
	
	
	private static class MessageLoop implements Runnable{
		
		@Override
		public void run() {
			
			String importantInfo[] = {
		            "Mares eat oats",
		            "Does eat oats",
		            "Little lambs eat ivy",
		            "A kid will eat ivy too"
		    };
			
			try{
				for (int i = 0; i < importantInfo.length; i++) {
			            //Pause for 4 seconds
			            Thread.sleep(4000);
			            //Print a message
			            threadMessage(importantInfo[i]);
				}
			}
			catch(InterruptedException e){
				threadMessage("I wasn't done!");
			}
			
			
		}//end run
		
		
	}//end class MessageLoop
	
	public static void main(String[] args) throws InterruptedException {
		
		long patience = 1000*60*60;
		
		if(args.length > 0){
			try {				
				patience = Long.parseLong(args[0]) * 1000;
				
			} catch (NumberFormatException e) {
				System.err.println("Argument must be Integer.");
				System.exit(1);
			}
		}//end if
		
		threadMessage("Starting MessageLoop thread");
		long stratTime = System.currentTimeMillis();
		
		Thread thr = new Thread(new MessageLoop());
		thr.start();
		
		
		threadMessage("Waiting for MessageLoop thread to finish");
		
		
		while(thr.isAlive()){
			threadMessage("Still waiting...");
			
			thr.join(1000); //0 or 1000
			
			if((System.currentTimeMillis() - stratTime) > patience && thr.isAlive()){
				threadMessage("Tired of wait");
				thr.interrupt();
				thr.join();
			}//end if
			
			
		}//end while		
		threadMessage("Finally!");
	}//end main()
	

}

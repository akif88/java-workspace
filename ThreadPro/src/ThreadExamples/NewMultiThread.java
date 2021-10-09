package ThreadExamples;

import java.io.IOException;
import java.io.InputStream;


public class NewMultiThread implements Runnable {
	
	String name;
	Thread t;
	
	public NewMultiThread(String threadName) {
		
		this.name = threadName;
		t = new Thread(this, name);
		System.out.println("New Thread" + t);
		t.start();	
	}

	@Override
	public void run() {
		try {
			
			for(int i = 5; i > 0 ; i--){
				System.out.format("%s: %d\n", name,i);
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			System.out.println("Child Interruted");
		}
		finally {
			System.out.println("Exiting child thread");
		}
		
	}//end run

}


class MultiThreadDemo{
	
	public static void main(String[] args) {
		NewMultiThread obj1 = new NewMultiThread("Thread-One");
		NewMultiThread obj2 = new NewMultiThread("Thread-Two");
		NewMultiThread obj3 = new NewMultiThread("Thread-Three");
		
		System.out.println("One: " + obj1.t.isAlive());
		System.out.println("Two: " + obj2.t.isAlive());
		System.out.println("Three: " + obj3.t.isAlive());
		
		try {
			
			while(obj3.t.isAlive() && obj2.t.isAlive() && obj1.t.isAlive()){
				System.out.println("Still waiting...");
			//wait the other threads(main thread)
			obj1.t.join(50);
			obj2.t.join(50);
			obj3.t.join(50);}
			
		} catch (Exception e) {
			System.out.println("Main Thread interrupted");
		}
			System.out.println("One: " + obj1.t.isAlive());
			System.out.println("Two: " + obj2.t.isAlive());
			System.out.println("Three: " + obj3.t.isAlive());
			System.out.println("Main Thread exiting");
	}//end main method
}



package synchronize;

public class Synch {

	public static void main(String[] args) {
		
		CallMe target = new CallMe();
		Caller obj1 = new Caller(target, "Hello");
		Caller obj2 = new Caller(target, "synchronized");
		Caller obj3 = new Caller(target, "World");
		
		//wait for thread die
		try {
			
			obj1.t.join();
			obj2.t.join();
			obj3.t.join();
			
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		

	}

}

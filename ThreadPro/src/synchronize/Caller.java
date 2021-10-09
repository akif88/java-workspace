package synchronize;

public class Caller implements Runnable {

	String msg;
	CallMe target;
	Thread t;
	
	public Caller(CallMe target, String msg){
		this.target = target;
		this.msg = msg;
		t = new Thread(this);
		t.start();		
	}

	@Override
	public void run() {
		synchronized(target){
			target.call(msg);
		}
	}
	
}

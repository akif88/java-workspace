
import threadcommunication.Consumer;
import threadcommunication.Producer;
import threadcommunication.Q;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	/*	ThreadDemo threadNow = new ThreadDemo("Thread-1");
		threadNow.start();
		
		ThreadDemo threadNow2 = new ThreadDemo("Thread-2");
		threadNow2.start();
	*/
	/*	ThreadDemoExtends threadN = new ThreadDemoExtends("Thread-1");
		threadN.start();
		
		ThreadDemoExtends threadN2 = new ThreadDemoExtends("Thread-2");
		threadN2.start();
		*/
		
		Q q = new Q();
		
		Producer pr = new Producer(q);
		Consumer cn = new Consumer(q);
		
		
		pr.run();
		cn.run();
		
	}

}

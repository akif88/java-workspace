package Serialization;

import java.io.Serializable;

public class MyClass implements Serializable {
	
	private transient String s;
	private int i;
	private double d;
	
	public MyClass(String s, int i, double d) {
		this.s = s;
		this.i = i;
		this.d = d;
	}
	
	@Override
	public String toString(){
		return "s=" + s + "; i=" + i + "; d=" + d;
	}

}

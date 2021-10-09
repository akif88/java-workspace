package Serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationDemo {

	public static void main(String[] args) {
		//Serialization
		try(ObjectOutputStream objOutStrm = new ObjectOutputStream(new FileOutputStream("/home/akif/serial.txt"))){
			
			MyClass object1 = new MyClass("Hello", -7, 1.5e10);
			System.out.println("object1: " + object1);
			
			objOutStrm.writeObject(object1);
			
		}catch (IOException e) {
			System.out.println(e);
		}
		
		//deSerialization
		try(ObjectInputStream objInStrm = new ObjectInputStream(new FileInputStream("/home/akif/serial.txt"))){
			
			MyClass object2 = (MyClass)objInStrm.readObject();
			
			System.out.println("Object2: " + object2);
			
		}catch (Exception e) {
			System.out.println(e);
		}
		

	}

}

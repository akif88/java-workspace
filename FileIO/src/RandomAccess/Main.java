package RandomAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Course[] course = new Course[4];
		
		course[0] = new Course("BBM101", "Programlamaya Giris I", 3);
		course[1] = new Course("BBM102", "Programlamaya Giris II", 3);
		course[2] = new Course("BBM103", "Programlamaya Giris Lab I", 3);
		course[3] = new Course("BBM103", "Programlamaya Giris Lab II", 3);
		
		RandomAccessFile raf = new RandomAccessFile(new File("/home/akif/course.txt"), "rw");
		
		for(int i=0; i < course.length; i++){
			raf.writeBytes(course[i].getCode());
			raf.writeBytes(course[i].getName());
			raf.writeInt(course[i].getCredit());
		}
		
		byte[] bytes = new byte[40];
		
		raf.seek(23);
		
		raf.read(bytes);
		String code = new String(bytes);
		
		
		raf.read(bytes);
		String name = new String(bytes);
		
		raf.read(bytes, 0, 4);
		int credit = ByteBuffer.wrap(bytes).getInt();
		System.out.println(new Course(code, name, credit));
		
	}

}

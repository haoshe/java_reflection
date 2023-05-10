package serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//序列化和反序列化
//serialization means write an Object to the hard disk from the memory. Deserialization is read the Object from the hard disk
//in order to serialize an Object, the class has to implement serializable interface
public class SerializableSample {
	
	//write several objects to the hard disk
	public static void writeObject() throws IOException {
		Person person1 = new Person("Katie", 12);
		Person person2 = new Person("Kevin", 18);
		Person person3 = new Person("Daniel", 15);
		
		//write and read have to be the same order
		//for keeping the order, we put all objects into a collection
		List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
		
		//write person class onto hard disk
		//specify the file name on the hard disk, where we want to write the person class. 
		//new FileOutputStream(String name) Creates a file output stream to write to the file with the specified name
	     
		//can ObjectOutputStream write a basic type data (int a = 1) to the hard disk?
		//yes, ObjectOutputStream extends OutputStream, OutputStream is a byte stream, it can write basic type data, so does ObjectOutputStream
		//write(int),write(byte[]),write(byte[],int,int), these methods can write basic type data
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/haoshe/Desktop/pers.obj"));
		
		//write the collection of objects to that file on the hard disk
		oos.writeObject(persons);
		
		oos.close();
	}
	
	public static void readObject() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		//read the Object from a file on the hard disk through ObjectInputStream(对象输入流)
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/Users/haoshe/Desktop/pers.obj"));
		
		Object personObj = ois.readObject();
		
		//cast(强转) to Person object and print并打印
		List<Person> pers = (List<Person>)personObj;
		for(Person per : pers) {
			System.out.println(per.getName() + "," + per.getAge());
		}
		ois.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		//writeObject();
		readObject();

	}
}

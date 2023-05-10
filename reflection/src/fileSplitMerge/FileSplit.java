package fileSplitMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/*
 * file split and merge
 * 1. split a size 7.5mb file into 8 sub files(7*1mb + 1*0.5mb)
 * 2. merge the sub files back to original size file 7.5mb
 */

public class FileSplit {
	
	public static void main(String[] args) throws IOException {
		
		//source file to be split
		File sourceFile = new File("/Users/haoshe/Desktop/solicitors letter 1.pdf");
		
		//new File() not only can get a file, but also can create a directory
		//create the directory used to store split files (生成一个目录,用来存放拆分后的文件)
		File splitDir = new File("/Users/haoshe/Desktop/splitDir");
		
		//number of software usage: 5
		if(hasRemainingTries()) {
			//the method is used to split the sourceFile into several parts, and put these parts into splitDir folder
			splitFile(sourceFile, splitDir);
		}else {
			System.out.println("the number of usage has reached the limit.");
		}
		
	}
	
	//number of usages has to be stored on the hard disk, can't be in the memory
	//every time we use the software, comparing the number with 5, then increase the number by one
	private static boolean hasRemainingTries() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		int count = 0;
		//every time when use: 1.check how many times has been used(3); 2.then add 1 to the previous number of times(4)
		//check how many times has been used previously
		prop.load(new FileInputStream("/Users/haoshe/Desktop/splitDir/tries.properties"));
		
		//the first time we will get a null
		String times = prop.getProperty("times");
		
		if(times == null) {//1st time
			//first time we get null. After using it, count becomes 1. Then we need to put 1 back
			count = 1;
			prop.setProperty("times", count+"");
		}else {//not the first time
			//get the previous usage times, add 1 to it, then put it back
			int timeCount = Integer.parseInt(times);
			timeCount++;
			prop.setProperty("times", timeCount+"");
			
			if(timeCount>5) {
				return false;
			}
		}
		prop.store(new FileOutputStream("/Users/haoshe/Desktop/splitDir/tries.properties"), "number of usages");
		return true;
	}

	public static void splitFile(File sourceFile, File splitDir) throws IOException {
		
		//if the directory doesn't exit, we create it
		//mkdirs() means create several directory .../splitDir/a/b/c 
		if(!splitDir.exists()) {
			splitDir.mkdir();
		}
		
		//input stream is to put file into memory from hard disk
		//output stream is to put file from memory onto hard disk
		//split: 1 input stream, n output streams(a,b,c)
		//merge: n input stream, 1 output streams(order matters a,b,c)
		
		//split(拆分)： 1 input stream
		FileInputStream in = new FileInputStream(sourceFile);
		
		OutputStream out = null;
		
		//define a buffer(缓冲区) in the memory
		//1024byte = 1kb; 1024*1024 = 1mb
		//our intention is whenever the buffer(size 1mb) is full, we flush its contents out to a file，also clear out the buffer
		//定义缓冲区为1M，当缓冲区填满时，一次性刷出一个文件，并把缓冲区清空
		byte[] buf = new byte[1024*1024];
		
		int len = -1;
		//this variable is to dynamically create a file name: 1.part, 2.part, 3.part...
		int count = 1;
		while((len = in.read(buf)) != -1) {
			
			//appoint a path where the output file stream should go to
			//new File(filePath, fileName): new File("c:/abc", "hello.txt") => c:/abc/hello.txt
			//the file name will be: 1.part,2.part,3.part,...
			out = new FileOutputStream(new File(splitDir, count++ +".part"));
			out.write(buf,0,len);
			
			//move the data in the buffer zone to count.part file before close the out stream
			//这个操作确保缓冲区的数据被涮到指定文件里
			//out.close();
			
			//only move the data to count.part, not closing the out stream
			out.flush();
		}
		//when while loop finishes, count variable will be 1 number bigger than it is inside while loop
		
		//when we split the file, how do we keep the original file name and the number of split files, for the preparation of combining sub files into the original file later
		//the way is to create a configuration file(9.conf), storing above description message 
		
		//method1:
		//config.properties is the common name for configuration files
		out = new FileOutputStream(new File(splitDir, "config.properties"));
		
		//output stream is a byte stream, has to be converted to byte array
		//inside 9.config file, will like: filename=solicitors letter 1.pdf
		//                                 partcount=2
		
		//we need a line separator for each line, otherwise it will be like: filename=solicitors letter 1.pdfpartcount=2
		//get current operation system line separator(当前操作系统换行符)
//		String lineSeparator = System.getProperty("line.separator");
//		out.write(("filename=" + sourceFile.getName()).getBytes());
//		out.write(lineSeparator.getBytes());
//		out.write(("partcount=" + (count-1)).getBytes());
//		out.close();
		//out.flush();
		
		//method 1 is too troublesome
		//method2: Properties write properties in the memory on to hard disk in a form of key=value
		Properties prop = new Properties();
		
		//Properties can change lines automatically after each key=value
		//key and value both have to be strings. add "" can convert a number into a string
		//setProperty() is to assign a value to a key
		prop.setProperty("filename", sourceFile.getName());
		prop.setProperty("partcount", (count-1)+"");
		
		//after assigning the value to a key, we need to write them to the hard disk
		//写入硬盘(保存: 持久化)
		//* @param   out      an output stream.
	    //* @param   comments   a description of the property list.
		//public void store(OutputStream out, String comments)
		prop.store(out, "file configuration...");
		
		out.close();
		in.close();
	}
}

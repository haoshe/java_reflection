package fileSplitMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MergeFile {
	
	public static void main(String[] args) throws IOException {
		
		//method 1
		/*
		//read the multiple split files(读取多个拆分后到文件)
		//read into the memory first, then flush them out to the hard disk
		//we need one input stream to read each file, if there are 9 files, we need 9 input stream
		//so we put all these input streams into an array list(inputs: collection of all input streams)
		List<FileInputStream> inputs = new ArrayList<>();
		for(int i=1; i<=2; i++) {
			inputs.add(new FileInputStream("/Users/haoshe/Desktop/splitDir/" + i + ".part"));
		}
		
		//specify the output stream of the merged files
		OutputStream out = new FileOutputStream("/Users/haoshe/Desktop/SplitAndMergedFile.pdf");
		
		//read multiple input streams into memory in order, then output it to SplitAndMergedFile.pdf in one go
		byte[] buffer = new byte[1024*1024];
		
		for(FileInputStream in : inputs) {
			int len = in.read(buffer);
			out.write(buffer,0,len);
		}
		out.close();
		
		//close input streams in turn
		for(FileInputStream in : inputs) {
			in.close();
		}
		*/
		
		//method 2
		//specify the location of the split files
		File splitDir = new File("/Users/haoshe/Desktop/splitDir");
		mergeFile(splitDir);
	}
	
	//merge files in splitDir folder
	public static void mergeFile(File splitDir) throws IOException {
		List<FileInputStream> inputs = new ArrayList<>();
		for(int i=1; i<=2; i++) {
			inputs.add(new FileInputStream("/Users/haoshe/Desktop/splitDir/" + i + ".part"));
		}
		
		//new SequenceInputStream() needs an Enumeration type argument
		//Enumeration is an interface, was created in Java 1.0. It is the same as a List
		//List is created in Java 1.2, we need to change List type to Enumeration type
		Enumeration<FileInputStream> en = Collections.enumeration(inputs);
		
		//multiple streams -> one single stream (多个流 -> 一个流)
		/*
		 * A {@code SequenceInputStream} represents
		 * the logical concatenation of other input
		 * streams. It starts out with an ordered
		 * collection of input streams and reads from
		 * the first one until end of file is reached,
		 * whereupon it reads from the second one,
		 * and so on, until end of file is reached
		 * on the last of the contained input streams.
		 */
		SequenceInputStream sqInput = new SequenceInputStream(en);
		
		//specify the output stream of the merged files(指定合并后的文件输出流)
		OutputStream out = new FileOutputStream("/Users/haoshe/Desktop/SplitAndMergedFile.pdf");
		
		//output sqInput(similar to file copy/类似于文件复制)
		byte[] buffer = new byte[1024];
		int len  = -1;
		while((len=sqInput.read(buffer)) != -1) {
			out.write(buffer,0,len);
		}
		out.close();
		sqInput.close();
	}
}

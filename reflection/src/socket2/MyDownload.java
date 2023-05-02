package socket2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//handle the thread for the client to download from the server
public class MyDownload implements Runnable{
	
	private Socket socket;
	public MyDownload() {
		
	}
	
	public MyDownload(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {// what the thread should do is written inside run() method
		
		System.out.println("connection with the client has established!");
		
		try {
			//for the run() to execute, we have to have this socket
			//the default value of the socket is null (private Socket socket;)
			//we need to pass this socket(Socket socket = server.accept();) to the socket through the constructor
			//the server sends a message to the client
			OutputStream out = socket.getOutputStream();
			
			//prepare for the document to be sent
			//IMPORTANT: the file has to be in the same folder as Eclipse, otherwise it will throw java.io.FileNotFindException:(Operation not permitted)
			File file = new File("/Applications/untitled folder/10gospel.pdf");
			
			//before we send the file, we have to read the file from the hard disc into the memory first using new FileInputStream()
			//Pay attention, this FileInputStream is different to Socket.getI/O stream()
			//Socket.getI/O stream is used for network transmission
			//read this file from the hard disk to memory
			InputStream fileIn = new FileInputStream(file);
			
			//define the size of the file parts to be sent each time in a byte array. think it as a buffer(缓冲区)
			byte[] fileBytes = new byte[1000];//send 1000 bytes each time
			
			//sending(because the file is too big to be sent at once, it needs to be sent in different parts using a loop)
			//fileIn.read(fileBytes) => read fileIn into fileBytes array non-stop, each time 100 bytes
			//so fileBytes array has some parts of filesIn in it, hence we can send it
			//read 100bytes, send 100bytes, read 100bytes, send 100bytes... until the reading has finished
			//fileIn.read(fileBytes) returns the number of bytes actually read, if it reads nothing, the return value will be -1
			//when the reading finishes, the while loop ends, no more sending
			int len = -1;
			while((len = fileIn.read(fileBytes)) != -1) {//as long as returned value != -1, we keep sending the file
				out.write(fileBytes);
			}
			
			//don't forget to close everything
			//don't close the server as we want to keep it running
			fileIn.close();
			out.close();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

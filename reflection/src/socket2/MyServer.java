package socket2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	public static void main(String[] args) throws IOException {
		//server uses ServerSocket to supply a port to the outside(client)
		//binding server's port, IP address defaults to the local IP address
		//the server releases a service to the outside, the address of the service: local IP : 9999
		ServerSocket server = new ServerSocket(9999);
		
		//ServerSocket provides accept(); method, to confirm the connection request from the client
		//we get accept() from ServerSocket, but the value it returns is a Socket type
		//we can understand it as because the client uses Socket to visit the server, the server accepts the cleint's visit, hence return value is a Socket
		Socket socket = server.accept();
		System.out.println("connection with the client has established!");
		
		//the server sends a message to the client
		OutputStream out = socket.getOutputStream();
		
//		//send String hello
//		String info = "Hello";//variable info is in the memory
//		//OutputStream type only sends bytes type data
//		//we need to change the String into a byte array
//		//why don't we just sent strings? because strings are special cases and bytes are universal.
//		//we can send any data forms by bytes
//		//using write() of OutputStream class to send a message
//		out.write(info.getBytes());
		
		//prepare for the document to be sent
		//IMPORTANT: the file has to be in the same folder as Eclipse, otherwise it will throw java.io.FileNotFindException:(Operation not permitted)
		File file = new File("/Applications/untitled folder/10gospel.pdf");
		
		//before we send the file, we have to read the file from the hard disc into the memory first using new FileInputStream()
		//Pay attention, this FileInputStream is different to Socket.getI/O stream()
		//Socket.getI/O stream is used for network transmission
		//read this file from the hard disk to memory
		InputStream fileIn = new FileInputStream(file);
		
		//define the size of the file parts to be sent each time in a byte array. think it as a buffer(缓冲区)
		byte[] fileBytes = new byte[100];//send 100 bytes each time
		
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
		fileIn.close();
		out.close();
		socket.close();
		server.close();
	}
}

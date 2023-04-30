package socket2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//client visits the server through Socket
		//the client connects to the service released by the server
		Socket socket = new Socket("192.168.1.33",9999);
		
		//accept the message sent by the server using InputStream
		InputStream in = socket.getInputStream();
		
//		//accept ordinary words hello
//		//read() only accepts byte type data, so we need a byte array to store the message send by the server
//		byte[] bs = new byte[100];
//		//using read() of InputStream class to receive a message
//		in.read(bs);
//		//bs is a byte array, we need to byte[] -> String for printing
//		System.out.println("client received the message from the server is: " + new String(bs));
		
		//the file parts we received from the server, will be stored in the memory
		//bs array is used to receive the file parts sent from the server, each time 100bytes 
		byte[] fileBytes = new byte[100];
		
		//the server sends the file parts through network transmission, we need Socket.getInputStream().read() to received them
		//Socket.getInputStream().read() returns a value, if the value is -1, it means we have received all files
		//variable len indicates the number of bytes we receive each time
		//each time len == 100bytes, the last time it might be less than 100 bytes
		int len = -1;
		
		//now we need to write the file from the memory to the hard disk, we use new FileOutputStream("destination path")
		//remember, when it has things to do with network transmission, we use Socke.getI/O stream()
		//          when transfer file from hard disk to memory or vice versa, we use new FileI/O stream()
		//the receiver can put the received file inside any folder, no need to be the same folder as eclipse
		FileOutputStream fileOut = new FileOutputStream("/Users/haoshe/Desktop/copy.pdf");
		
		//in.read() returns the number of bytes actually read, if it reads nothing, the return value will be -1
		while((len = in.read(fileBytes)) != -1) {//as long as the value != -1, we are still receiving the file
			/*
			 * @param      b     the data.
			 * @param      off   the start offset in the data.
			 * @param      len   the number of bytes to write.
			 * @throws     IOException  if an I/O error occurs.
			 */
			//write(byte b[], int off, int len)
			fileOut.write(fileBytes,0,len);
		}
			
		//don't forget to close everything
		fileOut.close();
		in.close();
		socket.close();
	}
}

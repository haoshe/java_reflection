package socket1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//client visits the server through Socket
		//the client connects to the service released by the server
		Socket socket = new Socket("192.168.1.55",9999);
		
		//accept the message sent by the server using InputStream
		InputStream in = socket.getInputStream();
		//read() only accepts byte type data, so we need a byte array to store the message send by the server
		byte[] bs = new byte[100];
		//using read() of InputStream class to receive a message
		in.read(bs);
		//bs is a byte array, we need to byte[] -> String for printing
		System.out.println("client received the message from the server is: " + new String(bs));
		
		//the client makes a feedback(反馈) to the server(sends a message to the server)
		OutputStream out = socket.getOutputStream();
		out.write("world".getBytes());
		
		//don't forget to close everything
		out.close();
		in.close();
		socket.close();
	}
}

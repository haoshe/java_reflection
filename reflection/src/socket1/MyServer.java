package socket1;

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
		
		String info = "Hello";
		//OutputStream type only sends bytes type data
		//we need to change the String into a byte array
		//why don't we just sent strings? because strings are special cases and bytes are universal.
		//we can send any data forms by bytes
		//using write() of OutputStream class to send a message
		out.write(info.getBytes());
		
		//accept the message from the client
		InputStream in = socket.getInputStream();
		byte[] bs = new byte[100];
		in.read(bs);
		System.out.println("accepted message from the client: " + new String(bs));
		
		//don't forget to close everything
		out.close();
		in.close();
		socket.close();
		server.close();
	}
}

package socket2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	public static void main(String[] args) throws IOException {
		//server uses ServerSocket to supply a port to the outside(client)
		//binding server's port, IP address defaults to the local IP address
		//the server releases a service to the outside, the address of the service: local IP : 9999
		ServerSocket server = new ServerSocket(9999);
		while(true) {
			//ServerSocket provides accept(); method, to confirm the connection request from the client
			//we get accept() from ServerSocket, but the value it returns is a Socket type
			//we can understand it as because the client uses Socket to visit the server, the server accepts the cleint's visit, hence return value is a Socket
			//we need to get the connection first, before we get a thread to handle the request. So don't put this statement in the run(){}
			Socket socket = server.accept();
			
//			//the thread for downloading the file
//			//through MyDownload constructor, pass the socket into the run() method
//			MyDownload download = new MyDownload(socket);
//			
//			//Runnable can't start on its own, has to be converted to the Thread(Runnable -> Thread)
//			Thread downloadThread = new Thread(download);
//			downloadThread.start();//start() execute run()
			
			//above commented code can be optimized as follows:
			new Thread(new MyDownload(socket)).start();
		}
	}
}

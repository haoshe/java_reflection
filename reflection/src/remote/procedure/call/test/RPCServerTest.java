package remote.procedure.call.test;

import remote.procedure.call.server.HelloService;
import remote.procedure.call.server.HelloServiceImpl;
import remote.procedure.call.server.Server;
import remote.procedure.call.server.ServerCenter;

public class RPCServerTest {
	public static void main(String[] args) {
		//start up a thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				//instantiate a service center object
				Server server = new ServerCenter(9999);
				//register HelloService class and its implementation class into the service center(map)
				server.register(HelloService.class, HelloServiceImpl.class);
				server.start();
			}
			
		}).start();//need to call start() method to start the thread

	}
}

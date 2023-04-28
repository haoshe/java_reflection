package remote.procedure.call.test;

import java.net.InetSocketAddress;

import remote.procedure.call.client.Client;
import remote.procedure.call.server.HelloService;

public class RPCClientTest {
	public static void main(String[] args) throws ClassNotFoundException {
		//because there is no HelloService class inside Client class, we have to pass in a String as a value
		//but the parameter requires a class, we use Class.forName() to get its class
		//we have to use the full class path to get the String's class
		HelloService service = Client.getRemoteProxyObj(Class.forName("remote.procedure.call.server.HelloService"), new InetSocketAddress("192.168.1.54",9999));
		System.out.println(service.sayHi("Katie"));
	}
}

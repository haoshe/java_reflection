package remote.procedure.call.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

//we need to start with server first
//服务中心的具体实现
public class ServerCenter implements Server{
	//map: 服务端的所有可供客户端访问的接口，都注册到该map中
	//key:接口的名字"HelloService"  value:真正的HelloService实现,用Class
	//you can write static or not, it doesn't matter
	private static HashMap<String, Class> serviceRegister = new HashMap();
	//端口名
	private static int port;//=9999. 最好不要写死，通过构造方法来传端口号
	public ServerCenter(int port) {
		this.port = port;
	}
	//开启服务端，给客户端暴露一个接口。开启服务必须通过ServerSocket
	@Override
	public void start(){
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			ServerSocket server = new ServerSocket();
			server.bind(new InetSocketAddress(port));
			//server.accept() will return a socket object, we can use socket object to accept request
			Socket socket = server.accept();//等待客户连接，要客户发起连结，所以开始写客户端
			//connection has been established between the client and the server
			//the server has received the request from the client, and is going to handle it
			//as we explained in the client, Object stream is easier to handle than input stream, hence we change inputStream to ObjectStream
			input = new ObjectInputStream(socket.getInputStream());
			//ObjectInputStream is very restrict about the order of the incoming data, it needs to be received one by one in the order of sending
			//use input.readUTF() to receive string type request
			String interfaceName = input.readUTF();
			String methodName = input.readUTF();
			//use input.readObject() to receive Object type request
			//it can be multiple parameter types, we use a Class array to receive these types. We can also use Object array, but Class array is more common
			Class[] parameterTypes = (Class[])input.readObject();//parameter types
			//the same goes for receiving parameters
			Class[] arguments = (Class[])input.readObject();//parameters
			//now we have received all the requests from the client, the next step is to find the specific interface corresponding to these requests inside map
			//get interface class based on interface name from the map
			Class interfaceClass = serviceRegister.get(interfaceName);//HelloService
			//after getting the interface, we find the method according to its name and parameter types
			Method method = interfaceClass.getMethod(methodName, parameterTypes);
			//after finding the method, we can call the method
			//make the return value as an object type because the return value can be any type
			Object result = method.invoke(interfaceClass.newInstance(), arguments);// String HelloService.sayHi(parameters);
			//the server has handled the client's request(call a method of an interface impl), the method returns a value, now we need to give the value back to the client
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//close input and output streams. 
			//don't do it like: input.close(), because if input = null, it will throw a Null pointer exception
			try {
				if(input != null)input.close();
				if(output != null)output.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		
	}
//  there is no problem to write this way, but we can optimize it
//	@Override
//	public void register(String serviceName, Class serviceImpl) {
//		serviceRegister.put(serviceName, serviceImpl);
//	}

	//as there is no use to get just the String name, we may as well get the string's reflection entrance
	//服务端收到客户端发过来的string，需得到此string对应的class才能操作
	@Override
	public void register(Class service, Class serviceImpl) {
		serviceRegister.put(service.getName(), serviceImpl);
	}

}

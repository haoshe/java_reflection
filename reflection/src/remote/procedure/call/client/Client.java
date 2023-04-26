package remote.procedure.call.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

//客户端通过动态代理对象访问服务端
//client requests to call a method of an interface in the server
public class Client {
	//获取代表服务端接口的动态代理对象(HelloService)
	//因为有可能接收任何类型的接口，所以返回值为T，代表任意类型的动态代理对象
	//serviceInterface: 请求的接口名
	//addr:待请求服务端端IP：端口
	@SuppressWarnings("unchecked")
	public static <T> T getRemoteProxyObj(Class serviceInterface, InetSocketAddress addr) {
		//newProxyInstance(a,b,c);
		/*
		 * a:类加载器。需要代理哪个类（例如HelloService接口），就需要将HelloService的类加载器传入第一个参数。没有什么业务逻辑，就是凑语法
		 * b:需要代理的对象具备哪些功能（方法）。方法是在接口里定以的，所以这个参数就是放接口的
		 *   单继承，多实现，一个类可以实现多个接口。所以这个参数放Class类型的接口数组
		 *   String str = new String();
		 *   String[] str = new String[]{"aa","bb","cc"};
		 * c:根据语法，需要一个InvocationHandler h对象
		 * 	 我们可以这样写：InvocationHandler handler = new InvocationHandlerImpl();
		 *   InvocationHandler is an interface, we have to write a class to implement InvocationHandler, override its method,
		 *   then instantiate an object of this class, pass the object as the argument. This is too complicated.
		 *   we can write an anonymous internal class
 		 */
		return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[] {serviceInterface}, new InvocationHandler(){
			//动态代理最终是通过此方法来实现的
			//this method will return a value, which is the returned value from the server
			/*
			 * proxy:需要代理的对象
			 * method：代理对象的方法（sayHello(list of parameters)）
			 * args:list of parameters(方法的参数列表)
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				ObjectOutputStream output = null;
				ObjectInputStream input = null;
				try {
					//client sends a request to the server, requesting a specific interface
					//the request is sent through Socket
					Socket socket = new Socket();
					//we need to a socketAddress, which has Ip:port. addr is the argument past into the method: public static <T> T getRemoteProxyObj(Class serviceInterface, InetSocketAddress addr)
					socket.connect(addr);
					//once we have the address(IP:Port), now we can send the request through OutPutStream()(输出流）
					//however, OutPutStream is a byte stream(字节流), it's a bit more troublesome（麻烦)
					//so instead we usually use serialized stream(序列化流) or object stream(对象流) 
					output = new ObjectOutputStream(socket.getOutputStream());
					//the client wants to call a method of an interface, he needs to send some information
					//information to be sent to the server: interface name, method name, method's parameter name, parameter type
					//for example: String Id, String is the parameter type, Id is the parameter name
					//interface name and method name are strings, use method: writeUTF()
					output.writeUTF(serviceInterface.getName());//send interface name
					output.writeUTF(method.getName());//send mehtod name
					// there could be multiple parameter types and parameters, that's why we have to use Object to receive any class types
					output.writeObject(method.getParameterTypes());
					output.writeObject(args);
					//client has sent the all the requests, now we are waiting for the server to handle these requests...
					//received the returned value of the method from the server
					input = new ObjectInputStream(socket.getInputStream());
					return input.readObject();//if there is an exception, this statement will not be executed, there will be no value returned
				}catch(Exception e) {//if there is an exception, the program will jump directly here
					e.printStackTrace();
					//that's why we have to write return null here
					return null;
				}finally {
					try {
						if(output != null) output.close();
						if(input != null) input.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}

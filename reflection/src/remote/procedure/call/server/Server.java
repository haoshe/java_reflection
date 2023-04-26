package remote.procedure.call.server;

//service center
public interface Server{
	//接口方法实现类有异常，那么接口方法也要抛同样的异常
	//if the implementation class of the interface throws an exception, then the interface has to throw the same exception
	//because the implementation class might throw multiple different exceptions,
	//for convenience, the interface can just throw Exception
	//but in this case, we use try catch to deal with exceptions, so the interface doesn't need to throw any exceptions
	public void start();
	public void stop();
	//registry service
//	public void register(String serviceName, Class serviceImpl);
	
	public void register(Class service, Class serviceImpl);
}

package remote.procedure.call.server;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHi(String name) {
		return "hi, " + name;
	}
}

package chen.webservice.jws;

import java.util.Arrays;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WebServiceTest {

	@WebMethod
	public String sayHello(String name) {
		return "hello " + name;
	}
	
	@WebMethod
	public String transStringArray(String[] arr) {
		return "I get " + Arrays.toString(arr);
	}
	
	@WebMethod
	public String[] getStringArray() {
		return new String[] { "arr1", "arr2" };
	}
	
	public static void main(String[] args) {
		WebServiceTest wst = new WebServiceTest();
		Endpoint.publish("http://localhost:8080/wst", wst);
	}
}

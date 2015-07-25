package chen.exception;

public class ExceptionUtilTest {

	public static void main(String[] args) {
		testGetFirstCause();
//		testGetFirstMessage();
	}

	public static void testGetFirstCause() {
		Exception e1 = new Exception("e1");
		Exception e2 = new Exception(e1);
		Throwable cause = ExceptionUtil.getFirstCause(e2);
		System.out.println(cause.getMessage());

		e1 = new Exception();
		e2 = new Exception(e1);
		cause = ExceptionUtil.getFirstCause(e2);
		System.out.println(cause.getMessage());
		
		cause = ExceptionUtil.getFirstCause(new Exception("111"));
		System.out.println(cause.getMessage());
		
		
	}
	
	public static void testGetFirstMessage() {
		Exception e1 = new Exception("e1");
		Exception e2 = new Exception(e1);
		System.out.println(ExceptionUtil.getFirstMessage(e2));
		
		e1 = new Exception();
		e2 = new Exception(e1);
		System.out.println(ExceptionUtil.getFirstMessage(e2));
		
		System.out.println(ExceptionUtil.getFirstMessage(new Exception("111")));
	}
}

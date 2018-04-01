package farm;

import java.lang.reflect.Method;


public class FirstClass {
	//private UIMain main = new UIMain();
	
	public void firstMethod() {
		
		try {
			Class<?> c = Class.forName("farm.Controller");
	        Object main = c.newInstance();
	        Method m = main.getClass().getMethod("endTurn");
	        m.invoke(main);
		} catch (Exception e) {
			
		}
	}
	public static void main (String [] args) {
		FirstClass fc = new FirstClass();
		fc.firstMethod();
	}

}
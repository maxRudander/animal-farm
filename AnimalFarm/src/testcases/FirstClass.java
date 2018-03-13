package testcases;

public class FirstClass {
	public void firstMethod() {
		System.out.println("Welcome to the farm.\rEnjoy your stay!");
	}
	public static void main (String [] args) {
		FirstClass fc = new FirstClass();
		fc.firstMethod();
	}

}
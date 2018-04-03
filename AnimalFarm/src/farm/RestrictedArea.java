package farm;

import java.util.ArrayList;

public class RestrictedArea {

	private ArrayList bordersInBound = new ArrayList();
	private ArrayList bordersOutBound = new ArrayList();
	private Animal animal;

	public RestrictedArea() {
//		this.animal = animal;
	}

	public void addInBoundBorder(int x1, int y1, int x2, int y2) {
		int[] borderArray = new int[4];
		borderArray[0] = x1;
		borderArray[1] = y1;
		borderArray[2] = x2;
		borderArray[3] = y2;
		bordersInBound.add(borderArray);
	}

	public void addOutBoundBorder(int x1, int y1, int x2, int y2) {
		int[] borderArray = new int[4];
		borderArray[0] = x1;
		borderArray[1] = y1;
		borderArray[2] = x2;
		borderArray[3] = y2;
		bordersOutBound.add(borderArray);
	}

	
	
	public boolean checkMovement(int x, int y) {
		if (!checkInBoundAnimal(x,y)) {
			return false;
		}
		if (!checkOutBoundAnimal(x,y)) {
			return false;
		}
		return true;
	}

	

	public boolean checkOutBoundAnimal(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersOutBound.size() && result == true; i++) {
			int[] array = (int[]) bordersOutBound.get(i);
			if ((x+40  >= array[0] && x <= array[2]) && (y+40  >= array[1] && y <= array[3])) {
//				System.out.println("checkOutBoundX - vänd");
				result = false;
			}
		}
		return result;
	}	
	public boolean checkInBoundAnimal(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersInBound.size() && result == true; i++) {
			int[] array = (int[]) bordersInBound.get(i);
			if (x <= array[0] || x + 40 >= array[2] || (y<=array[1]) || y+40>=array[3]) {
				result = false; // VÄND
			}
		}
		return result;
	}	

	public boolean check(int x, int y) {
		if (!checkInBound(x,y)) {
			return false;
		}
		if (!checkOutBound(x,y)) {
			return false;
		}
		return true;
	}

	

	public boolean checkOutBound(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersOutBound.size() && result == true; i++) {
			int[] array = (int[]) bordersOutBound.get(i);
			if ((x  >= array[0] && x <= array[2]) && (y  >= array[1] && y <= array[3])) {
//				System.out.println("checkOutBoundX - vänd");
				result = false;
			}
		}
		return result;
	}	
	public boolean checkInBound(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersInBound.size() && result == true; i++) {
			int[] array = (int[]) bordersInBound.get(i);
			if (x <= array[0] || x  >= array[2] || (y<=array[1]) || y>=array[3]) {
//				System.out.println("Check Inbound X - vänd");
				result = false; // VÄND
			}
		}
		return result;
	}	
}




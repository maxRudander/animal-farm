package farm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that handles restricted areas
 * 
 * @author Mikael Lindfors
 *
 */
public class RestrictedArea implements Serializable{
	private ArrayList bordersInBound = new ArrayList();
	private ArrayList bordersOutBound = new ArrayList();

	public RestrictedArea() {
	}

	/**
	 * Method that adds coordinates for an inbound border (used for fences)
	 * 
	 * @param x1 x1 coordinate
	 * @param y1 y1 coordinate
	 * @param x2 x2 coordinate
	 * @param y2 y2 coordinate
	 */
	public void addInBoundBorder(int x1, int y1, int x2, int y2) {
		int[] borderArray = new int[4];
		borderArray[0] = x1;
		borderArray[1] = y1;
		borderArray[2] = x2;
		borderArray[3] = y2;
		bordersInBound.add(borderArray);
	}

	/**
	 * Method that adds coordinates for an outbound border (used for keeping animals
	 * out of buildings)
	 * 
	 * @param x1 x1 coordinate
	 * @param y1 y1 coordinate
	 * @param x2 x2 coordinate
	 * @param y2 y2 coordinate
	 */
	public void addOutBoundBorder(int x1, int y1, int x2, int y2) {
		int[] borderArray = new int[4];
		borderArray[0] = x1;
		borderArray[1] = y1;
		borderArray[2] = x2;
		borderArray[3] = y2;
		bordersOutBound.add(borderArray);
	}
	public void removeOutBoundBorder(int x1, int y1, int x2, int y2) {
		for (int i=0;i<bordersOutBound.size();i++) {
			int [] tempArray =(int[]) bordersOutBound.get(i);
			if ((tempArray[0] == x1) && (tempArray[1] == y1  ) && (tempArray[2] == x2) && (tempArray[3] == y2)) {
				bordersOutBound.remove(i);
			}
		}
	}

	/**
	 * Method that checks if an animals next movement is ok for both inbound and
	 * outbound restrictions.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean result
	 */
	public boolean checkMovement(int x, int y) {
		if (!checkInBoundAnimal(x, y)) {
			return false;
		}
		if (!checkOutBoundAnimal(x, y)) {
			return false;
		}
		return true;
	}

	/**
	 * Method used for checking restricted areas that animals should keep away from
	 * (Staying away from buildings)
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean
	 */
	public boolean checkOutBoundAnimal(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersOutBound.size() && result == true; i++) {
			int[] array = (int[]) bordersOutBound.get(i);
			if ((x + 40 >= array[0] && x <= array[2]) && (y + 40 >= array[1] && y <= array[3]) && !((x > array[0] - 10
					&& x + 40 < array[2] + 10 && y > array[1] - 10 && y + 40 < array[3] + 10))) {
				result = false; // Turn around
			}
		}
		return result;
	}


	/**
	 * Method used for checking restricted areas that animals should avoid (used for fences)
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean
	 */
	public boolean checkInBoundAnimal(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersInBound.size() && result == true; i++) {
			int[] array = (int[]) bordersInBound.get(i);
			if (((x <= array[0] || x + 40 >= array[2]) || ((y <= array[1]) || y + 40 >= array[3]))
					&& ((x > array[0] - 10 && x + 40 < array[2] + 10 && y > array[1] - 10 && y + 40 < array[3] + 10))) {
				result = false; // Turn around
				break;
			}
		}
		return result;
	}

	/**
	 * Method used for checking if a area is restricted when placing buildings. (Not used at the moment)
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean
	 */
	public boolean check(int x, int y) {
		if (!checkInBound(x, y)) {
			return false;
		}
		if (!checkOutBound(x, y)) {
			return false;
		}
		return true;
	}

	/**
	 * Method used for checking if a area is restricted when placing buildings. (Not used at the moment)
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean
	 */
	public boolean checkOutBound(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersOutBound.size() && result == true; i++) {
			int[] array = (int[]) bordersOutBound.get(i);
			if ((x >= array[0] && x <= array[2]) && (y >= array[1] && y <= array[3])) {
				result = false; // Turn around
			}
		}
		return result;
	}

	/**
	 * Method used for checking if a area is restricted when placing buildings. (Not used at the moment)
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean
	 */
	public boolean checkInBound(int x, int y) {
		boolean result = true;
		for (int i = 0; i < bordersInBound.size() && result == true; i++) {
			int[] array = (int[]) bordersInBound.get(i);
			if (x <= array[0] || x >= array[2] || (y <= array[1]) || y >= array[3]) {
				result = false; // Turn around
			}
		}
		return result;
	}
}

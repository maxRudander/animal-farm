package farm;

import java.util.ArrayList;

public class RestrictedArea {

	private ArrayList bordersInBound = new ArrayList();
	private ArrayList bordersOutBound = new ArrayList();
		
	public RestrictedArea() {
		
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
	public boolean checkCoordinate(int x, int y) {
		for (int index1 = 0; index1<bordersInBound.size();index1++) {
			int[] array = (int[]) bordersInBound.get(index1);
			if ( x)
		}
		
		
		
		
		return false;
		
	}
	
	
}

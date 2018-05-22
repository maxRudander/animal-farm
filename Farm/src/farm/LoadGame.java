package farm;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadGame {
	private Controller controller;
	private String filename;
	private int x;
	private int y;
	private int counter = 1;
	
	public LoadGame(Controller controller) {
		this.controller = controller;
	}
	public void setFilename(String keyword) {
		this.filename = "saveFiles/" + keyword + ".txt";
		readInt();
	}
	public void readInt() {
		String xy;
		try(DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream (filename)))){
			int n = dis.readInt();
			switch (counter){
			
			case 1: 
				controller.setLoadedDay(n);
				break;
			case 2: 
				controller.setLoadedCash(n);
				break;
			case 3: 
				controller.loadAnimals("Cow", n);
				break;
			case 4: 
				controller.loadAnimals("Pig", n);
				break;
			case 5: 
				controller.loadAnimals("Sheep", n);
				break;
			case 6: 
				controller.loadAnimals("Chicken", n);
				break;
//			case 7: 
//				xy = String.valueOf(n);
//				this.y = Integer.parseInt(xy.substring(1));
//				this.x = Integer.parseInt(xy.substring(0, 1));
//			case 8:
//				controller.loadBuildings(x, y, n, "Barn");
//			case 9: 
//				xy = String.valueOf(n);
//				this.y = Integer.parseInt(xy.substring(1));
//				this.x = Integer.parseInt(xy.substring(0, 1));
//			case 10:
//				controller.loadBuildings(x, y, n, "PigSty");
//			case 11: 
//				xy = String.valueOf(n);
//				this.y = Integer.parseInt(xy.substring(1));
//				this.x = Integer.parseInt(xy.substring(0, 1));
//			case 12:
//				controller.loadBuildings(x, y, n, "HenHouse");
//			case 13: 
//				xy = String.valueOf(n);
//				this.y = Integer.parseInt(xy.substring(1));
//				this.x = Integer.parseInt(xy.substring(0, 1));
//			case 14:
//				controller.loadBuildings(x, y, n, "Stable");
			}
			counter++;
			if (counter == 7) {
				counter = 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	public void fileChooser() {	
		setFilename("Day");
		setFilename("Money");
		setFilename("Cow");
		setFilename("Pig");
		setFilename("Sheep");
		setFilename("Chicken");
//		new LoadGame("BarnLocation");
//		new LoadGame("Barn");
//		new LoadGame("PigstyLocation");
//		new LoadGame("Pigsty");
//		new LoadGame("HenHouseLocation");
//		new LoadGame("HenHouse");
//		new LoadGame("StableLocation");
//		new LoadGame("Stable");
	}
}

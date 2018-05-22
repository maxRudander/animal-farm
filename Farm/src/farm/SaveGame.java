package farm;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;

public class SaveGame {
	private String filename = "";
	private LinkedList <Integer>numbers = new LinkedList <Integer>();
	
	public SaveGame(LinkedList numbers) {
		this.numbers = numbers;
		fileChooser();
	}
	public SaveGame(String keyword, int number) {
		this.filename = "saveFiles/" + keyword + ".txt";
		saveInt(number);
	}
	public void saveInt(int number) {
		try( DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
    			new FileOutputStream(filename)))) {
				dos.writeInt(number);
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	public void fileChooser() {
		new SaveGame("Day", numbers.pop());
		new SaveGame("Money",numbers.pop());
		new SaveGame("Cow",numbers.pop());
		new SaveGame("Pig",numbers.pop());
		new SaveGame("Sheep",numbers.pop());
		new SaveGame("Chicken",numbers.pop());
//		new SaveGame("Barn", numbers.pop());
//		new SaveGame("Pigsty",numbers.pop());
//		new SaveGame("HenHouse",numbers.pop());
//		new SaveGame("Stable",numbers.pop());
//		new SaveGame("BarnLocation",numbers.pop());
//		new SaveGame("PigstyLocation",numbers.pop());
//		new SaveGame("HenHouseLocation",numbers.pop());
//		new SaveGame("StableLocation",numbers.pop());
	}
}

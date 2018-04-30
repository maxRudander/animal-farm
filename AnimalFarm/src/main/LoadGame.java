package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
/**
 * Class for loading an game.
 * @author Matthias Falk
 *
 */
public class LoadGame {
	private String filename = "saves/gamesave.dat";
	private LinkedList loadedList = new LinkedList();
	private ObjectInputStream ois;

	public LoadGame () throws IOException {
		ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream (filename)));
	}
	/**
	 * Method that reads the text file for loading an game and returns it
	 * @return loadedList the list that has been loaded
	 */
	public LinkedList load() {
		try {
			loadedList = (LinkedList) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		return loadedList;
	}
}

package farm;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import commodity.Cow;
import event.EventHandler;
import property.Barn;
import ui.UIMain;
/**
 * Console Playground for fun and games :) To add a command, create a new case
 * in the method triggerCommand. It would also be helpful (although not
 * necessary) if the added command was added to the list. Simply add a line to
 * the constructor.
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk
 *
 */
public class Console extends JPanel implements KeyListener {
	private UIMain ui;
	private Board mainBoard;
	private JTextField tField = new JTextField();
	private JTextArea tArea = new JTextArea();
	private ArrayList<String> commands = new ArrayList<String>();
	private EventHandler handler = EventHandler.getInstance();

	/** 
	 * Constructor for the Console.
	 * 
	 * @param ui - the UIMain to interact with
	 * @param mainBoard - the Board to interact with.
	 */
	public Console(UIMain ui, Board mainBoard) {
		this.ui = ui;
		this.mainBoard = mainBoard;
		setLayout(new BorderLayout());
		tField.addKeyListener(this);
		tArea.setEditable(false);
		tArea.setText("Welcome!" + System.lineSeparator() + "Type above. Press ENTER to send" + System.lineSeparator());
		add(tField, BorderLayout.NORTH);
		add(tArea, BorderLayout.CENTER);

		commands.add("add cow");
		commands.add("remove cow");
		commands.add("add cow xy");
		commands.add("add barn");
		commands.add("grid on");
		commands.add("grid off");
		commands.add("add border");
		commands.add("run event");
		commands.add("set season");

	}
	/**
	 * Triggers the command written in the text field. If the command is invalid, all available commands are to be printed.
	 * @param str - the written command.
	 */
	public void triggerCommand(String str) {
		int amount;
		int x;
		int y;
		tField.setText("");

		write(str);
		switch (str) {
		case "add cow":
			amount = Integer.parseInt(JOptionPane.showInputDialog(this, "How many cows would you like to add?"));
			for (int i = 0; i < amount; i++) {
				mainBoard.addAnimal(new Cow());
			}
			write(amount + " cows added.");
			break;

		case "remove cow":
			amount = Integer.parseInt(JOptionPane.showInputDialog(this, "How many cows would you like to remove?"));
			for (int i = 0; i < amount; i++) {
				mainBoard.removeAnimal(new Cow());
			}
			write(amount + " cows removed.");
			break;
		case "add cow xy":
			amount = Integer.parseInt(JOptionPane.showInputDialog(null, "How many cows would like to add?"));
			x = Integer.parseInt(JOptionPane.showInputDialog(null, "x coord"));
			y = Integer.parseInt(JOptionPane.showInputDialog(null, "y coord"));
			for (int i = 0; i < amount; i++) {
				mainBoard.addAnimal(new Cow(x, y));
			}
			write(amount + " cows added.");
			break;

		case "add barn":
			x = Integer.parseInt(JOptionPane.showInputDialog(null, "x coordinate"));
			y = Integer.parseInt(JOptionPane.showInputDialog(null, "y coordinate"));
			mainBoard.addBuilding(new Barn(x, y));
			break;
		case "grid on":
			mainBoard.grid(true);
			break;
		case "grid off":
			mainBoard.grid(false);
			break;
		case "add fence":
			int x1 = Integer.parseInt(JOptionPane.showInputDialog(null, "First x coordinate"));
			int y1 = Integer.parseInt(JOptionPane.showInputDialog(null, "First y coordinate"));
			int x2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Second x coordinate"));
			int y2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Second x coordinate"));
			mainBoard.addFence(x1, y1, x2, y2);
			break;
		case "run event":
			int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Event ID"));
			handler.runEvent(id);
			break;
		case "set season":
			int choice = -1;
			String season;
			season = JOptionPane.showInputDialog("Pick a season:\n1. Spring\n2. Summer\n3. Autumn\n4. Winter");
			try {
				choice = Integer.parseInt(season);
			}
			catch (NumberFormatException e) {
				choice = -1;
			}
			if (choice == 1) {
				mainBoard.alterSeason(Board.SPRING);
				write("Background: Spring");
			}
			else if (choice == 2) {
				mainBoard.alterSeason(Board.SUMMER);
				write("Background: Summer");
			}
			else if (choice == 3) {
				mainBoard.alterSeason(Board.AUTUMN);
				write("Background: Autumn");
			}
			else if (choice == 4) {
				mainBoard.alterSeason(Board.WINTER);
				write("Background: Winter");
			}
			else {
				break;
			}
			break;
			/*
			 * TODO
			 * add new commands here!!
			 * Don't forget break :)
			 */
		default:
			for (String cmd : commands) {
				write(cmd);
			}
			write("NOT a valdid command!" + System.lineSeparator() + "Available commands:");
		}
	}
	/**
	 * Updates the text area with a new addition.
	 * @param str - the text to be added.
	 */
	public void write(String str) {
		tArea.setText(str + System.lineSeparator() + tArea.getText());
	}

	/**
	 * If the ENTER key is pressed, the written command is triggered. Should work
	 * regardless of system. Hopefully...
	 */
	public void keyTyped(KeyEvent e) {
		if (System.lineSeparator().indexOf(e.getKeyChar()) > -1) {
			triggerCommand(tField.getText());
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}

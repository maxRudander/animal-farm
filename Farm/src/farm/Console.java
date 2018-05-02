package farm;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import commodity.Cow;
import event.EventHandler;
import event.Season;
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
		tArea.setText("Welcome!\nType above. Press ENTER to send\n");
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
	 * @param cmd - the written command.
	 */
	public void triggerCommand(String cmd) {
		int nbr;	// use for all cases if a single input number is required.
		String str; // use for all cases if a single input string is required.
		int x1;
		int x2;
		int y1;
		int y2;
		tField.setText("");

		write(cmd);
		switch (cmd) {
		case "" :
			clear();
			break;
		case "add cow":
			nbr = parseInt(JOptionPane.showInputDialog(this, "How many cows would you like to add?"));
			for (int i = 0; i < nbr; i++) {
				mainBoard.addAnimal(new Cow());
			}
			write(nbr + " cows added.");
			break;

		case "remove cow":
			nbr = parseInt(JOptionPane.showInputDialog(this, "How many cows would you like to remove?"));
			for (int i = 0; i < nbr; i++) {
				mainBoard.removeAnimal(new Cow());
			}
			write(nbr + " cows removed.");
			break;
		case "add cow xy":
			nbr = parseInt(JOptionPane.showInputDialog(null, "How many cows would like to add?"));
			x1 = parseInt(JOptionPane.showInputDialog(null, "x coord"));
			y1 = parseInt(JOptionPane.showInputDialog(null, "y coord"));
			for (int i = 0; i < nbr; i++) {
				mainBoard.addAnimal(new Cow(x1, y1));
			}
			write(nbr + " cows added.");
			break;

		case "add barn":
			x1 = parseInt(JOptionPane.showInputDialog(null, "x coordinate"));
			y1 = parseInt(JOptionPane.showInputDialog(null, "y coordinate"));
			mainBoard.addBuilding(new Barn(x1, y1));
			break;
		case "grid on":
			mainBoard.grid(true);
			break;
		case "grid off":
			mainBoard.grid(false);
			break;
		case "add fence":
			x1 = parseInt(JOptionPane.showInputDialog(null, "First x coordinate"));
			y1 = parseInt(JOptionPane.showInputDialog(null, "First y coordinate"));
			x2 = parseInt(JOptionPane.showInputDialog(null, "Second x coordinate"));
			y2 = parseInt(JOptionPane.showInputDialog(null, "Second y coordinate"));
			str = "Your coordinates are:\nx1: "+x1+"\ny1: "+y1+"\nx2: "+x2+"\ny2: "+y2; 
			if (JOptionPane.showConfirmDialog(null, str+"\n\nConfirm command!", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				mainBoard.addFence(x1, y1, x2, y2);
			}
			break;
		case "run event":
			nbr = parseInt(JOptionPane.showInputDialog(null, "Enter Event ID"));
			handler.runEvent(nbr);
			break;
		case "set season":
			str = JOptionPane.showInputDialog("Pick a season:\n1. Spring\n2. Summer\n3. Autumn\n4. Winter");
			nbr = parseInt(str);
			if (nbr == 1) {
				mainBoard.alterSeason(Season.SPRING);
				write("Background: Spring");
			}
			else if (nbr == 2) {
				mainBoard.alterSeason(Season.SUMMER);
				write("Background: Summer");
			}
			else if (nbr == 3) {
				mainBoard.alterSeason(Season.FALL);
				write("Background: Autumn");
			}
			else if (nbr == 4) {
				mainBoard.alterSeason(Season.WINTER);
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
			write("---");
			for (int i = 0; i < commands.size(); i++) {
				write(commands.get(i));
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
	public void clear() {
		tArea.setText("Welcome!\nType above. Press ENTER to send\n");
	}
	public int parseInt (String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + "\nDefault: 0");
			return 0;
		}
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
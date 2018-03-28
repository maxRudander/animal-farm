package farm;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class Console extends JPanel implements KeyListener {
	private UIMain ui;
	private Board mainBoard;
	private JTextField tf = new JTextField();
	private JTextArea ta = new JTextArea();
	private ArrayList<String> commands = new ArrayList<String>();

	/**
	 * Console Playground for fun and games :)
	 * To add a command, create a new case in the method triggerCommand.
	 * It would also be helpful (although not necessary) if the added command was added to the list.
	 * Simply add a line to the constructor
	 * 
	 * @param ui
	 * @param mainBoard
	 */
	public Console (UIMain ui, Board mainBoard) {
		this.ui = ui;
		this.mainBoard = mainBoard;
		setLayout(new BorderLayout());
		tf.addKeyListener(this);
		ta.setEditable(false);
		ta.setText("Welcome!" + System.lineSeparator() + "Type above. Press ENTER to send" + System.lineSeparator());
		add(tf, BorderLayout.NORTH);
		add(ta, BorderLayout.CENTER);

		commands.add("add cow");
		commands.add("remove cow");

	}

	public void triggerCommand(String str) {
		tf.setText("");
		int amount;
		write(str);
		switch (str) {
			case "add cow": amount = Integer.parseInt(JOptionPane.showInputDialog(null, "How many cows would you like to add?"));
			for (int i = 0; i<amount; i++) {
				mainBoard.addAnimal(new Cow());
			}
			write(amount + " cows added.");
			break;
			
			case "remove cow": amount = Integer.parseInt(JOptionPane.showInputDialog(null, "How many cows would you like to remove?"));
			for (int i = 0; i<amount; i++) {
				mainBoard.removeAnimal(new Cow());
			}
			write(amount + " cows removed.");
			break;
			//TODO add new commands here !! Don't forget break :)
			default : write("NOT a valdid command!" +System.lineSeparator() + "Available commands:");
			for (String cmd : commands) {
				write(cmd);
			}
			write("");
		}
	}
	public void write(String str) {
		ta.setText(ta.getText() + System.lineSeparator() + str);
	}
	
	/**
	 * If the ENTER key is pressed, the written command is triggered.
	 * Should work regardless of system. Hopefully...
	 */
	public void keyTyped(KeyEvent e) {
		if(System.lineSeparator().indexOf(e.getKeyChar()) > -1) {
			triggerCommand(tf.getText());
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}




}

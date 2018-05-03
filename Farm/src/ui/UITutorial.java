package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
/**
 * UI class for the ui that holds the help window
 * @author Max R
 *
 */
public class UITutorial extends JFrame {
	private JLabel title = new JLabel("Welcome, farmer!");
	private Font titleFont = new Font("Serif", Font.BOLD, 30);
	private JTextArea text = new JTextArea();
	/**
	 * Sets up the ui
	 * @param filename the filename of the help text document
	 */
	public UITutorial(String filename) {
		title.setFont(titleFont);
		setTextArea(filename);
		setBounds(new Rectangle(800,600));
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(text, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
		
	}
	/**
	 * Sets up the textarea with text from the in the filename
	 * @param filename filename wich hold the help file
	 */
	private void setTextArea(String filename) {
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			String line = buf.readLine();
			while (line !=null) {
				text.append(line);
				text.append(System.lineSeparator());
				line=buf.readLine();
			}
			text.setEditable(false);
		}
		catch (IOException e) {
			
		}

	}

}

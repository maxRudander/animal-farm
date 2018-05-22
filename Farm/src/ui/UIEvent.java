package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import event.Event;
import event.EventHandler;
/**
 * UI for the events. Also handles communication with the EventHandler to reveal which option has been selected.
 *  @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt, Matthias Svensson Falk.
 *
 */

public class UIEvent extends JDialog {
	private int id;
	private String title;
	private String text;
	private ImageIcon image;
	private String[] titleOptions;
	private String[] textOptions;
	
	private JPanel contentPane = new JPanel(new BorderLayout());
	private JPanel innerPane = new JPanel (new GridLayout(1,2));
	private JPanel southPane = new JPanel (new GridLayout(1,2));
	private JPanel optionPane = new JPanel();
	
	private JLabel borderImageNorth = new JLabel();
	private JLabel borderImageSouth = new JLabel();
	private JLabel borderImageWest = new JLabel();
	private JLabel borderImageEast = new JLabel();
	
	private ImageIcon horizontal = new ImageIcon("images/frame/0horizontal.jpg");
	private ImageIcon vertical = new ImageIcon("images/frame/0vertical.jpg");
	
	private JLabel eventTitle = new JLabel();
	private JLabel eventImage = new JLabel();
	
	private JTextArea eventText = new JTextArea();
	private JTextArea effectText = new JTextArea();
	
	private ArrayList<Option> optionList = new ArrayList<Option>();
	private EventHandler handler = EventHandler.getInstance();
	/**
	 * Constructs the UI for the Event provided.
	 * @param event - the Event to be opened.
	 */

	public UIEvent(Event event, boolean game) {
		id = event.getId();
		title = event.getTitle();
		text = event.getText();
		image = readImage(event.getImagePath());
		titleOptions = event.getTitleOptions();
		textOptions = event.getTextOptions();
		
		contentPane.setOpaque(false);
		innerPane.setOpaque(false);
		southPane.setOpaque(false);
		
		eventTitle.setText(title);
		eventTitle.setFont(new Font("Serif", Font.BOLD, 40));
		eventTitle.setHorizontalAlignment(SwingConstants.CENTER);
		eventText.setText(text);
		eventText.setEditable(false);
		eventText.setOpaque(false);
		effectText.setEditable(false);
		effectText.setOpaque(false);
		eventImage.setIcon(image);
		setLayout(new BorderLayout());
		add(borderImageNorth, BorderLayout.NORTH);
		add(borderImageSouth, BorderLayout.SOUTH);
		add(borderImageWest, BorderLayout.WEST);
		add(borderImageEast, BorderLayout.EAST);
		add(contentPane, BorderLayout.CENTER);
		contentPane.add(eventTitle, BorderLayout.NORTH);
		contentPane.add(innerPane, BorderLayout.CENTER);
		contentPane.add(southPane, BorderLayout.SOUTH);
		innerPane.add(eventText);
		innerPane.add(eventImage);
		for (int i = 0; i<titleOptions.length; i++) {
			new Option(titleOptions[i], textOptions[i]);
		}
		southPane.add(optionPane);
		southPane.add(effectText);
		optionPane.setLayout(new GridLayout(optionList.size(), 1));
		for (int i = 0; i<optionList.size(); i++) {
			optionPane.add(optionList.get(i));
		}
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (game) {
			setUndecorated(true);
			setModalityType(Dialog.ModalityType.APPLICATION_MODAL);		// needed in final!!
		}

		setResizable(false);
		pack();
		setBorder();
		pack();	
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**
	 * method that reads an image from the path provided in the string and returns it
	 * @param imagePath path to the image that will be read
	 * @return the ImageIcon that has been read
	 */
	
	public ImageIcon readImage(String imagePath) {
		try {
			return new ImageIcon(ImageIO.read(new File(imagePath)));
		}
		catch (IOException e) {
			return new ImageIcon("images/event/default.jpg");
		}
	}
	/**
	 * Adds pretty borders for the event window.
	 */
	public void setBorder() {
		int widthHorizontal = this.getWidth() + (2*vertical.getIconWidth());
		int heightHorizontal = horizontal.getIconHeight();
		int widthVertical = vertical.getIconWidth();
		int heightVertical = this.getHeight();
		horizontal = new ImageIcon(horizontal.getImage().getScaledInstance(widthHorizontal, heightHorizontal, Image.SCALE_DEFAULT));
		borderImageNorth.setIcon(horizontal);
		borderImageSouth.setIcon(horizontal);
		vertical = new ImageIcon(vertical.getImage().getScaledInstance(widthVertical, heightVertical, Image.SCALE_DEFAULT));
		borderImageWest.setIcon(vertical);
		borderImageEast.setIcon(vertical);
	}

	/**
	 * Inner class the handles the event options.
	 */
	private class Option extends JPanel implements MouseListener, ActionListener {
		private JButton btnOption = new JButton();
		private String optionEffects;

		/**
		 * Constructor for the option
		 * @param title - the option's title
		 * @param desc - the option's description.
		 */
		public Option(String title, String desc) {
			setLayout(new GridLayout(1,1));
			add(btnOption);
			btnOption.setText(title);
	        btnOption.setFocusPainted(false);
			optionEffects = desc;
			optionList.add(this);
			btnOption.addMouseListener(this);
			btnOption.addActionListener(this);
		}
		/**
		 * Supposed to trigger a method for the option.
		 */
		public void actionPerformed(ActionEvent e) {
			handler.triggerEffects(id, optionList.indexOf(this));
			dispose();
		}
		/**
		 * Reveals the option description in a label.
		 */
		public void mouseEntered(MouseEvent e) {
			effectText.setText(optionEffects);
		}
		/**
		 * Resets the label.
		 */
		public void mouseExited(MouseEvent e) {
			effectText.setText("");
		}
		/**
		 * Tests of the old, awful, way of handling events.
		 * The print out is still useful to identify event and option id.
		 * Will be removed in later stages.
		 */
		public void mousePressed(MouseEvent e) {
			double option = optionList.indexOf(this);
			option = option/10;
			option+= id;
			handler.triggerEffects(new StringBuilder().append(option).toString());
		}
		/**
		 * not used
		 */
		public void mouseReleased(MouseEvent e) {}
		/**
		 * not used
		 */
		public void mouseClicked(MouseEvent e) {}
	}

}
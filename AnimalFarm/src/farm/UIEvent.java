package farm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UIEvent extends JPanel {
	private JPanel contentpane = new JPanel(new BorderLayout());
	private JPanel innerpane = new JPanel (new GridLayout(1,2));
	private JPanel southpane = new JPanel (new GridLayout(1,2));
	private JPanel optionpane = new JPanel();

	private JLabel borderImageNorth = new JLabel();
	private JLabel borderImageSouth = new JLabel();
	private JLabel borderImageWest = new JLabel();
	private JLabel borderImageEast = new JLabel();

	ImageIcon horizontal = new ImageIcon("images/0horizontal.jpg");
	ImageIcon vertical = new ImageIcon("images/0vertical.jpg");


	private JLabel eventTitle = new JLabel();
	private JLabel eventImage = new JLabel();
	private JTextArea eventText = new JTextArea();
	private JTextArea effectText = new JTextArea();

	private ArrayList<Option> optionlist = new ArrayList<Option>();

	public UIEvent(int id, String title, String text, ImageIcon image, String[] titleOptions, String[] textOptions ) {
		eventTitle.setText(title);
		eventTitle.setFont(new Font("Serif", Font.BOLD, 40));
		eventText.setText(text);
		eventText.setEditable(false);
		effectText.setEditable(false);
		eventImage.setIcon(image);
		setLayout(new BorderLayout());
		add(borderImageNorth, BorderLayout.NORTH);
		add(borderImageSouth, BorderLayout.SOUTH);
		add(borderImageWest, BorderLayout.WEST);
		add(borderImageEast, BorderLayout.EAST);
		add(contentpane, BorderLayout.CENTER);
		contentpane.add(eventTitle, BorderLayout.NORTH);
		contentpane.add(innerpane, BorderLayout.CENTER);
		contentpane.add(southpane, BorderLayout.SOUTH);
		innerpane.add(eventText);
		innerpane.add(eventImage);
		for (int i = 0; i<titleOptions.length; i++) {
			new Option(titleOptions[i], textOptions[i]);
		}
		southpane.add(optionpane);
		southpane.add(effectText);
		optionpane.setLayout(new GridLayout(optionlist.size(), 1));
		for (int i = 0; i<optionlist.size(); i++) {
			optionpane.add(optionlist.get(i));
		}
	}
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

	private class Option extends JPanel implements MouseListener {
		private JLabel optiontxt = new JLabel();
		private String optionEffects;

		public Option(String title, String desc) {
			add(optiontxt);
			optiontxt.setText(title);
			optionEffects = desc;
			optionlist.add(this);
			addMouseListener(this);
		}

		public void mouseClicked(MouseEvent e) {
			System.exit(0);
			// TODO Trigger effect
		}

		public void mouseEntered(MouseEvent e) {
			effectText.setText(optionEffects);
		}

		public void mouseExited(MouseEvent e) {
			effectText.setText("");
		}

		public void mousePressed(MouseEvent arg0) {}

		public void mouseReleased(MouseEvent arg0) {}

	}

}

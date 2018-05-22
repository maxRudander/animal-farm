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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
/**
 * UI for the events. Also handels communication with the EventHandler to reveal which option has been selected.
 *  @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt, Matthias Svensson Falk
 *
 */

public class UIEvent extends JFrame {
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
	
	private ImageIcon horizontal = new ImageIcon("images/0horizontal.jpg");
	private ImageIcon vertical = new ImageIcon("images/0vertical.jpg");
	
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

	public UIEvent(Event event) {
		this.id=event.getId();
		this.title=event.getTitle();
		this.text=event.getText();
		this.image=event.getImage();
		this.titleOptions=event.getTitleOptions();
		this.textOptions=event.getTextOptions();
		eventTitle.setText(title);
		eventTitle.setFont(new Font("Serif", Font.BOLD, 40));
		eventTitle.setHorizontalAlignment(SwingConstants.CENTER);
		eventText.setText(text);
		eventText.setEditable(false);
		effectText.setEditable(false);
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		pack();
		setBorder();
		pack();
		setVisible(true);	
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
	private class Option extends JPanel implements MouseListener {
		private JLabel optionTxt = new JLabel();
		private String optionEffects;

		/**
		 * Constructor for the option
		 * @param title - the option's title
		 * @param desc - the option's description.
		 */
		public Option(String title, String desc) {
			add(optionTxt);
			optionTxt.setText(title);
			optionEffects = desc;
			optionList.add(this);
			addMouseListener(this);
		}
		/**
		 * Supposed to trigger a method for the option.
		 */
		public void mouseClicked(MouseEvent e) {
			handler.triggerEffect(id, optionList.indexOf(this));
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
		 * Still a bit useful for testing purposes. Will be removed in later stages.
		 */
		public void mousePressed(MouseEvent e) {
			double option = optionList.indexOf(this);
			option = option/10; //good for testing purposes. will be removed when product is finished
			option+= id;
			System.out.println(option);
			
			handler.triggerEffect(new StringBuilder().append(option).toString());
		}

		public void mouseReleased(MouseEvent e) {}

	}

}

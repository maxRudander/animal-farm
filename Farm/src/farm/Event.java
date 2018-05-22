package farm;

import javax.swing.ImageIcon;
/**
 * Class for storing all relevant information pertaining to an Event.
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk
 *
 */
public class Event {
	private int id;
	private String title;
	private String text;
	private ImageIcon image;
	private String[] titleOptions;
	private String[] textOptions;

	/**
	 * Constructor for the Event Class.
	 * @param id - the Event ID. To be used for identification and coupling with effects.
	 * @param title - the name of the Event.
	 * @param text - the content text of the Event.
	 * @param image - the Event image.
	 * @param titleOptions - an array of all the possible options to react on the Event.
	 * @param textOptions - and array of all the descriptions for the options.
	 */
	public Event (int id, String title, String text, ImageIcon image, String[] titleOptions, String[] textOptions ) {
		this.id=id;
		this.title=title;
		this.text=text;
		this.image=image;
		this.titleOptions=titleOptions;
		this.textOptions=textOptions;
	}

	/**
	 * Returns the Event ID.
	 * @return - the Event ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the Event ID.
	 * @param id - the Event ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the Event title.
	 * @return - the Event title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Event title.
	 * @param title - the Event title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the Event text.
	 * @return - the Event text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the Event text.
	 * @param text - the Event text.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the Event image.
	 * @return - the Event image.
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Sets the Event image.
	 * @param image - the Event image.
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}

	/**
	 * Returns the options for the Event.
	 * @return - the options for the Event.
	 */
	public String[] getTitleOptions() {
		return titleOptions;
	}

	/**
	 * Sets the options for the Event.
	 * @param titleOptions - the options for the Event.
	 */
	public void setTitleOptions(String[] titleOptions) {
		this.titleOptions = titleOptions;
	}

	/**
	 * Returns the descriptions for the options for the Event.
	 * @return - the descriptions for the options for the Event.
	 */
	public String[] getTextOptions() {
		return textOptions;
	}

	/**
	 * Sets the descriptions for the options for the Event.
	 * @param textOptions - the descriptions for the options for the Event.
	 */
	public void setTextOptions(String[] textOptions) {
		this.textOptions = textOptions;
	}
	/**
	 * Default Event to be returned if something goes wrong in the EventHandler (such as the entry of a nonexistent Event ID).
	 * @return - the default Event.
	 */
	public static Event alienInvasion () {
		String alienText = "Something has gone wrong!" + System.lineSeparator() + "Aliens are here to steal your farm";
		ImageIcon alienImage = new ImageIcon("images/aliens.jpg");
		String[] alienOpTitle = {"Oh no!","The truth is out there"};
		String[] alienOpText = {"The farm is lost", "Cattle is mutilated"+ System.lineSeparator() + "50 % chance of abduction"};
		return new Event (-1, "Alien Invasion!", alienText , alienImage, alienOpTitle, alienOpText);
	}

}

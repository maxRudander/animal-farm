package farm;

import javax.swing.ImageIcon;

public class Event {
	private int id;
	private String title;
	private String text;
	private ImageIcon image;
	private String[] titleOptions;
	private String[] textOptions;


	public Event (int id, String title, String text, ImageIcon image, String[] titleOptions, String[] textOptions ) {
		this.id=id;
		this.title=title;
		this.text=text;
		this.image=image;
		this.titleOptions=titleOptions;
		this.textOptions=textOptions;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public ImageIcon getImage() {
		return image;
	}


	public void setImage(ImageIcon image) {
		this.image = image;
	}


	public String[] getTitleOptions() {
		return titleOptions;
	}


	public void setTitleOptions(String[] titleOptions) {
		this.titleOptions = titleOptions;
	}


	public String[] getTextOptions() {
		return textOptions;
	}


	public void setTextOptions(String[] textOptions) {
		this.textOptions = textOptions;
	}

	public static Event alienInvasion () {
		String alienText = "Something has gone wrong!" + System.lineSeparator() + "Aliens are here to steal your farm";
		ImageIcon alienImage = new ImageIcon("images/aliens.jpg");
		String[] alienOpTitle = {"Oh no!","The truth is out there"};
		String[] alienOpText = {"The farm is lost", "Cattle is mutilated"+ System.lineSeparator() + "50 % chance of abduction"};
		return new Event (-1, "Alien Invasion!", alienText , alienImage, alienOpTitle, alienOpText);
	}

}

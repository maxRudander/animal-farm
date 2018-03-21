package farm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EventFileReader {
	private int id;
	private String title;
	private String text;
	private String[] titleOptions;
	private String[] textOptions;
	private ImageIcon icon;

	private ArrayList<String> list = new ArrayList<String>();

	public EventFileReader() {}

	public EventFileReader (String filename) {
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			String line = buf.readLine();
			while (line!=null) {
				if (!line.startsWith("#")) {


					id = Integer.parseInt(line);
					title = buf.readLine();
					icon = new ImageIcon(buf.readLine());

					line = buf.readLine();
					text = "";
					
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							text += line+System.lineSeparator();
							line = buf.readLine();
						}
					}
					else {
						text = line;
					}

					line = buf.readLine();
					list.clear();
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							list.add(line);
							line = buf.readLine();
						}
					}
					
					titleOptions = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						titleOptions[i]=list.get(i);
					}
					line = buf.readLine();
					list.clear();
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							list.add(line);
							line = buf.readLine();
						}
					}

					textOptions = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						textOptions[i]=list.get(i);
					}
					list.clear();
					line = buf.readLine();
					createEvent();
				}
				else {
					line = buf.readLine();
				}
			}
		} catch (IOException e) {};
	}

	public JFrame createEvent () {
		JFrame frame = new JFrame(id + title);
		UIEvent ui = new UIEvent(id, title, text, icon, titleOptions, textOptions);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(ui);
		frame.pack();
		ui.setBorder();
		frame.pack();
		frame.setVisible(true);

		return frame;
	}

	public static void main (String[] args) {
		new EventFileReader("files/testevent.txt");
	}

}

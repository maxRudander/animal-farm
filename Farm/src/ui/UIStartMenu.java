package ui;

import javax.swing.*;

import main.Controller;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Start menu that lets the user start the game, load an game or quit.
 * @author Matthias Svensson Falk, Max Rudander. 
 */
public class UIStartMenu extends JFrame implements ActionListener, Observer {
	private Controller game;

    private JLabel borderImageNorth = new JLabel();
    private JLabel borderImageSouth = new JLabel();
    private JLabel borderImageWest = new JLabel();
    private JLabel borderImageEast = new JLabel();
    private JLabel lblTitle = new JLabel(" Welcome to the Farm ");
    private JLabel lblSubtitle = new JLabel ("enjoy your stay");
    private JLabel lblImage = new JLabel();
    
    private JPanel contentPane = new JPanel();
    private JPanel rBtnPane = new JPanel();
    
    private ImageIcon i = new ImageIcon("images/property/Barn.png");
    private ImageIcon horizontal = new ImageIcon("images/frame/0horizontal.jpg");
    private ImageIcon vertical = new ImageIcon("images/frame/0vertical.jpg");
    
    private JButton btnNewGame = new JButton("New game");
    private JButton btnLoad = new JButton("Load game");
    private JButton btnQuit = new JButton("Exit");
    
    private ButtonGroup bGroup = new ButtonGroup();
    private JRadioButton rBtnTestEnvironment = new JRadioButton("Test environment");
    private JRadioButton rBtnGameEnvironment = new JRadioButton("Game environment");

    /**
     * sets up the UI
     * @param controller an instance to controller
     */
    public UIStartMenu() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        add(borderImageNorth, BorderLayout.NORTH);
        add(borderImageSouth, BorderLayout.SOUTH);
        add(borderImageWest, BorderLayout.WEST);
        add(borderImageEast, BorderLayout.EAST);
        add(contentPane, BorderLayout.CENTER);
        rBtnGameEnvironment.setSelected(true);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setRBtnPane();
        setContentPane();
        setButtons();
        pack();
        setBorder();
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    /**
     * adds the label and the buttons to the central panel
     */
    public void setContentPane(){
    	lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
    	lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
    	lblImage.setIcon(new ImageIcon(i.getImage().getScaledInstance(i.getIconWidth()/2,i.getIconHeight()/2, Image.SCALE_SMOOTH)));
    	lblImage.setHorizontalAlignment(SwingConstants.CENTER);
    	lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
    	lblSubtitle.setFont(new Font("Serif", Font.BOLD, 16));
    	contentPane.setOpaque(false);
        contentPane.setLayout(new GridLayout(7,1));
        contentPane.add(lblTitle);
        contentPane.add(lblImage);
        contentPane.add(lblSubtitle);
        contentPane.add(btnNewGame);
        contentPane.add(btnLoad);
        contentPane.add(btnQuit);
        contentPane.add(rBtnPane);
    }
    /**
     * sets up the JPanel that holds the RadioButtons
     */
    public void setRBtnPane() {
    	rBtnPane.setLayout(new GridLayout(1,2));
    	bGroup.add(rBtnGameEnvironment);
    	bGroup.add(rBtnTestEnvironment);
    	rBtnPane.add(rBtnGameEnvironment);
    	rBtnPane.add(rBtnTestEnvironment);
    }
    /**
     * adds fancy images to the windows borders
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
     * adds actionListener to the buttons
     */
    public void setButtons(){
        btnNewGame.addActionListener(this);
        btnLoad.addActionListener(this);
        btnQuit.addActionListener(this);
        rBtnTestEnvironment.addActionListener(this);
        rBtnGameEnvironment.addActionListener(this);
        btnNewGame.setFont(new Font("Monospaced", Font.BOLD, 32));
        btnLoad.setFont(new Font("SansSerif", Font.BOLD, 32));
        btnQuit.setFont(new Font("DialogInput", Font.BOLD, 32));
        rBtnTestEnvironment.setFont(new Font ("DialogInput", Font.BOLD, 12));
        rBtnGameEnvironment.setFont(new Font ("DialogInput", Font.BOLD, 12));
    }
    /**
     * handles the actions that will be performed when the buttons is pressed
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame){
            game = new Controller();
            game.addObserver(this);
            game.newGame(true, rBtnGameEnvironment.isSelected());
            this.setVisible(false);
        }
        if (e.getSource() == btnLoad){
        	this.setVisible(false);
        	game = new Controller();
        	game.addObserver(this);
        	game.loadedGame(true);
        }
        if (e.getSource() == btnQuit){
            System.exit(0);
        }
    }
    /**
     * Reveals/hides this frame depending on arg
     */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Boolean) {
			this.setVisible((Boolean) arg);
		}
	}
}

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mail.MailProperties;
import beans.MailBean;
import beans.MailConfigBean;
import db.DBManager;

/**
 * Frame used for mail message creation. 
 * @author Slaiy
 *
 */
public class ComposeMailFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private DBManager manager = null;
	private MailProperties properties = null;
	private ContactFrame contacts=null;
	private JTextField toTxt=null;
	private JTextField ccTxt=null;
	private JTextField bccTxt=null;
	private JTextField subjectTxt=null;
	private JEditorPane text=null;
	private String sender = null;
	
	/**
	 * Default constructor
	 */
	public ComposeMailFrame(){
		super("Compose New Mail");
	}

	/**
	 * Constructor setting the look and feel
	 * @param laf LookAndFeel to set
	 */
	public ComposeMailFrame(LookAndFeel laf){
		this();
		setLookAndFeel(laf);
		initialize();
	}
	
	/**
	 * Constructor setting a reference to the contact frame, the LookAndFeel, and the DBManager
	 * @param contacts reference to contact frame to make visible on button click.
	 * @param lookAndFeel LookAndFeel to set appearance.
	 * @param manager DBManager to set.
	 */
	public ComposeMailFrame(ContactFrame contacts, LookAndFeel lookAndFeel,DBManager manager) {
		this(lookAndFeel);
		this.contacts=contacts;
		this.manager=manager;
	}

	/**
	 * Sets the LookAndFeel of the frame.
	 * @param laf LookAndFeel to set.
	 */
	public void setLookAndFeel(LookAndFeel laf){
		try{
			UIManager.setLookAndFeel(laf);
		}
		catch (UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	/**
	 * Initializes GUI components.
	 */
	private void initialize(){
		properties = new MailProperties();
		MailConfigBean config = properties.loadProperties();
		sender = config.getUserEmail();
		//instantiating to/cc/bcc/subject and button panels.
		JPanel top = new JPanel();
		JPanel info = new JPanel();
		
		//instantiating components to fill mail info panel.
		JLabel to = new JLabel("To:");
		toTxt = new JTextField();
		JLabel cc = new JLabel("Cc:");
		ccTxt = new JTextField();
		JLabel bcc = new JLabel("Bcc:");
		bccTxt = new JTextField();
		JLabel subject = new JLabel("Subject:");
		subjectTxt = new JTextField();
		
		//setting layout and adding components to panel.
		info.setLayout(new GridLayout(4,2));
		
		info.add(to);
		info.add(toTxt);
		info.add(cc);
		info.add(ccTxt);
		info.add(bcc);
		info.add(bccTxt);
		info.add(subject);
		info.add(subjectTxt);

		top.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		//setting constraints to set the tool bar
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=3;
		constraints.gridheight=1;
		constraints.fill=GridBagConstraints.BOTH;
		top.add(createToolBar(),constraints);
		
		//setting constraints to set the info panel
		constraints.gridy=1;
		constraints.gridheight=2;
		top.add(info,constraints);
		
		add(top,BorderLayout.NORTH);
		text = new JEditorPane();
		add(text,BorderLayout.CENTER);
		
 		this.setMinimumSize(new Dimension(550,700));
		setJMenuBar(createMenuBar());
		pack();
		centerScreen();
	}
	
	/**
	 * Creates the toolbar to be positioned at the top of the JFrame
	 * @return JToolBar to set at the top of the frame.
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
        JButton button = null;

        //creating buttons
        button = makeToolBarButton("contacts", "Contacts","Open the contacts window.","Contacts");
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showContacts();					
			}});
        toolBar.add(button);
        
        button = makeToolBarButton("close", "Close","Close the mail composition window.","Close");
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}});
        toolBar.add(button);
        
        toolBar.add(new JToolBar.Separator());
        
        button = makeToolBarButton("send", "Send","Send created mail message.","Send");
        //setting ActionListener to the "Send" button
        button.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		//if the 'to' field and message field are not empty,
        		//a new mail message is created and saved to the database
				if(!toTxt.equals("") &&
					!text.equals("")){
					MailBean bean = new MailBean("null",toTxt.getText(),sender,
												subjectTxt.getText(),ccTxt.getText(),
												bccTxt.getText(),new Date().toString(),
												text.getText(),"Draft");
					manager.saveMail(bean);
				}
					
			}});
        toolBar.add(button);
        
        button = makeToolBarButton("clear", "Clear","Clear all of message's fields.","Clear");
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearFields();
			}});
        toolBar.add(button);  
        
        toolBar.setFloatable(false);
        toolBar.setRollover(false);
        return toolBar;
	}
	
	/**
	 * Clears all JTextFields in the frame.
	 */
	private void clearFields(){
		toTxt.setText("");
		ccTxt.setText("");
		bccTxt.setText("");
		subjectTxt.setText("");
		text.setText("");
	}
	
	/**
	 * Closes the frame before clearing the fields.
	 */
	private void closeFrame(){
		clearFields();
		toTxt.requestFocus();
		setVisible(false);
	}
	
	/**
	 * Creates buttons to populate the tool bar with.
	 * @param imageName name of image to set on button.
	 * @param actionCommand command to fire when action triggered.
	 * @param toolTipText text to set for when the mouse hovers over the button.
	 * @param altText alternate text to set if the image cannot be found.
	 * @return
	 */
	private JButton makeToolBarButton(String imageName,
			String actionCommand, String toolTipText, String altText) {

		ToolBarEventHandler tbeh = new ToolBarEventHandler();
		
		// look for the image.
		String imgLocation = "/images/" + imageName+".png";
		URL imageURL = ClientFrame.class.getResource(imgLocation);

		// create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(tbeh);
		
		//sets the button image if it is founds, or else sets alternate text
		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {
			button.setText(altText);
			System.err.println("Image not found: " + imgLocation);
		}
		return button;
	}
	
	//AcitonListener handling button clicks.
	class ToolBarEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) (e.getSource());
			String action = source.getActionCommand();
			switch(action){
			case "Close":
				setVisible(false);
				break;
			case "Contacts":
				contacts.setVisible(true);
				break;
			case "New Mail":
				new ComposeMailFrame();
				break;
			}
		}
	}
	
	/**
	 * Creates the menu bar to set at the the top of the frame.
	 * @return JMenuBar to set at the top of the frame
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		
		// Creating the first menu option.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The file menu");
		menu.setToolTipText("The file menu");
		menuBar.add(menu);

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Exit menu item");
		menuItem.setToolTipText("This is the Exit menu item");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Creating the second menu option.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription("The edit menu");
		menu.setToolTipText("The edit menu");
		menuBar.add(menu);

		menuItem = new JMenuItem("Cut");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Cut menu item");
		menuItem.setToolTipText("This is the Cut menu item");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Copy");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Copy menu item");
		menuItem.setToolTipText("This is the Copy menu item");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Paste");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Paste menu item");
		menuItem.setToolTipText("This is the Paste menu item");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Creating the second menu option.
		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription("The windows selection menu.");
		menu.setToolTipText("The edit menu");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Contacts");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This opens the mail contacts window.");
		menuItem.setToolTipText("This is the Cut menu item");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		return menuBar;
	}
	
	/**
	 * Displays the contacts frame.
	 */
	private void showContacts() {
			contacts.setLookAndFeel(UIManager.getLookAndFeel());
			contacts.setVisible(true);
	}
	
	/**
	 * Fires when a button is clicked. Just noticed I have about 3 action listeners in this class....wow, bad.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JMenuItem source = (JMenuItem) arg0.getSource();
		String name = source.getText();
		switch(name){
			case "Exit":
				closeFrame();
				break;
			case "Contacts":
				showContacts();
				break;
		}
	}
	
	/**
	 * Returns a reference to the "to" JTextField.
	 * @return
	 */
	public JTextField getToTxt(){
		return toTxt;
	}
	
	/**
	 * Centers the frame in the middle of the screen.
	 */
	private void centerScreen() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}
}
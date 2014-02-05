package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mail.MailProperties;
import mail.MailReceiver;
import mail.MailSender;
import beans.MailBean;
import beans.MailConfigBean;
import db.DBManager;
import db.MailTableModel;

/**
 * Main application frame.
 * @author Slaiy
 */
@SuppressWarnings("serial")
public class ClientFrame extends JFrame implements ActionListener{
	private GridBagConstraints c;
	private MailView view;
	private MailTreePanel tree;
	private MailTablePanel table;
	private ComposeMailFrame newMail = null;
	private MailSettingsFrame settings=null;
	private ContactFrame contacts = null;
	private DBManager manager =null;
	private MailSender sender = null;
	private MailReceiver receiver = null;
	private MailConfigBean config =null;
	private MailProperties properties=null;
	private JButton deleteMail = null;
	private JButton printMail = null;
	private EmailFocusTraversalPolicy traversal = null;
	private Vector<Component> traversalOrder = null; 
	private HelpSet helpSet = null;
	private HelpBroker helpBroker = null;
	
	/**
	 * Default constructor
	 */
	public ClientFrame(){
		this("E-mail");
	}
	
	/**
	 * Constructor setting the title to the frame.
	 * @param title
	 */
	public ClientFrame(String title){
		super(title);
		manager=new DBManager();
		view = new MailView();
		table = new MailTablePanel(manager, view, this);
		tree = new MailTreePanel(manager);
		manager.getAllContacts();
		config = new MailConfigBean();
		properties = new MailProperties();
		config=properties.loadProperties();
		sender=new MailSender(config);
		receiver=new MailReceiver(config);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initialize();
		centerScreen();
		traversal=new EmailFocusTraversalPolicy(traversalOrder);
	}
	
	/**
	 * Initializes the GUI components.
	 */
	private void initialize(){
		traversalOrder= new Vector<Component>();
		c= new GridBagConstraints();
		getContentPane().setLayout(new GridBagLayout());
		contacts=new ContactFrame(manager,this,UIManager.getLookAndFeel());
		
		//setting constraints to set the tool bar
		c.gridx=0;
		c.gridy=0;
		c.gridheight=1;
		c.gridwidth=3;
		c.weightx=.3;
		c.fill=GridBagConstraints.BOTH;
		add(createToolBar(),c);
		
		//setting constraints to set the mail tree
		c.gridy=1;
		c.gridheight=2;
		c.gridwidth=1;
		c.weighty=1.0;
		c.weightx=.2;
		traversalOrder.add(tree);
		add(tree,c);
		
		//setting constraints to set the mail table
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=.8;
		c.weighty=.2;
		traversalOrder.add(table);
		add(table,c);
		
		//setting constraints to set the mail viewe
		c.gridy=2;
		c.weighty=.8;
		traversalOrder.add(view);
		add(view,c);
		
		this.setMinimumSize(new Dimension(725,650));
		pack();
		makeHelp();
		setJMenuBar(createMenuBar());
		setVisible(true);
	}
	
	/**
	 * Creates the menu bar to be set at the top of the frame
	 * @return JMenuBar to set.
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		LookAndFeelMenuEventHandler landf = new LookAndFeelMenuEventHandler();
		
		menuBar = new JMenuBar();

		// Creating the first menu option.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The file menu");
		menu.setToolTipText("The file menu");
		traversalOrder.add(menu);
		menuBar.add(menu);

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Exit menu item");
		menuItem.setToolTipText("This is the Exit menu item");
		menuItem.addActionListener(this);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		// Creating the third menu option.
		menu = new JMenu("Look and Feel");
		menu.setMnemonic(KeyEvent.VK_L);
		menu.getAccessibleContext().setAccessibleDescription("The look and feel menu");
		menu.setToolTipText("The look and feel menu");
		traversalOrder.add(menu);
		menuBar.add(menu);

		menuItem = new JMenuItem("Windows");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Windows look and feel menu item");
		menuItem.setToolTipText("This is the Windows look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		menuItem = new JMenuItem("Windows Classic");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Windows Classic look and feel menu item");
		menuItem.setToolTipText("This is the Windows Classic look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		menuItem = new JMenuItem("Metal");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Metal look and feel menu item");
		menuItem.setToolTipText("This is the Metal look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		menuItem = new JMenuItem("Motif");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Motif look and feel menu item");
		menuItem.setToolTipText("This is the Motif look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		menuItem = new JMenuItem("Nimbus");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Nimbus look and feel menu item");
		menuItem.setToolTipText("This is the Nimbus look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		// Add a separator
		menu.addSeparator();

		menuItem = new JMenuItem("Cross Platform");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the Cross Platform look and feel menu item");
		menuItem.setToolTipText("This is the Cross Platform look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		menuItem = new JMenuItem("System");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This is the System look and feel menu item");
		menuItem.setToolTipText("This is the System look and feel.");
		menuItem.addActionListener(landf);
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		//  Creating the fourth menu option.
		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_W);
		menu.getAccessibleContext().setAccessibleDescription("The window menu");
		menu.setToolTipText("The window selection menu");
		traversalOrder.add(menu);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Contacts");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This opens the contacts window.");
		menuItem.setToolTipText("This opens the mail contacts window.");
		menuItem.addActionListener(this);
		traversalOrder.add(menuItem);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Compose New Mail");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This opens the message composition window.");
		menuItem.setToolTipText("This opens the mail composition window.");
		menuItem.addActionListener(this);
		traversalOrder.add(menuItem);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mail Settings Configuration");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This opens the message composition window.");
		menuItem.setToolTipText("This opens the mail settings configuration window.");
		menuItem.addActionListener(this);
		traversalOrder.add(menuItem);
		menu.add(menuItem);
		
		//  Creating the fifth menu option.
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_F1);
		menu.getAccessibleContext().setAccessibleDescription("The help menu");
		menu.setToolTipText("The help menu");
		traversalOrder.add(menu);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Help!");
		menuItem.setMnemonic(KeyEvent.VK_F1);
		menuItem.getAccessibleContext().setAccessibleDescription("This is the help file.");
		menuItem.setToolTipText("This is the help file.");
		menuItem.addActionListener(new CSH.DisplayHelpFromSource(helpBroker));
		traversalOrder.add(menuItem);
		menu.add(menuItem);

		return menuBar;
	}
	
	/**
	 * Creates the tool bar containing buttons.
	 * @return
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
        JButton button = null;
        
        //Adding buttons
        button = makeToolBarButton("settings", "Settings","Modify mail settings","Settings");
        //adding ActionListener to the settings button.
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingsButtonClick();
			}});
        traversalOrder.add(button);
        toolBar.add(button);

        button = makeToolBarButton("contacts", "Contacts","Open the contacts window.","Contacts");
      //adding ActionListener to the contacts button.
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				contactsButtonClick();
			}});
        traversalOrder.add(button);
        toolBar.add(button);
		
        button = makeToolBarButton("new_mail", "New Mail","Create a new mail message.","New Mail");
      //adding ActionListener to the new mail button.
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mailButtonClick();
			}});
        traversalOrder.add(button);
        toolBar.add(button);
        
        toolBar.add(new JSeparator());
        
        button = makeToolBarButton("send_receive", "Send/Receive Mail","Sends and receives all waiting messaged.","Send/Receive Mail");
      //adding ActionListener to the send/receive button.
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendReceiveButtonClick();
			}});
        traversalOrder.add(button);
        toolBar.add(button);
        
       deleteMail = makeToolBarButton("delete_mail", "Delete Mail","Delete selected mail.","Delete Mail");
     //adding ActionListener to the delete mail button.
       deleteMail.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getTable().getSelectedRow()>=0){
					MailBean bean = ((MailTableModel)table.getTable().getModel())
										.getMailBean(table.getTable().getSelectedRow());
					manager.putMailInTrash(bean);
				}
			}});
        traversalOrder.add(deleteMail);
        deleteMail.setEnabled(false);
        toolBar.add(deleteMail);
        
        printMail = makeToolBarButton("print_mail", "Print Mail","Print selected mail.","Print Mail");
      //adding ActionListener to the print mail button.
        printMail.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getTable().getSelectedRow()!=-1)
					try {view.print();} 
					catch (PrinterException e) {e.printStackTrace();}
			}});
        traversalOrder.add(printMail);
        printMail.setEnabled(false);
        toolBar.add(printMail);
        
        button = makeToolBarButton("perma_delete_mail", "Permanently Delete Mail","Permanently delete all mail in Deleted folder.","Permanently Delete Mail");
      //adding ActionListener to the empty trash button.
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
					manager.deleteMail();
			}});
        traversalOrder.add(button);
        toolBar.add(button);

        toolBar.setFloatable(false);
        toolBar.setRollover(false);
        return toolBar;
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
		
		// Look for the image.
		String imgLocation = "/images/" + imageName+".png";
		URL imageURL = ClientFrame.class.getResource(imgLocation);

		// create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);

		//sets the button image if it is founds, or else sets alternate text
		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}
		return button;
	}
	
	/**
	 * Displays the contacts frame
	 */
	private void contactsButtonClick() {
			contacts.setLookAndFeel(UIManager.getLookAndFeel());
			contacts.setVisible(true);
	}
	
	/**
	 * Displays the mail settings frame
	 */
	private void settingsButtonClick() {
		settings = new MailSettingsFrame(UIManager.getLookAndFeel());
	}
	
	/**
	 * Displays the new mail frame
	 */
	private void mailButtonClick() {
		if(newMail==null){
			newMail = new ComposeMailFrame(contacts,UIManager.getLookAndFeel(),manager);
		}
		else{
			newMail.setLookAndFeel(UIManager.getLookAndFeel());
			newMail.setVisible(true);
		}
	}

	/***
	 * Action performed when send/buttons click is clicked
	 */
	private void sendReceiveButtonClick() {
		manager.getMailByFolder("Draft");
		int ctr = ((MailTableModel)table.getTable().getModel()).getRowCount();
		MailBean bean = null;
		if(ctr>0){
			for(int i=0;i<ctr;i++){
				bean = ((MailTableModel)table.getTable().getModel()).getMailBean(i);
				if(sender.sendMail(bean)){
					bean.setFolder("Sent");
					manager.updateMail(bean);
				}
			}
			SwingUtilities.updateComponentTreeUI(this);	
		}
		ArrayList<MailBean> mail = (ArrayList<MailBean>)receiver.getMail();
		MailBean mailBean;
		for(int i=0;i<mail.size();i++){
			mailBean = mail.get(i);
			manager.saveMail(new MailBean("null",mailBean.getTo()==null?"":mailBean.getTo(),
										  mailBean.getFrom()==null?"":mailBean.getFrom(),
										  mailBean.getSubject()==null?"":mailBean.getSubject(),
										  mailBean.getCc()==null?"":mailBean.getCc(),
										  mailBean.getBcc()==null?"":mailBean.getBcc(),
										  mailBean.getDate()==null?"":mailBean.getDate(),
										  mailBean.getMessage()==null?"":mailBean.getMessage(),
										  "Inbox"));
		}
	}
	
	/**
	 * ActionListener 
	 */
	class LookAndFeelMenuEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem) (e.getSource());
			changeLookAndFeel(source.getText());
		}
	}
	
	/**
	 * Sets LookAndFeel to corresponding sent in String
	 * @param str LookAndFeel to set.
	 */
	private void changeLookAndFeel(String str) {
		try {
			switch (str) {
			case "Metal":
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				break;
			case "Motif":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				break;
			case "Windows":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				break;
			case "Aqua":
				UIManager.setLookAndFeel("javax.swing.plaf.mac.MacLookAndFeel");
				break;
			case "System":
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				break;
			case "Cross Platform":
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				break;
			case "Windows Classic":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
				break;
			case "Nimbus":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				break;
			}
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	// centers the frame in the middle of the screen
	private void centerScreen() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}
	
	/**
	 * ActionListener for menu bar....again, I just noticed i have multiple ActionListeners.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JMenuItem){
			JMenuItem source = (JMenuItem) arg0.getSource(); 
			String menu = source.getText();
			switch(menu){
			case "Exit":
				System.exit(0);
				break;
			case "Contacts":
				contactsButtonClick();
				break;
			case "Compose New Mail":
				mailButtonClick();
				break;
			case "Mail Settings Configuration":
				settingsButtonClick();
				break;
			}
		}
	}
	
	/**
	 * Enables "Delete mail" and "Print" buttons.
	 */
	public void mailSelectButtons(){
			deleteMail.setEnabled(true);
			printMail.setEnabled(true);
	}
	
	/**
	 * Returns a reference to the new mial frame.
	 * @return
	 */
	public ComposeMailFrame getComposeMail() {
		return newMail;
	}
	
	/**
	 * Creates and displays the "Help" frame.
	 */
	private void makeHelp() {
		// Find the HelpSet file and create the HelpSet object:
		String helpDir = "helpSet/main.hs";
		ClassLoader cl = ClientFrame.class.getClassLoader();
		try {
			URL hsURL = HelpSet.findHelpSet(cl, helpDir);
			helpSet = new HelpSet(null, hsURL);
		} catch (Exception ee) {
			// Say what the exception really is
			System.out.println("HelpSet " + ee.getMessage());
			System.out.println("HelpSet " + helpDir + " not found");
			return;
		}
		// Create a HelpBroker object:
		helpBroker = helpSet.createHelpBroker();
		helpBroker.enableHelpKey(rootPane, "EmailClient", null);
	}
}
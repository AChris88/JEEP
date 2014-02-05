package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import beans.ContactBean;
import db.ContactTableModel;
import db.DBManager;

/**
 * Frame for user to create, view, update, and delete contacts.
 * @author Slaiy
 */
public class ContactFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private DBManager manager;
	private int selectedRow =0;
	private ComposeMailFrame compose = null;
	private ClientFrame client= null;
	private JTextField fNameTxt=null;
	private JTextField lNameTxt=null;
	private JTextField emailTxt=null;
	
	/**
	 * Default constructor
	 */
	public ContactFrame(){
		super("Contacts");
	}
	
	/**
	 * Constructor setting DBManager, a reference to the ClientFrame, and the LookAndFeel
	 * @param manager DBManager to set.
	 * @param client ClientFrame to set.
	 * @param lookAndFeel LookAndFeel to set.
	 */
	public ContactFrame(DBManager manager, ClientFrame client,
			LookAndFeel lookAndFeel) {
		this();
		this.manager = manager;
		setLookAndFeel(lookAndFeel);
		this.client=client;
		initialize();
		centerScreen();
	}

	/**
	 * Setting GUI components
	 */
	private void initialize() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		//creating and setting components to the panel to enter
		//info about contacts to add.
		JPanel addUser = new JPanel();
		addUser.setLayout(new GridLayout(3,2));
		addUser.add(new JLabel("First Name:"));
		fNameTxt = new JTextField();
		addUser.add(fNameTxt);
		addUser.add(new JLabel("Last Name:"));
		lNameTxt = new JTextField();
		addUser.add(lNameTxt);
		addUser.add(new JLabel("E-mail:"));
		emailTxt = new JTextField();
		addUser.add(emailTxt);
		add(addUser,BorderLayout.PAGE_START);
		
		JPanel buttons = new JPanel();
		JButton add = new JButton("Add");
		
		//adding ActionListener to 'add' button
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!fNameTxt.getText().equals("") &&
				   !lNameTxt.getText().equals("") &&
				   !emailTxt.getText().equals("")){
					manager.saveContact(new ContactBean("-1",fNameTxt.getText(),lNameTxt.getText(),emailTxt.getText()));
					updateUI();
				}
			}});
		
		final JButton delete = new JButton("Delete");
		//adding ActionListener to 'delete' button
		delete.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteContact(new ContactBean(((ContactTableModel)manager.getContactModel()).getContactBean(selectedRow).getId(),
										"","",""));
				updateUI();
			}});
		
		JButton cancel = new JButton("Cancel");
		//adding ActionListener to 'cancel' button
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
				setVisible(false);
			}});
		
		final JButton edit = new JButton("Edit"); 
		//adding ActionListener to 'edit' button
		edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String tmpFName = JOptionPane.showInputDialog(null,"New First Name?");
				String tmpLName = JOptionPane.showInputDialog(null,"New Last Name?");
				String tmpEmail = JOptionPane.showInputDialog(null,"New E-mail?");
				
				String newFName = tmpFName == null ? "" : tmpFName;
				String newLName = tmpLName == null ? "" : tmpLName;
				String newEmail = tmpEmail == null ? "" : tmpEmail;
				ContactBean contact = new ContactBean(((ContactTableModel)manager.getContactModel()).getContactBean(selectedRow).getId(),
										newFName,newLName,newEmail);
				manager.saveContact(contact);
				updateUI();
			}});
		
		edit.setEnabled(false);
		delete.setEnabled(false);
		
		//adds the buttons to the button panel
		buttons.add(add);
		buttons.add(edit);
		buttons.add(delete);
		buttons.add(cancel);

		//adds the button panel
		add(buttons,BorderLayout.CENTER);
		
		JTable contact = new JTable(manager.getContactModel());
		contact.setAutoCreateColumnsFromModel(true);
		contact.setAutoscrolls(true);
		contact.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//adding action listener to contact button
		contact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable)e.getSource();
				int row = source.getSelectedRow();
				if(row!=-1){
					selectedRow=((JTable)e.getSource()).getSelectedRow();
					edit.setEnabled(true);
					delete.setEnabled(true);
				}
				else{
					edit.setEnabled(false);
					delete.setEnabled(false);
				}
				compose=client.getComposeMail();
				if(compose!=null)
					if(e.getClickCount()==2&&compose.isVisible()){
						String email = ((ContactTableModel)source.getModel()).getContactBean(selectedRow).getEmail();
						if(compose.getToTxt().getText().equals(""))
							compose.getToTxt().setText(email);
						else
							compose.getToTxt().setText(compose.getToTxt().getText()+", "+email);
					}
					
			}});
		contact.setToolTipText("If you have the 'Compose New Mail' window open, you can double-click on contacts to add them to the list of recipients");
		JScrollPane scroll = new JScrollPane(contact);
		add(scroll,BorderLayout.PAGE_END);
		
		pack();
	}
	
	/**
	 * Clears all JTextFields in the frame
	 */
	private void clearFields(){
		fNameTxt.setText("");
		lNameTxt.setText("");
		emailTxt.setText("");
	}

	/**
	 * Update the contacts frame... I realize now this is most likely useless.
	 */
	private void updateUI(){
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setLookAndFeel(LookAndFeel laf) {
		try{
			UIManager.setLookAndFeel(laf);
		}
		catch (UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	/**
	 * Centers the frame in the center of the screen
	 */
	private void centerScreen() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}
}
package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.MaskFormatter;
import mail.MailProperties;
import beans.MailConfigBean;

/**
 * Frame where users can set their mail configuratin settings.
 * @author Slaiy
 *
 */
public class MailSettingsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private MailProperties properties = null;
	private MailConfigBean configs = new MailConfigBean();
	private JFrame panelTest=null;
	
	/**
	 * Default constructor loading mail configuration properties.
	 */
	public MailSettingsFrame(){
		super("Mail Settings Configuation");
		panelTest=this;
		properties = new MailProperties();
		configs=properties.loadProperties();
	}
	
	/**
	 * Constructor calling default constructor setting LookAndFeel.
	 * @param lookAndFeel
	 */
	public MailSettingsFrame(LookAndFeel lookAndFeel) {
		this();
		setLookAndFeel(lookAndFeel);
		initialize();
	}

	/**
	 * Initializes GUI components.
	 */
	private void initialize(){
		setLayout(new GridLayout(19,4));
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("Username:"));
//		String userNameRegEx ="[a-zA-Z0-9._-]";
//		final JFormattedTextField uName = new JFormattedTextField(new RegExFormatter(userNameRegEx));
		final JFormattedTextField uName = new JFormattedTextField();
		uName.setText(configs.getUserName());
		add(uName);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("User E-mail:"));
//		String emailRegEx = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
//		final JFormattedTextField uEmail = new JFormattedTextField(new RegExFormatter(emailRegEx));
		final JFormattedTextField uEmail = new JFormattedTextField();
		uEmail.setText(configs.getUserEmail());
		add(uEmail);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("POP3 Server:"));
		final JFormattedTextField POP3Server = new JFormattedTextField();
		POP3Server.setText(configs.getPOP3Server());
		add(POP3Server);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("SMTP Server:"));
		final JFormattedTextField SMTPServer = new JFormattedTextField();
		SMTPServer.setText(configs.getSMTPServer());
		add(SMTPServer);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("POP3 Login:"));
		final JFormattedTextField POP3Login = new JFormattedTextField();
		POP3Login.setText(configs.getLoginPOP3());
		add(POP3Login);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("POP3 Password:"));
		final JFormattedTextField POP3Password = new JFormattedTextField();
		POP3Password.setText(configs.getPasswordPOP3());
		add(POP3Password);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("POP3 Port#:"));
//		String portRegEx = "^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$";
//		final JFormattedTextField POP3Port = new JFormattedTextField(createFormatter(portRegEx));
		final JFormattedTextField POP3Port = new JFormattedTextField();
		POP3Port.setText(""+configs.getPortPOP3());
//		POP3Port.setColumns(2);
		add(POP3Port);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("SMTP Port#:"));
		final JFormattedTextField SMTPPort = new JFormattedTextField();
		SMTPPort.setText(""+configs.getPortSMTP());
//		SMTPPort.setColumns(2);
		add(SMTPPort);
		add(new JLabel());
		
		final JRadioButton gmailTrue = new JRadioButton("True");
		final JRadioButton gmailFalse = new JRadioButton("False");
		ButtonGroup gmailGrp = new ButtonGroup();
		
		if(configs.isGmail())
			gmailTrue.doClick();
		else 
			gmailFalse.doClick();
		
		gmailGrp.add(gmailTrue);
		gmailGrp.add(gmailFalse);
		
		add(new JLabel());
		add(new JLabel("Is Gmail:"));
		add(gmailTrue);
		add(gmailFalse);
		
		final JRadioButton smtpTrue = new JRadioButton("True");
		final JRadioButton smtpFalse = new JRadioButton("False");
		ButtonGroup smtpGrp = new ButtonGroup();
		
		if(configs.isSMTP())
			smtpTrue.doClick();
		else 
			smtpFalse.doClick();
		
		smtpGrp.add(smtpTrue);
		smtpGrp.add(smtpFalse);
		
		add(new JLabel());
		add(new JLabel("Is SMTP Auth.:"));
		add(smtpTrue);
		add(smtpFalse);
		
		add(new JLabel());
		add(new JLabel("Database Server:"));
		final JFormattedTextField dbServer = new JFormattedTextField();
		dbServer.setText(""+configs.getDBServer());
		add(dbServer);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("Database Port:"));
		final JFormattedTextField dbPort = new JFormattedTextField();
		dbPort.setText(""+configs.getDBPort());
		add(dbPort);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("Database Name:"));
		final JFormattedTextField dbName = new JFormattedTextField();
		dbName.setText(""+configs.getDBName());
		add(dbName);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("Database Login:"));
		final JFormattedTextField dbLogin = new JFormattedTextField();
		dbLogin.setText(""+configs.getDBLogin());
		add(dbLogin);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel("Database Password:"));
		final JFormattedTextField dbPassword = new JFormattedTextField();
		dbPassword.setText(""+configs.getDBPassword());
		add(dbPassword);
		add(new JLabel());
		
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		//add ActionListener to the "OK" button
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MailConfigBean config;
				if(!uName.getText().equals("") &&
				   !uEmail.getText().equals("") &&
				   !POP3Server.getText().equals("") &&
				   !SMTPServer.getText().equals("") &&
				   !POP3Login.getText().equals("") &&
				   !POP3Password.getText().equals("") &&
				   !POP3Port.getText().equals("") &&
				   !SMTPPort.getText().equals("") &&
				   (gmailTrue.isSelected() || gmailFalse.isSelected()) &&
				   (smtpTrue.isSelected() || smtpFalse.isSelected()) &&
				   !dbServer.getText().equals("") &&
				   !dbPort.getText().equals("") &&
				   !dbName.getText().equals("") &
				   !dbLogin.getText().equals("") &&
				   !dbPassword.getText().equals("")){
					//if no fields are empty sets configurations and saves them.
					config = new MailConfigBean();
					config.setUserName(uName.getText());
					config.setUserEmail(uEmail.getText());
					config.setPOP3Server(POP3Server.getText());
					config.setSMTPServer(SMTPServer.getText());
					config.setLoginPOP3(POP3Login.getText());
					config.setPasswordPOP3(POP3Password.getText());
					config.setPortPOP3(Integer.parseInt(POP3Port.getText()));
					config.setPortSMTP(Integer.parseInt(SMTPPort.getText()));
					config.setGmail(gmailTrue.isSelected());
					config.setSMTP(smtpTrue.isSelected());
					config.setDBServer(dbServer.getText());
					config.setDBPort(dbPort.getText());
					config.setDBName(dbName.getText());
					config.setDBLogin(dbLogin.getText());
					config.setDBPassword(dbPassword.getText());
					properties.writeProperties(config);
					panelTest.setVisible(false);
				}
			}});
		
		//add ActionListener to the "Cancel" button
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
		}});
		
		add(new JLabel());
		add(ok);
		add(cancel);
		add(new JLabel());
		pack();
		centerScreen();
		setVisible(true);
	}

	/**
	 * Centers frame in the center of the screen
	 */
	private void centerScreen() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}
	
	/**
	 * Sets the LookAndFeel of the frame
	 * @param laf LookAndFeel to set
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
	
	private MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException e) {
			System.err.println("Illegal formatter: " + e.getMessage());
		}
		return formatter;
	}
}
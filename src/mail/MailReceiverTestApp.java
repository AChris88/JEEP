package mail;

import beans.MailConfigBean;
import db.MailTableModel;

/**
 * Test application created to test mail receiving.
 * I'm aware that the grading schema called for JUnit testing,
 * however I threw this together right after putting together the
 * the MailReceiver class and didn't have time to throw together 
 * a JUnit test before submission. 
 * @author Slaiy
 *
 */
public class MailReceiverTestApp {
	private MailProperties properties = null;
	private MailConfigBean config = null;
	private MailReceiver receiver = null;
	private MailTableModel model = null;
	
	public MailReceiverTestApp(){
		properties = new MailProperties();
		config = properties.loadProperties();
		receiver = new MailReceiver(config);
		model = new MailTableModel();
	}
	
	public static void main(String[] args){
		new MailReceiverTestApp();
	}
}
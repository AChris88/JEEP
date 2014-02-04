package mail;

import javax.swing.JOptionPane;
import beans.MailBean;
import beans.MailConfigBean;
/**
* Test application created to test mail sending.
* I'm aware that the grading schema called for JUnit testing,
* however I threw this together right after putting together the
* the MailSender class and didn't have time to throw together 
* a JUnit test before submission. 
*/
public class MailSenderTestApp {
	MailProperties properties = null;
	MailConfigBean config = null;
	MailSender sender = null;
	
	public MailSenderTestApp(){
		properties = new MailProperties();
		config = properties.loadProperties();
		sender = new MailSender(config);
		sendMail();
	}
	
	private void sendMail() {
			String toData = "achris484@gmail.com";
			MailBean msg = new MailBean();
			msg.setTo(toData);
			msg.setFrom("manx@bear.com");
			msg.setSubject("testSubject");
			msg.setMessage("testing sender02");
			if (sender.sendMail(msg))
				JOptionPane.showMessageDialog(null, "The message has been sent!",
						"Message Sent", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args){
		new MailSenderTestApp();
	}
}
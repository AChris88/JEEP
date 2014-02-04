package mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;
import com.sun.mail.pop3.POP3SSLStore;
import beans.MailBean;
import beans.MailConfigBean;

/**
 * Class in charge of receiving mail from the mail server specified in
 * the mail configuration settings. 
 * @author Slaiy
 */
public class MailReceiver {
	private MailConfigBean mailConfigData = null;
	private ArrayList<MailBean> mailMessageDataList = null;

	/**
	 * Constructor setting configurations via sent in MailConfigBean
	 * @param mailConfigData value containing mail configuration data.
	 */
	public MailReceiver(MailConfigBean mailConfigData) {
		this.mailConfigData = mailConfigData;
		mailMessageDataList = new ArrayList<MailBean>();
	}

	public ArrayList<MailBean> getMail() {
		boolean retVal = true;
		retVal = mailReceive();
		if (!retVal)
			mailMessageDataList.clear();
		return mailMessageDataList;
	}

	/**
	 * Handles the retrieval and processing of mail messages.
	 * @return true if messages have been received properly, else false.
	 */
	private boolean mailReceive() {
		boolean retVal = true;
		Store store = null;
		Folder folder = null;
		Session session = null;
		Properties pop3Props = new Properties();

		try {
			if (mailConfigData.isGmail()) {
				//setting POP3 settings
				pop3Props.put("mail.pop3.host",	mailConfigData.getPOP3Server());
				pop3Props.setProperty("mail.user",mailConfigData.getLoginPOP3());
				pop3Props.setProperty("mail.passwd",mailConfigData.getPasswordPOP3());
				pop3Props.setProperty("mail.pop3.port","" + mailConfigData.getPortPOP3());
				String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				pop3Props.setProperty("mail.pop3.socketFactory.class",SSL_FACTORY);
				pop3Props.setProperty("mail.pop3.socketFactory.port", ""+ mailConfigData.getPortPOP3());
				pop3Props.setProperty("mail.pop3.ssl", "true");
				pop3Props.setProperty("mail.pop3.socketFactory.fallback","false");
				URLName url = new URLName("pop3://"
						+ pop3Props.getProperty("mail.user") + ":"
						+ pop3Props.getProperty("mail.passwd") + "@"
						+ pop3Props.getProperty("mail.pop3.host") + ":"
						+ pop3Props.getProperty("mail.pop3.port"));

				// creates a mail session
				session = Session.getDefaultInstance(pop3Props);

				// gets hold of a POP3 message store, connecting to it
				store = new POP3SSLStore(session, url);

			}
			else {
				// create a mail session
				session = Session.getDefaultInstance(pop3Props);
				// get hold of a POP3 message store, connecting to it.
				store = session.getStore("pop3");
			}

			// connect to server
			System.out.println(mailConfigData);
			System.out.println(store.toString());
			store.connect(mailConfigData.getPOP3Server(),
						  mailConfigData.getLoginPOP3(),
						  mailConfigData.getPasswordPOP3());

			// get the default folder
			folder = store.getDefaultFolder();
			if (folder == null)
				throw new Exception(":No default folder");

			// get the INBOX from the default folder
			folder = folder.getFolder("INBOX");
			if (folder == null)
				throw new Exception(":No POP3 INBOX");

			folder.open(Folder.READ_ONLY);
//			folder.open(Folder.READ_WRITE);

			// getting all the waiting messages
			Message[] msgs = folder.getMessages();

			// turn the messages into beans
			retVal = process(msgs);
		} catch (NoSuchProviderException e) {
			JOptionPane.showMessageDialog(null,
					"There is no server at the POP3 address.",
					"POP3-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			retVal = false;
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"POP3-MessagingException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			retVal = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error " + e.getMessage(),
					"POP3-UnknownException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			retVal = false;
		} finally {
			//closing folder and store 
			try {
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (Exception ex2) {
				ex2.printStackTrace();
				JOptionPane
						.showMessageDialog(
								null,
								"There has been an error closing a folder\non the POP3 server.",
								"POP3-Folder Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return retVal;
	}

	/**
	 * Processes mail messages into MailBeans.
	 * @param messages array of messages to be processed.
	 * @return true if message has been properly processed, or else false.
	 */
	private boolean process(Message[] messages) {
		boolean retVal = true;
		MailBean msg = null;
		for (int i = 0; i < messages.length; i++) {
			msg = new MailBean();
			try {
				String from = null;
				try {
					from = ((InternetAddress) messages[i].getFrom()[0]).getPersonal();
					// get email address of sender
					if (from == null)
						from = ((InternetAddress) messages[i].getFrom()[0]).getAddress();
					else
						from = "["+ ((InternetAddress) messages[i].getFrom()[0]).getAddress() + "]";
					msg.setFrom(from);
				} catch (AddressException e) {
					from = "";
				}
				// get subject
				String subject = messages[i].getSubject();
				msg.setSubject(subject);

				// get date sent
				Date date = messages[i].getSentDate();
				msg.setDate(date.toString());

				// get the message
				Part messagePart = messages[i];
				String msgText = getMessageText(messagePart);
				msg.setMessage(msgText);
				mailMessageDataList.add(msg);

//				messages[i].setFlag(Flags.Flag.DELETED, true);
			} catch (MessagingException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"There is a problem reading a message.",
						"POP3-MessagingException", JOptionPane.ERROR_MESSAGE);
				retVal = false;
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"There has been an unknown error.",
						"POP3-UnknownException", JOptionPane.ERROR_MESSAGE);
				retVal = false;
			}
		}
		return retVal;
	}

	/**
	 * Examines the message text and  recursively identifies all parts sub parts,
	 * identifying text/plain sections.
	 * @param part 
	 * @return text retrieved from the sent in part.
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String getMessageText(Part part) throws MessagingException,IOException {
		if (part.isMimeType("text/*")) {
			String content = (String) part.getContent();
			return content;
		}

		if (part.isMimeType("multipart/alternative")) {
			// prefer plain text over html text
			Multipart multiPart = (Multipart) part.getContent();
			String text = null;
			for (int i = 0; i < multiPart.getCount(); i++) {
				Part bodyPart = multiPart.getBodyPart(i);
				if (bodyPart.isMimeType("text/html")) {
					if (text == null)
						text = getMessageText(bodyPart);
					continue;
				} else if (bodyPart.isMimeType("text/plain")) {
					String bodyPartMessageText = getMessageText(bodyPart);
					if (bodyPartMessageText != null)
						return bodyPartMessageText;
				} else {
					return getMessageText(bodyPart);
				}
			}
			return text;
		} else if (part.isMimeType("multipart/*")) {
			Multipart multiPart = (Multipart) part.getContent();
			for (int i = 0; i < multiPart.getCount(); i++) {
				String bodyPartMessageText = getMessageText(multiPart
						.getBodyPart(i));
				if (bodyPartMessageText != null)
					return bodyPartMessageText;
			}
		}
		return null;
	}
}
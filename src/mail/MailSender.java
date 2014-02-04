package mail;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import beans.MailBean;
import beans.MailConfigBean;

/**
 * Class in charge of sending mail via SMTP or GMail.
 * @author Slaiy
 */
public class MailSender {
	private MailConfigBean mailConfig;

	/**
	 * Constructor setting mail configurations via the sent in MailConfigBean
	 * @param mailConfigData bean containing the mail configuration settings.
	 */
	public MailSender(MailConfigBean mailConfigData) {
		this.mailConfig = mailConfigData;
	}

	/**
	 * Sends mail using SMTP or Gmail depending on the value set in the MailConfigBean. 
	 * @param mail message to be sent.
	 * @return true of the message was sent properly, or else false.
	 */
	public boolean sendMail(MailBean mail) {
		boolean retVal = true;
		if (mailConfig.isGmail()) {
			retVal = gmailSend(mail);
		} else {
			retVal = smtpSend(mail);
		}
		return retVal;
	}

	/**
	 * Sends a mail message through SMTP
	 * @param mail message to be sent.
	 * @return true of the message was sent properly, or else false
	 */
	private boolean smtpSend(MailBean mail) {
		boolean retVal = true;
		Session session = null;
		try {
			// create a properties object
			Properties smtpProps = new Properties();

			// add mail configuration to the properties
			smtpProps.put("mail.transport.protocol", "smtp");
			smtpProps.put("mail.smtp.host", mailConfig.getSMTPServer());
			smtpProps.put("mail.smtp.port", mailConfig.getPortSMTP());

			if (mailConfig.isSMTP()) {
				smtpProps.put("mail.smtp.auth", "true");
				Authenticator auth = new SMTPAuthenticator();
				session = Session.getInstance(smtpProps, auth);
			} else
				session = Session.getDefaultInstance(smtpProps);

			// create a new message
			MimeMessage msg = new MimeMessage(session);

			// set the single from field
			msg.setFrom(new InternetAddress(mailConfig
					.getUserEmail()));

			// set the to, cc, and bcc from their ArrayLists
			for (String emailAddress : mail.getTo().split(","))
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						emailAddress, false));

			String[] cc = mail.getCc().split(",");
			if (!cc[0].equals(""))
				for (String emailAddress : cc)
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress(emailAddress, false));
			String[] bcc = mail.getBcc().split(",");
			if (!bcc[0].equals(""))
				for (String emailAddress : bcc)
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(emailAddress, false));

			// set the subject
			msg.setSubject(mail.getSubject());

			// set the message
			msg.setText(mail.getMessage());

			// set some other header information
			msg.setHeader("X-Mailer", "Chris Allard's Email client");
			msg.setSentDate(new Date());

			if (mailConfig.isSMTP()) {
				Transport transport = session.getTransport();
				transport.connect();
				transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
				transport.close();
			} else
				Transport.send(msg);

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is no server at the SMTP address.",
					"SMTP-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (AddressException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is an error in a recipient's address.",
					"SMTP-AddressException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"SMTP-MessagingException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error.",
					"SMTP-UnknownException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		}
		return retVal;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = mailConfig.getLoginPOP3();
			String password = mailConfig.getPasswordPOP3();
			System.out.println(username + "\t" + password);
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * Sends the message through Gmail
	 * @param mail message to be sent
	 * @return true if message is sent successfully, or else false.
	 */
	private boolean gmailSend(MailBean mail) {
		boolean retVal = true;
		Transport transport = null;
		try {
			// create a properties object
			Properties smtpProps = new Properties();
			
			// add mail configuration to the properties
			smtpProps.put("mail.transport.protocol", "smtps");
			smtpProps.put("mail.smtps.host", mailConfig.getSMTPServer());
			smtpProps.put("mail.smtps.auth", "true");
			smtpProps.put("mail.smtps.quitwait", "false");

			// create a mail session
			Session mailSession = Session.getDefaultInstance(smtpProps);

			// instantiate the transport object
			transport = mailSession.getTransport();

			// create a new message
			MimeMessage msg = new MimeMessage(mailSession);

			// set the to, cc, and bcc from their ArrayLists
			for (String emailAddress : mail.getTo().split(","))
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						emailAddress, false));
			
			String[] cc = mail.getCc().split(",");
			if (!cc[0].equals(""))
				for (String emailAddress : cc)
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress(emailAddress, false));
			String[] bcc = mail.getBcc().split(",");
			if (!bcc[0].equals(""))
				for (String emailAddress : bcc)
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(emailAddress, false));

			// set the subject line
			msg.setSubject(mail.getSubject());
			// set the message body
			msg.setText(mail.getMessage());

			// set some other header information
			msg.setHeader("X-Mailer", "Comp Sci Tech Mailer");
			msg.setSentDate(new Date());

			// connect and authenticate to the server
			transport.connect(mailConfig.getSMTPServer(), mailConfig
					.getPortSMTP(), mailConfig.getLoginPOP3(),
					mailConfig.getPasswordPOP3());

			// send the message
			transport.sendMessage(msg, msg.getAllRecipients());

			// close the connection
			transport.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is no server at the SMTP address.",
					"Gmail-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (AddressException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is an error in a recipient's address.",
					"Gmail-AddressException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"Gmail-MessagingException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error.",
					"Gmail-UnknownException", JOptionPane.ERROR_MESSAGE);
			retVal = false;
		}
		return retVal;
	}
}
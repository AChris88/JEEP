package mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import beans.MailConfigBean;

public class MailProperties {

	private String propFileName;
	private Properties prop = null;

	/**
	 * Constructor calling super and setting the appropriate properties file.
	 */
	public MailProperties() {
		super();
		//SMTP
//		propFileName = "MailConfig.properties";
		
		//Gmail
		propFileName = "GMailConfig.properties";
		prop = new Properties();
	}

	/**
	 * Loads the properties, storing them into a MailConfigBean.
	 * @return MailConfigBean containing the mail configuration settings.
	 */
	public MailConfigBean loadProperties() {
		MailConfigBean mailConfigData = new MailConfigBean();
		FileInputStream propFileStream = null;
		//create a file object using the specified file name.
		File propFile = new File(propFileName);
		//if the file exists, the saved settings are stored a MailConfigBean
		if (propFile.exists()) {
			try {
				propFileStream = new FileInputStream(propFile);
				prop.load(propFileStream);
				propFileStream.close();
				
				// Storing the properties in the bean.
				mailConfigData.setUserName(prop.getProperty("userName"));
				mailConfigData.setUserEmail(prop.getProperty("userEmail"));
				mailConfigData.setPOP3Server(prop.getProperty("POP3Server"));
				mailConfigData.setSMTPServer(prop.getProperty("SMTPServer"));
				mailConfigData.setLoginPOP3(prop.getProperty("loginPOP3"));
				mailConfigData.setPasswordPOP3(prop.getProperty("passwordPOP3"));
				mailConfigData.setPortPOP3(Integer.parseInt(prop.getProperty("portPOP3")));
				mailConfigData.setPortSMTP(Integer.parseInt(prop.getProperty("portSMTP")));
				mailConfigData.setGmail(Boolean.parseBoolean(prop.getProperty("isGmail")));
				mailConfigData.setSMTP(Boolean.parseBoolean(prop.getProperty("isSMTP")));
				mailConfigData.setDBServer(prop.getProperty("dbServer"));
				mailConfigData.setDBPort(prop.getProperty("dbPort"));
				mailConfigData.setDBName(prop.getProperty("dbName"));
				mailConfigData.setDBLogin(prop.getProperty("dbLogin"));
				mailConfigData.setDBPassword(prop.getProperty("dbPassword"));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"The properties file has not been found.",
						"Missing Properties File", JOptionPane.ERROR_MESSAGE);
				mailConfigData = null;
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"There was an error reading the Properties file.",
						"Properties File Read Error", JOptionPane.ERROR_MESSAGE);
				mailConfigData = null;
				e.printStackTrace();
			}
		} else
			mailConfigData = null;

		return mailConfigData;
	}

	/**
	 * Writes the settings stored in the sent in MailConfigBean to
	 * a persisted file.
	 * @param mailConfigData Bean containing the settings to be saved.
	 * @return true if the properties are properly saved, or else false.
	 */
	public boolean writeProperties(MailConfigBean mailConfigData) {
		boolean retVal = true;

		prop.setProperty("userName", mailConfigData.getUserName());
		prop.setProperty("userEmail", mailConfigData.getUserEmail());
		prop.setProperty("POP3Server", mailConfigData.getPOP3Server());
		prop.setProperty("SMTPServer", mailConfigData.getSMTPServer());
		prop.setProperty("loginPOP3", mailConfigData.getLoginPOP3());
		prop.setProperty("passwordPOP3", mailConfigData.getPasswordPOP3());
		prop.setProperty("portPOP3", "" + mailConfigData.getPortPOP3());
		prop.setProperty("portSMTP", "" + mailConfigData.getPortSMTP());
		prop.setProperty("isGmail", Boolean.toString(mailConfigData.isGmail()));
		prop.setProperty("isSMTP", Boolean.toString(mailConfigData.isSMTP()));
		prop.setProperty("dbServer", mailConfigData.getDBServer());
		prop.setProperty("dbPort", mailConfigData.getDBPort());
		prop.setProperty("dbName", mailConfigData.getDBName());
		prop.setProperty("dbLogin", mailConfigData.getDBLogin());
		prop.setProperty("dbPassword", mailConfigData.getDBPassword());
		
		FileOutputStream propFileStream = null;
		File propFile = new File(propFileName);
		try {
			propFileStream = new FileOutputStream(propFile);
			prop.store(propFileStream, "-- MailConfig Properties --");
			propFileStream.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"The properties file has not been found.",
					"Missing Properties File", JOptionPane.ERROR_MESSAGE);
			retVal = false;
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"There was an error writing the Properties file.",
					"Properties File Write Error", JOptionPane.ERROR_MESSAGE);
			retVal = false;
			e.printStackTrace();
		}
		return retVal;
	}
}
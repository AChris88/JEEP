package beans;

/**Bean representing a single mail configuration, containing
 * a mail configuration's userName, userEmail, protocol, host,
 * portSMTP, portPOP3, POP3Server, SMTPServer, loginPOP3, passwordPOP3,
 * auth, tWait, isGmail, isSMTP, dbServer, dbPort, dbName, dbLogin,
 * and dbPassword fields.
 * @author Slaiy
 */
public class MailConfigBean {
	private String userName;
	private String userEmail;
	private String protocol;
	private String host;
	private int portSMTP;
	private int portPOP3;
	private String POP3Server;
	private String SMTPServer;
	private String loginPOP3;
	private String passwordPOP3;
	private String auth;
	private String tWait;
	private boolean isGmail;
	private boolean isSMTP;
	private String dbServer;
	private String dbPort;
	private String dbName;
	private String dbLogin;
	private String dbPassword;
	
	/**
	 * 
	 */
	public MailConfigBean(){
	}
	
	/**
	 * Accessor used to access the mail configuration's protocol field.
	 * @return the mail configuration's protocol field.
	 */
	public String getProtocol(){return protocol;}
	/**
	 * Mutator used to set the mail configuration's protocol field.
	 * @param to value to set as the mail configuration's protocol field.
	 */
	public void setProtocol(String protocol){this.protocol = protocol;}
	/**
	 * Accessor used to access the mail configuration's host field.
	 * @return the mail configuration's host field.
	 */
	public String getHost(){return host;}
	/**
	 * Mutator used to set the mail configuration's host field.
	 * @param to value to set as the mail configuration's host field.
	 */
	public void setHost(String host){this.host = host;}
	/**
	 * Accessor used to access the mail configuration's auth field.
	 * @return the mail configuration's auth field.
	 */
	public String getAuth(){return auth;}
	/**
	 * Mutator used to set the mail configuration's auth field.
	 * @param to value to set as the mail configuration's auth field.
	 */
	public void setAuth(String auth){this.auth = auth;}
	/**
	 * Accessor used to access the mail configuration's tWait field.
	 * @return the mail configuration's tWait field.
	 */
	public String gettWait(){return tWait;}
	/**
	 * Mutator used to set the mail configuration's tWait field.
	 * @param to value to set as the mail configuration's tWait field.
	 */
	public void settWait(String tWait){this.tWait = tWait;}
	/**
	 * Accessor used to access the mail configuration's portSMTP field.
	 * @return the mail configuration's portSMTP field.
	 */
	public int getPortSMTP(){return portSMTP;}
	/**
	 * Mutator used to set the mail configuration's portSMTP field.
	 * @param to value to set as the mail configuration's portSMTP field.
	 */
	public void setPortSMTP(int portSMTP) {this.portSMTP = portSMTP;}
	/**
	 * Accessor used to access the mail configuration's portPOP3 field.
	 * @return the mail configuration's portPOP3 field.
	 */
	public int getPortPOP3() {return portPOP3;	}
	/**
	 * Mutator used to set the mail configuration's portPOP3 field.
	 * @param to value to set as the mail configuration's portPOP3 field.
	 */
	public void setPortPOP3(int portPOP3) {this.portPOP3 = portPOP3;}
	/**
	 * Accessor used to access the mail configuration's POP3Server field.
	 * @return the mail configuration's POP3Server field.
	 */
	public String getPOP3Server() {	return POP3Server;}
	/**
	 * Mutator used to set the mail configuration's POP3Server field.
	 * @param to value to set as the mail configuration's POP3Server field.
	 */
	public void setPOP3Server(String pOP3Server) {	POP3Server = pOP3Server;}
	/**
	 * Accessor used to access the mail configuration's SMTPServer field.
	 * @return the mail configuration's SMTPServer field.
	 */
	public String getSMTPServer() {	return SMTPServer;}
	/**
	 * Mutator used to set the mail configuration's SMTPServer field.
	 * @param to value to set as the mail configuration's SMTPServer field.
	 */
	public void setSMTPServer(String sMTPServer) {	SMTPServer = sMTPServer;}
	/**
	 * Accessor used to access the mail configuration's loginPOP3 field.
	 * @return the mail configuration's loginPOP3 field.
	 */
	public String getLoginPOP3() {	return loginPOP3;}
	/**
	 * Mutator used to set the mail configuration's loginPOP3 field.
	 * @param to value to set as the mail configuration's loginPOP3 field.
	 */
	public void setLoginPOP3(String loginPOP3) {this.loginPOP3 = loginPOP3;	}
	/**
	 * Accessor used to access the mail configuration's passwordPOP3 field.
	 * @return the mail configuration's passwordPOP3 field.
	 */
	public String getPasswordPOP3() {	return passwordPOP3;}
	/**
	 * Mutator used to set the mail configuration's passwordPOP3 field.
	 * @param to value to set as the mail configuration's passwordPOP3 field.
	 */
	public void setPasswordPOP3(String passwordPOP3) {	this.passwordPOP3 = passwordPOP3;}
	/**
	 * Accessor used to access the mail configuration's userName field.
	 * @return the mail configuration's userName field.
	 */
	public String getUserName() {return userName;}
	/**
	 * Mutator used to set the mail configuration's userName field.
	 * @param to value to set as the mail configuration's userName field.
	 */
	public void setUserName(String userName) {this.userName = userName;	}
	/**
	 * Accessor used to access the mail configuration's userEmail field.
	 * @return the mail configuration's userEmail field.
	 */
	public String getUserEmail() {return userEmail;}
	/**
	 * Mutator used to set the mail configuration's userEmail field.
	 * @param to value to set as the mail configuration's userEmail field.
	 */
	public void setUserEmail(String userEmail) {this.userEmail = userEmail;	}
	/**
	 * Accessor used to access the mail configuration's isSMTP field.
	 * @return the mail configuration's isSMTP field.
	 */
	public boolean isSMTP() {	return isSMTP;	}
	/**
	 * Mutator used to set the mail configuration's isSMTP field.
	 * @param to value to set as the mail configuration's isSMTP field.
	 */
	public void setSMTP(boolean isSMTP) {	this.isSMTP = isSMTP;}
	/**
	 * Accessor used to access the mail configuration's isGmail field.
	 * @return the mail configuration's isGmail field.
	 */
	public boolean isGmail() {return isGmail;}		
	/**
	 * Mutator used to set the mail configuration's isGmail field.
	 * @param to value to set as the mail configuration's isGmail field.
	 */
	public void setGmail(boolean isGmail) {	this.isGmail = isGmail;	}
	/**
	 * Accessor used to access the mail configuration's dbServer field.
	 * @return the mail configuration's dbServer field.
	 */
	public String getDBServer() {return dbServer;}
	/**
	 * Mutator used to set the mail configuration's dbServer field.
	 * @param to value to set as the mail configuration's dbServer field.
	 */
	public void setDBServer(String dbServer) {this.dbServer=dbServer;}
	/**
	 * Accessor used to access the mail configuration's dbPort field.
	 * @return the mail configuration's dbPort field.
	 */
	public String getDBPort() {	return dbPort;}
	/**
	 * Mutator used to set the mail configuration's dbPort field.
	 * @param to value to set as the mail configuration's dbPort field.
	 */
	public void setDBPort(String dbPort) {this.dbPort=dbPort;}
	/**
	 * Accessor used to access the mail configuration's dbName field.
	 * @return the mail configuration's dbName field.
	 */
	public String getDBName() {	return dbName;}
	/**
	 * Mutator used to set the mail configuration's dbName field.
	 * @param to value to set as the mail configuration's dbName field.
	 */
	public void setDBName(String dbName) {this.dbName=dbName;}
	/**
	 * Accessor used to access the mail configuration's dbLogin field.
	 * @return the mail configuration's dbLogin field.
	 */
	public String getDBLogin() {return dbLogin;	}
	/**
	 * Mutator used to set the mail configuration's dbLogin field.
	 * @param to value to set as the mail configuration's dbLogin field.
	 */
	public void setDBLogin(String dbLogin) {this.dbLogin=dbLogin;}
	/**
	 * Accessor used to access the mail configuration's dbPassword field.
	 * @return the mail configuration's dbPassword field.
	 */
	public String getDBPassword() {	return dbPassword;}
	/**
	 * Mutator used to set the mail configuration's dbPassword field.
	 * @param to value to set as the mail configuration's dbPassword  field.
	 */
	public void setDBPassword(String dbPassword) {this.dbPassword=dbPassword;}

	@Override
	public String toString() {
		return "MailConfigBean [userName=" + userName + ", userEmail="
				+ userEmail + ", protocol=" + protocol + ", host=" + host
				+ ", portSMTP=" + portSMTP + ", portPOP3=" + portPOP3
				+ ", POP3Server=" + POP3Server + ", SMTPServer=" + SMTPServer
				+ ", loginPOP3=" + loginPOP3 + ", passwordPOP3=" + passwordPOP3
				+ ", auth=" + auth + ", tWait=" + tWait + ", isGmail="
				+ isGmail + ", isSMTP=" + isSMTP + ", dbServer=" + dbServer
				+ ", dbPort=" + dbPort + ", dbName=" + dbName + ", dbLogin="
				+ dbLogin + ", dbPassword=" + dbPassword + "]";
	}
}

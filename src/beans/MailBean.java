package beans;

/**Bean representing a single mail message, containing
 * a message's to, from, subject, cc, bcc, date, message
 * and folder fields.
 * @author Slaiy
 */
public class MailBean {
	private String id;
	private String to;
	private String from;
	private String subject;
	private String cc;
	private String bcc;
	private String date;
	private String message;
	private String folder;
	
	/**
	 * Default constructor instantiating all fields to an empty string
	 */
	public MailBean(){
		this("",null,"","",null,null,"","","");
	}

	/**Constructor setting all fields to values in parameter list.
	 * @param id of mail bean, if non-null, used identify bean to update
	 * @param to to set as contact's to.
	 * @param from to set as contact's from.
	 * @param subject to set as contact's subject.
	 * @param cc to set as contact's cc.
	 * @param bcc to set as contact's bcc.
	 * @param date to set as contact's date.
	 * @param message to set as contact's message.
	 * @param folder to set as contact's folder.
	 */
	public MailBean(String id, String to, String from,
			String subject, String cc, String bcc,
			String date, String message, String folder) {
		super();
		this.id = id;
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.cc = cc;
		this.bcc = bcc;
		this.date = date;
		this.message = message;
		this.folder = folder;
	}

	/**
	 * Accessor used to access the mail's to field.
	 * @return the mail's to field.
	 */
	public String getTo() {return to;}
	/**
	 * Mutator used to set the mail's to field.
	 * @param to value to set as the mail's to field.
	 */
	public void setTo(String to) {this.to = to;	}
	/**
	 * Accessor used to access the mail's cc field.
	 * @return the mail's cc field.
	 */
	public String getCc() {	return cc;	}
	/**
	 * Mutator used to set the mail's cc field.
	 * @param to value to set as the mail's cc field.
	 */
	public void setCc(String cc) {	this.cc = cc;	}
	/**
	 * Accessor used to access the mail's bcc field.
	 * @return the mail's bcc field.
	 */
	public String getBcc() {	return bcc;	}
	/**
	 * Mutator used to set the mail's bcc field.
	 * @param to value to set as the mail's bcc field.
	 */
	public void setBcc(String bcc) {	this.bcc = bcc;	}
	/**
	 * Accessor used to access the mail's from field.
	 * @return the mail's from field.
	 */
	public String getFrom(){return from;}
	/**
	 * Mutator used to set the mail's from field.
	 * @param to value to set as the mail's from field.
	 */
	public void setFrom(String from){this.from = from;}
	/**
	 * Accessor used to access the mail's subject field.
	 * @return the mail's subject field.
	 */
	public String getSubject(){return subject;}
	/**
	 * Mutator used to set the mail's subject field.
	 * @param to value to set as the mail's subject field.
	 */
	public void setSubject(String subject){this.subject = subject;}
	/**
	 * Accessor used to access the mail's date field.
	 * @return the mail's date field.
	 */
	public String getDate(){return date;}
	/**
	 * Mutator used to set the mail's date field.
	 * @param to value to set as the mail's date field.
	 */
	public void setDate(String date){this.date = date;}
	/**
	 * Accessor used to access the mail's message field.
	 * @return the mail's message field.
	 */
	public String getMessage(){return message;}
	/**
	 * Mutator used to set the mail's message field.
	 * @param to value to set as the mail's message field.
	 */
	public void setMessage(String message){this.message = message;}
	/**
	 * Accessor used to access the mail's id field.
	 * @return the mail's id field.
	 */
	public String getId(){return id;}
	/**
	 * Mutator used to set the mail's id field.
	 * @param to value to set as the mail's id field.
	 */
	public void setId(String id){this.id = id;}
	/**
	 * Accessor used to access the mail's folder field.
	 * @return the mail's folder field.
	 */
	public String getFolder(){return folder;}
	/**
	 * Mutator used to set the mail's folder field.
	 * @param to value to set as the mail's folder field.
	 */
	public void setFolder(String folder){this.folder = folder;}

	/**
	 * Overrides toString method which returns a string representation
	 * of the mail bean.
	 */
	@Override
	public String toString() {
		return "MailBean [id=" + id + ", to=" + to + ", from=" + from
				+ ", subject=" + subject + ", cc=" + cc + ", bcc=" + bcc
				+ ", date=" + date + ", message=" + message + ", folder="
				+ folder + "]";
	}
}

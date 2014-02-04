package beans;

/**Bean representing a single contact, containing
 * a person's first name, last name, and e-mail address.
 * @author Slaiy
 */
public class ContactBean {
	private String id;
	private String email;
	private String fName;
	private String lName;
	
	/**
	 * Default constructor instantiating all fields to an empty string
	 */
	public ContactBean(){
		this("","","","");
	}
	
	/**
	 * Constructor setting all fields to values in parameter list.
	 * @param id of contact bean, if non-null, used identify bean to update.
	 * @param fName to set as contact's first name.
	 * @param lName to set as contact's last name.
	 * @param email to set as contact's e-mail address.
	 */
	public ContactBean(String id,String fName, String lName,String email){
		this.email=email;
		this.fName=fName;
		this.lName=lName;
		this.id=id;
	}
	
	/**
	 * Accessor used to access the contact's e-mail address.
	 * @return	the contact's e-mail.
	 */
	public String getEmail(){return email;}
	/**
	 * Mutator used to set the contact's e-mail address.
	 * @param email value to set as the contact's e-mail address.
	 */
	public void setEmail(String email){this.email = email;}
	/**
	 * Accessor used to access the contact's first name.
	 * @return	the contact's first name.
	 */
	public String getfName(){return fName;}
	/**
	 * Mutator used to set the contact's first name.
	 * @param fName value to set as the contact's first name.
	 */
	public void setfName(String fName){this.fName = fName;}
	/**
	 * Accessor used to access the contact's last name.
	 * @return	the contact's last name.
	 */
	public String getlName(){return lName;}
	/**
	 * Mutator used to set the contact's last name.
	 * @param lName value to set as the contact's last name.
	 */
	public void setlName(String lName){this.lName = lName;}
	/**
	 * Accessor used to access the contact's id.
	 * @return	the contact's id.
	 */
	public String getId(){return id;}
	/**
	 * Mutator used to set the contact's id.
	 * @param id value to set as the contact's id.
	 */
	public void setId(String id){this.id = id;}
}
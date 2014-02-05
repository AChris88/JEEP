package db;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mail.MailProperties;
import beans.ContactBean;
import beans.MailBean;
import beans.MailConfigBean;

/**
 * Database Manager used to open a connection to a database, perform create,
 * read, update, and delete procedures, and then close the database connection.
 * 
 * @author Slaiy
 * 
 */
public class DBManager {
	private Logger logger = Logger.getLogger(getClass().getName());
	private Connection connection = null;
	private ContactTableModel contactModel = null;
	private MailTableModel mailModel = null;
	private FolderTableModel folderModel = null;
	private MailProperties props = null;
	private MailConfigBean configs = null;

	/**
	 * 0-param constructor instantiating the ContactTableModel, MailTableModel,
	 * FolderTbaleModel, MailProperties, and MailConfigBean.
	 */
	public DBManager() {
		contactModel = new ContactTableModel();
		mailModel = new MailTableModel();
		folderModel = new FolderTableModel();
		props = new MailProperties();
		configs = props.loadProperties();
	}

	/**
	 * Connection method used to access the database specified in the
	 * MailProperties.
	 */
	private void connect() {
		try {
			// Creating the connection url consisting of protocols, server, port
			// number, and database name.
			String url = "jdbc:mysql://" + configs.getDBServer() + ":"
					+ configs.getDBPort() + "/" + configs.getDBName();
			String user = configs.getDBLogin();
			String password = configs.getDBPassword();
			System.out.println(url + "\n" + user + "\n" + password);
			// Creating a connection to the database using the connetion url,
			// user name, and password
			connection = DriverManager.getConnection(url, user, password);

			Statement statement = null;
			DatabaseMetaData meta = connection.getMetaData();
			// Checks if table 'mail_table' exists
			ResultSet tables = meta.getTables(null, null, "mail_table", null);
			// If the table doesn't exist, it is created.
			if (!tables.next()) {
				statement = connection.createStatement();
				statement
						.execute("create table mail_table("
								+ "id			int unsigned not null primary key auto_increment,"
								+ "receiver	varchar(512) not null,"
								+ "sender		varchar(50) not null,"
								+ "subject		varchar(50) not null,"
								+ "cc			varchar(512) not null,"
								+ "bcc			varchar(512) not null,"
								+ "received	varchar(50) not null,"
								+ "message		varchar(512) not null,"
								+ "folder		varchar(50) not null" + ")");
			}

			// Checks if table 'contacts_table' exists
			tables = meta.getTables(null, null, "contacts_table", null);
			// If the table doesn't exist, it is created.
			if (!tables.next()) {
				statement = connection.createStatement();
				statement
						.execute("create table contacts_table("
								+ "id			int unsigned not null primary key auto_increment,"
								+ "first_name	varchar(50) not null,"
								+ "last_name 	varchar(50) not null,"
								+ "address		varchar(50) not null" + ")");
			}

			// Checks if table 'folders_table' exists
			tables = meta.getTables(null, null, "folders_table", null);
			// If the table doesn't exist, it is created.
			if (!tables.next()) {
				statement = connection.createStatement();
				statement
						.execute("create table folders_table("
								+ "id		int unsigned not null primary key auto_increment,"
								+ "folder	varchar(15) not null" + ")");
				statement
						.execute("insert into folders_table values(null, 'Inbox')");
				statement
						.execute("insert into folders_table values(null, 'Sent')");
				statement
						.execute("insert into folders_table values(null, 'Draft')");
				statement
						.execute("insert into folders_table values(null, 'Deleted')");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Cannot connect to the database!", "JDBC  Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.SEVERE, "Unable to connect to database!", e);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Unexpected error has occured!", "Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.SEVERE, "Unexpected error!", e);
		}
	}

	/**
	 * Method used to close the connection to the database.
	 */
	private void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Unable to close connection to database!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.SEVERE, "Unable to close connection to database!",
					e);
		}
	}

	// * MAIL ACCESS METHODS *\\
	/**
	 * Opens a connection the database, retrieves all mail messages,then closes
	 * the connection.
	 * 
	 * @return List containing MailBeans representing all mail in the database.
	 */
	public List<MailBean> getAllMail() {
		// Instantiates the variables needed to retrieve the mail
		List<MailBean> mail = new ArrayList<MailBean>();
		Statement statement = null;
		ResultSet results = null;
		// Connection to the database
		connect();
		try {
			statement = connection.createStatement();
			// store results of the query
			results = statement.executeQuery("SELECT * FROM mail_table");
			// while there are still results, add them to the list
			while (results.next()) {
				mail.add(new MailBean(results.getString(1), results
						.getString(2), results.getString(3), results
						.getString(4), results.getString(5), results
						.getString(6), results.getString(7), results
						.getString(8), results.getString(9)));
			}
			// load the retriev data into the mail table model
			mailModel.loadData(results);
			// retrieve the meta data from the results
			ResultSetMetaData mtd = results.getMetaData();
			// use the meta data to load the column names for the table.
			mailModel.loadColumnNames(mtd);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Unnable to retrieve all mail!", "JDBC Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records!", e);
		} finally {
			close();
		}
		return mail;
	}

	/**
	 * Saves a mail record, either by inserting it or modifying it.
	 * 
	 * @param mail
	 *            value to be saved.
	 * @return true if save is successful, false if it isn't.
	 */
	public boolean saveMail(MailBean mail) {
		boolean valid = false;
		if (mail != null) {
			if (mail.getId().equals("null"))
				valid = insertMail(mail);
			else
				valid = updateMail(mail);
		}
		return valid;
	}

	/**
	 * 
	 * @param mail
	 * @return
	 */
	public boolean updateMail(MailBean mail) {
		boolean valid = true;
		PreparedStatement statement = null;
		connect();
		try {
			// create the SQL statement.
			String update = "UPDATE mail_table SET folder='" + mail.getFolder()
					+ "'" + " WHERE id =" + mail.getId();
			statement = connection.prepareStatement(update);
			// execute the statement, obtaining the number of updated fields.
			int records = statement.executeUpdate();
			// if more than one row is affected, something failed, setting valid
			// to false
			if (records != 1)
				valid = false;
			// update the changes to the mail model
			mailModel.updateMail(mail.getId());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cannot update contact!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Cannot update record!", e);
			valid = false;
		} finally {
			close();
		}
		return valid;
	}

	/**
	 * Updated the specified MailBean's 'folder' value to Deleted in the databse
	 * and model.
	 * 
	 * @param mail
	 *            value to send to the Deleted folder
	 * @return true if the value was successfully updated, orelse false.
	 */
	public boolean putMailInTrash(MailBean mail) {
		boolean valid = true;
		PreparedStatement statement = null;
		connect();
		try {
			// create SQL statement
			String update = "UPDATE mail_table SET folder='" + "Deleted'"
					+ " WHERE id =" + mail.getId();
			statement = connection.prepareStatement(update);
			// execute statement obtaining the number of updated rows
			int records = statement.executeUpdate();
			// if more than one row is affected, something failed, setting valid
			// to false
			if (records != 1)
				valid = false;
			// update changes to the mail model.
			mailModel.updateMail(mail.getId());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cannot update contact!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Cannot update record!", e);
			valid = false;
		} finally {
			close();
		}
		return valid;
	}

	/**
	 * Inserts a new MailBean into the database, and updates the model.
	 * 
	 * @param mail
	 *            value to be inserted into the database.
	 * @return true if the MailBean was successfully inserted into the database,
	 *         orelse false.
	 */
	private boolean insertMail(MailBean mail) {
		boolean valid = true;
		PreparedStatement statement;
		connect();
		try {
			// create the SQL PreparedStatement.
			statement = connection
					.prepareStatement("INSERT INTO mail_table "
							+ "(id, receiver, sender, subject, cc, bcc, received, message, folder)"
							+ "VALUES (?,?,?,?,?,?,?,?,?)");
			// inserting values into PreparedStatement
			statement.setString(1, null);
			statement.setString(2, mail.getTo());
			statement.setString(3, mail.getFrom());
			statement.setString(4,
					mail.getSubject() == null ? "" : mail.getSubject());
			statement.setString(5, mail.getCc() == null ? "" : mail.getCc());
			statement.setString(6, mail.getBcc() == null ? "" : mail.getBcc());
			statement
					.setString(7, mail.getDate() == null ? "" : mail.getDate());
			statement.setString(8,
					mail.getMessage() == null ? "" : mail.getMessage());
			statement.setString(9,
					mail.getFolder() == null ? "" : mail.getFolder());

			// execute statement obtaining the number of inserted rows
			int records = statement.executeUpdate();
			// if more than one row is affected, something failed, setting valid
			// to false
			if (records != 1)
				valid = false;
			Statement getLast = connection.createStatement();
			ResultSet row = getLast.executeQuery("SELECT LAST_INSERT_ID()");
			row.next();
			mailModel.insertMail(new MailBean("" + row.getInt(1), mail.getTo(),
					mail.getFrom(), mail.getSubject(), mail.getCc(), mail
							.getBcc(), mail.getDate(), mail.getMessage(), mail
							.getFolder()));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Unable to insert mail!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Unable to insert record!", e);
			valid = false;
		} finally {
			close();
		}
		return valid;
	}

	/**
	 * Deletes all mail messages in the 'Deleted' folder.
	 * 
	 * @return
	 */
	public boolean deleteMail() {
		boolean valid = true;
		// gets all mail in 'Deleted' folder.
		ArrayList<MailBean> beans = (ArrayList<MailBean>) getMailByFolder("Deleted");
		PreparedStatement statement = null;
		int ctr;
		if (beans.size() != 0) {
			// gets the number of elements retrieved.
			ctr = beans.size();
			connect();
			try {
				for (int i = 0; i < ctr; i++) {
					// Just realized this should probably be
					// "Delete from mail_table where folder='Delete'"
					statement = connection
							.prepareStatement("DELETE FROM mail_table WHERE id= ?");
					statement.setString(1, beans.get(i).getId());
					int records = statement.executeUpdate();
					if (records != 1)
						valid = false;
					// updates the model once the record had been deleted
					mailModel.removeMail(beans.get(i).getId());
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Cannot delete mail!",
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Cannot update record!", e);
				valid = false;
			} finally {
				close();
			}
		}
		return valid;
	}

	/**
	 * Retrieves all the mail in a specified folder.
	 * 
	 * @param folder
	 *            name to retrieve mail from.
	 * @return List containing all mail records from the specified folder.
	 */
	public List<MailBean> getMailByFolder(String folder) {
		List<MailBean> mail = new ArrayList<MailBean>();
		Statement statement = null;
		ResultSet results = null;
		// makes sure the folder name is not null or an empty string
		if (folder != null && folder.length() > 0) {
			connect();
			try {
				statement = connection.createStatement();
				// execute the query, storing the results.
				results = statement
						.executeQuery("SELECT * FROM mail_table WHERE folder = '"
								+ folder + "'");
				// while there are still results, adds them to the mail List.
				while (results.next()) {
					mail.add(new MailBean(results.getString(1), results
							.getString(2), results.getString(3), results
							.getString(4), results.getString(5), results
							.getString(6), results.getString(7), results
							.getString(8), results.getString(9)));
				}
				// updates the mail model with the results.
				mailModel.loadData(results);
				// retrieves the meta data from the results.
				ResultSetMetaData mtd = results.getMetaData();
				// uses the meta data to load the column names into the model.
				mailModel.loadColumnNames(mtd);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Cannot retrieve records!",
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Cannot retrieve records!", e);
			} finally {
				close();
			}
		}
		return mail;
	}

	/**
	 * Retrieve mail based on the date it was received or by the the sender.
	 * NOTE: Unfortunately, this method has not been implemented.
	 * 
	 * @param isDate
	 *            indicates whether we are searching by date or by e-mail
	 *            address
	 * @param dateOrAddress
	 *            the data or address to search for.
	 * @return List containing all mail records retrieved.
	 */
	public List<ContactBean> getMailByAddressOrDate(boolean isDate,
			String dateOrAddress) {
		List<ContactBean> contacts = new ArrayList<ContactBean>();
		PreparedStatement statement = null;
		ResultSet results = null;
		connect();
		try {
			// preparing the statement based on whether out search key is a date
			// or e-mail.
			statement = connection
					.prepareStatement("SELECT * FROM mail_table WHERE "
							+ (isDate ? "received=" : "sender=") + "?");
			statement.setString(1, dateOrAddress);
			// executed the query, storing the results.
			results = statement.executeQuery();
			// while there are still results, add them to the mail List
			while (results.next()) {
				contacts.add(new ContactBean(results.getString(1), results
						.getString(2), results.getString(3), results
						.getString(4)));
			}
			// load the retrieved data into the model
			mailModel.loadData(results);
			// retrieve the meta data from the results
			ResultSetMetaData mtd = results.getMetaData();
			// use the meta data to load the column names into the model
			mailModel.loadColumnNames(mtd);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Unnable to retrieve contact by name or address!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records!", e);
		} finally {
			close();
		}
		return contacts;
	}

	// * CONTACTS ACCESS METHODS *\\
	/**
	 * Gets all contacts in the database.
	 * 
	 * @return List containing all contact records retrieved.
	 */
	public List<ContactBean> getAllContacts() {
		List<ContactBean> contacts = new ArrayList<ContactBean>();
		Statement statement = null;
		ResultSet results = null;
		connect();
		try {
			statement = connection.createStatement();
			// execute the query for the records in the table 'contacts_table'
			results = statement.executeQuery("SELECT * FROM contacts_table");

			// while there are results, they are added to the contact List
			while (results.next())
				contacts.add(new ContactBean(results.getString(1), results
						.getString(2), results.getString(3), results
						.getString(4)));
			// load results into the contact table model.
			contactModel.loadData(results);
			// retrieve meta data from the results.
			ResultSetMetaData mtd = results.getMetaData();
			// load meta data into the contact table model to create the column
			// names
			contactModel.loadColumnNames(mtd);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Unnable to retrieve all contacts!", "JDBC Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records!", e);
		} finally {
			close();
		}
		return contacts;
	}

	/**
	 * Inserts a new contact into the database.
	 * 
	 * @param contact
	 *            value to insert into the database.
	 * @return true if value was properly inserted, orelse false.
	 */
	private boolean insertContact(ContactBean contact) {
		boolean valid = true;
		PreparedStatement statement;
		connect();
		try {
			// preparing the statement
			statement = connection
					.prepareStatement("INSERT INTO contacts_table "
							+ "(id, first_name, last_name, address)"
							+ "VALUES (?,?,?,?)");
			// setting values in prepared statement
			statement.setString(1, null);
			statement.setString(2, contact.getfName());
			statement.setString(3, contact.getlName());
			statement.setString(4, contact.getEmail());

			// execute statement, storing number of affected rows
			int records = statement.executeUpdate();
			// if more or fewer then 1 row had been affected, something failed.
			if (records != 1)
				valid = false;
			Statement getLast = connection.createStatement();
			// querying the database to get the id of the last value inserted.
			ResultSet row = getLast.executeQuery("SELECT LAST_INSERT_ID()");
			row.next();
			// inserts the contact into the contact table model
			contactModel
					.insertContact(new ContactBean("" + row.getInt(1), contact
							.getfName(), contact.getlName(), contact.getEmail()));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Unable to insert contact!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Unable to insert record!", e);
			valid = false;
		} finally {
			close();
		}
		return valid;
	}

	/**
	 * Saves a contact to the database, either by updating it, or inserting if
	 * it doesn't exist.
	 * 
	 * @param contact
	 *            value to save.
	 * @return true if the value was saved properly, or else false.
	 */
	public boolean saveContact(ContactBean contact) {
		boolean valid = false;
		if (contact != null) {
			if (contact.getId().equals("-1"))
				valid = insertContact(contact);
			else
				valid = updateContact(contact);
		}
		return valid;
	}

	/**
	 * Updates a contact in the database
	 * 
	 * @param contact
	 *            value to update.
	 * @return true if the contact was properly updated, or else false.
	 */
	private boolean updateContact(ContactBean contact) {
		boolean valid = true;
		if (contact != null && !contact.getfName().trim().equals("")
				&& contact.getEmail().trim().equals("")) {
		PreparedStatement statement = null;
		connect();
		try {
			// preparing SQL statement
			String update = "UPDATE contacts_table SET first_name='"
					+ contact.getfName() + "', last_name='"
					+ contact.getlName() + "', address='" + contact.getEmail()
					+ "' WHERE id =" + contact.getId();
			statement = connection.prepareStatement(update);
			// executing statement, storing updated records.
			int records = statement.executeUpdate();
			// if more or less than one row has been updated, something failed.
			if (records != 1)
				valid = false;
			// update changes to the contact table model.
			contactModel.updateContact(contact.getId());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cannot update contact!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Cannot update record!", e);
			valid = false;
		} finally {
			close();
		}
		}
		return valid;
	}

	/**
	 * Deletes the contact from the database.
	 * 
	 * @param contact
	 *            value to be removed from the database.
	 * @return true if the value was properly updated, or else false.
	 */
	public boolean deleteContact(ContactBean contact) {
		boolean valid = true;
		PreparedStatement statement = null;
		if (contact != null & !contact.getId().equals("-1")) {
			connect();
			try {
				// preparing the SQL statement
				statement = connection
						.prepareStatement("DELETE FROM contacts_table WHERE id="
								+ contact.getId());
				// execute the statement, storing the affected rows.
				int records = statement.executeUpdate();
				// if more or less than 1 row has been affected, something
				// failed.
				if (records != 1)
					valid = false;
				// update changes to the contact table model.
				contactModel.deleteContact(contact.getId());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Cannot delete contact!",
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Cannot update record!", e);
				valid = false;
			} finally {
				close();
			}
		}
		return valid;
	}

	// * FOLDER ACCESS METHODS \\*
	/**
	 * Gets all folders in the database.
	 * 
	 * @return List containing all folders in the database.
	 */
	public List<String> getAllFolders() {
		List<String> folders = new ArrayList<String>();
		Statement statement = null;
		ResultSet results = null;
		connect();
		try {
			statement = connection.createStatement();
			// executes the query for the data in the table 'folders_table'
			results = statement.executeQuery("SELECT * FROM folders_table");
			// while there are still results, they are added to the List
			while (results.next()) {
				folders.add(results.getString(2));

			}
			// load the data into the folder table model.
			folderModel.loadData(results);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Unnable to retrieve all folders!", "JDBC Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records!", e);
		} finally {
			close();
		}
		return folders;
	}

	/**
	 * Inserts a folder into the database.
	 * 
	 * @param name
	 *            value of the folder to be inserted
	 * @return true if folder was properly inserted, or else false.
	 */
	public boolean insertFolder(String name) {
		boolean valid = true;
		PreparedStatement statement;
		connect();
		try {
			// preparing statement
			statement = connection
					.prepareStatement("INSERT INTO folders_table (id, folder) VALUES (?,?)");
			// inserting values into statement
			statement.setString(1, null);
			statement.setString(2, name);
			// execute statement, storing rows affected.
			int records = statement.executeUpdate();
			// if more or less than 1 rows were affected, something failed.
			if (records != 1)
				valid = false;
			Statement getLast = connection.createStatement();
			// retrieving the id of the last record inserted.
			ResultSet row = getLast.executeQuery("SELECT LAST_INSERT_ID()");
			row.next();
			// inserting the folder into the folder table model.
			folderModel.insertFolder(name);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Unable to insert folder!",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Unable to insert record!", e);
			valid = false;
		} finally {
			close();
		}
		return valid;
	}

	/**
	 * Deletes the select folder from the database
	 * 
	 * @param name
	 *            value of the folder to delete
	 * @return true if the folder was properly deleted, or else false.
	 */
	public boolean deleteFolder(String name) {
		boolean valid = true;
		PreparedStatement statement = null;
		if (!name.equals("")) {
			connect();
			try {
				// preparing the SQL statement.
				statement = connection
						.prepareStatement("SELECT * FROM folders_table WHERE folder='"
								+ name + "'");
				// executing the statement, checking if the folder exists.
				boolean records = statement.execute();
				// if the folder exists, it is deleted.
				if (records) {
					statement = connection
							.prepareStatement("DELETE FROM folders_table WHERE folder='"
									+ name + "'");
					int rows = statement.executeUpdate();
					if (rows != 1)
						valid = false;
					// updates the change tot he model.
					folderModel.deleteFolder(name);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Cannot delete contact!",
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Cannot update record!", e);
				valid = false;
			} finally {
				close();
			}
		} else
			JOptionPane.showMessageDialog(null,
					"You must enter a folder to delete!");
		return valid;
	}

	/**
	 * Used to get a handle to the ContactTableModel
	 * 
	 * @return a reference to the ContableTableModel
	 */
	public ContactTableModel getContactModel() {
		return contactModel;
	}

	/**
	 * Used to get a handle to the MailTableModel
	 * 
	 * @return a reference to the MailTableModel
	 */
	public MailTableModel getMailModel() {
		return mailModel;
	}

	/**
	 * Used to get a handle to the FolderTableModel
	 * 
	 * @return a reference to the FolderTableModel
	 */
	public FolderTableModel getFolderModel() {
		return folderModel;
	}
}
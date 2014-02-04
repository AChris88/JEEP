package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import beans.ContactBean;
import beans.MailBean;
import db.ContactTableModel;
import db.DBManager;
import db.FolderTableModel;
import db.MailTableModel;

public class JUnitDATesting {
	
	public JUnitDATesting(){}

	@Test
	public void checkEmptyMail() {
		DBManager manager = new DBManager();
		manager.getAllMail();
		assertTrue(manager.getMailModel().getRowCount()==0);
	}

	@Test
	public void checkInsertMail() {
		DBManager manager = new DBManager();
		assertTrue(manager.saveMail(new MailBean("null","test1@fake.com","pisco_ssour@hotmail.com",
				  "testSubject","","","11/12/2012",
				  "testMessage","Draft")));
	}
	
	@Test
	public void checkIfMail() {
		DBManager manager = new DBManager();
		manager.getAllMail();
		assertTrue(manager.getMailModel().getRowCount()!=0);
	}
	
	@Test
	public void checkEmptyContacts() {
		DBManager manager = new DBManager();
		manager.getAllContacts();
		assertTrue(manager.getContactModel().getRowCount()==0);
	}
	

	@Test
	public void checkInsertContact() {
		DBManager manager = new DBManager();
		assertTrue(manager.saveContact(new ContactBean("-1","Chris","Allard",
				  "allardchris88@gmail.com")));
	}
	
	
	@Test
	public void checkIfContacts() {
		DBManager manager = new DBManager();
		manager.getAllContacts();
		assertTrue(manager.getContactModel().getRowCount()!=0);
	}
	

	@Test
	public void checkUpdateContacts() {
		ContactTableModel model = new ContactTableModel();
		DBManager manager = new DBManager();
		manager.getAllContacts();
		assertTrue(manager.saveContact(new ContactBean("1","Sponge","BobSquarePants",
				  "allardchris88@gmail.com")));
	}
	
	@Test
	public void checkIfFolders() {
		DBManager manager = new DBManager();
		manager.getAllFolders();
		assertTrue(manager.getFolderModel().getRowCount()!=0);
	}
	

	@Test
	public void checkGetMail() {
		DBManager manager = new DBManager();
		manager.getAllMail();
		assertTrue(manager.getMailModel().getRowCount()!=0);
	}

	@Test
	public void checkGetMailByEmail(){
		DBManager manager = new DBManager();
		manager.getMailByAddressOrDate(false, "pisco_ssour@hotmail.com");
		assertTrue(manager.getMailModel().getRowCount()!=0);
	}
	
	@Test
	public void checkGetMailByDate(){
		DBManager manager = new DBManager();
		manager.getMailByAddressOrDate(true, "11/12/2012");
		assertTrue(manager.getMailModel().getRowCount()!=0);
	}
	
	
	@Test
	public void checkGetContact() {
		DBManager manager = new DBManager();
		manager.getAllContacts();
		assertTrue(manager.getContactModel().getContactBean(0).getId().equals("1") &&
				   manager.getContactModel().getContactBean(0).getfName().equals("Sponge") &&
				   manager.getContactModel().getContactBean(0).getlName().equals("BobSquarePants") &&
				   manager.getContactModel().getContactBean(0).getEmail().equals("allardchris88@gmail.com"));
	}
	
	@Test
	public void checkGetFolder() {
		DBManager manager = new DBManager();
		manager.getAllFolders();
		assertTrue(manager.getFolderModel().getFolder(0).equals("Inbox"));
	}

	@Test
	public void checkDeleteMail(){
		DBManager manager = new DBManager();
		manager.getAllMail();
		assertTrue(manager.updateMail(new MailBean("1","","","","","","","","Inbox")));
	}
	
	@Test
	public void checkDeleteContact(){
		DBManager manager = new DBManager();
		manager.getAllContacts();
		assertTrue(manager.deleteContact(new ContactBean("1","","","")));
	}
}
package testing;
/**
 * Test "suite" created to run JUnit tests in the proper order.
 * @author Slaiy
 *
 */
public class TestSuite {
	public static void main(String[] args) {
		JUnitDATesting test = new JUnitDATesting();
		
		System.out.println("----- Test Cycle Beginning -----");
		
		test.checkEmptyMail();
		System.out.println("checkEmptyMail DONE!");
		
		test.checkInsertMail();
		System.out.println("checkInsertMail DONE!");
		
		test.checkIfMail();
		System.out.println("checkIfMail DONE!");
		
		test.checkEmptyContacts();
		System.out.println("checkEmptyContacts DONE!");
		
		test.checkInsertContact();
		System.out.println("checkInsertContacts DONE!");
		
		test.checkIfContacts();
		System.out.println("checkIfContacts DONE!");
		
		test.checkUpdateContacts();
		System.out.println("checkUpdateContacts DONE!");
		
		test.checkIfFolders();
		System.out.println("checkIfFolders DONE!");
		
		test.checkGetMail();
		System.out.println("checkGetMail DONE!");
		
		test.checkGetMailByEmail();
		System.out.println("checkGetMailByEmail DONE!");
		
		test.checkGetMailByDate();
		System.out.println("checkGetMailByDate DONE!");
		
		test.checkGetContact();
		System.out.println("checkGetContacts DONE!");
		
		test.checkGetFolder();
		System.out.println("checkGetFolder DONE!");
		
		test.checkDeleteMail();
		System.out.println("checkDeleteMail DONE!");
		
		test.checkDeleteContact();
		System.out.println("checkDeleteContact DONE!");
		
		System.out.println("----- Test Cycle Sucessfully Completed -----");
		
		System.exit(0);
	}
}
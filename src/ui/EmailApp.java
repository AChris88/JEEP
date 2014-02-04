package ui;

/**
 * Class to launch the e-mail client.
 * 
 * ------------KNOWN ISSUES------------
 * 1. Images link in Help menu are broken.
 * 2. Drag and drop can detect valid drop locations, but update doesn't trigger.
 * 3. Was not able to fix my issue my RegEx's displaying my JFormattedTextFields
 * 	  so I disabled any RegEx/Mask validation to make the app. easier to handle when testing.
 * 4. Localization is practically non-existent. I started at the beginning of the project and
 *    never got back to it in time.
 * 5. Contacts windows doens't update on Contact delete, thought it does for Contact add..
 
 * @author Slaiy
 */
public class EmailApp {
	public static void main(String[] args) {
		ClientFrame frame = new ClientFrame("E-mail");
		frame.setVisible(true);
	}
}
package ui;

/**
 * Class to launch the e-mail client.
 * 
 * ------------KNOWN ISSUES------------
 * 1. Drag and drop can detect valid drop locations, but update doesn't trigger.
 * 2. Regex config.
 */
public class EmailApp {
	public static void main(String[] args) {
		ClientFrame frame = new ClientFrame("E-mail");
		frame.setVisible(true);
	}
}
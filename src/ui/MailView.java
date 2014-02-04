package ui;

import javax.swing.JEditorPane;

/**
 * Area used to display the mail's message.
 * @author Slaiy
 */
public class MailView extends JEditorPane{
	private static final long serialVersionUID = 1L;
	public MailView(){
		super();
		this.setContentType("text/html");
		this.setEditable(false);
	}
}
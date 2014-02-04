package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import db.DBManager;

/**
 * Class extending JTree to display the mail folders
 * @author Slaiy
 *
 */
public class MailTree extends JTree{
	private static final long serialVersionUID = 1L;
	private DefaultMutableTreeNode top = new DefaultMutableTreeNode();
	private DBManager manager=null;
	
	/**
	 * Constructor setting the top node and the DBManager.
	 * @param top
	 * @param manager
	 */
	public MailTree(DefaultMutableTreeNode top,DBManager manager) {
		super(top);
		this.top=top;
		this.manager=manager;
		initialize();
	}
		
	/**
	 * Initializing and setting the GUI components.
	 */
	private void initialize(){
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        makeTreePopupMenu();
	}
	
	/**
	 * Makes the popup menu on right-click events.
	 */
	private void makeTreePopupMenu() {
		JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        
        menuItem = new JMenuItem("Create Folder");
        //adds ActionListener to "Create Folder" option
        menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String folderName = JOptionPane.showInputDialog(null, "Please enter the name of the folder to be created.");
				manager.insertFolder(folderName);
				top.add(new DefaultMutableTreeNode(folderName));
			}});
        popup.add(menuItem);
        
        menuItem = new JMenuItem("Delete Folder");
      //adds ActionListener to "Delete Folder" option
        menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String folderName = JOptionPane.showInputDialog(null, "Please enter the name of the folder to be deleted.\n"+
																	  "NOTE: The folder must be empty before it can be deletted!");
				manager.deleteFolder(folderName);
			}});
        popup.add(menuItem);
        MouseListener popupListener= new PopupListener(popup);
        addMouseListener(popupListener);
	}
	
	/**
	 * MouseAdapter to listen to pop-up events. 
	 */
	 class PopupListener extends MouseAdapter {
	        JPopupMenu popup;

	        public PopupListener(JPopupMenu popupMenu) {
	            popup = popupMenu;
	        }

	        public void mousePressed(MouseEvent e) {
	            maybeShowPopup(e);
	        }

	        public void mouseReleased(MouseEvent e) {
	            maybeShowPopup(e);
	        }

	        private void maybeShowPopup(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	                popup.show(e.getComponent(),
	                           e.getX(), e.getY());
	            }
	        }
	    }
}
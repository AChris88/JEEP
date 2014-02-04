package ui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import db.DBManager;

/**
 * JPanel containing the MailTree to display the mail folders.
 * @author Slaiy
 *
 */
@SuppressWarnings("serial")
public class MailTreePanel extends JPanel implements TreeSelectionListener{
	private MailTree tree=null;
	private JScrollPane pane=null;
	private DBManager manager;
	
	/**
	 * Constructor setting the DBManager.
	 * @param manager
	 */
	public MailTreePanel(DBManager manager){
		super(new BorderLayout());
		this.manager = manager;
		initialize();
	}
	
	/**
	 * Initializes and sets GUI components.
	 */
	private void initialize(){
		pane = new JScrollPane(tree);
		add(pane,BorderLayout.CENTER);
		List<String> folders = manager.getAllFolders();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Mail");
		int size = folders.size();
		for(int i =0; i< size;i++)
			top.add(new DefaultMutableTreeNode(folders.remove(0)));
		tree = new MailTree(top,manager);
		tree.addTreeSelectionListener(this);
		tree.setTransferHandler(new JTreeTransferHandler(tree, manager.getFolderModel(), manager));
		add(new JScrollPane(tree));
	}
	
	/**
	 * Performed when a different node on the tree is selected.
	 */
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		String path = arg0.getPath().toString();
		String folder = path.substring(path.indexOf(",")+2,path.length()-1);
		if(!folder.equals("Mail"))
			manager.getMailByFolder(folder);
		else
			manager.getAllMail();
		}
	}

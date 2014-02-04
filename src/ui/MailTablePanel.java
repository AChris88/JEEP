package ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import db.DBManager;
import db.MailTableModel;

/**
 * Panel containing the MailTable to display mail.
 * @author Slaiy
 *
 */
public class MailTablePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private MailTable table;
	private MailView view;
	private DBManager manager;
	private ClientFrame client =null;
	
	/**
	 * Default constructor
	 */
	public MailTablePanel(){
		super();
	}
	
	/**
	 * Constructor setting the DBManager, MailView, and ClientFrame
	 * @param manager DBManager to set
	 * @param view MailView to set
	 * @param client ClientFrame to set
	 */
	public MailTablePanel(DBManager manager, MailView view, ClientFrame client) {
		this();
		this.view=view;
		this.manager=manager;
		this.client=client;
		this.setLayout(new BorderLayout());
		initialize();
	}
	
	/**
	 * Initializes and sets the MailTable
	 */
	private void initialize(){
		manager.getAllMail();
		table = new MailTable(manager);
		table.setDragEnabled(true);
		table.setTransferHandler(new JTableTransferHandler(table));
		//add ActionListener to the mail table
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable)e.getSource();
				int row = source.getSelectedRow();
				if(row>=0){
					view.setText(((MailTableModel)source.getModel()).getMailBean(row).getMessage());
					client.mailSelectButtons();
				}
			}});
		JTableTransferHandler xfer = new JTableTransferHandler(table);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setAutoscrolls(true);
		add(scroll, BorderLayout.CENTER);
	}
	
	/**
	 * Returns a handle to the mail table
	 * @return reference to the mail table.
	 */
	public JTable getTable() {
		return table;
	}
}
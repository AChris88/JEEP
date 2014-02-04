package ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import db.DBManager;

/**
 * Table displaing mail in the MailTableModel
 * @author Slaiy
 *
 */
public class MailTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public MailTable(){}
	
	/**
	 * Constructor setting the DBManager
	 * @param manager DBManager to set.
	 */
	public MailTable(DBManager manager){
		setModel(manager.getMailModel());
		initialize();
	}
	
	/**
	 * Setting table settings 
	 */
	private void initialize() {
		setAutoCreateColumnsFromModel(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		setPreferredScrollableViewportSize(getPreferredSize());
		setFillsViewportHeight(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
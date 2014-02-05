package db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import beans.ContactBean;

/**
 * Class extending AbstractTableModel to be used in displaying e-mail contacts
 * @author Slaiy
 */
public class ContactTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Vector<String> colNames = new Vector<String>();
	private Vector<ContactBean> data = new Vector<ContactBean>();
	private Vector<Integer> doUpdate = new Vector<Integer>();
	
	/**
	 * returns the number of columns in the model.
	 */
	@Override
	public int getColumnCount() {
		return colNames.size();
	}

	/**
	 * return the number of rows in the model.
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * return an Object value at the specified row/column intersection	
	 */
	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
			case 0:
				return data.elementAt(row).getfName();
			case 1:
				return data.elementAt(row).getlName();
			case 2:
				return data.elementAt(row).getEmail();
		}
		throw new IllegalArgumentException("Cannot retrieve from selected field");
	}
	
	/**
	 * sets an Object value at the specified row/column intersection
	 */
	public void setValueAt(Object value, int row, int col){
		switch(col){
		case 0:
			break;
		case 1:
			data.elementAt(row).setId((String)value);
			break;
		case 2:
			data.elementAt(row).setfName((String)value);
			break;
		case 3:
			data.elementAt(row).setlName((String)value);
			break;
		case 4:
			data.elementAt(row).setEmail((String)value);
			break;
		}
		fireTableCellUpdated(row, col);
		doUpdate.addElement(row);
	}
	
	/**
	 * Loads the data received in the ResultSet into the model.
	 * @param resultSet data to load into the model
	 * @return number of rows in the ResultSet
	 */
	public int loadData(ResultSet resultSet) {
		int rowCount = 0;
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				rowCount++;
				data.addElement(new ContactBean(resultSet.getString(1), resultSet
						.getString(2), resultSet.getString(3), resultSet.getString(4)));
			}
			fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	
	/**
	 * Loads column names received in the ResultSetMetaData
	 * @param rsmd meta data containing column names.
	 * @return number of columns in the meta data.
	 */
	public int loadColumnNames(ResultSetMetaData rsmd) {
		int colCount = 0;
		try {
			colCount = rsmd.getColumnCount();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		colNames.addElement("First Name");
		colNames.addElement("Last Name");
		colNames.addElement("E-mail");
		return colCount;
	}
	
	/**
	 * Returns a handle to the contact bean at the specified index.
	 * @param row index to retrieve a contact from
	 * @return contact stored at the specified index.
	 */
	public ContactBean getContactBean(int row) {
		return data.elementAt(row);
	}
	
	/**
	 * Deletes the contact with the specified id
	 * @param id value to search for and remove.
	 */
	public void deleteContact(String id){
		for(int i= 0;i < data.size(); i++)
			if(data.elementAt(i).getId().equals(id)){
				data.remove(i);
				break;
			}		
		fireTableDataChanged();
	}
	
	/**
	 * Inserts the specified contact into the database.
	 * @param contact value to insert into the database.
	 */
	public void insertContact(ContactBean contact){
		data.add(contact);
		fireTableDataChanged();
	}
	
	/**
	 * Updates the specified contact.
	 * @param contactId id value of the contact to search for and update.
	 */
	public void updateContact(String contactId){
		int ctr=0;
		do{
			if(data.elementAt(ctr).getId().equals(contactId)){
				doUpdate.add(ctr);
				ctr = data.size();
			}
			ctr++;
		}
		while(ctr<data.size()-1);
		fireTableDataChanged();
	}
	 
	/**
	 * Returns the name of the column at the specified index.
	 */
	@Override
	public String getColumnName(int column){
		return colNames.get(column);
	}
}
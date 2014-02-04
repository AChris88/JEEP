package db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import beans.MailBean;

public class MailTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Vector<String> columnNames = new Vector<String>();
	private Vector<MailBean> data = new Vector<MailBean>();
		
	/**
	 * returns the number of columns in the model.
	 */
	@Override
	public int getColumnCount() {
		return columnNames.size();
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
				return data.elementAt(row).getFrom();
			case 1:
				return data.elementAt(row).getSubject();
			case 2:
				return data.elementAt(row).getDate();
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
			data.elementAt(row).setTo((String)value);
			break;
		case 2:
			data.elementAt(row).setFrom((String)value);
			break;
		case 3:
			data.elementAt(row).setSubject((String)value);
			break;
		case 4:
			data.elementAt(row).setCc((String)value);
			break;
		case 5:
			data.elementAt(row).setBcc((String)value);
			break;
		case 6:
			data.elementAt(row).setDate((String)value);
			break;
		case 7:
			data.elementAt(row).setMessage((String)value);
			break;
		case 8:
			data.elementAt(row).setFolder((String)value);
			break;
		}
		fireTableCellUpdated(row, col);
	}
	
	/**
	 * Loads the data received in the ResultSet into the model.
	 * @param resultSet data to load into the model
	 * @return number of rows in the ResultSet
	 */
	public int loadData(ResultSet resultSet) {
		int rowCount = 0;
		data=new Vector<MailBean>();
		try {
			if(resultSet.first()){
				resultSet.beforeFirst();
				while (resultSet.next()) {
					rowCount++;
					data.addElement(new MailBean(resultSet.getString(1), resultSet.getString(2),
							 resultSet.getString(3), resultSet.getString(4),
							 resultSet.getString(5), resultSet.getString(6), 
							 resultSet.getString(7), resultSet.getString(8),
							 resultSet.getString(9)));
				}
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
		int colCount=0;
		try {
			colCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		columnNames.addElement("Sender");
		columnNames.addElement("Subject");
		columnNames.addElement("Date Received");
		return colCount;
	}
	
	/**
	 * Inserts the specified mail into the database.
	 * @param mail value to insert into the database.
	 */
	public void insertMail(MailBean mail){
		data.addElement(mail);
		fireTableDataChanged();
	}
	
	/**
	 * Deletes the mail with the specified id
	 * @param id value to search for and remove.
	 */
	public void removeMail(String id){
		int ctr=0;
		do{
			if(data.elementAt(ctr).getId().equals(id)){
				data.removeElementAt(ctr);
				ctr = data.size();
			}
			ctr++;
		}
		while(ctr < data.size()-1);
		fireTableDataChanged();
	}
	
	/**
	 * Returns the name of the column at the specified index.
	 */
	@Override
	public String getColumnName(int column){
		return columnNames.get(column);
	}
	
	/**
	 * Returns the data type in the specified column.
	 */
	@Override
	public Class<? extends Object> getColumnClass(int colIndex){
		switch(colIndex){
		case 0:
			return data.get(0).getId().getClass();
		case 1:
			return data.get(0).getTo().getClass();
		case 2:
			return data.get(0).getFrom().getClass();
		case 3:
			return data.get(0).getSubject().getClass();
		case 4:
			return data.get(0).getCc().getClass();
		case 5:
			return data.get(0).getBcc().getClass();
		case 6:
			return data.get(0).getDate().getClass();
		case 7:
			return data.get(0).getMessage().getClass();
		case 8:
			return data.get(0).getFolder().getClass();
		}
		return null;		
	}
	
	/**
	 * returns a reference to the MailBean at the specified index.
	 * @param row index value to retrieve from.
	 * @return MailBean stored at specified index.
	 */
	public MailBean getMailBean(int row) {
		return data.elementAt(row);
	}

	/**
	 * Updates the mail element with the specified id.
	 * @param contactId id values to search for and update.
	 */
	public void updateMail(String contactId){
		int ctr=0;
		do{
			if(data.elementAt(ctr).getId().equals(contactId)){
				//This is a cheap hack to fix a last-minute bug where my GUI wouldn't
				//update properly when a message was being updated.
				//(ie, clicking Delete would update a message's folder value from "Inbox" to "Deleted")
				data.removeElementAt(ctr);
				ctr = data.size();
			}
			ctr++;
		}
		while(ctr<data.size()-1);
		fireTableDataChanged();
	}
}
package db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 * Class extending AbstractTableModel to be used in displaying e-mail folders
 * @author Slaiy
 */
public class FolderTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Vector<String> colNames = new Vector<String>();
	private Vector<String> data = new Vector<String>();
	
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
		/*
		return null;
		switch(col){
			case 0:
				return data.elementAt(row).getId();
			case 1:
				return data.elementAt(row).getTo();
			case 2:
				return data.elementAt(row).getFrom();
			case 3:
				return data.elementAt(row).getSubject();
			case 4:
				return data.elementAt(row).getCc();
			case 5:
				return data.elementAt(row).getBcc();
			case 6:
				return data.elementAt(row).getDate();
			case 7:
				return data.elementAt(row).getMessage();
			case 8:
				return data.elementAt(row).getFolder();
		}
		*/
		throw new IllegalArgumentException("Cannot retrieve from selected field");
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
				data.addElement(resultSet.getString(2));
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
			for (int i = 1; i <= colCount; ++i)
				colNames.addElement(rsmd.getColumnName(i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colCount;
	}
	
	/**
	 * Returns a handle to the folder at the specified index.
	 * @param row index to retrieve a folder name from
	 * @return folder name stored at the specified index.
	 */
	public String getFolder(int row) {
		return data.elementAt(row);
	}
	
	/**
	 * Deletes the contact with the specified name
	 * @param name value to search for and remove.
	 */
	public void deleteFolder(String name) {
        int ctr=0;
        do{
                if(data.equals(name)){
                        data.removeElementAt(ctr);
                        ctr = data.size()-1;
                }
                ctr++;
        }
        while(ctr < data.size()-1);
        fireTableDataChanged();
	}
	
	/**
	 * Inserts the specified folder name into the database.
	 * @param folder name value to insert into the database.
	 */
	public void insertFolder(String name){
	    data.add(name);
	    fireTableDataChanged();
	}
}
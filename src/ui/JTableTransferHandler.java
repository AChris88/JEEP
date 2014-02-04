package ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class JTableTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;
	private final DataFlavor localObjectFlavor = new ActivationDataFlavor(
			Integer.class, DataFlavor.javaJVMLocalObjectMimeType,
			"MailBean");
	private JTable table = null;

	/**
	 * Constructor setting the table to the one sent in.
	 * @param table
	 */
	public JTableTransferHandler(JTable table) {
		this.table = table;
	}

	/**
	 * Creates and returns transferable. 
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		assert (c == table);
		return new DataHandler(new Integer(table.getSelectedRow()),
				localObjectFlavor.getMimeType());
	}

	/**
	 * Returns source action
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY;
	}
}
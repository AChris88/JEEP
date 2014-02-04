package ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.activation.ActivationDataFlavor;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import beans.MailBean;

import db.DBManager;
import db.FolderTableModel;
import db.MailTableModel;

public class JTreeTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;
	protected DefaultTreeModel tree;
	private FolderTableModel tableModel;
	private DBManager manager;

	private final DataFlavor localObjectFlavor = new ActivationDataFlavor(
			Integer.class, DataFlavor.javaJVMLocalObjectMimeType,
			"MailBean");

	/**
	 * Creates a JTreeTransferHandler to handle the mail tree.
	 * @param tree
	 */
	public JTreeTransferHandler(JTree tree, FolderTableModel tableModel,
			DBManager dbManager) {
		super();
		this.tree = (DefaultTreeModel) tree.getModel();
		this.tableModel = tableModel;
		this.manager = dbManager;
	}

	/**
	 * Returns source action.
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.NONE;
	}

	/**
	 * Verify that the data dropped is the DataFlavor (Type) expected
	 * 
	 * @param supp
	 * @return
	 */
	@Override
	public boolean canImport(TransferSupport supp) {
		// highlights the drop target
		supp.setShowDropLocation(true);
		if (supp.isDataFlavorSupported(localObjectFlavor)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean importData(TransferSupport supp) {
		System.out.println("inImportData");
		if (this.canImport(supp)) {
			System.out.println("inIf");
			try {
				System.out.println("inTry");
				// Fetch the data to transfer
				Transferable t = supp.getTransferable();
				System.out.println("Transferbale set");
				int theRow = (Integer) t.getTransferData(localObjectFlavor);
				System.out.println("the row set");
				// Fetch the drop location
				TreePath loc = ((javax.swing.JTree.DropLocation) supp
						.getDropLocation()).getPath();
			//  blocks on manager.getMailModel().getMailBean(theRow).getId() 
				manager.updateMail(new MailBean(""+manager.getMailModel().getMailBean(theRow).getId(),"","","","","","","",loc.getLastPathComponent().toString().trim()));
				return true;
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}

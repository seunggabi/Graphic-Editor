package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import constant.GEConstant;
import constant.GEConstant.EFileMenuItem;
import entity.GEModel;
import shapes.GEShape;

public class GEFileMenu extends GEMenu {
	private static final long serialVersionUID = 1L;
	private Vector<JMenuItem> menuItems;
	private ActionHandler actionHandler;
	private String currentDirectory;
	private String fileName;

	public GEFileMenu() {
		try {
			currentDirectory = (String) GEModel.open(".\\config\\currentDirectory.conf");
		} catch (ClassNotFoundException | IOException e) {
			currentDirectory = GEConstant.SCURRENTDIRECTORY;
		}

		menuItems = new Vector<JMenuItem>();
		for (EFileMenuItem eMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(eMenuItem.getMenuItemName());
			menuItems.add(menuItem);
			this.add(menuItem);

			actionHandler = new ActionHandler();
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(menuItem.getName());
		}
	}

	public void shutdownSave() {
		try {
			GEModel.save(".\\config\\currentDirectory.conf", currentDirectory);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void saveAs() {
		fileName = null;
		GEFileChooser chooser = new GEFileChooser(currentDirectory);
		int returnVal = chooser.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory = chooser.getSelectedFile().getParent();
			fileName = chooser.getSelectedFile().getName();
			frame.getPanel().save(currentDirectory + '\\' + fileName + "." + GEConstant.SFILEEXTENSION);
		}
	}

	public int saveOrNot(String msg) {
		GEOptionDialog optionDialog = new GEOptionDialog();
		optionDialog.init(frame);
		optionDialog.setTitle(GEConstant.SDIALOGNAME);
		optionDialog.setMessage(msg);

		int status = 0;
		status = optionDialog.resultDialog();
		return status;
	}

	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GEFileChooser chooser = null;
			chooser = new GEFileChooser(currentDirectory);

			if (e.getActionCommand().equals(EFileMenuItem.Exit.getMenuItemName())) {
				GEOptionDialog optionDialog = new GEOptionDialog();
				optionDialog.init(frame);
				optionDialog.setTitle(GEConstant.SDIALOGNAME);
				optionDialog.setMessage(GEConstant.SCLOSE);

				int status = 0;
				status = optionDialog.resultDialog();
				if (status == JOptionPane.YES_OPTION) {
					shutdownSave();
					System.exit(0);
				}
			} else if (e.getActionCommand().equals(EFileMenuItem.Print.getMenuItemName())) {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(frame);
				if (job.printDialog()) {
					try {
						job.print();
					} catch (Exception exception) {
					}
				}
			} else if (e.getActionCommand().equals(EFileMenuItem.Save.getMenuItemName())) {
				if (frame.getPanel().isbUpdated() && fileName != null) {
					frame.getPanel().save(currentDirectory + '\\' + fileName);
				} else if (!frame.getPanel().isbUpdated() && fileName != null) {
				} else {
					saveAs();
				}
			} else if (e.getActionCommand().equals(EFileMenuItem.SaveAs.getMenuItemName())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenuItem.Open.getMenuItemName())) {
				fileName = null;
				GEOptionDialog optionDialog = new GEOptionDialog();
				optionDialog.init(frame);
				optionDialog.setTitle(GEConstant.SDIALOGNAME);
				optionDialog.setMessage(GEConstant.SOPEN);

				int status = 0;
				if (frame.getPanel().isbUpdated()) {
					status = optionDialog.resultDialog();
				}
				if (!frame.getPanel().isbUpdated() || status == JOptionPane.YES_OPTION) {
					int returnVal = chooser.showOpenDialog(frame);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						currentDirectory = chooser.getSelectedFile().getParent();
						fileName = chooser.getSelectedFile().getName();
						frame.getPanel().open(currentDirectory + '\\' + fileName);
					}
				}
			} else if (e.getActionCommand().equals(EFileMenuItem.New.getMenuItemName())) {
				if (frame.getPanel().isbUpdated()) {
					int reply = saveOrNot(GEConstant.SNEW);
					if (reply == JOptionPane.CANCEL_OPTION) {
					} else {
						if (reply == JOptionPane.OK_OPTION) {
							saveAs();
						}
						frame.getPanel().setShapes(new Vector<GEShape>());
						frame.paint(frame.getGraphics());
						frame.getPanel().setbUpdated(false);
					}
				}
			} else if (e.getActionCommand().equals(EFileMenuItem.Close.getMenuItemName())) {
				GEOptionDialog optionDialog = new GEOptionDialog();
				optionDialog.init(frame);
				optionDialog.setTitle(GEConstant.SDIALOGNAME);
				optionDialog.setMessage(GEConstant.SCLOSE);

				int status = 0;
				if (frame.getPanel().isbUpdated()) {
					status = optionDialog.resultDialog();
				}
				if (!frame.getPanel().isbUpdated() || status == JOptionPane.YES_OPTION) {
					frame.getPanel().setShapes(new Vector<GEShape>());
					frame.paint(frame.getGraphics());
					frame.getPanel().setbUpdated(false);
				}
			}
		}
	}
}

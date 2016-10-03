package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;

import constant.GEConstant.EEditMenuItem;

public class GEEditMenu extends GEMenu {
	private static final long serialVersionUID = 1L;
	private Vector<JMenuItem> menuItems;
	private ActionHandler actionHandler;
	public GEEditMenu() {
		menuItems = new Vector<JMenuItem>();
		for (EEditMenuItem eMenuItem : EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(eMenuItem.getMenuItemName());
			menuItems.add(menuItem);
			this.add(menuItem);
			
			actionHandler = new ActionHandler();
			menuItem.addActionListener(actionHandler);
		}
	}
	
	public void init(GEFrame frame)
	{
		super.init(frame);
	}
	private void undo() {
		this.frame.getPanel().undo();
	}
	private void redo() {
		this.frame.getPanel().redo();
	}
	public void cut() {
		this.frame.getPanel().cut();
	}
	public void paste() {
		this.frame.getPanel().paste();
	}
	public void copy() {
		this.frame.getPanel().copy();
	}
	public void delete() {
		this.frame.getPanel().delete();
	}
	public void selectAll() {
		this.frame.getPanel().selectAll();
	}
	public void deselectAll() {
		this.frame.getPanel().deselectAll();
	}
	
	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenuItem.Redo.getMenuItemName())) {
				redo();
			} else if (e.getActionCommand().equals(EEditMenuItem.Undo.getMenuItemName())) {
				undo();
			} else if (e.getActionCommand().equals(EEditMenuItem.Cut.getMenuItemName())) {
				cut();
			} else if (e.getActionCommand().equals(EEditMenuItem.Paste.getMenuItemName())) {
				paste();
			} else if (e.getActionCommand().equals(EEditMenuItem.Copy.getMenuItemName())) {
				copy();
			} else if (e.getActionCommand().equals(EEditMenuItem.Delete.getMenuItemName())) {
				delete();
			} else if (e.getActionCommand().equals(EEditMenuItem.SelectALL.getMenuItemName())) {
				selectAll();
			} else if (e.getActionCommand().equals(EEditMenuItem.DeselectAll.getMenuItemName())) {
				deselectAll();
			} else if (e.getActionCommand().equals(EEditMenuItem.UnGroup.getMenuItemName())) {
				frame.getPanel().unGroup();
			} else if (e.getActionCommand().equals(EEditMenuItem.Group.getMenuItemName())) {
				frame.getPanel().group();
			} 
		}
	}
}

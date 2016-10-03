package frames;

import java.util.Vector;

import javax.swing.JMenuItem;

import constant.GEConstant.EFontMenuItem;

public class GEFontMenu extends GEMenu {
	private static final long serialVersionUID = 1L;
	private Vector<JMenuItem> menuItems;

	public GEFontMenu() {
		menuItems = new Vector<JMenuItem>();
		for (EFontMenuItem eMenuItem : EFontMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(eMenuItem.name());
			menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
}

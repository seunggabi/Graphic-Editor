package frames;

import java.util.Vector;

import javax.swing.JMenuBar;

import constant.GEConstant.EMenu;

public class GEMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private Vector<GEMenu> menu;

	public GEMenuBar() {
		super();
		menu = new Vector<GEMenu>();
		for (EMenu eMenuBar : EMenu.values()) {
			menu.add(eMenuBar.getMenu());
		}
		for (int i = 0; i < menu.size(); i++) {
			this.add(menu.get(i));
		}
	}

	public void init(GEFrame frame) {
		for (int i = 0; i < menu.size(); i++) {
			menu.get(i).init(frame);
		}
	}
}

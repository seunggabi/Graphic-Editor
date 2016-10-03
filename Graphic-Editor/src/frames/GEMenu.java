package frames;

import javax.swing.JMenu;

public class GEMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	protected GEFrame frame;

	public void init(GEFrame frame) {
		this.frame = frame;
	}
}

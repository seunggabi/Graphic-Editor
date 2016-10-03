package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constant.GEConstant;
import constant.GEConstant.EButtons;

public class GEToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private ButtonGroup buttonGroup;
	private JRadioButton button;
	private GEPanel panel;
	private ActionHandler actionHandler;

	public GEToolBar() {
		super();
		buttonGroup = new ButtonGroup();
		actionHandler = new ActionHandler();
		this.setSize(400, 100);

		for (EButtons eButton : EButtons.values()) {
			button = new JRadioButton();
			button.setIcon(new ImageIcon(eButton.getButtonImage()));
			button.setSelectedIcon(new ImageIcon(eButton.getSelectedButtonImage()));
			button.addActionListener(actionHandler);
			button.setActionCommand(eButton.name());

			this.add(button);
			this.buttonGroup.add(button);
		}
	}

	public void init(GEPanel panel) {
		this.panel = panel;
		((JRadioButton) this.getComponent(GEConstant.EButtons.Rectangle.ordinal())).doClick();
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EButtons selectButton = EButtons.valueOf(e.getActionCommand());
			panel.setCurrentShape(selectButton.getShape());
		}
	}
}

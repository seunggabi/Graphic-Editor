package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;

import constant.GEConstant.EColorMenuItem;
import shapes.GEShape;

public class GEColorMenu extends GEMenu {
	private static final long serialVersionUID = 1L;
	private Vector<JMenuItem> menuItems;
	private ActionHandler actionHandler;

	public GEColorMenu() {
		menuItems = new Vector<JMenuItem>();
		for (EColorMenuItem eMenuItem : EColorMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(eMenuItem.getMenuItemName());
			menuItems.add(menuItem);
			this.add(menuItem);
			
			actionHandler = new ActionHandler();
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(menuItem.getName());
		}
	}
	
	class ActionHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EColorMenuItem.FillColor.getMenuItemName())) {
				Color fillColor = JColorChooser.showDialog(null, "Line Color Selection", null);
				if(fillColor != null){
					frame.getPanel().setFillColor(fillColor);
					for(GEShape s : frame.getPanel().selectShapes())
					{
						s.setFillColor(fillColor);
					}
				}
				frame.getPanel().repaint();
			}
			else if (e.getActionCommand().equals(EColorMenuItem.LineColor.getMenuItemName())) {
				Color lineColor = JColorChooser.showDialog(null, "Fill Color Selection", null);
				if(lineColor != null){
					frame.getPanel().setLineColor(lineColor);
					for(GEShape s : frame.getPanel().selectShapes())
					{
						s.setLineColor(lineColor);
					}
				}
				frame.getPanel().repaint();
			}
		}
	}
}

package frames;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JFrame;

import constant.GEConstant;

public class GEFrame extends JFrame implements Printable {
	private static final long serialVersionUID = 1L;
	private GEMenuBar menuBar;
	private GEPanel panel;
	private GEToolBar toolBar;

	public GEFrame() {
		super();
		this.menuBar = new GEMenuBar();
		this.panel = new GEPanel();
		this.toolBar = new GEToolBar();
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(this.toolBar, BorderLayout.NORTH);
		this.add(this.panel, BorderLayout.CENTER);
		setSize(GEConstant.FRAME_W, GEConstant.FRAME_H);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setJMenuBar(menuBar);
	}
	
	public void init()
	{
		panel.init();
		menuBar.init(this);
		toolBar.init(panel);
	}
	public GEPanel getPanel() {return panel;}

	@Override
	public int print(Graphics pg, PageFormat pf, int pageNum) {
		if (pageNum > 0) {
			return Printable.NO_SUCH_PAGE;
		}

		this.paint(pg);
		Graphics2D g2d = (Graphics2D) pg;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		return Printable.PAGE_EXISTS;
	}
}

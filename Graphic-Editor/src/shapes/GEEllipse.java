package shapes;

import java.awt.geom.Ellipse2D;

public class GEEllipse extends GEShape {
	private static final long serialVersionUID = 1L;
	private Ellipse2D ellipse;

	public GEEllipse() {
		super();
		this.setShape(new Ellipse2D.Double());
		ellipse = (Ellipse2D) shape;
	}
	
	@Override
	public void setPoint(int x, int y) {
		this.ellipse.setFrame(x, y, 0, 0);
	}
	@Override
	public void addPoint(int x, int y) {
		this.ellipse.setFrame(this.ellipse.getX(), this.ellipse.getY(), x-this.ellipse.getX(), y-this.ellipse.getY());
	}
	@Override
	public void movePoint(int x, int y) {
		addPoint(x, y);
	}
	@Override
	public void cloneShape() {
		ellipse = new Ellipse2D.Double(this.ellipse.getX(), this.ellipse.getY(), this.ellipse.getWidth(), this.ellipse.getHeight());
		this.setShape(ellipse);
	}
}
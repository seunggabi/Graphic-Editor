package shapes;

import java.awt.geom.Line2D;

public class GELine extends GEShape {
	private static final long serialVersionUID = 1L;
	private Line2D line;
	
	public GELine() {
		super();
		this.setShape(new Line2D.Double());
		line = (Line2D) this.shape;
	}
	
	public boolean onShape(int x, int y)
	{	
		if(isSelected())
		{
			seteSelectedAnchor(getAnchors().onAnchor(x, y));
			if (this.geteSelectedAnchor() != null)
				return true;
		}
		if(this.line.getX2() >= x && this.line.getX1() <= x)
			if(this.line.getY2() >= y && this.line.getY1() <= y)
				if(this.geteSelectedAnchor() == null)
				{
					this.seteSelectedAnchor(EAnchors.MM);
					return true;
				}
		return false;
	}
	
	@Override
	public void setPoint(int x, int y) {
		this.line.setLine(x, y, x, y);
	}
	@Override
	public void addPoint(int x, int y) {
		line.setLine(line.getX1(), line.getY1(), x, y);
	}
	@Override
	public void movePoint(int x, int y) {
		addPoint(x, y);
	}
	@Override
	public void cloneShape() {
		line = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
		this.setShape(line);
	}
}

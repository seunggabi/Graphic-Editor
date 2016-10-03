package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

public class GEGroup extends GEShape {

	private static final long serialVersionUID = 1L;
	private Vector<GEShape> shapes;
	private Rectangle groupRect;

	public GEGroup() {
		super();
		this.setShape(new Rectangle());
		this.shapes = new Vector<GEShape>();
		this.groupRect = (Rectangle) shape;
		setSelected(true);
	}
	
	public void cloneShape() {
		groupRect = new Rectangle(groupRect);
		this.setShape(groupRect);
	}

	@Override
	public void setPoint(int x, int y) {
		this.groupRect.setLocation(x, y);
	}

	@Override
	public void addPoint(int x, int y) {
		this.groupRect.setSize(x - this.groupRect.x, y - this.groupRect.y);
	}

	@Override
	public void movePoint(int x, int y) {
		addPoint(x, y);
	}
	
	public boolean onShape(int x, int y) {
		if (this.selected) {
			this.eSelectedAnchor = this.anchors.onAnchor(x, y);
			if (this.eSelectedAnchor != null)
				return true;
		}
		
		Rectangle boundingRect = null;
		for(GEShape s : this.shapes)
		{
			if(boundingRect == null)
				 boundingRect = new Rectangle(this.shapes.get(0).getShape().getBounds());
			boundingRect = boundingRect.union(s.getShape().getBounds());
		}
		if(boundingRect != null)
			setShape(boundingRect);
		if (this.shape.contains(x, y)) {
			if (this.eSelectedAnchor == null)
				this.eSelectedAnchor = EAnchors.MM;
			return true;
		}
		return false;
	}

	public boolean addShape(GEShape shape) {
		if (this.groupRect.contains(shape.getShape().getBounds())) {
			shape.setSelected(false);
			this.shapes.add(shape);
			return true;
		}
		return false;
	}
	
	public boolean isValidShape()
	{
		if(this.shapes.size() > 0)
			return true;
		return false;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setXORMode(g2D.getBackground());
		Rectangle boundingRect = null;
		
		for(GEShape s : this.shapes)
		{
			if(boundingRect == null)
				 boundingRect = new Rectangle(this.shapes.get(0).getShape().getBounds());
			s.draw(g2D);
			boundingRect = boundingRect.union(s.getShape().getBounds());
		}
		if(boundingRect != null)
			setShape(boundingRect);
		if (this.selected && boundingRect != null) {
			this.anchors.setAnchorGeo(boundingRect.x, boundingRect.y, boundingRect.width, boundingRect.height);
			this.anchors.draw(g2D);
		}
	}

	public Vector<GEShape> getShapes() {
		return this.shapes;
	}
}

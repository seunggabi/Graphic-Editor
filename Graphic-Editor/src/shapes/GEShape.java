package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Serializable;

public abstract class GEShape implements Serializable, Cloneable {
	protected Shape shape;
	private static final long serialVersionUID = 1L;

	public static enum EAnchors {
		NN, SS, EE, WW, NE, NW, SE, SW, RR, MM
	};

	protected EAnchors eSelectedAnchor;
	protected boolean selected;
	protected GEAnchors anchors;
	protected Color lineColor;
	protected Color fillColor;

	public GEShape() {
		super();
		this.shape = null;
		this.eSelectedAnchor = null;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (this.selected && this.anchors == null) {
			this.anchors = new GEAnchors();
		} else if (!this.selected) {
			this.anchors = null;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void seteSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}

	public EAnchors geteSelectedAnchor() {
		return eSelectedAnchor;
	}

	public GEAnchors getAnchors() {
		return anchors;
	}

	public void setAnchors(GEAnchors anchors) {
		this.anchors = anchors;
	}

	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setXORMode(g2D.getBackground());
		g2D.setColor(this.fillColor);
		g2D.fill(this.shape);
		if (this.lineColor != null) {
			g2D.setColor(this.lineColor);
		}
		g2D.draw(this.shape);
		if (this.selected) {
			Rectangle boundingRect = shape.getBounds();
			this.anchors.setAnchorGeo(boundingRect.x, boundingRect.y, boundingRect.width, boundingRect.height);
			this.anchors.draw(g2D);
		}
	}

	public boolean onShape(int x, int y) {
		if (this.selected) {
			this.eSelectedAnchor = this.anchors.onAnchor(x, y);
			if (this.eSelectedAnchor != null)
				return true;
		}
		if (this.shape.contains(x, y)) {
			if (this.eSelectedAnchor == null)
				this.eSelectedAnchor = EAnchors.MM;
			return true;
		}
		return false;
	}
	
	public boolean isValidShape()
	{
		Rectangle boundingRect = shape.getBounds();
		if(boundingRect.width == 0)
			return false;
		return true;
	}

	public GEShape clone() {
		try {
			GEShape s = (GEShape) super.clone();
			s.cloneShape();
			return s;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	abstract public void cloneShape();

	abstract public void setPoint(int x, int y);

	abstract public void addPoint(int x, int y);

	abstract public void movePoint(int x, int y);
}
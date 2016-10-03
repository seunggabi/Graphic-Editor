package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class GEPolygon extends GEShape {
	private static final long serialVersionUID = 1L;
	private Polygon polygon;
	private GeneralPath polyline;
	private String polylineView;
	protected AffineTransform affineTransform;

	public GEPolygon() {
		super();
		this.setShape(new Polygon());
		this.polygon = (Polygon) shape;
		this.polyline = new GeneralPath();
		polylineView = "show";
		affineTransform = new AffineTransform();
	}

	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setXORMode(g2D.getBackground());
		g2D.setColor(this.fillColor);
		g2D.fill(this.shape);
		if (this.lineColor != null) {
			g2D.setColor(this.lineColor);
		}
		g2D.draw(shape);
		if (this.selected) {
			Rectangle boundingRect = shape.getBounds();
			this.anchors.setAnchorGeo(boundingRect.x, boundingRect.y, (int) (boundingRect.width), (int) (boundingRect.height));
			this.anchors.draw(g2D);
		}
		if (polygon.npoints == 4 && polylineView.equals("show")) {
			g2D.draw(this.polyline);
			polylineView = "hide";
		} else if (polylineView.equals("hide")) {
			g2D.draw(this.polyline);
			polylineView = "none";
		}
		if (polygon.npoints > 4) {
			polyline.reset();
		}
	}

	@Override
	public void setPoint(int x, int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
		this.polyline.moveTo(x, y);
		this.polyline.lineTo(x, y);
	}

	@Override
	public void addPoint(int x, int y) {
		if (polygon.npoints == 3)
			this.polyline.lineTo(x, y);
		this.polygon.addPoint(x, y);
	}

	@Override
	public void movePoint(int x, int y) {
		this.polygon.xpoints[this.polygon.npoints - 1] = x;
		this.polygon.ypoints[this.polygon.npoints - 1] = y;
	}

	@Override
	public void cloneShape() {
		int x, y;
		polygon = new Polygon();
		for (int i = 0; i < this.polygon.npoints; i++) {
			x = this.polygon.xpoints[i];
			y = this.polygon.ypoints[i];
			this.polygon.addPoint(x, y);
		}
		this.setShape(polygon);
	}
}

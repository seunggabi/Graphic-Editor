package shapes;

import java.awt.Polygon;

public class GEHeart extends GEShape {
	private static final long serialVersionUID = 1L;
	private Polygon heart;
	private int x, y, w, h;

	public GEHeart() {
		super();
		this.setShape(new Polygon());
		heart = (Polygon) shape;
	}

	private void setHeart() {
		heart.reset();
		heart.addPoint(this.x, this.y + h / 4);
		heart.addPoint(this.x + w / 4, this.y);
		heart.addPoint(this.x + w / 2, this.y + h / 4);
		heart.addPoint(this.x + w / 4 * 3, this.y);
		heart.addPoint(this.x + w, this.y + h / 4);
		heart.addPoint(this.x + w / 2, this.y + h);
	}

	@Override
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void addPoint(int x, int y) {
		this.w = x - this.x;
		this.h = y - this.y;
		setHeart();
	}

	@Override
	public void movePoint(int x, int y) {
		addPoint(x, y);
	}

	@Override
	public void cloneShape() {
		heart = new Polygon();
		setHeart();
		this.setShape(heart);
	}
}

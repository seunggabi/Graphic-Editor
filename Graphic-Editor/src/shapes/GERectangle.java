package shapes;

import java.awt.Rectangle;

public class GERectangle extends GEShape {
	private static final long serialVersionUID = 1L;
	private Rectangle rectangle;

	public GERectangle() {
		super();
		this.setShape(new Rectangle());
		rectangle = (Rectangle) shape;
	}
	
	public void cloneShape()
	{
		rectangle = new Rectangle(rectangle);
		this.setShape(rectangle);
	}
	@Override
	public void setPoint(int x, int y) {
		this.rectangle.setLocation(x, y);
	}
	@Override
	public void addPoint(int x, int y) {
		this.rectangle.setSize(x-this.rectangle.x, y-this.rectangle.y);
	}
	@Override
	public void movePoint(int x, int y) {
		addPoint(x, y);
	}
}

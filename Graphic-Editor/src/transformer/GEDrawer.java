package transformer;

import java.awt.Graphics;

import shapes.GEShape;

public class GEDrawer extends GETransformer {

	public GEDrawer(GEShape shape) {
		super(shape);
	}

	@Override
	public void initTransforming(Graphics g, int x, int y) {
		this.getShape().setPoint(x, y);
		this.getShape().addPoint(x, y);
		this.getShape().draw(g);
	}
	@Override
	public void keepTransforming(Graphics g, int x, int y) {
		this.getShape().draw(g);
		this.getShape().movePoint(x, y);
		this.getShape().draw(g);
	}
	@Override
	public void continueTransforming(Graphics g, int x, int y) {
		this.getShape().draw(g);
		this.getShape().addPoint(x, y);
		this.getShape().draw(g);
	}
	@Override
	public void finishTransforming(Graphics g, int x, int y) {
	}
}

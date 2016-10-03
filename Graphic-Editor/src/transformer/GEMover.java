package transformer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GEGroup;
import shapes.GEShape;

public class GEMover extends GETransformer {
	
	public GEMover(GEShape shape) {
		super(shape);
	}
	
	@Override
	public void initTransforming(Graphics g, int x, int y) {
		this.oldP = new Point(x, y);
	}
	@Override
	public void keepTransforming(Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(this.anchorP.getX(), this.anchorP.getY());
		this.shape.draw(g2D);
		affineTransform.setToTranslation(x-this.oldP.x, y-this.oldP.y);
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		if (this.shape.isSelected()) {
			this.shape.getAnchors().setTransformedShape(affineTransform);
		}
		if(this.shape instanceof GEGroup){
			GEGroup groupChild = (GEGroup)this.shape;
			Vector<GEShape> shapes = groupChild.getShapes();
			for(GEShape s : shapes){
				affineTransform.setToTranslation(x-this.oldP.x, y-this.oldP.y);
				s.setShape(affineTransform.createTransformedShape(s.getShape()));
			}
		}
		this.oldP = new Point(x, y);
		this.shape.draw(g2D);
		g2D.setTransform(saveAT);
	}
	@Override
	public void continueTransforming(Graphics g, int x, int y) {
	}
	@Override
	public void finishTransforming(Graphics g, int x, int y) {
	}
}

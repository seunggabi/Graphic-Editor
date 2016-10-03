package transformer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GEGroup;
import shapes.GEShape;

public class GERotator extends GETransformer {

	private Point centerP;
	public GERotator(GEShape shape) {
		super(shape);
	}

	
	private double computeRotationAngle(Point startP, Point previousP, Point currentP) {
		double startAngle = Math.toDegrees(
				Math.atan2(startP.getX()-previousP.getX(), startP.getY()-previousP.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(startP.getX()-currentP.getX(), startP.getY()-currentP.getY()));
		double angle = startAngle-endAngle;
		if (angle<0) angle += 360;
		return angle;
	}
	@Override
	public void initTransforming(Graphics g, int x, int y) {
		this.oldP = new Point(x, y);
		centerP = new Point(
				(int)this.shape.getShape().getBounds().getCenterX(), 
				(int)this.shape.getShape().getBounds().getCenterY());
	}

	@Override
	public void keepTransforming(Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(this.anchorP.getX(), this.anchorP.getY());
		this.shape.draw(g2D);
		double rotationAngle = computeRotationAngle(centerP, oldP, new Point(x, y));
		affineTransform.setToRotation(Math.toRadians(rotationAngle), centerP.getX(), centerP.getY());
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		if (shape.isSelected()) {
			shape.getAnchors().setTransformedShape(affineTransform);
		}
		if(shape instanceof GEGroup){
			GEGroup groupChild = (GEGroup)this.shape;
			Vector<GEShape> shapes = groupChild.getShapes();
			for(GEShape s : shapes){
				double rotationGAngle = computeRotationAngle(centerP, oldP, new Point(x, y));
				affineTransform.setToRotation(Math.toRadians(rotationGAngle), centerP.getX(), centerP.getY());
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

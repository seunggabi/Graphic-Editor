package transformer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GEShape;

abstract public class GETransformer {
	protected GEShape shape;
	protected Point oldP, anchorP;
	protected AffineTransform affineTransform;
	protected Vector<GEShape> groupList;
	
	public GETransformer(GEShape shape){
		this.shape = shape;
		groupList = new Vector<GEShape>();
		oldP = new Point(0, 0);
		anchorP = new Point(0, 0);
		affineTransform =  new AffineTransform();
	}
	
	public GEShape getShape() {return shape;}
	public void setShape(GEShape shape) {this.shape = shape;}

	abstract public void initTransforming(Graphics g, int x, int y);
	abstract public void keepTransforming(Graphics g, int x, int y);
	abstract public void continueTransforming(Graphics g, int x, int y);
	abstract public void finishTransforming(Graphics g, int x, int y);
}

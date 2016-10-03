package transformer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

import shapes.GEAnchors;
import shapes.GEGroup;
import shapes.GEShape;
import shapes.GEShape.EAnchors;

public class GEResizer extends GETransformer {
	
	public GEResizer(GEShape shape) {
		super(shape);
	}
	
	private Point getResizeAnchor() {
		GEAnchors anchors = this.getShape().getAnchors();
		Point resizeAnchor = new Point();
		switch (this.getShape().geteSelectedAnchor()) { 
		case EE: resizeAnchor.setLocation(anchors.getBounds(EAnchors.WW).getX(), 	0); 	 								break;
		case WW: resizeAnchor.setLocation(anchors.getBounds(EAnchors.EE).getX(), 	0); 	 								break;
		case SS: resizeAnchor.setLocation(0, 			  							anchors.getBounds(EAnchors.NN).getY()); break;
		case NN: resizeAnchor.setLocation(0, 			  							anchors.getBounds(EAnchors.SS).getY()); break;
		case SE: resizeAnchor.setLocation(anchors.getBounds(EAnchors.NW).getX(), 	anchors.getBounds(EAnchors.NW).getY()); break;
		case NE: resizeAnchor.setLocation(anchors.getBounds(EAnchors.SW).getX(), 	anchors.getBounds(EAnchors.SW).getY()); break;
		case SW: resizeAnchor.setLocation(anchors.getBounds(EAnchors.NE).getX(), 	anchors.getBounds(EAnchors.NE).getY()); break;
		case NW: resizeAnchor.setLocation(anchors.getBounds(EAnchors.SE).getX(), 	anchors.getBounds(EAnchors.SE).getY()); break;
		default: break;
		}
		return resizeAnchor;
	}	
	private Point2D computeResizeFactor(Point previousP, Point currentP) {
		int dw = 0;
		int dh = 0;
		switch (shape.geteSelectedAnchor()) {
		case EE: dw=  currentP.x-previousP.x; 	dh=  0; 						break;
		case WW: dw=-(currentP.x-previousP.x);	dh=  0; 						break;
		case SS: dw=  0;						dh=  currentP.y-previousP.y; 	break;
		case NN: dw=  0;						dh=-(currentP.y-previousP.y); 	break;
		case SE: dw=  currentP.x-previousP.x; 	dh=  currentP.y-previousP.y;	break;
		case NE: dw=  currentP.x-previousP.x; 	dh=-(currentP.y-previousP.y);	break;
		case SW: dw=-(currentP.x-previousP.x);	dh=  currentP.y-previousP.y;	break;			
		case NW: dw=-(currentP.x-previousP.x);	dh=-(currentP.y-previousP.y);	break;
		default: break;
		}
		double cw = shape.getShape().getBounds().getWidth();
		double ch = shape.getShape().getBounds().getHeight();
		double xf = 1.0;
		double yf = 1.0;
		if (cw > 0.0)
			xf = dw / cw + xf;
		if (ch > 0.0)			
			yf = dh / ch + yf;
		
		return new Point2D.Double(xf, yf);
	}
	private Point2D computeCResizeFactor(Point previousP, Point currentP) {
		int dw = 0;
		int dh = 0;
		switch (shape.geteSelectedAnchor()) {
		case EE: dw=  currentP.x-previousP.x; 	dh=  0; 						break;
		case WW: dw=-(currentP.x-previousP.x);	dh=  0; 						break;
		case SS: dw=  0;						dh=  currentP.y-previousP.y; 	break;
		case NN: dw=  0;						dh=-(currentP.y-previousP.y); 	break;
		case SE: dw=  currentP.x-previousP.x; 	dh=  currentP.y-previousP.y;	break;
		case NE: dw=  currentP.x-previousP.x; 	dh=-(currentP.y-previousP.y);	break;
		case SW: dw=-(currentP.x-previousP.x);	dh=  currentP.y-previousP.y;	break;			
		case NW: dw=-(currentP.x-previousP.x);	dh=-(currentP.y-previousP.y);	break;
		default: break;
		}
		// compute resize 
		double cw = shape.getShape().getBounds().getWidth();
		double ch = shape.getShape().getBounds().getHeight();
		double xf = 1.0;
		double yf = 1.0;
		if (cw > 0.0)
			xf = dw / cw + xf;
		if (ch > 0.0)			
			yf = dh / ch + yf;
		
		return new Point2D.Double(xf, yf);
	}
	@Override
	public void initTransforming(Graphics g, int x, int y) {
		oldP = new Point(x, y);
		anchorP = getResizeAnchor();
		affineTransform.setToTranslation(-anchorP.getX(), -anchorP.getY());
		this.getShape().setShape(affineTransform.createTransformedShape(this.getShape().getShape()));
		if (this.getShape().isSelected()) {
			this.getShape().getAnchors().setTransformedShape(affineTransform);
		}
	}
	@Override
	public void keepTransforming(Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(anchorP.getX(), this.anchorP.getY());
		this.shape.draw(g2D);
		Point2D resizeFactor = computeResizeFactor(oldP, new Point(x, y));		
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		this.shape.setShape(affineTransform.createTransformedShape(shape.getShape()));
		if (shape.isSelected()) {
			shape.getAnchors().setTransformedShape(affineTransform);
		}
		if(shape instanceof GEGroup){
			GEGroup groupChild = (GEGroup)this.shape;
			Vector<GEShape> shapes = groupChild.getShapes();
			for(GEShape s : shapes){
				Point2D CresizeFactor = computeCResizeFactor(oldP, new Point(x, y));
				affineTransform.setToScale(CresizeFactor.getX(), CresizeFactor.getY());
				s.setShape(affineTransform.createTransformedShape(s.getShape()));
			}
		}
		oldP.setLocation(x, y);
		this.shape.draw(g2D);
		g2D.setTransform(saveAT);
	}
	@Override
	public void continueTransforming(Graphics g, int x, int y) {
	}
	@Override
	public void finishTransforming(Graphics g, int x, int y) {
		affineTransform.setToTranslation(anchorP.getX(), anchorP.getY());
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		if (this.shape.isSelected()) {
			this.shape.getAnchors().setTransformedShape(affineTransform);
		}
	}
}

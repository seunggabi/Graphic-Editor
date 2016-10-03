package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Vector;

import shapes.GEShape.EAnchors;

public class GEAnchors implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector<Rectangle> anchors;
	private final int AW = 8;
	private final int AH = 8;
	public GEAnchors(){
		this.anchors = new Vector<Rectangle>();
		for(int i=0; i<EAnchors.values().length-1; i++)
		{
			anchors.add(new Rectangle());
		}
	}
	public Rectangle getBounds(EAnchors anchor) { return anchors.get(anchor.ordinal()).getBounds();}
	public void setAnchorGeo(int x, int y, int w, int h)
	{
		int d = AW/2;
		x = x-d;
		y = y-d;
		this.anchors.get(EAnchors.EE.ordinal()).setBounds(x+w, y+h/2, AW, AH);
		this.anchors.get(EAnchors.WW.ordinal()).setBounds(x, y+h/2, AW, AH);
		this.anchors.get(EAnchors.NN.ordinal()).setBounds(x+w/2, y, AW, AH);
		this.anchors.get(EAnchors.SS.ordinal()).setBounds(x+w/2, y+h, AW, AH);
		this.anchors.get(EAnchors.NE.ordinal()).setBounds(x+w, y, AW, AH);
		this.anchors.get(EAnchors.NW.ordinal()).setBounds(x, y, AW, AH);
		this.anchors.get(EAnchors.SE.ordinal()).setBounds(x+w, y+h, AW, AH);
		this.anchors.get(EAnchors.SW.ordinal()).setBounds(x, y+h, AW, AH);
		this.anchors.get(EAnchors.RR.ordinal()).setBounds(x+w/2, y-h/2, AW, AH);
	}
	public void draw(Graphics2D g2D)
	{
		for(int i=0; i<EAnchors.values().length-1; i++)
		{
			g2D.draw(this.anchors.get(i));
		}
	}
	public EAnchors onAnchor(int x, int y){
		for(int i=0; i<EAnchors.values().length-1; i++)
		{
			if(this.anchors.get(i).contains(x, y))
			{
				return EAnchors.values()[i];
			}
		}
		return null;
	}
	public void setTransformedShape(AffineTransform affineTrnasform) {
		for (int i=0; i<anchors.size(); i++) {
			Shape transformedShape = affineTrnasform.createTransformedShape(anchors.get(i));			
			double x = transformedShape.getBounds().getCenterX();
			double y = transformedShape.getBounds().getCenterY();
			Rectangle anchor = new Rectangle();
			anchor.setFrameFromCenter(x, y, x+AW/2, y+AH/2);
			anchors.set(i, anchor);
		}
	}
}

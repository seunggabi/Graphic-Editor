package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JPanel;

import constant.GEConstant.EDrawingState;
import entity.GEModel;
import shapes.GEGroup;
import shapes.GEShape;
import shapes.GEShape.EAnchors;
import transformer.GEDrawer;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GERotator;
import transformer.GETransformer;

public class GEPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GETransformer currentTransformer;
	private MouseHandler mouseHandler;

	private static Vector<GEShape> shapes;
	private GEDoStack doStack;
	private Vector<GEShape> temp;
	private Vector<GEShape> buffer;
	private Vector<GEShape> groupBuffer;
	private GECursor cursor;

	private GEShape selectedShape;
	private GEShape onShape;
	private GEShape currentShape;
	private boolean bUpdated;

	private Color lineColor;
	private Color fillColor;

	public GEPanel() {
		super();
		GEPanel.shapes = new Vector<GEShape>();
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.bUpdated = false;
		this.doStack = new GEDoStack();
		buffer = new Vector<GEShape>();
		temp = new Vector<GEShape>();
		groupBuffer = new Vector<GEShape>();
		cursor = new GECursor();
	}

	public void init() {
		initColor();
	}

	public void initColor() {
		lineColor = Color.black;
		fillColor = getBackground();
	}

	public void cut() {
		this.doStack.push(shapes);
		buffer.clear();
		temp.clear();
		for (GEShape s : shapes) {
			if (s.isSelected())
				temp.add(s);
		}
		for (GEShape s : temp) {
			if (s.isSelected()) {
				buffer.add(s.clone());
				shapes.remove(s);
			}
		}
		repaint();
	}

	public void paste() {
		this.doStack.push(shapes);
		for (GEShape s : buffer) {
			if (s.isSelected())
				shapes.add(s.clone());
		}
		repaint();
	}

	public void copy() {
		this.doStack.push(shapes);
		buffer.clear();
		temp.clear();
		for (GEShape s : shapes) {
			if (s.isSelected())
				temp.add(s);
		}
		for (GEShape s : temp) {
			if (s.isSelected())
				buffer.add(s.clone());
		}
	}

	public void delete() {
		this.doStack.push(shapes);
		temp.clear();
		for (GEShape s : shapes) {
			if (s.isSelected())
				temp.add(s);
		}
		for (GEShape s : temp) {
			if (s.isSelected()) {
				shapes.remove(s);
			}
		}
		repaint();
	}

	public void redo() {
		Vector<GEShape> stack = this.doStack.redo(GEPanel.shapes);
		if (stack != null)
			GEPanel.shapes = stack;
		repaint();
	}

	public void undo() {
		Vector<GEShape> stack = this.doStack.undo(GEPanel.shapes);
		if (GEPanel.shapes != null && stack != null)
			GEPanel.shapes = stack;
		repaint();
	}
	
	public void selectAll() {
		for(GEShape s : GEPanel.shapes)
		{
			s.setSelected(true);
		}
		repaint();
	}
	
	public void deselectAll() {
		for(GEShape s : GEPanel.shapes)
		{
			s.setSelected(false);
		}
		repaint();
	}

	public Vector<GEShape> getShapes() {
		return shapes;
	}

	public void setShapes(Vector<GEShape> shapes) {
		GEPanel.shapes = shapes;
	}
	
	public Vector<GEShape> selectShapes()
	{
		Vector<GEShape> shapes = new Vector<GEShape>();
		boolean exist = false;
		for(GEShape s : getShapes())
		{
			if(s.isSelected())
			{	
				exist = true;
				shapes.add(s);
			}
		}
		if(exist == true)
			return shapes;
		else
			return null;
	}

	public void group()
	{
		GEGroup group = new GEGroup();
		groupBuffer.clear();
		for(GEShape s: GEPanel.shapes)
		{
			groupBuffer.add(s);
		}
		group.setSelected(true);
		for(GEShape s: this.groupBuffer)
		{
			if(s.isSelected())
			{
				s.setSelected(false);
				group.getShapes().add(s);
				GEPanel.shapes.remove(s);
			}
		}
		GEPanel.shapes.add(group);
		repaint();
	}
	
	public void unGroup()
	{
		if(selectedShape.getClass().getSimpleName().equals("GEGroup"))
		{
			GEGroup group = (GEGroup) this.selectedShape;
			for(GEShape s: group.getShapes())
			{
				GEPanel.shapes.add(s);
			}
			GEPanel.shapes.remove(group);
		}
		repaint();
	}
	
	private void initTransforming(int x, int y) {
		this.bUpdated = true;
		this.currentTransformer.initTransforming(this.getGraphics(), x, y);
	}

	private void keepTransforming(int x, int y) {
		this.currentTransformer.keepTransforming(this.getGraphics(), x, y);
	}

	private void continueTransforming(int x, int y) {
		this.currentTransformer.continueTransforming(this.getGraphics(), x, y);
	}

	private void finishTransforming(int x, int y) {
		if(currentShape.getClass().getSimpleName().equals("GEGroup") && currentTransformer.getClass().getSimpleName().equals("GEDrawer"))
		{
			GEGroup group = (GEGroup) this.currentShape;
			groupBuffer.clear();
			for(GEShape s: GEPanel.shapes)
			{
				groupBuffer.add(s);
			}
			group.setSelected(true);
			for(GEShape s: this.groupBuffer)
			{
				if(group.addShape(s))
				{
					GEPanel.shapes.remove(s);
				}
			}
		}
		this.currentTransformer.finishTransforming(this.getGraphics(), x, y);
		this.doStack.push(shapes);
		currentShape.setLineColor(lineColor);
		currentShape.setFillColor(fillColor);
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (GEShape shape : this.getShapes()) {
			shape.draw(g);
		}
	}

	public void setCurrentShape(GEShape currentShape) {
		this.currentShape = currentShape;
	}

	private GEShape onShape(int x, int y) {
		for (GEShape shape : GEPanel.shapes) {
			if (shape.onShape(x, y)) {
				return shape;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void open(String fileName) {
		try {
			shapes = (Vector<GEShape>) GEModel.open(fileName);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		bUpdated = false;
		this.repaint();
	}

	public void save(String fileName) {
		try {
			GEModel.save(fileName, GEPanel.shapes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bUpdated = false;
	}

	public boolean isbUpdated() {
		return bUpdated;
	}

	public void setbUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {
		private EDrawingState eDrawingState = EDrawingState.idle;

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
		}

		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.drawingNP) {
				finishTransforming(e.getX(), e.getY());
				getShapes().add(currentShape);
				selectedShape = currentShape;
				selectedShape.setSelected(true);
				eDrawingState = EDrawingState.idle;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.drawingNP) {
				keepTransforming(e.getX(), e.getY());
			}
			onShape = onShape(e.getX(), e.getY());
			setCursor(cursor.getCursor(onShape));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.idle) {
				selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					deselectAll();
					try {
						currentShape = currentShape.getClass().newInstance();
					} catch (InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
					currentTransformer = new GEDrawer(currentShape);
					if (!currentShape.getClass().getSimpleName().equals("GEPolygon")) {
						initTransforming(e.getX(), e.getY());
						eDrawingState = EDrawingState.drawingTP;
					} else if (currentShape.getClass().getSimpleName().equals("GEPolygon")) {
						initTransforming(e.getX(), e.getY());
						eDrawingState = EDrawingState.drawingNP;
						keepTransforming(e.getX(), e.getY());
					}
				} else {
					selectedShape.setSelected(true);
					if (selectedShape.geteSelectedAnchor() == EAnchors.MM) {
						currentTransformer = new GEMover(selectedShape);
					} else if (selectedShape.geteSelectedAnchor() == EAnchors.RR) {
						currentTransformer = new GERotator(selectedShape);
					} else {
						currentTransformer = new GEResizer(selectedShape);
					}
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.moving;
				}
			} else if (eDrawingState == EDrawingState.drawingNP) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.drawingTP) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.drawingNP) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.moving) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.drawingTP) {
				finishTransforming(e.getX(), e.getY());
				if(currentShape.isValidShape())
				{
					getShapes().add(currentShape);
					selectedShape = currentShape;
					selectedShape.setSelected(true);
				}
				else
				{
					selectedShape = null;
				}
				eDrawingState = EDrawingState.idle;
			} else if (eDrawingState == EDrawingState.drawingNP) {
				continueTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.moving) {
				eDrawingState = EDrawingState.idle;
				finishTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
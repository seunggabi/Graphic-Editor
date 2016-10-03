package frames;

import java.util.Vector;

import shapes.GEShape;

public class GEDoStack {
	private final int MAXSIZE = 10;
	private Vector<Vector<GEShape>> stack;
	private Vector<Vector<GEShape>> redoStack;
	
	public GEDoStack() {
		this.stack = new Vector<Vector<GEShape>>();
		this.redoStack = new Vector<Vector<GEShape>>();
	}
	private Vector<GEShape> cloneShapes(Vector<GEShape> shapes)
	{
		Vector<GEShape> s = new Vector<GEShape>();
		for(int i=0; i<shapes.size(); i++)
		{
			s.add(shapes.get(i).clone());
		}
		return s;
	}
	public void push(Vector<GEShape> shapes){
		this.redoStack.clear();
		this.stack.add(cloneShapes(shapes));
		if(this.stack.size() > MAXSIZE)
			this.stack.remove(0);
	}
	public Vector<GEShape> undo(Vector<GEShape> shapes) {
		if(this.stack.size() > 0)
		{
			this.redoStack.add(cloneShapes(shapes));
			return this.stack.remove(stack.size()-1);
		}
		return null;
	}
	public Vector<GEShape> redo(Vector<GEShape> shapes) {
		if(this.redoStack.size() > 0)
		{
			this.stack.add(cloneShapes(shapes));
			return this.redoStack.remove(redoStack.size()-1);
		}
		return null;
	}
}

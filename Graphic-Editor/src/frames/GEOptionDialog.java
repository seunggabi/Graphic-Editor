package frames;

import javax.swing.JOptionPane;

public class GEOptionDialog {
	private String title = null;
	private String message = null;
	private Object[] options = null;
	private String question = null;
	private GEFrame frame = null;
	
	public GEOptionDialog()
	{
		options = new Object[]{ "Yes", "No", "Cancel" };
	}
	
	public void init(GEFrame frame)
	{
		this.frame = frame;
	}
	public int resultDialog()
	{
		return JOptionPane.showOptionDialog(frame, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
	}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public Object[] getOptions() {return options;}
	public void setOptions(Object[] options) {this.options = options;}
	public String getQuestion() {return question;}
	public void setQuestion(String question) {this.question = question;}
	public GEFrame getFrame() {return frame;}
	public void setFrame(GEFrame frame) {this.frame = frame;}
}

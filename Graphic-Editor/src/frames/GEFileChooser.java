package frames;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import constant.GEConstant;

public class GEFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	FileFilter filter = null;
	
	public GEFileChooser(String currentDirectory) {
		super(currentDirectory);

		filter = new FileNameExtensionFilter("Graphics Editor", GEConstant.SFILEEXTENSION);
		this.addChoosableFileFilter(filter);
		this.setFileFilter(filter);
	}
}

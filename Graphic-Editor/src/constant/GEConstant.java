package constant;

import java.awt.Cursor;

import frames.GEColorMenu;
import frames.GEEditMenu;
import frames.GEFileMenu;
import frames.GEFontMenu;
import frames.GEMenu;
import shapes.GEEllipse;
import shapes.GEGroup;
import shapes.GEHeart;
import shapes.GELine;
import shapes.GEPolygon;
import shapes.GERectangle;
import shapes.GEShape;

public class GEConstant {
	public static final int FRAME_W = 400;
	public static final int FRAME_H = 600;

	public static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	public static Cursor MOVE_CURSOR = new Cursor(Cursor.MOVE_CURSOR);
	
	public static enum EDrawingState {
		idle, drawingTP, drawingNP, moving
	};

	private static final String rootDirectory = "rsc/";

	public static enum EButtons {
		Rectangle(rootDirectory + "rectangle.PNG", rootDirectory + "rectangle_select.PNG", new GERectangle())
		, Group(rootDirectory + "group.PNG", rootDirectory + "group_select.PNG", new GEGroup())
		, Ellipse(rootDirectory + "ellipse.PNG", rootDirectory + "ellipse_select.PNG", new GEEllipse())
		, Line(rootDirectory + "line.PNG", rootDirectory + "line_select.PNG", new GELine())
		, Heart(rootDirectory + "heart.PNG", rootDirectory + "heart_select.PNG", new GEHeart())
		, Polygon(rootDirectory + "polygon.png", rootDirectory + "polygon_select.png", new GEPolygon());

		private String buttonImage;
		private String selectedButtonImage;
		private GEShape shape;

		private EButtons(String buttonImage, String selectedButtonImage, GEShape shape) {
			this.buttonImage = buttonImage;
			this.selectedButtonImage = selectedButtonImage;
			this.shape = shape;
		}

		public String getButtonImage() {
			return buttonImage;
		}

		public String getSelectedButtonImage() {
			return selectedButtonImage;
		}

		public GEShape getShape() {
			return shape;
		}

		public void setShape(GEShape shape) {
			this.shape = shape;
		}
	}

	public static enum EMenu {
		File("File", new GEFileMenu()), Edit("Edit", new GEEditMenu()), Color("Color", new GEColorMenu()), Font("Font",
				new GEFontMenu());

		private String menuName;
		private GEMenu menu;

		private EMenu(String menuName, GEMenu menu) {
			this.menuName = menuName;
			this.menu = menu;
			this.menu.setText(menuName);
		}

		public String getMenuName() {
			return menuName;
		}

		public GEMenu getMenu() {
			return menu;
		}
	}

	public static enum EFileMenuItem {
		New("New"), Open("Open"), Save("Save"), SaveAs("Save As"), Close("Close"), Print("Print"), Exit("Exit");

		private String menuItemName;

		private EFileMenuItem(String menuItemName) {
			this.menuItemName = menuItemName;
		}

		public String getMenuItemName() {
			return menuItemName;
		}
	}

	public static enum EEditMenuItem {
		Cut("Cut"), Copy("Copy"), Paste("Paste"), Delete("Delete"), Redo("Redo"), Undo("Undo"), SelectALL(
				"Select All"), DeselectAll("Deselect All"), Group("Group"), UnGroup("Ungroup");

		private String menuItemName;

		private EEditMenuItem(String menuItemName) {
			this.menuItemName = menuItemName;
		}

		public String getMenuItemName() {
			return menuItemName;
		}
	}

	public static enum EColorMenuItem {
		LineColor("Line Color"), FillColor("Fill Color");

		private String menuItemName;

		private EColorMenuItem(String menuItemName) {
			this.menuItemName = menuItemName;
		}
		public String getMenuItemName() {
			return menuItemName;
		}
		
	}

	public static enum EFontMenuItem {
		Font("Font"), Size("Size");

		private String menuItemName;

		private EFontMenuItem(String menuItemName) {
			this.menuItemName = menuItemName;
		}

		public String getMenuItemName() {
			return menuItemName;
		}
	}
	
	public static String SNEW = "변경 내용을 저장하시겠습니까?";
	public static String SOPEN = "정말 오픈하시겠습니까?";
	public static String SSAVE = "정말 저장하시겠습니까?";
	public static String SCLOSE = "정말 종료하시겠습니까?";
	public static String SDIALOGNAME = "도우미";
	public static String SFILEEXTENSION = "gps";
	public static final String SCURRENTDIRECTORY = ".\\data";
}

package mainframecomponent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import menuoption.EditMenu;
import menuoption.FileMenu;
import menuoption.HelpMenu;
import menuoption.PictureMenu;
import workstation.MainFrame;

public class MenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	public FileMenu fileOption;//这里为了提高代码的复用率,旨在主界面退出时使用IsSave
	private EditMenu editOption=new EditMenu();
	private PictureMenu pictureOption;
	private HelpMenu helpOption=new HelpMenu();
	private JMenu []menu= {fileOption,editOption,pictureOption,helpOption};
	
	public MenuBar(MainFrame mainFrame) {
		fileOption=new FileMenu(mainFrame.drawPanel);
		pictureOption=new PictureMenu(mainFrame,fileOption);
		menu[0]=fileOption;
		menu[2]=pictureOption;
		addMenu();
	}
	
	private void addMenu() {
		for(int i=0;i<menu.length;i++)
			add(menu[i]);
	}
	
}

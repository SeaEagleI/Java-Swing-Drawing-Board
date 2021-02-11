package mainframecomponent;

import java.awt.Color;
import javax.swing.JToolBar;

import panel.ColorsPanel;
import panel.ShapesPanel;
import panel.ShapesQueueUpdate;
import panel.StrokesPanel;

public class ToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private ShapesPanel graphButtons=new ShapesPanel();
	private ColorsPanel colourButtons=new ColorsPanel();
	private StrokesPanel thicknessButtons=new StrokesPanel();
	private ShapesQueueUpdate shortCutTools;
	
	public ToolBar(String name, int orientation,DrawBoard paintBoard) {
		super(name, orientation);
		setBackground(Color.white);
		shortCutTools=new ShapesQueueUpdate(paintBoard);
		add(shortCutTools);
		addSeparator();
		add(graphButtons);
		addSeparator();
		add(colourButtons);
		addSeparator();
		add(thicknessButtons);
	}

}

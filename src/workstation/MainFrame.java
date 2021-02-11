package workstation;

import static panel.ColorsPanel.jButtonBack;
import static panel.Function.backgroundColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import mainframecomponent.DrawBoard;
import mainframecomponent.MenuBar;
import mainframecomponent.PositionLabel;
import panel.ColorsPanel;
import panel.ShapesPanel;
import panel.ShapesQueueUpdate;
import panel.StrokesPanel;

public class MainFrame extends JFrame{
		private static final long serialVersionUID = 1L;
		private String title = "Drawing Board";
		private PositionLabel mousePositionIndicator = new PositionLabel();
		public DrawBoard drawPanel = new DrawBoard(mousePositionIndicator);
		private MenuBar menuBar = new MenuBar(this);

		private JPanel jPanelWest=new JPanel();
		private ShapesPanel graphButtons = new ShapesPanel();
		private ShapesQueueUpdate otherOperations = new ShapesQueueUpdate(drawPanel);

		private JPanel jPanelSouth = new JPanel();
		private JPanel styleSheetPanel=new JPanel();
		private ColorsPanel jPanelPalette = new ColorsPanel();
		private StrokesPanel strokesPanel = new StrokesPanel();
		private JPanel bottomBar = new JPanel();
		private JLabel copyrightLabel = new JLabel("Andrew Wang Copyright 2019 ", SwingConstants.RIGHT);

		public MainFrame(){
			setTitle(title);
			setMainFrameImage();
			setBounds(310,50,1025,715);
			setLayout(new BorderLayout());
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			addWindowListener();
			setJMenuBar(menuBar);
			add(drawPanel, BorderLayout.CENTER);

			/**************************West Panel Settings*************************************/
			jPanelWest.setBackground(new Color(240, 240, 240));
			jPanelWest.setPreferredSize(new Dimension(100, 60));
			jPanelWest.setLayout(new FlowLayout(1,0,0));
			jPanelWest.setBorder(new BevelBorder(0,Color.WHITE,Color.DARK_GRAY));
			add(jPanelWest, BorderLayout.WEST);

			//ShapePanel: 6x2 GridLayout JRadioButtons
			jPanelWest.add(graphButtons);

			//ShapesQueueUpdate: 2x2 
			//jPanelWest.addSeperator();
			jPanelWest.add(otherOperations);

			/**************************South Panel Settings*************************************/
			//styleSheetPanel
			//2 SubPanels: jPanelPalette, strokesPanel
			styleSheetPanel.setBackground(new Color(240, 240, 240));
			styleSheetPanel.setPreferredSize(new Dimension(0, 70));
			styleSheetPanel.setLayout(new BoxLayout(styleSheetPanel, BoxLayout.X_AXIS));
			/**styleSheetPanel.jPanelPalette, for Color Configurations*/
			styleSheetPanel.add(jPanelPalette);
			styleSheetPanel.add(Box.createHorizontalStrut(10));
			/**StrokesPanel.strokesPanel, for Line Thickness & Fonts Configurations*/
			styleSheetPanel.add(strokesPanel);

			//bottomBar
			//2 SubLabels: mousePositionIndicator, copyrightLabel
			bottomBar.setBackground(new Color(240, 240, 240));
			bottomBar.setLayout(new BorderLayout());
			bottomBar.add(mousePositionIndicator, BorderLayout.WEST);
			bottomBar.add(copyrightLabel, BorderLayout.CENTER);

			//jPanelSouth
			//2 SubPanels: styleSheetPanel, bottomBar
			jPanelSouth.setBackground(new Color(240, 240, 240));
			jPanelSouth.setPreferredSize(new Dimension(0, 85));
			jPanelSouth.setLayout(new BorderLayout());
			jPanelSouth.add(styleSheetPanel, BorderLayout.NORTH);
			jPanelSouth.add(bottomBar, BorderLayout.SOUTH);
			add(jPanelSouth,BorderLayout.SOUTH);

			setVisible(true);

			/**************************Background Color Settings**********************/
			jButtonBack.addActionListener(new ActionListener() {//选定一种字体
				@Override
				public void actionPerformed(ActionEvent e) {
					//Set Background Color
					Color tmpColor = JColorChooser.showDialog(null,"选择背景颜色",backgroundColor);
					if (tmpColor!=null) {
						backgroundColor = tmpColor;
						jButtonBack.setBackground(backgroundColor);
						drawPanel.setBackground(backgroundColor);
				    }
				}
			});
		}

		private void setMainFrameImage() {
			Toolkit kit=Toolkit.getDefaultToolkit();
			setIconImage(kit.getImage("ImageIcons/MainFrameIcon/TitleIcon.png"));
		}

		public static void main(String []args) {
			MainFrame drawingPicture =new MainFrame();
			drawingPicture.end();
		}

		private void addWindowListener() {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					menuBar.fileOption.isSave(true);
					int res=JOptionPane.showConfirmDialog(drawPanel, "您确定要退出Drawing Panel了吗?", 
							"退出提示", JOptionPane.WARNING_MESSAGE);
					if(res==JOptionPane.OK_OPTION)
						System.exit(0);
				}
			});
		}

		private void end() {
		}

}



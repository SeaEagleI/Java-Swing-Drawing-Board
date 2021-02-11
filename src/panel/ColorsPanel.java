package panel;

import static panel.Function.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ColorsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public JButton []colorButtons = new JButton[30];
	public static JButton jButtonPen = new JButton();
	public static JButton jButtonBack = new JButton();
//	colorChoice = 0;

	public ColorsPanel() {
		setBackground(new Color(240, 240, 240));
//		setPreferredSize(new Dimension(45, 150));
//		setLayout(null);

		//添加左下角调色盘
//		JPanel jPanelPalette=new JPanel();
		setBounds(0, 9, 240, 60);
		setLayout(new BorderLayout());

		/**笔刷显色区代码块*/
		//面板
		JPanel jPanelChange=new JPanel();
		jPanelChange.setBorder(new BevelBorder(1,Color.white,Color.darkGray));
		jPanelChange.setLayout(null);
		jPanelChange.setPreferredSize(new Dimension(70, 70));
		//jButtonPen: Color Button for Pen Color Configurations (Multiple Selection)
		jButtonPen.setBackground(color);
		jButtonPen.setBorder(new BevelBorder(0, Color.white, Color.GRAY));
		jButtonPen.setBounds(5, 5, 40, 40);
		jButtonPen.setToolTipText("设置画笔颜色");
		jButtonPen.addActionListener(new ActionListener() {//选定一种字体
			@Override
			public void actionPerformed(ActionEvent e) {
				//Set Pen Color
				Color tmpColor = JColorChooser.showDialog(null,"选择画笔颜色",color);
				if (tmpColor!=null) {
					color = tmpColor;
					jButtonPen.setBackground(color);
			    }
			}
		});
		//jButtonBack: Color Button for Background Color Configurations (Multiple Selection)
		jButtonBack.setBackground(backgroundColor);
		jButtonBack.setBorder(new BevelBorder(0, Color.white, Color.GRAY));
		jButtonBack.setBounds(25, 25, 40, 40);
		jButtonBack.setToolTipText("设置背景颜色");
		jPanelChange.add(jButtonPen);
		jPanelChange.add(jButtonBack);
		add(jPanelChange,BorderLayout.WEST);

		//颜料盒代码块: jPanelPigment
		JPanel jPanelPigment = new JPanel();
		jPanelPigment.setLayout(new GridLayout(3, 10));
		jPanelPigment.setPreferredSize(new Dimension(45, 150));
		Color []colors= {
						new Color(0, 0, 0),new Color(128, 128, 128),new Color(128, 0, 0),new Color(128, 128, 0),
						new Color(0, 128, 0),new Color(0, 128, 128),new Color(0, 0, 128),new Color(128, 0, 128),
						new Color(128, 128, 64),new Color(0, 64, 64),new Color(0, 128, 255),new Color(0, 64, 128),
						new Color(128, 0, 255),new Color(128, 64, 0),new Color(255, 255, 255),new Color(192, 192, 192),
						new Color(255, 0, 0),new Color(255, 255, 0),new Color(0, 255, 0),new Color(0, 255, 255),
						new Color(0, 0, 255),new Color(255, 0, 255),new Color(255, 255, 128),new Color(0, 255, 128),
						new Color(128, 255, 255),new Color(128, 128, 255),new Color(255, 0, 128),new Color(255, 128, 64),
						new Color(128, 192, 255),new Color(255, 128, 192)
		};
//		SouthColorListener southColorListener=new SouthColorListener(jButtonPen);
		for(int i=0;i<colors.length;i++) {
			JButton jButtonPigment=new JButton();
			jButtonPigment.setBackground(colors[i]);
			jButtonPigment.setPreferredSize(new Dimension(15, 15));
			jButtonPigment.setBorder(new BevelBorder(1,Color.WHITE,Color.DARK_GRAY));
			jButtonPigment.addActionListener(this);
			colorButtons[i] = jButtonPigment;
//			System.out.println(i);
			jPanelPigment.add(jButtonPigment);
		}
		add(jPanelPigment);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<colorButtons.length;i++) {
			if(e.getSource()==colorButtons[i]) {
				colorChoice=i;
				newColor();
			}
		}
	}

}

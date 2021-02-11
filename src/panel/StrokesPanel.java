package panel;

import static panel.Function.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StrokesPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton []strokeButtons=new JButton[4];
	public JComboBox<String> fontComboBox;//下拉字体框
	public JComboBox<ImageIcon> widthComboBox;//下拉字体框

	public StrokesPanel() {
		setLayout(new FlowLayout());

		//Width Panel
		JPanel widthPanel = new JPanel();
		widthPanel.setLayout(new BoxLayout(widthPanel,BoxLayout.Y_AXIS));
		//Add Stroke Buttons
		String []name= {"一级","二级","三级","四级"};
		ImageIcon []strokeIcons=new ImageIcon[4];
		for(int i=0;i<strokeButtons.length;i++) {
			strokeIcons[i]=new ImageIcon("ImageIcons/StrokeIcons/"+name[i]+".png");
		}
		widthComboBox = new JComboBox<ImageIcon>(strokeIcons);
		widthComboBox.setAlignmentX(CENTER_ALIGNMENT);
		widthComboBox.setAlignmentY(CENTER_ALIGNMENT);
		widthComboBox.addItemListener(new ItemListener() {//选定一种字体
			@Override
			public void itemStateChanged(ItemEvent e) {
				strokeChoice = widthComboBox.getSelectedIndex();
				newStroke();
			}
		});
		JLabel widthLabel = new JLabel("线条粗细",JLabel.CENTER);
		widthLabel.setAlignmentX(CENTER_ALIGNMENT);
		widthPanel.add(widthLabel);
		widthPanel.add(widthComboBox);
		add(widthPanel);
		add(Box.createHorizontalStrut(10));

		//Font Panel
		JPanel fontPanel = new JPanel();
		fontPanel.setLayout(new BoxLayout(fontPanel,BoxLayout.Y_AXIS));
		//Add Font Styles
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String []styleNames = ge.getAvailableFontFamilyNames();  //加载系统字体
		fontComboBox = new JComboBox<String>(styleNames);
		fontComboBox.setBounds(5, 10, 30, 8);
		fontComboBox.setAlignmentX(CENTER_ALIGNMENT);
		fontComboBox.setAlignmentY(CENTER_ALIGNMENT);
		fontComboBox.setMaximumRowCount(10);
		fontComboBox.setMinimumSize(new Dimension(80, 20));
		fontComboBox.setToolTipText("字体");
		fontComboBox.addItemListener(new ItemListener() {//选定一种字体
			@Override
			public void itemStateChanged(ItemEvent e) {
				fontStyle = styleNames[fontComboBox.getSelectedIndex()];
			}
		});
		JLabel fontLabel = new JLabel("字体",JLabel.CENTER);
		fontLabel.setAlignmentX(CENTER_ALIGNMENT);
		fontPanel.add(fontLabel);
		fontPanel.add(fontComboBox);
		add(fontPanel);
	}
}

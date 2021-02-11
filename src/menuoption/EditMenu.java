package menuoption;

import static panel.ColorsPanel.jButtonPen;
import static panel.Function.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class EditMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuItem []editItem=new JMenuItem[2];
	
	public EditMenu() {
		super("编辑");
		addEditItem();
	}
	
	private void addEditItem() {
		String []name= {"编辑画笔颜色","编辑画笔粗细"};
		ImageIcon []editIcons=new ImageIcon[2];
		int []keyBoard= {KeyEvent.VK_E,KeyEvent.VK_R};
		for(int i=0;i<editItem.length;i++) {
		    editIcons[i]=new ImageIcon("ImageIcons/MenuBarIcons/EditIcons/"+name[i]+".png");
	      editItem[i]=new JMenuItem(name[i], editIcons[i]);
	      editItem[i].setAccelerator(KeyStroke.getKeyStroke(keyBoard[i], InputEvent.CTRL_MASK));
	      editItem[i].addActionListener(this);
	      add(editItem[i]);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==editItem[0]) {
			color = JColorChooser.showDialog(null,"选择画笔颜色",color);
			jButtonPen.setBackground(color);
		}
		else if(e.getSource()==editItem[1]) {
			float tmp;
			String input=JOptionPane.showInputDialog("请输入画笔粗细");
			if(input!=null) {//防止用户点击取消,造成的空指针异常
				tmp=Float.parseFloat(input);
				if(tmp<0)
					JOptionPane.showMessageDialog(null, "负数无效,请输入有效数据","错误提示",JOptionPane.INFORMATION_MESSAGE);
				else
					stroke=tmp;
			}
		}
	}

}

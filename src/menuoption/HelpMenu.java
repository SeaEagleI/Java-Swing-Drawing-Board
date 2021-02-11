package menuoption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class HelpMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuItem []helpItem=new JMenuItem[2];
	private String []name= {"关于画图界面","关于制作团队"};
	private ImageIcon []helpIcons=new ImageIcon[2];
	private String []message=new String[2];
	
	public HelpMenu() {
		super("帮助");
		readMessage();
		addHelpItem();
	}

	private void addHelpItem() {
	  ImageIcon []helpIcons=new ImageIcon[2];
		int []keyBoard= {KeyEvent.VK_Q,KeyEvent.VK_W};
		for(int i=0;i<helpItem.length;i++) {
			helpIcons[i]=new ImageIcon("ImageIcons/MenuBarIcons/HelpIcons/"+name[i]+".png");
			helpItem[i]=new JMenuItem(name[i], helpIcons[i]);
			helpItem[i].setAccelerator(KeyStroke.getKeyStroke(keyBoard[i], InputEvent.CTRL_MASK));
			helpItem[i].addActionListener(this);
			add(helpItem[i]);
		}
	}

	private void readMessage() {
		int len;
		byte []tmp=new byte[350];
		for(int i=0;i<message.length;i++) {
			try(BufferedInputStream input=new BufferedInputStream(new FileInputStream("HelpInformation/"+name[i]+".txt"))){
				len=input.read(tmp);
				message[i]=new String(tmp,0,len);
			}catch(FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "不存在该文件", "打开文件错误提示", JOptionPane.ERROR_MESSAGE);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==helpItem[0]) {
			JOptionPane.showMessageDialog(null, message[0], name[0], JOptionPane.INFORMATION_MESSAGE, helpIcons[0]);
		}
		else if(e.getSource()==helpItem[1]) {
			JOptionPane.showMessageDialog(null, message[1], name[1], JOptionPane.INFORMATION_MESSAGE, helpIcons[1]);
		}
	}

}

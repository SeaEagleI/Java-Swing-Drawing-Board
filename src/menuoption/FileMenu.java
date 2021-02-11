package menuoption;

import static panel.Function.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import mainframecomponent.DrawBoard;
//import menuoption.MenuBar.ImageFileFilter;
import shapes.Shape;

public class FileMenu extends JMenu implements ActionListener{
	private static final long serialVersionUID = 1L;
	private DrawBoard drawPanel;
	private JMenuItem []fileItem=new JMenuItem[5];
	private File openFile=null;
	private int lastEOF=-1;
    public JFileChooser fileChooser = null;

	public FileMenu(DrawBoard paintBoard) {
		super("文件");
		drawPanel=paintBoard;
		addFileItem();
	}
	
	private void addFileItem() {
		String []name= {"新建","打开","保存","另存为","退出"};
		ImageIcon []fileIcons=new ImageIcon[5];
		int []keyBoard= {KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D,KeyEvent.VK_Z,KeyEvent.VK_X};
		for(int i=0;i<fileItem.length;i++) {
			fileIcons[i]=new ImageIcon("ImageIcons/MenuBarIcons/FileIcons/"+name[i]+".png");
			fileItem[i]=new JMenuItem(name[i],fileIcons[i]);
			fileItem[i].setAccelerator(KeyStroke.getKeyStroke(keyBoard[i], InputEvent.CTRL_MASK));
			fileItem[i].addActionListener(this);
			add(fileItem[i]);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fileItem[0]) {
			isSave(false);
			drawPanel.image=null;
			newFile();
		}
		else if(e.getSource()==fileItem[1]) {
			isSave(false);
			openPicture();
		}
		else if(e.getSource()==fileItem[2]) {
			if(lastEOF!=index) {//如果有去修改图片才重新覆盖本地图片,否则不做处理
				if(lastEOF!=-1)
					shapesQueue[lastEOF].EOF=true;//将先前打开文件的对象结束符修改
				savePicture(true);
			}
		}
		else if(e.getSource()==fileItem[3]) {
			if(lastEOF!=-1&&lastEOF!=index)//打开的文件要另存为时,将文件的结束符修改
				shapesQueue[lastEOF].EOF=true;
			savePicture(false);
		}
		else if(e.getSource()==fileItem[4]) {
			isSave(true);
			int res=JOptionPane.showConfirmDialog(drawPanel, "您确定要退出Java MiNi Drawing Tool了吗?", "退出提示", JOptionPane.WARNING_MESSAGE);
			if(res==JOptionPane.OK_OPTION)
				Exit();
		}
	}

	public void isSave(boolean flag) {//实现当要打开或新建一个新文件或退出时,是否要把对当前的修改保存到本地
		int res=-1;
		if(openFile==null&&index!=-1&&(index!=lastEOF||(index==lastEOF&&isChanged==true))) {//先前已经另存为过,但是之后使得画板上的图形与原先最大数目不等或者在在图形对象数目不变的条件下更改了其中的一个图形,index=-1没必要保存空文件
			res=JOptionPane.showConfirmDialog(drawPanel, "您还没保存当前画面到本地,是否要保存?", "另存为提示", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION) {
				if(lastEOF!=-1)//一次都没有另存的时候LastEOF=-1,会出现数组越界异常
					shapesQueue[lastEOF].EOF=true;//当又在原先的基础上又画出图形时,需要修改对象结束标志,避免打开时有过早对象结束符,或撤销部分图形时,否则在后续恢复过程中,在画出图形时,保存时对象结束符会有一个较早的
				savePicture(false);
			}
		}
		else if(openFile!=null&&index!=-1&&(index!=lastEOF||(index==lastEOF&&isChanged==true))) {
			res=JOptionPane.showConfirmDialog(drawPanel, "您还没有将更改保存到当前打开文件,是否要保存?", "保存提示", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION) {
				shapesQueue[lastEOF].EOF=true;
				savePicture(true);
			}
		}
		if(res==JOptionPane.NO_OPTION&&flag==true)
			Exit();
	}
	
	public void newFile() {
		openFile=null;//新文件还没有打开的图片文件设置为null,方便实现用户错把保存当另存为
		index=maxShapesCount=lastEOF=-1;
		isChanged=false;
		drawPanel.repaint();
	}

    public JFileChooser getFileChooser()
    {
        if (fileChooser==null) {
            fileChooser = new JFileChooser();                        //create file chooser
            fileChooser.setFileFilter(new ImageFileFilter());         //set file extension to ImageFiles (ValidTails)
            fileChooser.setAccessory(new ImagePreview(fileChooser));
        }
        return fileChooser;
    }
    
	private File openWindow() {
		JFileChooser pictureChooser = getFileChooser();
		pictureChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result=pictureChooser.showOpenDialog(drawPanel);//this 为父组件
		if(result==JFileChooser.CANCEL_OPTION) return null;
		return pictureChooser.getSelectedFile();
	}
	
	private void openPicture() {
		openFile=openWindow();
		if(openFile!=null) {
			try(ObjectInputStream ShapeObject=new ObjectInputStream(new FileInputStream(openFile))) {
				for(index=0;true;index++) {
					shapesQueue[index]=(Shape)ShapeObject.readObject();//子类引用无法接受父类对象Object
					if(!shapesQueue[index].EOF) {
						maxShapesCount=lastEOF=index;//打开画图图片对象数的上界设立在此
						break;
					}
				}
				drawPanel.repaint();//将打开图像呈现在画板上
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(drawPanel, "不存在该文件", "打开文件错误提示", JOptionPane.ERROR_MESSAGE);
			}catch(StreamCorruptedException e) {
				JOptionPane.showMessageDialog(drawPanel, "并非二进制字节码对象文件", "文件读取错误提示", JOptionPane.ERROR_MESSAGE);
			}catch(EOFException e) {
				index=-1;//避免因index=0,而实际并没有对象被引用所导致的重绘空指针异常
				JOptionPane.showMessageDialog(drawPanel, "该文件没有任何记录", "文件结束错误提示", JOptionPane.ERROR_MESSAGE);
			}catch(ClassNotFoundException e) {
				JOptionPane.showMessageDialog(drawPanel, "无法创建对象", "无法找到类对象错误提示", JOptionPane.ERROR_MESSAGE);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private  File saveWindow() {
		JFileChooser pictureChooser=getFileChooser();
		pictureChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result=pictureChooser.showSaveDialog(drawPanel);
		if(result==JFileChooser.CANCEL_OPTION) return null;
		File src=pictureChooser.getSelectedFile();
		if(src.exists()) {
			result=JOptionPane.showConfirmDialog(drawPanel, "您是否要覆盖当前本地文件?", "保存文件提示", JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.NO_OPTION)
				return saveWindow();
			else
				return src;//表明覆盖当前本地文件
		}
		else
			return src;//表明另存在本地
	}
	
	private void savePicture(boolean flag) {//flag标志表明是另存为还是保存
		File src=null;
		if(!flag)
			src=saveWindow();
		else {
			if(openFile!=null)
				src=openFile;
			else
				src=saveWindow();//实现错把保存当另存为的处理
		}
		if(src!=null) {
			//资源保护模块会自动关闭流并且刷新缓冲区
			try(ObjectOutputStream ShapeObject=new ObjectOutputStream(new FileOutputStream(src))) {
				for(int i=0;i<=index;i++) {
					if(i==index)
						shapesQueue[i].EOF=false;
					ShapeObject.writeObject(shapesQueue[i]);
				}
				lastEOF=index;//实现同一文件多次修改,不断将结束符推后
				isChanged=false;
				if(!flag)//另存文件完成时,自动将OpenFile的文件句柄改为src(另存之后的文件)
					openFile=src;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private static class ImageFileFilter extends FileFilter {
    	String[] ValidTails = {"jpg", "png", "jpeg", "gif"};

        public boolean accept(File file) {             //filer files to display
        	String FileName = file.getName().toLowerCase();
            String FileSuffix = FileName.substring(FileName.lastIndexOf(".")+1);
        	return Arrays.asList(ValidTails).contains(FileSuffix) || file.isDirectory();
        }

        public String getDescription() {
            return "Image Files (*.jpg, *.png, *.jpeg, *.gif) ";
        }
    }

	private void Exit() {
		System.exit(0);
	}

}

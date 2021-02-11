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
		super("�ļ�");
		drawPanel=paintBoard;
		addFileItem();
	}
	
	private void addFileItem() {
		String []name= {"�½�","��","����","���Ϊ","�˳�"};
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
			if(lastEOF!=index) {//�����ȥ�޸�ͼƬ�����¸��Ǳ���ͼƬ,����������
				if(lastEOF!=-1)
					shapesQueue[lastEOF].EOF=true;//����ǰ���ļ��Ķ���������޸�
				savePicture(true);
			}
		}
		else if(e.getSource()==fileItem[3]) {
			if(lastEOF!=-1&&lastEOF!=index)//�򿪵��ļ�Ҫ���Ϊʱ,���ļ��Ľ������޸�
				shapesQueue[lastEOF].EOF=true;
			savePicture(false);
		}
		else if(e.getSource()==fileItem[4]) {
			isSave(true);
			int res=JOptionPane.showConfirmDialog(drawPanel, "��ȷ��Ҫ�˳�Java MiNi Drawing Tool����?", "�˳���ʾ", JOptionPane.WARNING_MESSAGE);
			if(res==JOptionPane.OK_OPTION)
				Exit();
		}
	}

	public void isSave(boolean flag) {//ʵ�ֵ�Ҫ�򿪻��½�һ�����ļ����˳�ʱ,�Ƿ�Ҫ�ѶԵ�ǰ���޸ı��浽����
		int res=-1;
		if(openFile==null&&index!=-1&&(index!=lastEOF||(index==lastEOF&&isChanged==true))) {//��ǰ�Ѿ����Ϊ��,����֮��ʹ�û����ϵ�ͼ����ԭ�������Ŀ���Ȼ�������ͼ�ζ�����Ŀ����������¸��������е�һ��ͼ��,index=-1û��Ҫ������ļ�
			res=JOptionPane.showConfirmDialog(drawPanel, "����û���浱ǰ���浽����,�Ƿ�Ҫ����?", "���Ϊ��ʾ", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION) {
				if(lastEOF!=-1)//һ�ζ�û������ʱ��LastEOF=-1,���������Խ���쳣
					shapesQueue[lastEOF].EOF=true;//������ԭ�ȵĻ������ֻ���ͼ��ʱ,��Ҫ�޸Ķ��������־,�����ʱ�й�����������,��������ͼ��ʱ,�����ں����ָ�������,�ڻ���ͼ��ʱ,����ʱ�������������һ�������
				savePicture(false);
			}
		}
		else if(openFile!=null&&index!=-1&&(index!=lastEOF||(index==lastEOF&&isChanged==true))) {
			res=JOptionPane.showConfirmDialog(drawPanel, "����û�н����ı��浽��ǰ���ļ�,�Ƿ�Ҫ����?", "������ʾ", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION) {
				shapesQueue[lastEOF].EOF=true;
				savePicture(true);
			}
		}
		if(res==JOptionPane.NO_OPTION&&flag==true)
			Exit();
	}
	
	public void newFile() {
		openFile=null;//���ļ���û�д򿪵�ͼƬ�ļ�����Ϊnull,����ʵ���û���ѱ��浱���Ϊ
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
		int result=pictureChooser.showOpenDialog(drawPanel);//this Ϊ�����
		if(result==JFileChooser.CANCEL_OPTION) return null;
		return pictureChooser.getSelectedFile();
	}
	
	private void openPicture() {
		openFile=openWindow();
		if(openFile!=null) {
			try(ObjectInputStream ShapeObject=new ObjectInputStream(new FileInputStream(openFile))) {
				for(index=0;true;index++) {
					shapesQueue[index]=(Shape)ShapeObject.readObject();//���������޷����ܸ������Object
					if(!shapesQueue[index].EOF) {
						maxShapesCount=lastEOF=index;//�򿪻�ͼͼƬ���������Ͻ������ڴ�
						break;
					}
				}
				drawPanel.repaint();//����ͼ������ڻ�����
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(drawPanel, "�����ڸ��ļ�", "���ļ�������ʾ", JOptionPane.ERROR_MESSAGE);
			}catch(StreamCorruptedException e) {
				JOptionPane.showMessageDialog(drawPanel, "���Ƕ������ֽ�������ļ�", "�ļ���ȡ������ʾ", JOptionPane.ERROR_MESSAGE);
			}catch(EOFException e) {
				index=-1;//������index=0,��ʵ�ʲ�û�ж������������µ��ػ��ָ���쳣
				JOptionPane.showMessageDialog(drawPanel, "���ļ�û���κμ�¼", "�ļ�����������ʾ", JOptionPane.ERROR_MESSAGE);
			}catch(ClassNotFoundException e) {
				JOptionPane.showMessageDialog(drawPanel, "�޷���������", "�޷��ҵ�����������ʾ", JOptionPane.ERROR_MESSAGE);
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
			result=JOptionPane.showConfirmDialog(drawPanel, "���Ƿ�Ҫ���ǵ�ǰ�����ļ�?", "�����ļ���ʾ", JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.NO_OPTION)
				return saveWindow();
			else
				return src;//�������ǵ�ǰ�����ļ�
		}
		else
			return src;//��������ڱ���
	}
	
	private void savePicture(boolean flag) {//flag��־���������Ϊ���Ǳ���
		File src=null;
		if(!flag)
			src=saveWindow();
		else {
			if(openFile!=null)
				src=openFile;
			else
				src=saveWindow();//ʵ�ִ�ѱ��浱���Ϊ�Ĵ���
		}
		if(src!=null) {
			//��Դ����ģ����Զ��ر�������ˢ�»�����
			try(ObjectOutputStream ShapeObject=new ObjectOutputStream(new FileOutputStream(src))) {
				for(int i=0;i<=index;i++) {
					if(i==index)
						shapesQueue[i].EOF=false;
					ShapeObject.writeObject(shapesQueue[i]);
				}
				lastEOF=index;//ʵ��ͬһ�ļ�����޸�,���Ͻ��������ƺ�
				isChanged=false;
				if(!flag)//����ļ����ʱ,�Զ���OpenFile���ļ������Ϊsrc(���֮����ļ�)
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

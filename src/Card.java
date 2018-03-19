package src;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Card extends JLabel implements MouseListener{
	public String path;
	public int color;//1-蓝色，2-绿色,3-红色,4-黄色,0-未定（万能牌）
	public int value;//0-9普通牌, 22——+2 , 44——+4 , 33——万能牌 , 10——翻转 , 11——禁
	public int level;//0-9、翻转、禁=1 , 万能牌=2 , +2=1 , +4=4
	public boolean isClicked=false;
	public Card(String path,int color,int value,int level)
	{
		this.setSize(110, 172);//221*343
		this.path=path;
		setIcon(path,this);
		this.color=color;
		this.value=value;
		this.level=level;
		this.addMouseListener(this);
	}
	public void setIcon(String file,JLabel com)
	{  
		  ImageIcon ico=new ImageIcon(file);  
		  Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);  
		  ico=new ImageIcon(temp);  
		  com.setIcon(ico);  
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this)
		{
			if(this.isClicked==false)
			{
				this.setLocation(this.getX(), this.getY()-16);
				isClicked=true;
			}
			else
			{
				this.setLocation(this.getX(), this.getY()+16);
				isClicked=false;
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

package src;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class help extends JFrame implements ActionListener,MouseListener{
	JPanel jp=new JPanel();
	JPanel jpbutton=new JPanel();
	JPanel jpall=new JPanel();
	CardLayout card=new CardLayout();
	JLabel jl[]=new JLabel[8],jlbg=new JLabel();
	JButton back,exit,forward;
	int NumOfChoose=0;
	public help(){
		jpall.setLayout(null);
		jpbutton.setLayout(null);
		jpbutton.setOpaque(false);
		jpbutton.setBounds(0, 760, 1000, 240);
		back=new JButton();
		back.addActionListener(this);
		back.addMouseListener(this);
		exit=new JButton();
		exit.addActionListener(this);
		exit.addMouseListener(this);
		forward=new JButton();
		forward.addActionListener(this);
		forward.addMouseListener(this);
		back.setBounds(100, 15, 200, 100);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		exit.setBounds(400, 15, 200, 100);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		forward.setBounds(700, 15, 200, 100);
		forward.setBorderPainted(false);
		forward.setContentAreaFilled(false);
		setButtonIcon("images/help_back.png", back);
		setButtonIcon("images/help_exit.png", exit);
		setButtonIcon("images/help_forward.png", forward);
		jlbg.setBounds(0, 0, 1000, 240);
		setIcon("images/help_buttonbg.jpg",jlbg);
		
		
		jpbutton.add(back);
		jpbutton.add(exit);
		jpbutton.add(forward);
		jpbutton.add(jlbg);
		
		jp.setBounds(0, 0, 1000, 760);
		jp.setLayout(card);
		for(int i=0;i<8;i++)
		{
			jl[i]=new JLabel();
			jl[i].setBounds(0, 0, 1000, 760);
			jp.add(""+i,jl[i]);
		}
		setIcon("images/help_1.jpg",jl[0]);
		setIcon("images/help_2.jpg",jl[1]);
		setIcon("images/help_3.jpg",jl[2]);
		setIcon("images/help_4.jpg",jl[3]);
		setIcon("images/help_5.jpg",jl[4]);
		setIcon("images/help_6.jpg",jl[5]);
		setIcon("images/help_7.jpg",jl[6]);
		setIcon("images/help_8.jpg",jl[7]);
		
		jpall.add(jpbutton);
		jpall.add(jp);
		add(jpall);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowsWedth = 1000;
		int windowsHeight = 1000;
		//设置窗体在显示器居中显示
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		setUndecorated(true);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exit){
			this.setVisible(false);
		}
		if(e.getSource()==back){
			if(NumOfChoose!=0)
			{
				NumOfChoose--;
				card.show(jp, ""+NumOfChoose);
			}
		}
		if(e.getSource()==forward){
			if(NumOfChoose!=7)
			{
				NumOfChoose++;
				card.show(jp, ""+NumOfChoose);
			}
		}
	}
	public void setIcon(String file,JLabel com)
	{ 
		ImageIcon ico=new ImageIcon(file);  
		Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		com.setIcon(ico);  
	}
	public void setButtonIcon(String file,JButton com)
	{ 
	     ImageIcon ico=new ImageIcon(file);  
	     Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);  
	     ico=new ImageIcon(temp);  
	     com.setIcon(ico);  
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==back){
			back.setBounds(90, 10, 220, 110);
			setButtonIcon("images/help_back.png", back);
		}
		if(e.getSource()==exit){
			exit.setBounds(390, 10, 220, 110);
			setButtonIcon("images/help_exit.png", exit);
		}
		if(e.getSource()==forward){
			forward.setBounds(690, 10, 220, 110);
			setButtonIcon("images/help_forward.png", forward);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==back){
			back.setBounds(100, 15, 200, 100);
			setButtonIcon("images/help_back.png", back);
		}
		if(e.getSource()==exit){
			exit.setBounds(400, 15, 200, 100);
			setButtonIcon("images/help_exit.png", exit);
		}
		if(e.getSource()==forward){
			forward.setBounds(700, 15, 200, 100);
			setButtonIcon("images/help_forward.png", forward);
		}
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

package src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StartMenu extends JFrame implements ActionListener,MouseListener{
	JPanel jp=new JPanel();
	JButton jb1,jb2,jb3,jb4;
	Thread th;
	AudioPlayWave mybgm;
	public StartMenu()
	{
		jp.setLayout(null);
		jb1=new JButton();
		jb2=new JButton();
		jb3=new JButton();
		jb4=new JButton();
		//jb1设置
		ImageIcon ico=new ImageIcon("images/opt1.jpg");
		Image temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		jb1=new JButton(ico);
		jb1.setBounds(105, 100, 156, 242);
		jb1.addActionListener(this);
		jb1.addMouseListener(this);
		jp.add(jb1);
		//jb2设置
		ico=new ImageIcon("images/opt2.jpg");
		temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		jb2=new JButton(ico);
		jb2.setBounds(310, 150, 156, 242);
		jb2.addActionListener(this);
		jb2.addMouseListener(this);
		jp.add(jb2);
		//jb3设置
		ico=new ImageIcon("images/opt3.jpg");
		temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		jb3=new JButton(ico);
		jb3.setBounds(515, 200, 156, 242);
		jb3.addActionListener(this);
		jb3.addMouseListener(this);
		jp.add(jb3);
		//jb4设置
		ico=new ImageIcon("images/opt4.jpg");
		temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		jb4=new JButton(ico);
		jb4.setBounds(720, 250, 156, 242);
		jb4.addActionListener(this);
		jb4.addMouseListener(this);
		jp.add(jb4);
        //设置窗口（大小、背景、音乐等）
		mybgm=new AudioPlayWave("video/bgm.wav");
		mybgm.start();
        ImageIcon background = new ImageIcon("images/bg.jpg");  
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowsWedth = 1000;
		int windowsHeight = 600;
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		setSize(1000,600);
		temp=background.getImage().getScaledInstance(this.getWidth(),this.getHeight(),background.getImage().SCALE_DEFAULT);  
		background=new ImageIcon(temp);  
		JLabel label = new JLabel(background); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		setUndecorated(true);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());  
		jp.add(label);
	    this.add(jp);
	    setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			this.setVisible(false);
			new SinglePlayerFrame();
			mybgm.suspend();
		}
		if(e.getSource()==jb2)
		{
			JOptionPane.showMessageDialog(this,"暂不支持此功能！"); 
		}
		if(e.getSource()==jb3)
		{
			new help();
		}
		if(e.getSource()==jb4)
		{
			System.exit(0);
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			ImageIcon ico=new ImageIcon("images/opt1.jpg");
			Image temp=ico.getImage().getScaledInstance(172,266,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb1.setIcon(ico);
			jb1.setBounds(97, 88, 172, 266);
		}
		if(e.getSource()==jb2)
		{
			ImageIcon ico=new ImageIcon("images/opt2.jpg");
			Image temp=ico.getImage().getScaledInstance(172,266,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb2.setIcon(ico);
			jb2.setBounds(302, 138, 172, 266);
		}
		if(e.getSource()==jb3)
		{
			ImageIcon ico=new ImageIcon("images/opt3.jpg");
			Image temp=ico.getImage().getScaledInstance(172,266,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb3.setIcon(ico);
			jb3.setBounds(506, 182, 172, 266);
		}
		if(e.getSource()==jb4)
		{
			ImageIcon ico=new ImageIcon("images/opt4.jpg");
			Image temp=ico.getImage().getScaledInstance(172,266,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb4.setIcon(ico);
			jb4.setBounds(712, 238, 172, 266);
		}
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			ImageIcon ico=new ImageIcon("images/opt1.jpg");
			Image temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb1.setIcon(ico);
			jb1.setBounds(105, 100, 156, 242);
		}
		if(e.getSource()==jb2)
		{
			ImageIcon ico=new ImageIcon("images/opt2.jpg");
			Image temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb2.setIcon(ico);
			jb2.setBounds(310, 150, 156, 242);
		}
		if(e.getSource()==jb3)
		{
			ImageIcon ico=new ImageIcon("images/opt3.jpg");
			Image temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb3.setIcon(ico);
			jb3.setBounds(515, 200, 156, 242);
		}
		if(e.getSource()==jb4)
		{
			ImageIcon ico=new ImageIcon("images/opt4.jpg");
			Image temp=ico.getImage().getScaledInstance(156,242,ico.getImage().SCALE_DEFAULT);  
			ico=new ImageIcon(temp);  
			jb4.setIcon(ico);
			jb4.setBounds(720, 250, 156, 242);
		}
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}


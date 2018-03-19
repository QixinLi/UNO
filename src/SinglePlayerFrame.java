package src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SinglePlayerFrame extends JFrame implements MouseListener{
	JPanel jp=new JPanel();//主框架
	JPanel jpcard=new JPanel();//玩家牌区
	JLabel jl_desk=new JLabel();//桌布
	JLabel jlplayer[]=new JLabel[6];//玩家头像
	JLabel remaincard[]=new JLabel[6];//剩余牌数
	JLabel cardback[]=new JLabel[6];//卡背
	JLabel TurnTo=new JLabel();//顺时针，逆时针标志
	JLabel CardOut=new JLabel();//打出的牌
	JLabel Flag=new JLabel();//旗帜（轮到谁出牌）
	JLabel send=new JLabel("出牌");
	JLabel pass=new JLabel("不出");
	JLabel red=new JLabel("红");
	JLabel blue=new JLabel("蓝");
	JLabel green=new JLabel("绿");
	JLabel yellow=new JLabel("黄");
	JLabel message=new JLabel();
	
	Point p[]=new Point[6];//flag位置
	
	Card card[]=new Card[108];//原牌堆
	//List<Card> washedcard=new ArrayList();//已经被洗过的牌（牌堆）
	int NumOfCard=107;//牌堆剩余牌数
	List<Card> calledcard=new ArrayList();//打出去的牌
	int NumOfTempCard=0;//打出去的牌数
	int turn=-1;//转向，逆时针-1，顺时针1
	int offcolor=0;//当前颜色
	int offvalue=0;//当前值
	int offlevel=0;//当前级别
	boolean requirechoose=false;//是否需要进一步选择
	int mychoose=0;//我的选择为（1红，2蓝，3绿，4黄）
	int play;//当前玩家
	
	List<Card> player[]=new ArrayList[6];//玩家申明
	int NumOfPlayer[]=new int[6];
	
	Thread th=new Thread();
	AudioClip call; 
	AudioClip draw;
	AudioClip draw_2;
	AudioClip draw_4;
	AudioClip uno;
	AudioClip winner;
	AudioClip loser;
	AudioClip pass1;
	AudioClip pass2;
	AudioClip pass3;
	AudioClip pass4;
	AudioClip call2;
	
	public SinglePlayerFrame()//构造函数
	{
		jp.setLayout(null);
		
		jpcard.setLayout(null);
		jpcard.setBounds(200, 700, 800, 200);
		jpcard.setOpaque(false);
		
		TurnTo.setBounds(375, 125, 650, 650);
		setIcon("images/逆时针.png",TurnTo);
		jp.add(TurnTo);
		
		Flag.setSize(50, 50);
		setIcon("images/flag.png",Flag);
		jp.add(Flag);
		
		CardOut.setBounds(635,374,110,172);
		setIcon("images/back.jpg",CardOut);
		jp.add(CardOut);
		
		message.setBounds(635, 546, 110, 35);
		message.setFont(new Font("楷体",Font.PLAIN,35));
		message.setForeground(Color.white);
		jp.add(message);
		
		send.setBounds(900,600,100,100);
		send.setFont(new Font("楷体",Font.PLAIN,35));
		send.setForeground(Color.green);
		send.addMouseListener(this);
		send.setVisible(false);
		jp.add(send);
		pass.setBounds(1000,600,100,100);
		pass.setFont(new Font("楷体",Font.PLAIN,35));
		pass.setForeground(Color.green);
		pass.addMouseListener(this);
		pass.setVisible(false);
		jp.add(pass);
		
		red.setBounds(980,670,35,35);
		red.setFont(new Font("楷体",Font.PLAIN,35));
		red.setForeground(Color.red);
		red.addMouseListener(this);
		red.setVisible(false);
		jp.add(red);
		blue.setBounds(1025,670,35,35);
		blue.setFont(new Font("楷体",Font.PLAIN,35));
		blue.setForeground(Color.blue);
		blue.addMouseListener(this);
		blue.setVisible(false);
		jp.add(blue);
		green.setBounds(1070,670,35,35);
		green.setFont(new Font("楷体",Font.PLAIN,35));
		green.setForeground(Color.		green);
		green.addMouseListener(this);
		green.setVisible(false);
		jp.add(		green);
		yellow.setBounds(1115,670,35,35);
		yellow.setFont(new Font("楷体",Font.PLAIN,35));
		yellow.setForeground(Color.yellow);
		yellow.addMouseListener(this);
		yellow.setVisible(false);
		jp.add(yellow);
		
		LoadPlayerImage();
		LoadMusic();
		
		for(int i=0;i<6;i++)
		{
			player[i]=new ArrayList<Card>(); //玩家牌
			NumOfPlayer[i]=0;
		}
		
		CreateCard();
		Shuffle();
		jp.add(jpcard);
		Perflop();
		
		jl_desk.setBounds(200, 100, 1000, 700);
		setIcon("images/deskbg.png",jl_desk);
		jp.add(jl_desk);
		
		
		
		//设置窗口（大小、背景等）
        ImageIcon background = new ImageIcon("images/singleframebg.jpg");  
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowsWedth = 1400;
		int windowsHeight = 900;
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		setSize(1400,900);
		Image temp=background.getImage().getScaledInstance(this.getWidth(),this.getHeight(),background.getImage().SCALE_DEFAULT);  
		background=new ImageIcon(temp);  
		JLabel label = new JLabel(background); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		setUndecorated(true);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());  
		jp.add(label);
	    this.add(jp);
	    setVisible(true);
	    
	    StartGame();
	}
	private void LoadMusic()
	{
		try {      
		    File f = new File("video/啪.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    call = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/draw_card.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    draw = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/draw_2.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    draw_2 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/draw_4.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    draw_4 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/uno.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    uno = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/胜利.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    winner = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/败北.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    loser = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/要不起男.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    pass1 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/过.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    pass2 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/不要男.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    pass3 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/pass男.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    pass4 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		try {      
		    File f = new File("video/大你.wav"); 
		    URI uri = f.toURI();
		    URL url = uri.toURL();  //解析地址
		    call2 = Applet.newAudioClip(url);
		   } 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	}
	private void LoadPlayerImage()//加载用户图片
	{
		for(int i=0;i<6;i++)
		{
			jlplayer[i]=new JLabel();
			remaincard[i]=new JLabel();
			cardback[i]=new JLabel();
		}		
		jlplayer[0].setBounds(1020, 720, 160, 160);
		setIcon("images/player0.jpg",jlplayer[0]);
		jp.add(jlplayer[0]);
		
		jlplayer[1].setBounds(20, 590, 100, 100);
		setIcon("images/player1.jpg",jlplayer[1]);
		jp.add(jlplayer[1]);
		cardback[1].setBounds(130, 590, 110, 172);
		setIcon("images/back.jpg",cardback[1]);
		jp.add(cardback[1]);
		remaincard[1].setBounds(20,690,100,72);
		remaincard[1].setText(""+NumOfPlayer[1]);
		remaincard[1].setFont(new Font("Segoe Script",Font.PLAIN,35));
		remaincard[1].setForeground(Color.YELLOW);
		jp.add(remaincard[1]);
		
		jlplayer[2].setBounds(20, 170, 100, 100);
		setIcon("images/player2.gif",jlplayer[2]);
		jp.add(jlplayer[2]);
		cardback[2].setBounds(130, 170, 110, 172);
		setIcon("images/back.jpg",cardback[2]);
		jp.add(cardback[2]);
		remaincard[2].setBounds(20,270,100,72);
		remaincard[2].setText(""+NumOfPlayer[2]);
		remaincard[2].setFont(new Font("Segoe Script",Font.PLAIN,35));
		remaincard[2].setForeground(Color.YELLOW);
		jp.add(remaincard[2]);
		
		jlplayer[3].setBounds(590, 10, 100, 100);
		setIcon("images/player3.jpg",jlplayer[3]);
		jp.add(jlplayer[3]);
		cardback[3].setBounds(700, 10, 110, 172);
		setIcon("images/back.jpg",cardback[3]);
		jp.add(cardback[3]);
		remaincard[3].setBounds(590,110,100,72);
		remaincard[3].setText(""+NumOfPlayer[3]);
		remaincard[3].setFont(new Font("Segoe Script",Font.PLAIN,35));
		remaincard[3].setForeground(Color.YELLOW);
		jp.add(remaincard[3]);
		
		jlplayer[4].setBounds(1150, 170, 100, 100);
		setIcon("images/player4.jpg",jlplayer[4]);
		jp.add(jlplayer[4]);
		cardback[4].setBounds(1260, 170, 110, 172);
		setIcon("images/back.jpg",cardback[4]);
		jp.add(cardback[4]);
		remaincard[4].setBounds(1150,270,100,72);
		remaincard[4].setText(""+NumOfPlayer[4]);
		remaincard[4].setFont(new Font("Segoe Script",Font.PLAIN,35));
		remaincard[4].setForeground(Color.YELLOW);
		jp.add(remaincard[4]);
		
		jlplayer[5].setBounds(1150, 550, 100, 100);
		setIcon("images/player5.gif",jlplayer[5]);
		jp.add(jlplayer[5]);
		cardback[5].setBounds(1260, 550, 110, 172);
		setIcon("images/back.jpg",cardback[5]);
		jp.add(cardback[5]);
		remaincard[5].setBounds(1150,650,100,72);
		remaincard[5].setText(""+NumOfPlayer[5]);
		remaincard[5].setFont(new Font("Segoe Script",Font.PLAIN,35));
		remaincard[5].setForeground(Color.YELLOW);
		jp.add(remaincard[5]);
		
		p[0]=new Point(1020,720);
		p[1]=new Point(20,590);
		p[2]=new Point(20,170);
		p[3]=new Point(590, 10);
		p[4]=new Point(1150, 170);
		p[5]=new Point(1150, 550);
	}
	private void CreateCard()//创建所有卡牌
	{
		//定义蓝牌
		card[0]=new Card("images/b0.jpg",1,0,1);
		card[1]=new Card("images/b1.jpg",1,1,1);
		card[2]=new Card("images/b1.jpg",1,1,1);
		card[3]=new Card("images/b2.jpg",1,2,1);
		card[4]=new Card("images/b2.jpg",1,2,1);
		card[5]=new Card("images/b3.jpg",1,3,1);
		card[6]=new Card("images/b3.jpg",1,3,1);
		card[7]=new Card("images/b4.jpg",1,4,1);
		card[8]=new Card("images/b4.jpg",1,4,1);
		card[9]=new Card("images/b5.jpg",1,5,1);
		card[10]=new Card("images/b5.jpg",1,5,1);
		card[11]=new Card("images/b6.jpg",1,6,1);
		card[12]=new Card("images/b6.jpg",1,6,1);
		card[13]=new Card("images/b7.jpg",1,7,1);
		card[14]=new Card("images/b7.jpg",1,7,1);
		card[15]=new Card("images/b8.jpg",1,8,1);
		card[16]=new Card("images/b8.jpg",1,8,1);
		card[17]=new Card("images/b9.jpg",1,9,1);
		card[18]=new Card("images/b9.jpg",1,9,1);
		card[19]=new Card("images/breverse.jpg",1,10,1);
		card[20]=new Card("images/breverse.jpg",1,10,1);
		card[21]=new Card("images/bskip.jpg",1,11,1);
		card[22]=new Card("images/bskip.jpg",1,11,1);
		card[23]=new Card("images/bdraw2.jpg",1,22,1);
		card[24]=new Card("images/bdraw2.jpg",1,22,1);
		//定义绿牌
		card[25]=new Card("images/g0.jpg",2,0,1);
		card[26]=new Card("images/g1.jpg",2,1,1);
		card[27]=new Card("images/g1.jpg",2,1,1);
		card[28]=new Card("images/g2.jpg",2,2,1);
		card[29]=new Card("images/g2.jpg",2,2,1);
		card[30]=new Card("images/g3.jpg",2,3,1);
		card[31]=new Card("images/g3.jpg",2,3,1);
		card[32]=new Card("images/g4.jpg",2,4,1);
		card[33]=new Card("images/g4.jpg",2,4,1);
		card[34]=new Card("images/g5.jpg",2,5,1);
		card[35]=new Card("images/g5.jpg",2,5,1);
		card[36]=new Card("images/g6.jpg",2,6,1);
		card[37]=new Card("images/g6.jpg",2,6,1);
		card[38]=new Card("images/g7.jpg",2,7,1);
		card[39]=new Card("images/g7.jpg",2,7,1);
		card[40]=new Card("images/g8.jpg",2,8,1);
		card[41]=new Card("images/g8.jpg",2,8,1);
		card[42]=new Card("images/g9.jpg",2,9,1);
		card[43]=new Card("images/g9.jpg",2,9,1);
		card[44]=new Card("images/greverse.jpg",2,10,1);
		card[45]=new Card("images/greverse.jpg",2,10,1);
		card[46]=new Card("images/gskip.jpg",2,11,1);
		card[47]=new Card("images/gskip.jpg",2,11,1);
		card[48]=new Card("images/gdraw2.jpg",2,22,1);
		card[49]=new Card("images/gdraw2.jpg",2,22,1);
		//定义红牌
		card[50]=new Card("images/r0.jpg",3,0,1);
		card[51]=new Card("images/r1.jpg",3,1,1);
		card[52]=new Card("images/r1.jpg",3,1,1);
		card[53]=new Card("images/r2.jpg",3,2,1);
		card[54]=new Card("images/r2.jpg",3,2,1);
		card[55]=new Card("images/r3.jpg",3,3,1);
		card[56]=new Card("images/r3.jpg",3,3,1);
		card[57]=new Card("images/r4.jpg",3,4,1);
		card[58]=new Card("images/r4.jpg",3,4,1);
		card[59]=new Card("images/r5.jpg",3,5,1);
		card[60]=new Card("images/r5.jpg",3,5,1);
		card[61]=new Card("images/r6.jpg",3,6,1);
		card[62]=new Card("images/r6.jpg",3,6,1);
		card[63]=new Card("images/r7.jpg",3,7,1);
		card[64]=new Card("images/r7.jpg",3,7,1);
		card[65]=new Card("images/r8.jpg",3,8,1);
		card[66]=new Card("images/r8.jpg",3,8,1);
		card[67]=new Card("images/r9.jpg",3,9,1);
		card[68]=new Card("images/r9.jpg",3,9,1);
		card[69]=new Card("images/rreverse.jpg",3,10,1);
		card[70]=new Card("images/rreverse.jpg",3,10,1);
		card[71]=new Card("images/rskip.jpg",3,11,1);
		card[72]=new Card("images/rskip.jpg",3,11,1);
		card[73]=new Card("images/rdraw2.jpg",3,22,1);
		card[74]=new Card("images/rdraw2.jpg",3,22,1);
		//定义黄色
		card[75]=new Card("images/y0.jpg",4,0,1);
		card[76]=new Card("images/y1.jpg",4,1,1);
		card[77]=new Card("images/y1.jpg",4,1,1);
		card[78]=new Card("images/y2.jpg",4,2,1);
		card[79]=new Card("images/y2.jpg",4,2,1);
		card[80]=new Card("images/y3.jpg",4,3,1);
		card[81]=new Card("images/y3.jpg",4,3,1);
		card[82]=new Card("images/y4.jpg",4,4,1);
		card[83]=new Card("images/y4.jpg",4,4,1);
		card[84]=new Card("images/y5.jpg",4,5,1);
		card[85]=new Card("images/y5.jpg",4,5,1);
		card[86]=new Card("images/y6.jpg",4,6,1);
		card[87]=new Card("images/y6.jpg",4,6,1);
		card[88]=new Card("images/y7.jpg",4,7,1);
		card[89]=new Card("images/y7.jpg",4,7,1);
		card[90]=new Card("images/y8.jpg",4,8,1);
		card[91]=new Card("images/y8.jpg",4,8,1);
		card[92]=new Card("images/y9.jpg",4,9,1);
		card[93]=new Card("images/y9.jpg",4,9,1);
		card[94]=new Card("images/yreverse.jpg",4,10,1);
		card[95]=new Card("images/yreverse.jpg",4,10,1);
		card[96]=new Card("images/yskip.jpg",4,11,1);
		card[97]=new Card("images/yskip.jpg",4,11,1);
		card[98]=new Card("images/ydraw2.jpg",4,22,1);
		card[99]=new Card("images/ydraw2.jpg",4,22,1);
		//定义万能牌
		card[100]=new Card("images/wild.jpg",0,33,2);
		card[101]=new Card("images/wild.jpg",0,33,2);
		card[102]=new Card("images/wild.jpg",0,33,2);
		card[103]=new Card("images/wild.jpg",0,33,2);
		card[104]=new Card("images/wild4.jpg",0,44,4);
		card[105]=new Card("images/wild4.jpg",0,44,4);
		card[106]=new Card("images/wild4.jpg",0,44,4);
		card[107]=new Card("images/wild4.jpg",0,44,4);
	}
	private void Shuffle()//洗牌
	{
		//打乱顺序
		for(int i=0;i<200;i++){
			Random random=new Random();
			int a=random.nextInt(108);
			int b=random.nextInt(108);
			Card k=card[a];
			card[a]=card[b];
			card[b]=k;
		}
	}
	private void Perflop()//发牌
	{
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<6;j++)
			{
				Draw1(j);
			}
		}
	}
	private void Draw1 (int i)//摸一张牌
	{
		if(NumOfCard<0)
		{
			Random random=new Random();
			boolean isvisited[]=new boolean[calledcard.size()-1];
			int temp;
			for(int g=0;g<calledcard.size()-1;g++)
			{
				isvisited[g]=false;
			}
			for(int j=0;j<calledcard.size()-1;j++)
			{
				do
				{
					temp=random.nextInt(calledcard.size()-1);
				}while(isvisited[temp]);
				card[0]=calledcard.get(temp);
				NumOfCard++;
			}
			int num=calledcard.size()-1;
			for(int h=0;h<num;h++)
			{
				calledcard.remove(0);
			}
		}
		if(i==0)
		{
			player[0].add(card[NumOfCard]);
			player[0].get(player[0].size()-1).setLocation((680-35*(player[0].size()-1)), 14);
			jpcard.add(player[0].get(player[0].size()-1));
			player[0].get(player[0].size()-1).setVisible(true);
			NumOfCard--;
			jpcard.repaint();
		}
		else
		{
			player[i].add(card[NumOfCard]);
			NumOfCard--;
		}
		NumOfPlayer[i]++;
		remaincard[i].setText(""+NumOfPlayer[i]);
	}
	private boolean isGameOver()//判断游戏是否结束
	{
		boolean isGameOver=false;
		for(int i=0;i<6;i++)
		{
			if(player[i].size()==0)
			{
				isGameOver=true;
				if(i==0)
				{
					winner.play();
					JOptionPane.showMessageDialog(this,"恭喜你获得胜利！游戏结束"); 
				}
				else
				{
					loser.play();
					JOptionPane.showMessageDialog(this,(i+1)+"号玩家赢得游戏的胜利！游戏结束"); 
				}
			}

		}
		return isGameOver;
	}
	private boolean canCall(Card ca)//判断打出该牌是否符合规则
	{
		boolean canCall=false;
		if(ca.level>offlevel)//如果卡牌级别比当前级别高，则可以出牌
		{
			canCall=true;
		}
		else
		{
			if(ca.color==offcolor)//如果卡牌颜色和当前颜色一样，则可以出牌
			{
				canCall=true;
			}
			else if(ca.value==offvalue)//如果卡牌数值和当前数值一样，则可以出牌
			{
				canCall=true;
			}
		}
		return canCall;
	}
	private void StartGame()//开始游戏
	{
		Random ra=new Random();
		play=ra.nextInt(6);
		Flag.setLocation(p[play]);
		th = new Thread() 
		{
			public void run() {
				while(!isGameOver())
				{
					if(play==0)
					{
						PlayerCall();
					}
					else
					{
						try {
							Thread.sleep(2500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ComputerTurnSend();
					}
				}
			}
		};
		th.start();
	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==send)
		{
			PlayerTurnSend();
		}
		if(e.getSource()==pass)
		{
			Draw1(0);//摸一张牌
			passMusic();
			play=(6+turn)%6;
			synchronized(th){
				th.notify();
			}
		}
		if(e.getSource()==red)
		{
			mychoose=1;
			synchronized(th){
				th.notify();
			}
		}
		if(e.getSource()==blue)
		{
			mychoose=2;
			synchronized(th){
				th.notify();
			}
		}
		if(e.getSource()==green)
		{
			mychoose=3;
			synchronized(th){
				th.notify();
			}
		}
		if(e.getSource()==yellow)
		{
			mychoose=4;
			synchronized(th){
				th.notify();
			}
		}
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	public void PlayerCall()//玩家回合
	{
		send.setVisible(true);
		pass.setVisible(true);
		synchronized(th)
		{
			try {
				th.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		send.setVisible(false);
		pass.setVisible(false);
		if(requirechoose)
		{
			red.setVisible(true);
			blue.setVisible(true);
			green.setVisible(true);
			yellow.setVisible(true);
			synchronized(th)
			{
				try {
					th.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			switch(mychoose)
			{
			case 1:
				offcolor=3;
				message.setText("转红");
				break;
			case 2:
				offcolor=1;
				message.setText("转蓝");
				break;
			case 3:
				offcolor=2;
				message.setText("转绿");
				break;
			case 4:
				offcolor=4;
				message.setText("转黄");
				break;
			}
			mychoose=0;
			requirechoose=false;
			red.setVisible(false);
			blue.setVisible(false);
			green.setVisible(false);
			yellow.setVisible(false);
			
		}
		if(player[0].size()==1)
		{
			if(player[0].get(0).value>=0&&player[0].get(0).value<=9)
			{
				uno.play();
				try {
					th.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				Draw1(0);
			}
		}
	}
	public void PlayerTurnSend()//玩家回合出牌
	{
		int i=0;
		for(;i<player[0].size();i++)
		{
			if(player[0].get(i).isClicked)
			{
				break;
			}
		}
		if(i<player[0].size())
		{
			if(canCall(player[0].get(i)))
			{
				callMusic();
				String str="";
				switch(player[0].get(i).color)
				{
				case 1:
					str+="蓝";
					break;
				case 2:
					str+="绿";
					break;
				case 3:
					str+="红";
					break;
				case 4:
					str+="黄";
					break;
				}
				setIcon(player[0].get(i).path,CardOut);
				if(player[0].get(i).value==22)//如果是+2
				{
					offcolor=player[0].get(i).color;
					offvalue=player[0].get(i).value;
					offlevel=1;
					int nextplayer=(6+turn)%6;
					for(int j=0;j<2;j++)
					{
						Draw1(nextplayer);
					}
					message.setText(str+"+2");
					play=(6+turn*2)%6;
					try {
						th.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					draw_2.play();
				}
				else if(player[0].get(i).value==44)//如果是+4
				{
					requirechoose=true;
					int nextplayer=(0+turn+6)%6;
					for(int j=0;j<4;j++)
					{
						Draw1(nextplayer);
					}
					offvalue=0;
					offlevel=1;
					play=(6+turn*2)%6;
					try {
						th.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					draw_4.play();
				}
				else if(player[0].get(i).value==33)//如果是万能牌
				{
					requirechoose=true;
					offvalue=0;
					offlevel=1;
					play=(6+turn)%6;
				}
				else if(player[0].get(i).value==10)//如果是翻转
				{
					if(turn==1)//如果原来是顺时针
					{
						setIcon("images/逆时针.png",TurnTo);
						turn=-1;
					}
					else if(turn==-1)
					{
						setIcon("images/顺时针.png",TurnTo);
						turn=1;
					}
					offcolor=player[0].get(i).color;
					offvalue=player[0].get(i).value;
					offlevel=player[0].get(i).level;
					message.setText(str+"翻转");
					play=(6+turn)%6;
				}
				else if(player[0].get(i).value==11)//如果是禁
				{
					offcolor=player[0].get(i).color;
					offvalue=player[0].get(i).value;
					offlevel=player[0].get(i).level;
					message.setText(str+"禁");
					play=(6+turn*2)%6;
				}
				else{
					offcolor=player[0].get(i).color;
					offvalue=player[0].get(i).value;
					offlevel=player[0].get(i).level;
					message.setText(str+player[0].get(i).value);
					play=(6+turn)%6;
				}
				player[0].get(i).setVisible(false);
				jpcard.remove(player[0].get(i));
				calledcard.add(player[0].get(i));
				player[0].remove(i);
				for(int j=i;j<player[0].size();j++)
				{
					player[0].get(j).setLocation(player[0].get(j).getX()+35, player[0].get(j).getY());
				}
				Flag.setLocation(p[play]);
				synchronized(th){
					th.notify();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this,"你所打出的牌不符合规则!"); 
			}
		}
	}
	public void ComputerTurnSend()//AI回合出牌
	{
		int i=0;
		boolean canCall=false;
		for(;i<player[play].size();i++)
		{
			if(canCall(player[play].get(i)))//找出能出的牌
			{
				canCall=true;
				break;
			}
		}
		if(canCall)
		{
			callMusic();
			int replay=play;
			String str="";
			switch(player[play].get(i).color)
			{
			case 1:
				str+="蓝";
				break;
			case 2:
				str+="绿";
				break;
			case 3:
				str+="红";
				break;
			case 4:
				str+="黄";
				break;
			}
			setIcon(player[play].get(i).path,CardOut);
			NumOfPlayer[play]--;
			remaincard[play].setText(""+NumOfPlayer[play]);
			if(player[play].get(i).value==22)//如果是+2
			{
				offcolor=player[play].get(i).color;
				offvalue=player[play].get(i).value;
				offlevel=1;
				int nextplayer=(6+play+turn)%6;
				for(int j=0;j<2;j++)
				{
					Draw1(nextplayer);
				}
				message.setText(str+"+2");
				play=(6+play+turn*2)%6;
				try {
					th.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				draw_2.play();
			}
			else if(player[play].get(i).value==44)//如果是+4
			{
				int nextplayer=(6+play+turn)%6;
				for(int j=0;j<4;j++)
				{
					Draw1(nextplayer);
				}
				Random ra=new Random();
				int choose=ra.nextInt(4)+1;//随机选择转的颜色
				switch(choose)
				{
				case 1:
					offcolor=3;
					message.setText("转红");
					break;
				case 2:
					offcolor=1;
					message.setText("转蓝");
					break;
				case 3:
					offcolor=2;
					message.setText("转绿");
					break;
				case 4:
					offcolor=4;
					message.setText("转黄");
					break;
				}
				offvalue=0;
				offlevel=1;
				play=(6+play+turn*2)%6;
				try {
					th.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				draw_4.play();
			}
			else if(player[play].get(i).value==33)//如果是万能牌
			{
				Random ra=new Random();
				int choose=ra.nextInt(4)+1;//随机选择转的颜色
				switch(choose)
				{
				case 1:
					offcolor=3;
					message.setText("转红");
					break;
				case 2:
					offcolor=1;
					message.setText("转蓝");
					break;
				case 3:
					offcolor=2;
					message.setText("转绿");
					break;
				case 4:
					offcolor=4;
					message.setText("转黄");
					break;
				}
				offvalue=0;
				offlevel=1;
				play=(6+play+turn)%6;
			}
			else if(player[play].get(i).value==10)//如果是翻转
			{
				if(turn==1)//如果原来是顺时针
				{
					setIcon("images/逆时针.png",TurnTo);
					turn=-1;
				}
				else if(turn==-1)
				{
					setIcon("images/顺时针.png",TurnTo);
					turn=1;
				}
				offcolor=player[play].get(i).color;
				offvalue=player[play].get(i).value;
				offlevel=player[play].get(i).level;
				message.setText(str+"翻转");
				play=(6+play+turn)%6;
			}
			else if(player[play].get(i).value==11)//如果是禁
			{
				offcolor=player[play].get(i).color;
				offvalue=player[play].get(i).value;
				offlevel=player[play].get(i).level;
				message.setText(str+"禁");
				play=(6+play+turn*2)%6;
			}
			else{
				offcolor=player[play].get(i).color;
				offvalue=player[play].get(i).value;
				offlevel=player[play].get(i).level;
				message.setText(str+player[play].get(i).value);
				play=(6+play+turn)%6;
			}
			calledcard.add(player[replay].get(i));
			player[replay].remove(i);
			if(player[replay].size()==1)
			{
				if(player[replay].get(0).value>=0&&player[replay].get(0).value<=9)
				{
					uno.play();
					try {
						th.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					Draw1(replay);
				}
			}
			
			Flag.setLocation(p[play]);
		}
		else
		{
			Draw1(play);
			passMusic();
			play=(6+play+turn)%6;
			Flag.setLocation(p[play]);
		}
	}	
	public void setIcon(String file,JLabel com)
	{ 
		ImageIcon ico=new ImageIcon(file);  
		Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);  
		ico=new ImageIcon(temp);  
		com.setIcon(ico);  
	}
	public void passMusic()
	{
		Random ra=new Random();
		int n=ra.nextInt(5);
		switch(n)
		{
		case 0:
			draw.play();
			break;
		case 1:
			pass1.play();
			break;
		case 2:
			pass2.play();
			break;
		case 3:
			pass3.play();
			break;
		case 4:
			pass4.play();
			break;		
		}
	}
	public void callMusic()
	{
		Random ra=new Random();
		int n=ra.nextInt(2);
		switch(n)
		{
		case 0:
			call.play();
			break;
		case 1:
			call2.play();
			break;	
		}
	}
}

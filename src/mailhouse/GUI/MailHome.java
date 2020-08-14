package mailhouse.GUI;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.*;

import mailhouse.client.Client;

public class MailHome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * new JFrame is created on main thread.
		 */
		SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		new MailHome();
		}
	});
	}

	public static  ImageIcon imageIcon;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * parent panel on frame which contains card layout
	 */
	final static JPanel parent = new JPanel();
	
	/**
	 * CardLayout refrences to be used on panel
	 */	
	final static CardLayout c1=new CardLayout();
	
	/**
	 * Contains path to icon image
	 */
	public static String icon = "mailhouse3blur.jpg";
	/**
	 * Contains path to background image
	 */
//	public static String back_grnd="D:\\java_workspace\\MailHouse\\src\\mailHouse.GUI\\mailhouse3blur.jpg";
	public static String back_grnd="mailhouse3blur.jpg";
	/**
	 * Generates a GUI layout for every object call
	 */
	public static Color clist;
	
	/**
	 * Client of network
	 */
	public Client client;
	
	public JTextField emailText;
	public JTextField passText;
	public JMenuBar menubar;
	public JMenu optionsMenu;
	public JMenuItem  inboxItem, composeItem, sentItem,logoutItem,draftsItem;
	public final JPanel main_su;
	public MailHome(){
		try{
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			this.client = new Client(addr,9999);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final JFrame homeFrame=new JFrame("MailHouse");
		Font fLabel=new Font("Comic Sans MS",Font.BOLD,screenSize.width/50);
		Font fhead=new Font("Algerian",Font.BOLD,screenSize.width/15);
		Font fText=new Font("Georgia",Font.BOLD,screenSize.width/51);
		//System.out.println(screenSize);
		Font fbutton=new Font("Comic Sans MS",Font.BOLD,screenSize.width/46);
		 JLabel Homebase;
		Color c=new Color(211,211,211);
		homeFrame.setSize(screenSize.width, screenSize.height);
		homeFrame.setVisible(true);
		homeFrame.setLocation(0,0);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon(icon);
		homeFrame.setIconImage(img.getImage());
		GuiHandler gh = new GuiHandler(this.client,this);
		
		
		homeFrame.add(parent);
		//menubar
		menubar = new JMenuBar();
		
		optionsMenu = new JMenu("Options");
		optionsMenu.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		
		logoutItem = new JMenuItem("Logout");
		inboxItem = new JMenuItem("Inbox");
		composeItem = new JMenuItem("Compose..");
		sentItem = new JMenuItem("Sent");
		draftsItem = new JMenuItem("Drafts");
		
		logoutItem.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		inboxItem.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		composeItem.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		sentItem.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		draftsItem.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		
		optionsMenu.add(logoutItem);
		optionsMenu.add(inboxItem);
		optionsMenu.add(composeItem);
		optionsMenu.add(sentItem);
		optionsMenu.add(draftsItem);
		
		menubar.add(optionsMenu);
		
		homeFrame.setJMenuBar(menubar);
		
		this.setUpMenuBarListeners();
		optionsMenu.setEnabled(false);;
		//end of menubar set up
		
		
		//Background as Jlabel
		
		Homebase = new JLabel();
		Homebase.setSize(screenSize.width, screenSize.height);
		BufferedImage imag = null;
		try {
		    imag = ImageIO.read(new File(back_grnd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Image dimg = imag.getScaledInstance(Homebase.getWidth(), Homebase.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		Homebase.setIcon(imageIcon);
		imageIcon = new ImageIcon(dimg);
		//homeFrame.add(Homebase);
		homeFrame.setResizable(false);
		
		
		//main panel
		main_su = new JPanel();
				//Card Layout 
				
				parent.setLayout(c1);
				parent.add(main_su,"1");
				
				
				

				
				c1.show(parent, "1");
		main_su.add(Homebase);
				
		//Label for heading
		JLabel headLabel=new JLabel("MAIL HOUSE");
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(fhead);
		headLabel.setBounds(screenSize.width/2, screenSize.height/80, screenSize.width/2, screenSize.height/10);
		Homebase.add(headLabel);
		
		//Label for emailid
		JLabel eLabel=new JLabel("EMAIL ID : ");
		eLabel.setForeground(Color.WHITE);
		eLabel.setFont(fLabel);
		eLabel.setBounds(screenSize.width/2, screenSize.height/8, screenSize.width/2, screenSize.height/4);
		Homebase.add(eLabel);
		
		//emabcail text fields
		emailText=new JTextField();
		emailText.setBounds(screenSize.width/2, (int) (screenSize.height/3.25), (int)(screenSize.width/(3.5)), screenSize.height/25);
		emailText.setFont(fLabel);
		Homebase.add(emailText);
		emailText.setHorizontalAlignment(JTextField.CENTER);
		  
		
		
		//Label for password
		JLabel pLabel=new JLabel("PASSWORD : ");
		pLabel.setForeground(Color.WHITE);
		pLabel.setFont(fLabel);
		pLabel.setBounds(screenSize.width/2, (int) (screenSize.height/3), screenSize.width/2, screenSize.height/4);
		Homebase.add(pLabel);

				
		//password text fields
		passText=new JPasswordField();
		passText.setBounds(screenSize.width/2, (int) (screenSize.height/1.9), (int)(screenSize.width/(3.5)), screenSize.height/25);
		passText.setFont(fText);
		Homebase.add(passText);
		passText.setHorizontalAlignment(JTextField.CENTER);
						
				
		//Button for Login
		JButton loginBut=new JButton("Login");
		loginBut.setActionCommand("login_click");
		loginBut.setBounds((int) (screenSize.width/(2)),(int) (screenSize.height/(1.5)), screenSize.width/8, screenSize.height/15);
		loginBut.setBorderPainted(true);
		loginBut.setFont(fbutton);
		loginBut.setFocusable(false);
		loginBut.addMouseListener(gh);
		Homebase.add(loginBut);		
	
		
		//Button for Register
		JButton regBut=new JButton("Register");
		regBut.setActionCommand("register_click");
		regBut.setBounds((int) (screenSize.width/(1.5)),(int) (screenSize.height/(1.5)), screenSize.width/8, screenSize.height/15);
		regBut.setBorderPainted(true);
		regBut.setFont(fbutton);
		regBut.setFocusable(false);
		regBut.addMouseListener(gh);
		Homebase.add(regBut);		

				
		
	} 
	
	public void setUpMenuBarListeners(){
		
		logoutItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				
				if(client.user!=null){
					client.user=null;
					client.mails=null;
					client.currentMail=null;
					parent.add(main_su,"1");
					c1.show(parent, "1");
					emailText.setText("");
					passText.setText("");
					optionsMenu.setEnabled(false);
				}
			}
			
			
		});
		
		inboxItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				if(client.user!=null){
					optionsMenu.setEnabled(true);
					final InboxScreen is = new InboxScreen(client);
					parent.add(main_su,"1");
					c1.show(parent, "1");
				}
				
			}
			
			
		});
		composeItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				
				if(client.user!=null){
					optionsMenu.setEnabled(true);
					final ComposeScreen is = new ComposeScreen(client);
		    		MailHome.parent.add(is,"3");
		    		MailHome.c1.show(MailHome.parent, "3");
				}
			}
			
			
		});
		
		sentItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				if(client.user!=null){
					optionsMenu.setEnabled(true);
					final SentScreen is = new SentScreen(client);
		    		MailHome.parent.add(is,"4");
		    		MailHome.c1.show(MailHome.parent, "4");
				}
				
			}
			
			
		});
		
		draftsItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				if(client.user!=null){
					optionsMenu.setEnabled(true);
					final DraftListScreen is = new DraftListScreen(client);
		    		MailHome.parent.add(is,"6");
		    		MailHome.c1.show(MailHome.parent, "6");
				}
				
			}
			
			
		});
	}

}

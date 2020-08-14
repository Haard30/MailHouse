package mailhouse.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mailhouse.client.Client;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.text.*;
import java.util.Calendar;
public class RegistrationScreen extends JPanel{
	
	public static JLabel baseRegister;
	
	Font fLabel=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/50);
	Font fText=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/100);
	Font fhead=new Font("Algerian",Font.BOLD,MailHome.screenSize.width/25);

	public JTextField emailToText;
	public JTextField subText,nameText,emailText;
	public JTextField passText,conpassText;
	public JRadioButton r1,r2;
	public JDatePickerImpl datePicker;
	Font fbutton=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/75);
	GuiHandler gh;
	Client c;
	public RegistrationScreen(Client c){
		this.c = c;
		gh = new GuiHandler(c);
		gh.rs = this;
		
		baseRegister = new JLabel();
		baseRegister.setSize(MailHome.screenSize.width, MailHome.screenSize.height);
		
		BufferedImage imag = null;
		try {
		    imag = ImageIO.read(new File(MailHome.back_grnd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Image dimg = imag.getScaledInstance(baseRegister.getWidth(), baseRegister.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		baseRegister.setIcon(imageIcon);
		imageIcon = new ImageIcon(dimg);
		this.add(baseRegister);
		
		
		//Label for heading
				JLabel headLabel=new JLabel("MAIL HOUSE-REGISTRATION");
				headLabel.setForeground(Color.WHITE);
				headLabel.setFont(fhead);
				headLabel.setBounds(MailHome.screenSize.width/3, MailHome.screenSize.height/80, MailHome.screenSize.width, MailHome.screenSize.height/10);
				baseRegister.add(headLabel);
				
	
		//Label for name
				JLabel nameLabel=new JLabel("Name : ");
				nameLabel.setForeground(Color.WHITE);
				nameLabel.setFont(fLabel);
				nameLabel.setBounds(MailHome.screenSize.width/2, MailHome.screenSize.height/100, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(nameLabel);

		//Label for gender
				JLabel genLabel=new JLabel("Gender : ");
				genLabel.setForeground(Color.WHITE);
				genLabel.setFont(fLabel);
				genLabel.setBounds(MailHome.screenSize.width/2,MailHome.screenSize.height/10, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(genLabel);

		//Label for date of birth
				JLabel dobLabel=new JLabel("Date Of Birth : ");
				dobLabel.setForeground(Color.WHITE);
				dobLabel.setFont(fLabel);
				dobLabel.setBounds(MailHome.screenSize.width/2,MailHome.screenSize.height/5, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(dobLabel);
				
		//Date Chooser
				UtilDateModel udm = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(udm);
				datePicker = new JDatePickerImpl(datePanel);
				datePicker.setBounds((int)(MailHome.screenSize.width/(1.5)),(int)(MailHome.screenSize.height/(3.20)), MailHome.screenSize.width/10, MailHome.screenSize.height/40);
				baseRegister.add(datePicker);
				
		//Label for emailid
				JLabel emailLabel=new JLabel("Email id : ");
				emailLabel.setForeground(Color.WHITE);
				emailLabel.setFont(fLabel);
				emailLabel.setBounds(MailHome.screenSize.width/2,MailHome.screenSize.height/3, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(emailLabel);
		
		//Label for password
				JLabel passLabel=new JLabel("Password : ");
				passLabel.setForeground(Color.WHITE);
				passLabel.setFont(fLabel);
				passLabel.setBounds(MailHome.screenSize.width/2,(int)(MailHome.screenSize.height/(2.25)), MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(passLabel);

		//Label for confirm password
				JLabel conpassLabel=new JLabel("Confirm Password : ");
				conpassLabel.setForeground(Color.WHITE);
				conpassLabel.setFont(fLabel);
				conpassLabel.setBounds(MailHome.screenSize.width/2,(int)(MailHome.screenSize.height/(1.8)), MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseRegister.add(conpassLabel);
				
				
		//name text fields
				nameText=new JTextField();
				nameText.setBounds((int)(MailHome.screenSize.width/(1.72)),(int) (MailHome.screenSize.height/(8.25)), (int)(MailHome.screenSize.width/(6)), MailHome.screenSize.height/30);
				nameText.setFont(fText);
				
				baseRegister.add(nameText);
				nameText.setForeground(Color.BLACK);
				nameText.setHorizontalAlignment(SwingConstants.CENTER);
				nameText.setOpaque(true);
				
		//Radio buttons for male female
				r1=new JRadioButton(" Male");    
				r2=new JRadioButton(" Female");
				r1.setBounds((int)(MailHome.screenSize.width/(1.68)), MailHome.screenSize.height/6, (int)(MailHome.screenSize.width/(8)), MailHome.screenSize.height/8);
				r2.setBounds((int)(MailHome.screenSize.width/(1.45)), MailHome.screenSize.height/6, (int)(MailHome.screenSize.width/(8)), MailHome.screenSize.height/8);
				ButtonGroup bg=new ButtonGroup();    
				bg.add(r1);bg.add(r2);
				r1.setFont(fLabel);
				r1.setSelected(true);
				r1.setForeground(Color.WHITE);
				r1.setOpaque(false);
				r2.setFont(fLabel);
				r2.setForeground(Color.WHITE);
				r2.setOpaque(false);
				baseRegister.add(r1);
				baseRegister.add(r2);
				
				
		//email text fields
				emailText=new JTextField();
				emailText.setBounds((int)(MailHome.screenSize.width/(1.6)),(int)(MailHome.screenSize.height/(2.25)), (int)(MailHome.screenSize.width/(6)), MailHome.screenSize.height/30);
				emailText.setFont(fText);
				
				baseRegister.add(emailText);
				emailText.setForeground(Color.BLACK);
				emailText.setHorizontalAlignment(SwingConstants.CENTER);
				emailText.setOpaque(true);
		
		//password text fields
				passText=new JPasswordField();
				passText.setBounds((int)(MailHome.screenSize.width/(1.6)),(int)(MailHome.screenSize.height/(1.78)), (int)(MailHome.screenSize.width/(6)), MailHome.screenSize.height/30);
				passText.setFont(fText);
				
				baseRegister.add(passText);
				passText.setForeground(Color.BLACK);
				passText.setHorizontalAlignment(SwingConstants.CENTER);
				passText.setOpaque(true);
		
		//confirm password text fields
				conpassText=new JPasswordField();
				conpassText.setBounds((int)(MailHome.screenSize.width/(1.4)),(int)(MailHome.screenSize.height/(1.5)), (int)(MailHome.screenSize.width/(6)), MailHome.screenSize.height/30);
				conpassText.setFont(fText);
				
				baseRegister.add(conpassText);
				conpassText.setForeground(Color.BLACK);
				conpassText.setHorizontalAlignment(SwingConstants.CENTER);
				conpassText.setOpaque(true);
				

			
		//Button to register
				JButton regBut=new JButton("Register");
				regBut.setBounds(MailHome.screenSize.width/2,(int) (MailHome.screenSize.height/(1.251)), MailHome.screenSize.width/10, MailHome.screenSize.height/15);
				regBut.setActionCommand("RegisterClick");
				regBut.setBorderPainted(true);
				regBut.setFont(fbutton);
				regBut.setFocusable(false);
				regBut.addMouseListener(gh);
				baseRegister.add(regBut);		
	
		//Button for back
				JButton backBut=new JButton("<--Back");
				backBut.setBounds(10,(int) (MailHome.screenSize.height/(100)), MailHome.screenSize.width/10, MailHome.screenSize.height/15);
				backBut.setActionCommand("BackClick");
				backBut.setBorderPainted(true);
				backBut.setFont(fbutton);
				backBut.setFocusable(false);
				backBut.addMouseListener(gh);
				baseRegister.add(backBut);		

	
	}

}

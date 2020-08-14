package mailhouse.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mailhouse.client.Client;
import mailhouse.domain.Mail;

public class ComposeScreen extends JPanel{
	
	public static JLabel baseCompose;
	
	Font fLabel=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/50);
	Font fText=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/100);
	public JTextField emailToText;
	public JTextField subText;
	public JTextArea textArea;
	Font fbutton=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/75);
	GuiHandler gh;
	Client c;

	
	public ComposeScreen(Client c){
		this.c = c;
		if(!this.c.currentMail.isDraft()){
			this.c.currentMail = new Mail();
			this.c.currentMail.setFromUser(this.c.user);
		}
		this.c.currentMail.setFromUser(this.c.user);
		gh= new GuiHandler(c,null);
		gh.cs = this;
		
		baseCompose = new JLabel();
		baseCompose.setSize(MailHome.screenSize.width, MailHome.screenSize.height);
		
		BufferedImage imag = null;
		try {
		    imag = ImageIO.read(new File(MailHome.back_grnd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Image dimg = imag.getScaledInstance(baseCompose.getWidth(), baseCompose.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		baseCompose.setIcon(imageIcon);
		imageIcon = new ImageIcon(dimg);
		this.add(baseCompose);
		
		//Panel For Compose area
//		JPanel composePanel = new JPanel();
//		composePanel.setBounds((int) (MailHome.screenSize.width/200), (int)(MailHome.screenSize.height/(10)),(int) (MailHome.screenSize.width/(1.025)), (MailHome.screenSize.height));
//		Dimension d = new Dimension((int) (MailHome.screenSize.width/(1.5)), MailHome.screenSize.height/(1));
//		
//		composePanel.setPreferredSize(d);
//		//baseCompose.add(composePanel);
//		
//		composePanel.setOpaque(false);
//		
		//Label for to
				JLabel toLabel=new JLabel("To : ");
				toLabel.setForeground(Color.WHITE);
				toLabel.setFont(fLabel);
				toLabel.setBounds(MailHome.screenSize.width/50, MailHome.screenSize.height/50, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseCompose.add(toLabel);

		//Label for subject
				JLabel subLabel=new JLabel("Subject : ");
				subLabel.setForeground(Color.WHITE);
				subLabel.setFont(fLabel);
				subLabel.setBounds(MailHome.screenSize.width/50,MailHome.screenSize.height/8, MailHome.screenSize.width/2, MailHome.screenSize.height/4);
				baseCompose.add(subLabel);
				
		//to text fields
				emailToText=new JTextField();
				emailToText.setBounds(MailHome.screenSize.width/8, MailHome.screenSize.height/8, (int)(MailHome.screenSize.width/(3.5)), MailHome.screenSize.height/20);
				emailToText.setFont(fText);
				baseCompose.add(emailToText);
				emailToText.setForeground(Color.WHITE);
				emailToText.setHorizontalAlignment(SwingConstants.CENTER);
				emailToText.setOpaque(false);
				  
		//subject text fields
				subText=new JTextField();
				subText.setBounds(MailHome.screenSize.width/8, (int)(MailHome.screenSize.height/(4.42)), (int)(MailHome.screenSize.width/(2.25)), MailHome.screenSize.height/20);
				subText.setFont(fText);
				baseCompose.add(subText);
				subText.setForeground(Color.WHITE);
				subText.setHorizontalAlignment(SwingConstants.CENTER);
				subText.setOpaque(false);
				  
				
				
		//Button to send mail
				JButton saveDraftBut=new JButton("<- Save Draft ->");
				saveDraftBut.setActionCommand("savedraft_click");
				saveDraftBut.setBounds((int) (MailHome.screenSize.width/(1.25)),(int)(MailHome.screenSize.height/(6.25)), MailHome.screenSize.width/5, MailHome.screenSize.height/15);
				saveDraftBut.setBorderPainted(true);
				saveDraftBut.setFont(fbutton);
				saveDraftBut.setFocusable(false);
				saveDraftBut.addMouseListener(gh);
				baseCompose.add(saveDraftBut);				
				
		//Button to send mail
				JButton sendBut=new JButton("Send ->");
				sendBut.setActionCommand("send_click");
				sendBut.setBounds((int) (MailHome.screenSize.width/(1.25)),(int)(MailHome.screenSize.height/(4.25)), MailHome.screenSize.width/5, MailHome.screenSize.height/15);
				sendBut.setBorderPainted(true);
				sendBut.setFont(fbutton);
				sendBut.setFocusable(false);
				sendBut.addMouseListener(gh);
				baseCompose.add(sendBut);		
		
				
		//Text area for mail body
				textArea = new JTextArea ("--Write your mail here --");
				textArea.setFont(fLabel);
				JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scroll.setBounds(MailHome.screenSize.width/50, (int)(MailHome.screenSize.height/(3)), (int)(MailHome.screenSize.width/(1.025)),(int) (MailHome.screenSize.height/(2.25)));
				
				baseCompose.add(scroll);

			
		//Button to select file
				JButton attBut=new JButton("Add Attachments");
				attBut.setActionCommand("attachment_click");
				attBut.setBounds(MailHome.screenSize.width/50,(int) (MailHome.screenSize.height/(1.25)), MailHome.screenSize.width/6, MailHome.screenSize.height/15);
				attBut.setBorderPainted(true);
				attBut.setFont(fbutton);
				attBut.setFocusable(false);
				attBut.addMouseListener(gh);
				baseCompose.add(attBut);		

				System.out.println("in composescreen draft not if"+this.c.currentMail.isDraft());
				if(this.c.currentMail.isDraft()){
					System.out.println("in composescreen draft if");
					this.textArea.setText(this.c.currentMail.getContent());
					this.emailToText.setText(this.c.currentMail.getToUser().getEmail());
					this.subText.setText(this.c.currentMail.getSubject());
				}
		
		
		
	}

}

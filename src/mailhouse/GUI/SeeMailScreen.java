package mailhouse.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import mailhouse.client.Client;
import mailhouse.domain.Attachment;
import mailhouse.domain.Mail;

public class SeeMailScreen extends JPanel {

public static JLabel baseCompose;
	
	Font fLabel=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/50);
	Font fText=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/100);
	public JLabel emailToText;
	public JLabel subText;
	public JTextArea textArea;
	public JList jlist;
	private DefaultListModel model;
	Font fbutton=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/75);
	GuiHandler gh;
	Client c;

	
	public SeeMailScreen(Client c){
		this.c = c;
		
		gh= new GuiHandler(c,null);
		gh.sms = this;
		
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
				JLabel toLabel=new JLabel("From : ");
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
				emailToText= new JLabel();
				emailToText.setBounds(MailHome.screenSize.width/8, MailHome.screenSize.height/8, (int)(MailHome.screenSize.width/(3.5)), MailHome.screenSize.height/20);
				emailToText.setFont(fText);
				emailToText.setText(this.c.currentMail.getToUser().getEmail());
				baseCompose.add(emailToText);
				emailToText.setForeground(Color.WHITE);
				emailToText.setHorizontalAlignment(SwingConstants.CENTER);
				emailToText.setOpaque(false);
				  
		//subject text fields
				subText= new JLabel();
				subText.setBounds(MailHome.screenSize.width/8, (int)(MailHome.screenSize.height/(4.42)), (int)(MailHome.screenSize.width/(2.25)), MailHome.screenSize.height/20);
				subText.setFont(fText);
				subText.setText(this.c.currentMail.getSubject());
				baseCompose.add(subText);
				subText.setForeground(Color.WHITE);
				subText.setHorizontalAlignment(SwingConstants.CENTER);
				subText.setOpaque(false);
				  
		
				
		//Button to send mail
				JButton sendBut=new JButton("Forward->");
				sendBut.setActionCommand("forward_click");
				sendBut.setBounds((int) (MailHome.screenSize.width/(1.25)),(int)(MailHome.screenSize.height/(4.25)), MailHome.screenSize.width/5, MailHome.screenSize.height/15);
				sendBut.setBorderPainted(true);
				sendBut.setFont(fbutton);
				sendBut.setFocusable(false);
				sendBut.addMouseListener(gh);
				baseCompose.add(sendBut);		
		
				
		//Text area for mail body
				textArea =  new JTextArea();
				String[] lines = this.c.currentMail.getContent().split("\\n");
//				for(int i = 0; i<lines.length;i++)
//					textArea.setText(textArea.getText() + lines[i] + "\n");
				textArea.setText(this.c.currentMail.getContent());
				textArea.setFont(fLabel);
				textArea.setEditable(false);
				JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scroll.setBounds(MailHome.screenSize.width/50, (int)(MailHome.screenSize.height/(3)), (int)(MailHome.screenSize.width/(1.025)),(int) (MailHome.screenSize.height/(2.25)));
				
				baseCompose.add(scroll);

			
		//Button to select file
				List<Attachment> attachs = this.c.currentMail.getAttachment();
				model = new DefaultListModel();
				
				for(int i=0;i<attachs.size();i++){
					model.addElement(attachs.get(i).getFileName());
				}
				
				jlist = new JList(model);
				jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
				jlist.setVisibleRowCount(4);
				jlist.setBounds(MailHome.screenSize.width/50,(int) (MailHome.screenSize.height/(1.25)), (int)(MailHome.screenSize.width/(1.025)), MailHome.screenSize.height/15);
				jlist.setFont(fbutton);
				
				jlist.addMouseListener(new java.awt.event.MouseAdapter() {
                     @Override
			         public void mouseClicked(java.awt.event.MouseEvent evt) {
                          listMouseClicked(evt);
                     }
                 });
				
				baseCompose.add(jlist);	
				
				
				//JButton attBut=new JButton("View Attachments");
				//attBut.setActionCommand("attachment_click");
				//attBut.setBounds(MailHome.screenSize.width/50,(int) (MailHome.screenSize.height/(1.25)), (int)(MailHome.screenSize.width/(1.025)), MailHome.screenSize.height/15);
				//attBut.setBorderPainted(true);
			//	attBut.setFont(fbutton);
			//	attBut.setFocusable(false);
			//	attBut.addMouseListener(gh);
			//	baseCompose.add(attBut);		

		
		
		
		
		
	}
	
	private void listMouseClicked(java.awt.event.MouseEvent evt){
		List<Attachment> attachs = this.c.currentMail.getAttachment();
		int selectedRowIndex = jlist.getSelectedIndex();
		String name = (String) model.getElementAt(selectedRowIndex);
		String extension = attachs.get(selectedRowIndex).getExtension();
		String home = System.getProperty("user.home");
		File file = new File(home+"/Downloads/" + name + extension); 
		FileOutputStream fos =null;
		Writer fstream = null;
		Attachment selectedatt = attachs.get(selectedRowIndex);
		try {
			System.out.println("Downloading...");
			fos = new FileOutputStream(file);
			
			selectedatt = this.c.getFile(selectedatt);
			
			
			System.out.println(new String(selectedatt.getData()));
			//fstream = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			fos.write(selectedatt.getData());
			
			System.out.println("Downloaded...");
			fos.close();
		} catch (IOException e) {
			System.out.println("Downloading exception...");
			e.printStackTrace();
		}
		finally{
			try{
				fos.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

package mailhouse.GUI;

import java.awt.Color;




import javax.swing.filechooser.FileSystemView;

import java.awt.event.MouseAdapter;

import javax.swing.*;

import mailhouse.client.Client;
import mailhouse.domain.Attachment;
import mailhouse.domain.User;
import mailhouse.domain.UserProfile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZoneId;

public class GuiHandler extends MouseAdapter {
	
	private Client c;
	public MailHome m;
	public InboxScreen is;
	public ComposeScreen cs;
	public SentScreen ss;
	public SeeMailScreen sms;
	public RegistrationScreen rs;
	
	public GuiHandler(Client c){
		
		this.c = c;
	}
	
	public GuiHandler(Client c,MailHome m){
		
		this.c=c;
		this.m = m;
	}
	
    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	((JButton) evt.getComponent()).setBackground(Color.lightGray);
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
    	((JButton) evt.getComponent()).setBackground(UIManager.getColor("control"));
    	
    }
    public void mouseClicked(java.awt.event.MouseEvent evt) {
    	String ac_command = ((JButton) evt.getComponent()).getActionCommand();
    	
    	
    	if(ac_command.equals("login_click")){
    		
    		String email = this.m.emailText.getText();
    		String password = this.m.passText.getText();
    		System.out.println(c);
    		
    		User user = this.c.login(email, password);
    		System.out.println(user);
    		if(user != null){
    			
	    		//final InboxScreen is = new InboxScreen(c);
    			m.optionsMenu.setEnabled(true);
    			final InboxScreen is = new InboxScreen(c);
	    		MailHome.parent.add(is,"2");
	    		MailHome.c1.show(MailHome.parent, "2");
    		}
    	}
    	else if(ac_command.equals("register_click")){
    		
    		final RegistrationScreen is = new RegistrationScreen(c);
    		MailHome.parent.add(is,"7");
    		MailHome.c1.show(MailHome.parent, "7");
    	}
    	else if(ac_command.equals("attachment_click")){
			//File handling
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Multiple file and directory selection:");
			jfc.setMultiSelectionEnabled(true);
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File[] files = jfc.getSelectedFiles();
				System.out.println("Directories found\n");
				if(this.c.currentMail.getAttachment()==null){
					this.c.currentMail.setAttachment(new ArrayList<Attachment>());
				}
				for(File oneFile : files){
					Attachment att = new Attachment();
					att.setFile(oneFile);
					
					String fileName = oneFile.getName();
					att.setFileName(fileName);
					String extension = "";
					int i = fileName.lastIndexOf('.');
					if (i > 0) {
					    extension = fileName.substring(i);
					}
					att.setFileName(fileName.substring(0, i));
					att.setExtension(extension);
					this.c.currentMail.addAttachment(att);
				}
		
			}
			
		}
    	else if(ac_command.equals("send_click")){
			User toUser = new User();
			toUser.setEmail(this.cs.emailToText.getText());
			this.c.currentMail.setToUser(toUser);
			this.c.currentMail.setContent(this.cs.textArea.getText());
			this.c.currentMail.setDateTime(LocalDateTime.now());
			this.c.currentMail.setSubject(this.cs.subText.getText());
			this.c.currentMail.setMailId(0);
			try{
				this.c.sendMail(this.c.currentMail);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			final InboxScreen is = new InboxScreen(c);
    		MailHome.parent.add(is,"2");
    		MailHome.c1.show(MailHome.parent, "2");
			
		}
    	else if(ac_command.equals("savedraft_click")){
			User toUser = new User();
			toUser.setEmail(this.cs.emailToText.getText());
			this.c.currentMail.setToUser(toUser);
			this.c.currentMail.setContent(this.cs.textArea.getText());
			this.c.currentMail.setDateTime(LocalDateTime.now());
			this.c.currentMail.setSubject(this.cs.subText.getText());
			this.c.currentMail.setMailId(0);
			try{
				this.c.storeDraft(this.c.currentMail);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			final DraftListScreen is = new DraftListScreen(c);
    		MailHome.parent.add(is,"6");
    		MailHome.c1.show(MailHome.parent, "6");
			
		}
    	else if(ac_command.equals("forward_click")){
			
			String email = JOptionPane.showInputDialog("Enter email");
			User toUser = new User();
			toUser.setEmail(email);
			this.c.currentMail.setFromUser(this.c.user);
			this.c.currentMail.setToUser(toUser);
			if(this.c.currentMail.getforwardId()==-1){
				this.c.currentMail.setforwardId(this.c.currentMail.getMailId());
			}
			else{
				this.c.currentMail.setforwardId(this.c.currentMail.getforwardId());//redundant 
			}
			this.c.forward(this.c.currentMail);
			
		}
    	else if(ac_command.equals("RegisterClick")){
    		UserProfile userprofile;
    		//System.out.println("password match " + rs.passText.getPassword().toString()+ " "+rs.conpassText.getPassword().toString());
    		if(rs.passText.getText().equals(rs.conpassText.getText())){
    			//System.out.println("password match " + rs.passText.getPassword().toString()+ " "+rs.conpassText.getPassword().toString());
				User user = new User();
				userprofile = new UserProfile();
				user.setEmail(rs.emailText.getText());
				user.setUsername(rs.nameText.getText());
				Date date = (Date) rs.datePicker.getModel().getValue();
    			LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    			userprofile.setDateOfBirth(ldt);
    			user.setPassword(rs.passText.getText());
				if(rs.r1.isSelected()){
					userprofile.setGender(false);
					
				}
				else if(rs.r2.isSelected()){
					userprofile.setGender(true);
				}
				
				User newUser = this.c.register(user, userprofile);
				if(newUser == null){
					//set Label
				}
				else{
					this.c.userprofile = userprofile;
					
					MailHome.c1.show(MailHome.parent, "1");
				}
				
    		}
    		else{
    			//set label
    			System.out.println("error");
    		}
			
			
		}
		else if(ac_command.equals("BackClick")){
		
			//String email = JOptionPane.showInputDialog("Enter email");
			
			//final MailHome is = new MailHome();
			
			MailHome.c1.show(MailHome.parent, "1");
		}
		
    	

    }
	

}

package mailhouse.server;

import java.util.*;
import java.io.*;
import java.net.*;
import mailhouse.packets.*;
import mailhouse.domain.*;
import mailhouse.exceptions.*;
import java.sql.*;

public class Handler implements Runnable{
	
	private InputStream in;
	private OutputStream out;
	private DataAccess da;
	
	public Handler(InputStream in, OutputStream out){
		this.in = in;
		this.out = out;
		
		initializeDataAccess();
	}
	
	private void initializeDataAccess(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("B:\\Faizal\\CSAProject\\MailHouse\\src\\mailhouse\\server\\config.properties");
		
			prop.load(input);

			this.da = new DataAccess(prop.getProperty("jdbcURL"),prop.getProperty("driver"),prop.getProperty("username"),prop.getProperty("password"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

  
	}
	
	public void run(){
		try{
			
		
			User user = new User();
			UserProfile userprofile =  new UserProfile();
			Mail mail = new Mail();
			Attachment att = new Attachment();
			ErrorPacket perr = new ErrorPacket("Incorrect username or password");
			
			while(true){
				ObjectOutputStream oos = new ObjectOutputStream(out);
				ObjectInputStream ois = new ObjectInputStream(in);
				System.out.println("recieving");
				Packet p = (Packet)ois.readObject();
				
				System.out.println("recieved");
				switch(p.getPacketType()){
					case LoginPacket:
						LoginPacket login  = (LoginPacket) p;
						user = login.getUser();
						try{
							user = this.da.login(user);
							login.setUser(user);
							oos.writeObject(login);
						}
						catch(UserNotFoundException une){
							perr = new ErrorPacket("Incorrect username or password");
							oos.writeObject(perr);
						}
						
						
					break;
					case ProfilePacket:
						System.out.println("in case profile packet");
						ProfilePacket profile = (ProfilePacket) p;
						user = profile.getUser();
						userprofile=profile.getUserProfile();
						try{
							user=this.da.register(userprofile,user);
							profile.setUser(user);
							oos.writeObject(profile);
						}
						catch(UserNotFoundException|ClassNotFoundException exp){
							perr = new ErrorPacket("cannot register!");
							oos.writeObject(perr);
						}
					
					break;
					case SendMailPacket:
						System.out.println("in sendmail case");
						SendMailPacket sendMailPacket = (SendMailPacket) p;
						mail = sendMailPacket.getMail();
						
						try{
							System.out.println("handler before method");
							this.da.storeMail(mail);
							System.out.println("hander after method");
							
						}
						catch(UserNotFoundException|SQLException e){
							e.printStackTrace();
							perr = new ErrorPacket("send mail failed please check email");
							oos.writeObject(perr);
						}
						
					break;
					case ForwardPacket:
						
						ForwardPacket fpacket = (ForwardPacket) p;
						mail = fpacket.getMail();
						
						try{
							System.out.println("handler before method");
							this.da.forwardMail(mail);
							System.out.println("hander after method");
							
						}
						catch(UserNotFoundException|SQLException e){
							e.printStackTrace();
							perr = new ErrorPacket("cant send email check email");
							oos.writeObject(perr);
						}
						
					break;
					case GetMailListPacket:
						GetMailListPacket getMailListPacket = (GetMailListPacket) p;
						user = getMailListPacket.getUser();
						try{
							List<SubMail> mails = this.da.getMails(user);
							getMailListPacket.setmailList(mails);
							if(mails == null){
								ErrorPacket ep = new ErrorPacket("error retreiving mails");
								oos.writeObject(ep);
							}else
								oos.writeObject(getMailListPacket);
						}
						catch(UserNotFoundException|ClassNotFoundException exp){
							exp.printStackTrace();
							perr = new ErrorPacket("mail list not retrieved");
							oos.writeObject(perr);
						}
						
					break;
					case GetSentMailsPacket:
						GetSentMailsPacket getSentMailsPacket = (GetSentMailsPacket) p;
						user = getSentMailsPacket.getUser();
						try{
							List<SubMail> mails = this.da.getSentMails(user);
							getSentMailsPacket.setmailList(mails);
							if(mails == null){
								ErrorPacket ep = new ErrorPacket("error retreiving mails");
								oos.writeObject(ep);
							}else
								oos.writeObject(getSentMailsPacket);
						}
						catch(UserNotFoundException|ClassNotFoundException exp){
							exp.printStackTrace();
						}
						
					break;
					case GetMailPacket:
						GetMailPacket getMailPacket = (GetMailPacket) p;
						mail = getMailPacket.getMail();
						try{
							
							mail = this.da.getMail(mail);
							getMailPacket.setMail(mail);
							oos.writeObject(getMailPacket);
						}
						catch(MailNotFoundException|ClassNotFoundException|UserNotFoundException exp){
							exp.printStackTrace();
							perr = new ErrorPacket("error retrieving mail it may have been deleted!");
							oos.writeObject(perr);
						}
					break;
					case LogoutPacket:
					
					break;
					case GetFilePacket:
						GetFilePacket getFilePacket = (GetFilePacket) p;
						att = getFilePacket.getAttachment();
						try{
							
							att = this.da.getAttachment(att);
							getFilePacket.setAttachment(att);
							oos.writeObject(getFilePacket);
						}
						catch(ClassNotFoundException exp){
							perr = new ErrorPacket("error downloading file");
							oos.writeObject(perr);
							exp.printStackTrace();
						}
					break;
					case GetDraftPacket:
						GetDraftPacket getDraftPacket = (GetDraftPacket) p;
						mail = getDraftPacket.getMail();
						try{
							
							mail = this.da.getDraft(mail);
							getDraftPacket.setMail(mail);
							oos.writeObject(getDraftPacket);
						}
						catch(MailNotFoundException|ClassNotFoundException|UserNotFoundException exp){
							perr = new ErrorPacket("error retrieving draft");
							oos.writeObject(perr);
							exp.printStackTrace();
						}
					break;
					case GetDraftListPacket:
						GetDraftListPacket getDraftListPacket = (GetDraftListPacket) p;
						user = getDraftListPacket.getUser();
						try{
							List<SubMail> mails = this.da.getDrafts(user);
							getDraftListPacket.setmailList(mails);
							if(mails == null){
								ErrorPacket ep = new ErrorPacket("error retreiving drafts");
								oos.writeObject(ep);
							}else
								oos.writeObject(getDraftListPacket);
						}
						catch(UserNotFoundException|ClassNotFoundException exp){
							exp.printStackTrace();
							perr = new ErrorPacket("drafts retrieval failed login failed");
							oos.writeObject(perr);
						}
					break;
					case SaveDraftPacket:
						//System.out.println("in savedraft case");
						SaveDraftPacket saveDraftPacket = (SaveDraftPacket) p;
						mail = saveDraftPacket.getMail();
						
						try{
							System.out.println("handler before method");
							this.da.storeDraft(mail);
							System.out.println("hander after method");
							
						}
						catch(UserNotFoundException|SQLException e){
							e.printStackTrace();
							perr = new ErrorPacket("save draft failed please check email!");
							oos.writeObject(perr);
						}
					break;
					default:
						System.out.println("default");
					break;
				}
			}
		}
		catch(IOException|ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
}
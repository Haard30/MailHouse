package mailhouse.packets;

import java.util.ArrayList;
import java.util.List;

import mailhouse.domain.SubMail;
import mailhouse.domain.User;

public class GetDraftListPacket extends Packet {

	
	private List<SubMail> mailList = new ArrayList<>();
	private User user;
	
	public GetDraftListPacket(List<SubMail> mail,User user){
		super(PacketTypes.GetDraftListPacket);
		this.mailList = mail;
		this.user = user;
	}
	
	
	public GetDraftListPacket(){
		super(PacketTypes.GetDraftListPacket);
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void setmailList(List<SubMail> mailList){
		this.mailList = mailList;
	}
	
	public List<SubMail> getMailList(){
		return this.mailList;
	}
	
	public boolean addMail(SubMail subMail){
		return this.mailList.add(subMail);
	}
	
	public boolean removeMail(SubMail subMail){
		return this.mailList.remove(subMail);
	}
}

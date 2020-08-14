package mailhouse.packets;

import mailhouse.domain.Mail;

public class SaveDraftPacket extends Packet {

private Mail mail;
	
	public SaveDraftPacket(Mail mail){
		super(PacketTypes.SaveDraftPacket);
		this.mail = mail;
	}
	
	public Mail getMail(){
		return this.mail;
	}
	
	public void setMail(Mail mail){
		this.mail = mail;
	}
}

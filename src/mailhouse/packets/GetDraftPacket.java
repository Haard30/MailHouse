package mailhouse.packets;

import mailhouse.domain.Mail;

public class GetDraftPacket extends Packet {

private Mail mail;
	
	public GetDraftPacket(Mail mail){
		super(PacketTypes.GetDraftPacket);
		this.mail = mail;
	}
	
	public GetDraftPacket(){
		super(PacketTypes.GetDraftPacket);
		this.mail =null;
	}
	
	public Mail getMail(){
		return this.mail;
	}
	
	public void setMail(Mail mail){
		this.mail = mail;
	}
}

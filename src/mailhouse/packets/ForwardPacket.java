package mailhouse.packets;

import mailhouse.domain.Mail;

public class ForwardPacket extends Packet {

	
	private Mail mail;
	
	public ForwardPacket(Mail mail){
		super(PacketTypes.ForwardPacket);
		this.mail = mail;
	}
	
	public Mail getMail(){
		return this.mail;
	}
	
	public void setMail(Mail mail){
		this.mail = mail;
	}
}

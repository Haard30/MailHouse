package mailhouse.packets;

import mailhouse.domain.Attachment;
import mailhouse.domain.User;

public class GetFilePacket extends Packet{

	
	
	public Attachment att;
	
	public GetFilePacket( Attachment att){
		super(PacketTypes.GetFilePacket);
		this.att = att;
	}
	
	public Attachment getAttachment(){
		return this.att;
	}
	
	public void setAttachment( Attachment att){
		this.att = att;
	}
}

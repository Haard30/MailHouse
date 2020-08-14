package mailhouse.packets;

import java.util.*;

public enum PacketTypes{
	
	LoginPacket(1),
	ProfilePacket(2),
	SendMailPacket(3),
	GetMailListPacket(4),
	GetMailPacket(5),
	LogoutPacket(6),
	GetSentMailsPacket(7),
	GetFilePacket(8),
	ForwardPacket(9),
	//new on 18-04-19
	GetDraftListPacket(10),
	GetDraftPacket(11),
	SaveDraftPacket(12),
	//
	ErrorPacket(-1);
	
	private int packetId;
	
	PacketTypes(int packetId){
		this.packetId = packetId;
	}
	
	
}
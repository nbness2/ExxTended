package org.dementhium.net.packethandlers;

import org.dementhium.model.player.Player;
import org.dementhium.net.PacketHandler;
import org.dementhium.net.message.Message;
import org.dementhium.util.InputHandler;

/**
 *
 * @author 'Mystic Flow <Steven@rune-server.org>
 */
public final class InputPacketHandler extends PacketHandler {

	public static final int STRING_OPTION = 51, INTEGER_OPTION = 5;

	@Override
	public void handlePacket(Player player, Message packet) {
		switch(packet.getOpcode()) {
		case STRING_OPTION:
			String string = packet.readRS2String();
			InputHandler.handleStringInput(player, string);
			break;
		case INTEGER_OPTION:
			int value = packet.readInt();
			InputHandler.handleIntegerInput(player, value);
			break;
		}
	}

}

package com.kamesuta.mc.autoinput.handler;

import java.util.HashSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();
	public static final HashSet<Integer> tickKeys = new HashSet<Integer>();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase==TickEvent.Phase.START&&InputHandler.keyInput)
			for (final Integer line : tickKeys)
				KeyBinding.onTick(line);
	}
}
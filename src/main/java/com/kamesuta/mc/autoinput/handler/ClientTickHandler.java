package com.kamesuta.mc.autoinput.handler;

import java.util.HashSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();
	public static final HashSet<Integer> tickKeys = new HashSet<Integer>();
	public static final HashSet<Integer> stateKeys = new HashSet<Integer>();

	public boolean restart;
	public Minecraft mc = Minecraft.getMinecraft();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase==TickEvent.Phase.START&&InputHandler.keyInput) {
			for (final Integer line : tickKeys)
				KeyBinding.onTick(line);

			if (this.restart&&this.mc.currentScreen==null) {
				this.restart = false;
				for (final Integer line : stateKeys)
					KeyBinding.setKeyBindState(line, true);
			}

			if (this.mc.currentScreen!=null&&!this.restart)
				this.restart = true;
		}
	}
}
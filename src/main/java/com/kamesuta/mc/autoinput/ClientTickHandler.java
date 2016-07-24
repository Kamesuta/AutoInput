package com.kamesuta.mc.autoinput;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();

	private final Minecraft mc = Minecraft.getMinecraft();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			this.mc.mcProfiler.startSection("autoinput");
			for (final Integer i : InputHandler.continuousKeys) {
				if (InputHandler.keyInput)
					KeyBinding.onTick(i);
			}
			this.mc.mcProfiler.endSection();
		}

		if (InputHandler.keyInput && this.mc.currentScreen != null) {
			for (final Integer i : InputHandler.holdKeys)
				KeyBinding.setKeyBindState(i, true);
		}
	}
}
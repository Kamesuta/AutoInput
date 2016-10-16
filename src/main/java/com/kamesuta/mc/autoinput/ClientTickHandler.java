package com.kamesuta.mc.autoinput;

import java.util.HashSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.settings.KeyBinding;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();
	private final HashSet<Integer> keys = new HashSet<Integer>();

	private ClientTickHandler() {
	}

	public HashSet<Integer> getKeys() {
		return this.keys;
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START && InputHandler.keyInput) {
			//			Minecraft.getMinecraft().mcProfiler.startSection(Reference.MODID);
			for (final Integer line : this.keys)
				KeyBinding.onTick(line);
			//			Minecraft.getMinecraft().mcProfiler.endSection();
		}
	}

	public void add(final int code) {
		this.keys.add(code);
	}

	public void remove(final int code) {
		this.keys.remove(code);
	}
}
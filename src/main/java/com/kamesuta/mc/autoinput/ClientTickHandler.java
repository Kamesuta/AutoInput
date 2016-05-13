package com.kamesuta.mc.autoinput;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();

	public static boolean rightclick = false;
	public static boolean leftclick = false;

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
	}

	@SubscribeEvent
	public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			this.minecraft.mcProfiler.startSection("autoclick");
//			if (this.minecraft.currentScreen == null || this.minecraft.currentScreen.allowUserInput)
				if (rightclick) {
					KeyBinding.onTick(1 - 100);
				}
			this.minecraft.mcProfiler.endSection();
		}
	}

	@SubscribeEvent
	public void onClinentTick(TickEvent.ClientTickEvent event){
		if (event.phase == TickEvent.Phase.START) {
			this.minecraft.mcProfiler.startSection("leftclick");
				if (leftclick) {
					KeyBinding.setKeyBindState(1 - 101, true);
				} else {
					KeyBinding.setKeyBindState(1 - 101, false);
				}
			this.minecraft.mcProfiler.endSection();

		}
	}
}
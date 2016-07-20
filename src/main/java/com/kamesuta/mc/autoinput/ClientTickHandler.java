package com.kamesuta.mc.autoinput;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();

	public static boolean rightclick = false;

	//	static {
	//		keys = new HashSet<Integer>();
	//		keys.add(1-100);
	//		keys.add(100-58);
	//		//		keys.add(0-100);//keys.remove(1-100);
	//	}

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			this.minecraft.mcProfiler.startSection("autoclick");
			if (rightclick)
				KeyBinding.onTick(1 - 100);
			this.minecraft.mcProfiler.endSection();
		}
	}
	//	@SubscribeEvent
	//	public void onClientTick(TickEvent.ClientTickEvent event) {
	//		if (event.phase == TickEvent.Phase.START) {
	//			this.minecraft.mcProfiler.startSection("autoclick");
	//				if (rightclick) {
	//					for (Integer i : keys) {
	//						KeyBinding.onTick(i);
	//					}
	//				}
	//			this.minecraft.mcProfiler.endSection();
	//		}
	//	}
}
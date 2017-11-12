package com.kamesuta.mc.autoinput.handler;

import com.google.common.collect.ImmutableList;
import com.kamesuta.mc.autoinput.reference.Reference;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOpenHandler {
	public static final GuiOpenHandler INSTANCE = new GuiOpenHandler();
	private static final ImmutableList<Class<? extends GuiScreen>> EGNORE_CLASSES = ImmutableList.of(
			GuiInventory.class,
			GuiContainerCreative.class,
			GuiChat.class,
			GuiDownloadTerrain.class,
			GuiMainMenu.class,
			GuiConnecting.class,
			GuiDisconnected.class,
			GuiMultiplayer.class,
			GuiWorldSelection.class,
			GuiScreenWorking.class);

	private GuiOpenHandler() {
	}

	@SubscribeEvent
	public void onOpenGui(final GuiOpenEvent event) {
		if (event.getGui()!=null&&!EGNORE_CLASSES.contains(event.getGui().getClass())) {
			Reference.logger.info(event.getGui().getClass().getSimpleName());
			InputHandler.keyInput = false;
		}
	}
}

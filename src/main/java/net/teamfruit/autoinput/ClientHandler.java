package net.teamfruit.autoinput;

import java.util.HashSet;

import com.google.common.collect.ImmutableList;

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
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientHandler {
	public static final ClientHandler INSTANCE = new ClientHandler();
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

	private ClientHandler() {
	}

	public final HashSet<Integer> tickKeys = new HashSet<Integer>();

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase==TickEvent.Phase.START&&InputHandler.INSTANCE.isKeyInput())
			for (final Integer line : this.tickKeys)
				KeyBinding.onTick(line);

	}

	@SubscribeEvent
	public void onGuiOpen(final GuiOpenEvent event) {
		if (event.getGui()!=null)
			if (EGNORE_CLASSES.contains(event.getGui().getClass())) {
				if (InputHandler.INSTANCE.isKeyInput())
					for (final AutoInputKey binding : KeyStore.INSTANCE.getKeys())
						if (binding.getKeyCode()!=0&&!binding.getMode())
							KeyBinding.setKeyBindState(binding.getKeyCode(), true);
			} else
				InputHandler.INSTANCE.setKeyInput(false);
	}

}

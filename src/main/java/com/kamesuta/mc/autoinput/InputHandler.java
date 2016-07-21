package com.kamesuta.mc.autoinput;

import java.util.HashSet;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.kamesuta.mc.autoinput.reference.Names;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding(Names.Keys.GUI, Keyboard.KEY_L, Names.Keys.CATEGORY);
	private static final KeyBinding KEY_BINDING_TOGGLE = new KeyBinding(Names.Keys.TOGGLE, Keyboard.KEY_K, Names.Keys.CATEGORY);

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI, KEY_BINDING_TOGGLE};

	private final Minecraft mc = Minecraft.getMinecraft();
	protected static boolean holdInput = false;

	private InputHandler() {
	}

	protected static final HashSet<Integer> holdKeys = new HashSet<Integer>();

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (this.mc.currentScreen == null) {
			if (KEY_BINDING_GUI.isPressed())
				this.mc.displayGuiScreen(new GuiAutoInput());

			if (KEY_BINDING_TOGGLE.isPressed()) {
				ClientTickHandler.continuousKeys.clear();
				InputHandler.holdKeys.clear();
				final Iterator it = GuiAutoInput.keys.iterator();
				while (it.hasNext()) {
					final GuiKeyBinding keyBinding = (GuiKeyBinding) it.next();
					final int keys = keyBinding.getKeyCode();
					if (keys != 0) {
						if (keyBinding.getMode()) {
							ClientTickHandler.continuousKeys.add(keys);
						} else {
							InputHandler.holdKeys.add(keys);
						}
					}
				}
				ClientTickHandler.continuousInput = !ClientTickHandler.continuousInput;
				holdInput = !holdInput;
				if (ClientTickHandler.continuousInput)
					KeyBinding.unPressAllKeys();
				for (final Integer i : holdKeys) {
					if (holdInput) {
						KeyBinding.setKeyBindState(i, true);
					} else {
						KeyBinding.setKeyBindState(i, false);
					}
				}
			}
		}
	}
}

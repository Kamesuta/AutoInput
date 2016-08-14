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

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI, KEY_BINDING_TOGGLE };

	private final Minecraft mc = Minecraft.getMinecraft();
	protected static boolean keyInput = false;

	private InputHandler() {
	}

	protected static final HashSet<Integer> holdKeys = new HashSet<Integer>();
	protected static final HashSet<Integer> continuousKeys = new HashSet<Integer>();

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()) {
			if (this.mc.currentScreen == null)
				this.mc.displayGuiScreen(new GuiAutoInput());
		}

		if (KEY_BINDING_TOGGLE.isPressed()) {
			continuousKeys.clear();
			holdKeys.clear();
			final Iterator it = GuiAutoInput.keys.iterator();
			while (it.hasNext()) {
				final GuiKeyBinding keyBinding = (GuiKeyBinding) it.next();
				final int keys = keyBinding.getKeyCode();
				if (keys != 0) {
					if (keyBinding.getMode()) {
						holdKeys.add(keys);
					} else {
						continuousKeys.add(keys);
					}
				}
			}

			if (this.mc.currentScreen == null)
				keyInput = !keyInput;

			for (final Integer i : holdKeys) {
				if (keyInput) {
					KeyBinding.setKeyBindState(i, true);
				} else {
					KeyBinding.setKeyBindState(i, false);
				}
			}
		}
	}
}

package com.kamesuta.mc.autoinput.handler;

import org.lwjgl.input.Keyboard;

import com.kamesuta.mc.autoinput.GuiAutoInput;
import com.kamesuta.mc.autoinput.GuiKeyBinding;
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
	public static boolean keyInput;

	public final Minecraft mc = Minecraft.getMinecraft();

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()&&this.mc.currentScreen==null)
			this.mc.displayGuiScreen(new GuiAutoInput());

		if (KEY_BINDING_TOGGLE.isPressed()&&this.mc.currentScreen==null) {
			for (final GuiKeyBinding binding : GuiAutoInput.keys) {
				final int code = binding.getKeyCode();
				if (code!=0) {
					if (binding.getMode()) {
						if (keyInput)
							ClientTickHandler.tickKeys.remove(code);
						else
							ClientTickHandler.tickKeys.add(code);
					} else {
						KeyBinding.setKeyBindState(code, !keyInput);
						if (keyInput)
							ClientTickHandler.stateKeys.remove(code);
						else
							ClientTickHandler.stateKeys.add(code);

					}
				}
			}
			keyInput = !keyInput;
		}
	}
}

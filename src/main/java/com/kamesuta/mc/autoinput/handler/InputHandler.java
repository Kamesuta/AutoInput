package com.kamesuta.mc.autoinput.handler;

import org.lwjgl.input.Keyboard;

import com.kamesuta.mc.autoinput.GuiAutoInput;
import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.reference.Names;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding(Names.Keys.GUI, Keyboard.KEY_L, Names.Keys.CATEGORY);
	private static final KeyBinding KEY_BINDING_TOGGLE = new KeyBinding(Names.Keys.TOGGLE, Keyboard.KEY_K, Names.Keys.CATEGORY);

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI, KEY_BINDING_TOGGLE };
	public static boolean keyInput;

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()&&Minecraft.getMinecraft().currentScreen==null)
			Minecraft.getMinecraft().displayGuiScreen(new GuiAutoInput());

		if (KEY_BINDING_TOGGLE.isPressed()&Minecraft.getMinecraft().currentScreen==null) {
			for (final GuiKeyBinding binding : GuiAutoInput.keys) {
				final int code = binding.getKeyCode();
				if (code!=0)
					if (binding.getMode()) {
						if (keyInput)
							ClientTickHandler.tickKeys.remove(code);
						else
							ClientTickHandler.tickKeys.add(code);
					} else
						KeyBinding.setKeyBindState(code, !keyInput);
			}
			keyInput = !keyInput;
		}
	}
}

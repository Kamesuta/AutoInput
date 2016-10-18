package com.kamesuta.mc.autoinput;

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
	public static boolean keyInput = false;

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()&&this.mc.currentScreen==null)
			this.mc.displayGuiScreen(new GuiBnn());

		if (KEY_BINDING_TOGGLE.isPressed()&&this.mc.currentScreen==null) {
			for (final GuiKeyBinding binding : GuiBnn.keys) {
				if (binding.getKeyCode()!=0) {
					if (binding.getMode()) {
						KeyBinding.setKeyBindState(binding.getKeyCode(), keyInput);
					} else {
						if (keyInput) {
							ClientTickHandler.INSTANCE.remove(binding.getKeyCode());
						} else {
							ClientTickHandler.INSTANCE.add(binding.getKeyCode());
						}
					}
				}
			}
			keyInput = !keyInput;
		}
	}
}

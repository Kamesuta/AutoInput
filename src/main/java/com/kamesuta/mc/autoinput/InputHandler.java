package com.kamesuta.mc.autoinput;

import org.lwjgl.input.Keyboard;

import com.kamesuta.mc.autoinput.reference.Names;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	public static boolean switchMode = true;

	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding(Names.Keys.GUI, Keyboard.KEY_L, Names.Keys.CATEGORY);
	private static final KeyBinding KEY_BINDING_TOGGLE = new KeyBinding(Names.Keys.TOGGLE, Keyboard.KEY_K, Names.Keys.CATEGORY);

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI, KEY_BINDING_TOGGLE};

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (this.minecraft.currentScreen == null) {
			if (KEY_BINDING_GUI.isPressed()) {
				InputGui.gui();
			}
		}
	}

	//	@SubscribeEvent
	//	public void RightInput(final InputEvent event) {
	//		if (this.minecraft.currentScreen == null) {
	//			if (KEY_BINDING_SWITCH_1.isPressed()) {
	//				ClientTickHandler.rightclick = !ClientTickHandler.rightclick;
	//			}
	//		}
	//	}
}

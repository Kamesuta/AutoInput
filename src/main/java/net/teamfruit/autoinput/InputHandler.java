package net.teamfruit.autoinput;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.teamfruit.autoinput.gui.GuiAutoInput;
import net.teamfruit.autoinput.reference.Names;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding(Names.Keys.GUI, Keyboard.KEY_L, Names.Keys.CATEGORY);
	private static final KeyBinding KEY_BINDING_TOGGLE = new KeyBinding(Names.Keys.TOGGLE, Keyboard.KEY_K, Names.Keys.CATEGORY);

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI, KEY_BINDING_TOGGLE };
	public boolean keyInput;

	private InputHandler() {
	}

	public boolean isKeyInput() {
		return this.keyInput;
	}

	public void setKeyInput(final boolean keyInput) {
		this.keyInput = keyInput;
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()&&Minecraft.getMinecraft().currentScreen==null)
			Minecraft.getMinecraft().displayGuiScreen(new GuiAutoInput());

		if (KEY_BINDING_TOGGLE.isPressed()&Minecraft.getMinecraft().currentScreen==null) {
			for (final AutoInputKey binding : KeyStore.INSTANCE.getKeys()) {
				final int code = binding.getKeyCode();
				if (code!=0)
					if (binding.getMode()) {
						if (this.keyInput)
							ClientHandler.INSTANCE.tickKeys.remove(code);
						else
							ClientHandler.INSTANCE.tickKeys.add(code);
					} else
						KeyBinding.setKeyBindState(code, !this.keyInput);
			}
			this.keyInput = !this.keyInput;
			JavaScriptManager.instance.onToggle(this.keyInput);
		}
	}
}

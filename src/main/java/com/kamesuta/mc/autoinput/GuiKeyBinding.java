package com.kamesuta.mc.autoinput;

public class GuiKeyBinding {
	private int keycode;
	private boolean mode;

	public GuiKeyBinding() {
	}

	public int getKeyCode() {
		return this.keycode;
	}
	public GuiKeyBinding setKeyCode(final int keycode) {
		this.keycode = keycode;
		return this;
	}

	public boolean getMode() {
		return this.mode;
	}

	public GuiKeyBinding setMode(final boolean mode) {
		this.mode = mode;
		return this;
	}
}

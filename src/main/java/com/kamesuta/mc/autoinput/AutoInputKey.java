package com.kamesuta.mc.autoinput;

public class AutoInputKey {
	private int keycode;
	private boolean mode;

	public AutoInputKey() {
	}

	public int getKeyCode() {
		return this.keycode;
	}
	public AutoInputKey setKeyCode(final int keycode) {
		this.keycode = keycode;
		return this;
	}

	public boolean getMode() {
		return this.mode;
	}

	public AutoInputKey setMode(final boolean mode) {
		this.mode = mode;
		return this;
	}
}

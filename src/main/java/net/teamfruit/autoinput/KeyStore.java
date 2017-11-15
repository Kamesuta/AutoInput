package net.teamfruit.autoinput;

import java.util.List;

import com.google.common.collect.Lists;

public class KeyStore {
	public static final KeyStore INSTANCE = new KeyStore();

	private final List<AutoInputKey> keys = Lists.newArrayList(
			new AutoInputKey().setMode(true).setKeyCode(-99),
			new AutoInputKey(),
			new AutoInputKey());

	private KeyStore() {
	}

	public List<AutoInputKey> getKeys() {
		return this.keys;
	}

	public AutoInputKey newKey() {
		final AutoInputKey key = new AutoInputKey();
		this.keys.add(key);
		return key;
	}

	public void add(final AutoInputKey key) {
		this.keys.add(key);
	}

	public void remove(final AutoInputKey key) {
		this.keys.remove(key);
	}

	public boolean isDuplicate(final AutoInputKey key) {
		return isDuplicate(key.getKeyCode());
	}

	public boolean isDuplicate(final int keycode) {
		for (final AutoInputKey key : this.keys)
			if (key.getKeyCode()==keycode)
				return true;
		return false;
	}
}

package com.kamesuta.mc.autoinput.guiparts;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kamesuta.mc.autoinput.GuiBnn;
import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class KeyButton extends Button {

	private final GuiKeyBinding keyBinding;
	private final GuiBnn guiBnn;
	private boolean reception;

	public KeyButton(final R position, final String text, final GuiKeyBinding keyBinding, final GuiBnn gui) {
		super(position, text);
		this.keyBinding = keyBinding;
		this.guiBnn = gui;
	}

	public KeyButton(final R position, final GuiKeyBinding keyBinding, final GuiBnn gui) {
		this(position, getName(keyBinding.getKeyCode()), keyBinding, gui);
	}

	@Override
	public boolean keyTyped(final WEvent ev, final Area pgp, final Point p, final char c, final int keycode) {
		if (this.reception) {
			this.keyBinding.setKeyCode((keycode==Keyboard.KEY_ESCAPE) ? Keyboard.KEY_NONE : keycode);
			setText(getName(keycode));
			this.reception = false;
			return true;
		} else {
			this.guiBnn.setHook(false);
		}
		return false;
	}

	@Override
	public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area abs = getGuiPosition(pgp);
		if (this.reception) {
			final int mouseButton = -100+button;
			this.keyBinding.setKeyCode(mouseButton);
			setText(getName(mouseButton));
			this.reception = false;
			this.guiBnn.setHook(false);
		} else if (abs.pointInside(p)) {
			this.reception = true;
			setText(EnumChatFormatting.WHITE+"> "+EnumChatFormatting.YELLOW+this.text+EnumChatFormatting.WHITE+" <");
			this.guiBnn.setHook(true);
			return true;
		}
		return false;
	}

	public static String getName(final int code) {
		if (code<0) {
			switch (code) {
				case -100:
					return I18n.format(Names.Keys.KEY0);
				case -99:
					return I18n.format(Names.Keys.KEY1);
				case -98:
					return I18n.format(Names.Keys.KEY2);
				default:
					return Mouse.getButtonName(code+100);
			}
		} else {
			return Keyboard.getKeyName((code==1) ? Keyboard.KEY_NONE : code);
		}
	}
}

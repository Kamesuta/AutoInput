package net.teamfruit.autoinput.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.resources.I18n;
import net.teamfruit.autoinput.AutoInputKey;
import net.teamfruit.autoinput.bnnwidget.WEvent;
import net.teamfruit.autoinput.bnnwidget.position.Area;
import net.teamfruit.autoinput.bnnwidget.position.Point;
import net.teamfruit.autoinput.bnnwidget.position.R;
import net.teamfruit.autoinput.reference.Names;

public class GuiKeyButton extends GuiButton {

	private final AutoInputKey keyBinding;
	private boolean reception;

	public GuiKeyButton(final R position, final AutoInputKey keyBinding) {
		super(position);
		this.keyBinding = keyBinding;
		setText(getName(keyBinding.getKeyCode()));
	}

	public boolean isReception() {
		return this.reception;
	}

	@Override
	public boolean keyTyped(final WEvent ev, final Area pgp, final Point p, final char c, final int keycode) {
		if (this.reception) {
			this.keyBinding.setKeyCode(keycode==Keyboard.KEY_ESCAPE ? Keyboard.KEY_NONE : keycode);
			setText(getName(keycode));
			this.reception = false;

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
		} else if (abs.pointInside(p)&&button==0) {
			this.reception = true;
			setText(ChatFormatting.WHITE+"> "+ChatFormatting.YELLOW+this.text+ChatFormatting.WHITE+" <");
			return true;
		}
		return false;
	}

	public static String getName(final int code) {
		if (code<0)
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
		else
			return Keyboard.getKeyName(code==1 ? Keyboard.KEY_NONE : code);
	}
}

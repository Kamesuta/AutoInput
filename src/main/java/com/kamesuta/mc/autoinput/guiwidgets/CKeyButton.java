package com.kamesuta.mc.autoinput.guiwidgets;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kamesuta.mc.autoinput.InputHandler;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.Point;
import com.kamesuta.mc.guiwidget.position.RelativePosition;

import net.minecraft.util.EnumChatFormatting;

public class CKeyButton extends CButton{

	private static boolean receptionMode = false;
	private String displayString = Keyboard.getKeyName(0);

	public static boolean getReceptionMode() {
		return receptionMode;
	}

	public static void setReceptionMode(final boolean receptionMode) {
		CKeyButton.receptionMode = receptionMode;
	}

	public CKeyButton(final RelativePosition rp, final String name) {
		super(rp, name);
		this.rp = rp;
		this.name = name;
	}

	@Override
	public String getName() {
		final int keyCode = InputHandler.getKeyCode();
		if (keyCode < 0) {
			this.displayString = Mouse.getButtonName(keyCode + 100);
		} else {
			this.displayString = Keyboard.getKeyName(keyCode);
		}

		if (receptionMode){
			return EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.displayString + EnumChatFormatting.WHITE + " <";
		} else {
			return this.displayString;
		}
	}

	@Override
	public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (abs.pointInside(p)) {
			if (receptionMode) {
				InputHandler.setKeyCode(button -100);
				receptionMode = false;
			} else if (button == 0) {
				receptionMode = true;
			}
		}
	}

	@Override
	public void keyTyped(final GuiTools tools, final GuiPosition pgp, final Point mouse, final char c, final int keycode) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (receptionMode){
			if (keycode == 1) {
				InputHandler.setKeyCode(0);
			} else {
				receptionMode = false;
				InputHandler.setKeyCode(keycode);
			}
		}
	}
}

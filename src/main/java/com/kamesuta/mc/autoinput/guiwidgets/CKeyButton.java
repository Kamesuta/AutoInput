package com.kamesuta.mc.autoinput.guiwidgets;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.IPositionRelative;
import com.kamesuta.mc.guiwidget.position.Point;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class CKeyButton extends CButton {

	private boolean receptionMode = false;
	protected GuiKeyBinding keyBinding;
	public IGuiControllable controllable;

	public CKeyButton(final IPositionRelative rp, final GuiKeyBinding keyBinding, final IGuiControllable controllable) {
		super(rp);
		this.rp = rp;
		this.keyBinding = keyBinding;
		this.controllable = controllable;
	}

	public GuiKeyBinding getKeyBinding() {
		return this.keyBinding;
	}

	public boolean getReceptionMode() {
		return this.receptionMode;
	}

	public void setReceptionMode(final boolean receptionMode) {
		this.receptionMode = receptionMode;
	}

	@Override
	public String getName() {
		final int keyCode = getKeyBinding().getKeyCode();
		String disp;
		if (keyCode < 0) {
			switch(keyCode) {
			case -100:
				disp = I18n.format(Names.Keys.KEY0);
				break;
			case -99:
				disp = I18n.format(Names.Keys.KEY1);
				break;
			case -98:
				disp = I18n.format(Names.Keys.KEY2);
				break;
			default:
				disp = Mouse.getButtonName(keyCode+100);
			}
		} else {
			disp = Keyboard.getKeyName(keyCode);
		}

		if (this.receptionMode){
			return EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + disp + EnumChatFormatting.WHITE + " <";
		} else {
			return disp;
		}
	}

	@Override
	public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (this.receptionMode) {
			getKeyBinding().setKeyCode(-100+button);
			this.receptionMode = false;
			this.controllable.setControllable(null);
		} else if (abs.pointInside(p) && button == 0) {
			this.receptionMode = true;
			this.controllable.setControllable(this);
		}
	}

	@Override
	public void keyTyped(final GuiTools tools, final GuiPosition pgp, final Point mouse, final char c, final int keycode) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (this.receptionMode) {
			getKeyBinding().setKeyCode((keycode == 1) ? 0 : keycode);
			this.receptionMode = false;
		} else if (keycode == 1) {
			final Minecraft mc = Minecraft.getMinecraft();
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation("gui.button.press")));
		}
		this.controllable.setControllable(null);
	}
}

package com.kamesuta.mc.autoinput.guiparts;

import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

import net.minecraft.client.resources.I18n;

public class ToggleButton extends Button {

	public static String buttonEnabled = I18n.format(Names.Gui.SIWTCH_1);
	public static String buttonDisabled = I18n.format(Names.Gui.SIWTCH_2);

	private final GuiKeyBinding keyBinding;

	public ToggleButton(final R position, final String text, final GuiKeyBinding keyBinding) {
		super(position, text);
		this.keyBinding = keyBinding;
	}

	public ToggleButton(final R position, final GuiKeyBinding keyBinding) {
		this(position, keyBinding.getMode() ? buttonEnabled : buttonDisabled, keyBinding);
	}

	@Override
	protected void onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area abs = getGuiPosition(pgp);
		if (abs.pointInside(p)&&button==0) {
			this.keyBinding.setMode(!this.keyBinding.getMode());
			setText(this.keyBinding.getMode() ? buttonEnabled : buttonDisabled);
		}
	}
}

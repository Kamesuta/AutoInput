package com.kamesuta.mc.autoinput.guiparts;

import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.reference.Names;

import net.minecraft.client.resources.I18n;

public class ToggleButton extends Button {

	private final GuiKeyBinding keyBinding;

	public ToggleButton(final R position, final GuiKeyBinding keyBinding) {
		super(position);
		this.keyBinding = keyBinding;
		setText(keyBinding.getMode() ? I18n.format(Names.Gui.SIWTCH_1) : I18n.format(Names.Gui.SIWTCH_2));
	}

	@Override
	protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area abs = getGuiPosition(pgp);
		if (abs.pointInside(p)&&button==0) {
			this.keyBinding.setMode(!this.keyBinding.getMode());
			setText(this.keyBinding.getMode() ? I18n.format(Names.Gui.SIWTCH_1) : I18n.format(Names.Gui.SIWTCH_2));
		}
		return super.onClicked(ev, pgp, p, button);
	}
}

package net.teamfruit.autoinput.gui;

import net.minecraft.client.resources.I18n;
import net.teamfruit.autoinput.AutoInputKey;
import net.teamfruit.autoinput.bnnwidget.WEvent;
import net.teamfruit.autoinput.bnnwidget.position.Area;
import net.teamfruit.autoinput.bnnwidget.position.Point;
import net.teamfruit.autoinput.bnnwidget.position.R;
import net.teamfruit.autoinput.reference.Names;

public class GuiToggleButton extends GuiButton {

	private final AutoInputKey keyBinding;

	public GuiToggleButton(final R position, final AutoInputKey keyBinding) {
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

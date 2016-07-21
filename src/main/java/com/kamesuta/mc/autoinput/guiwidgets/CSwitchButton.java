package com.kamesuta.mc.autoinput.guiwidgets;

import com.kamesuta.mc.autoinput.GuiKeyBinding;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.IPositionRelative;
import com.kamesuta.mc.guiwidget.position.Point;

import net.minecraft.client.resources.I18n;

public class CSwitchButton extends CButton{

	protected GuiKeyBinding keySwitch;
	private final String buttonEnabled = I18n.format(Names.Gui.SIWTCH_1);
	private final String buttonDisabled = I18n.format(Names.Gui.SIWTCH_2);

	public CSwitchButton(final IPositionRelative rp, final GuiKeyBinding keySwitch) {
		super(rp);
		this.keySwitch = keySwitch;
	}

	public GuiKeyBinding getKeyBinding() {
		return this.keySwitch;
	}

	@Override
	public String getName() {
		return this.keySwitch.getMode() ? this.buttonEnabled : this.buttonDisabled;
	}

	@Override
	public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (abs.pointInside(p)) {
			onClicked(button);
			//							mc.thePlayer.sendChatMessage("ButtonBが押されました");
		}
	}

	private void onClicked(final int button) {
		if (button == 0) {
			this.keySwitch.setMode(!this.keySwitch.getMode());
		}
	}
}

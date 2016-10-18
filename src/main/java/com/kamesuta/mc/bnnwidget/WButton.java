package com.kamesuta.mc.bnnwidget;

import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

public class WButton extends WBase {
	public String text;
	public String actionCommand;
	private boolean isEnabled = true;

	public WButton(final R position, final String text) {
		super(position);
		this.text = text;
	}

	public void setText(final String s) {
		this.text = s;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(final boolean b) {
		this.isEnabled = b;
	}

	@Override
	public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area abs = getGuiPosition(pgp);
		if (abs.pointInside(p)) {
			if (isEnabled())
				onClicked(ev, pgp, p, button);
			return true;
		}
		return false;
	}

	protected void onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		if (this.actionCommand != null)
			ev.eventDispatch(this.actionCommand, Integer.valueOf(button));
	}

	public WButton setActionCommand(final String string) {
		this.actionCommand = string;
		return this;
	}
}
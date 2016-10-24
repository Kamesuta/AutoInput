package com.kamesuta.mc.autoinput.bnnwidget;

import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;

public abstract class WBase extends WComponent {

	protected R position;

	public WBase(final R position) {
		this.position = position;
	}

	public R getGuiRelative() {
		return this.position;
	}

	public Area getGuiPosition(final Area pgp) {
		return pgp.child(getGuiRelative());
	}

	@Override
	public WCommon top(final WEvent ev, final Area pgp, final Point point) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(point))
			return this;
		return null;
	}
}

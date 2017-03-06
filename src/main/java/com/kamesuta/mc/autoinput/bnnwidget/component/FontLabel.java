package com.kamesuta.mc.autoinput.bnnwidget.component;

import javax.annotation.Nonnull;

import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.font.WFontRenderer;
import com.kamesuta.mc.autoinput.bnnwidget.motion.Motion;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Coord;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.bnnwidget.var.V;
import com.kamesuta.mc.autoinput.bnnwidget.var.VMotion;
import com.kamesuta.mc.autoinput.bnnwidget.var.VPercent;

public class FontLabel extends FontScaledLabel {
	protected final @Nonnull VMotion height = V.pm(1f);
	private final @Nonnull VPercent absheight = V.per(V.a(0f), V.a(font().FONT_HEIGHT), this.height);
	private final @Nonnull R limit = new R(Coord.height(this.absheight));

	public FontLabel(final @Nonnull R position, final @Nonnull WFontRenderer wf) {
		super(position, wf);
	}

	@Override
	protected void drawText(final @Nonnull WEvent ev, final @Nonnull Area a, final @Nonnull Point p, final float frame, final float opacity) {
		super.drawText(ev, a.child(this.limit), p, frame, opacity);
	}

	public float getScale() {
		return this.height.get();
	}

	public @Nonnull FontLabel setScale(final float scale) {
		this.height.add(Motion.move(scale));
		return this;
	}
}

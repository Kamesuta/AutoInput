package com.kamesuta.mc.autoinput.guiparts;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.bnnwidget.WButton;
import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.widget.RenderHelper;

public class Button extends WButton {

	protected int textcolor = 0xffffff;

	public Button(final R position, final String text) {
		super(position, text);
	}

	public void setTextColor(final int color) {
		this.textcolor = color;
	}

	public int getTextColor() {
		return this.textcolor;
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(p)) {
			glLineWidth(2f);
			glColor4f(1, 1, 1, 0.2f);
			RenderHelper.startShape();
			draw(a, GL_LINE_LOOP);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
		}
		glPushMatrix();
		glTranslated(a.x1()+a.w()/2, a.y1()+a.h()/2, 0);
		drawStringC(this.text, 0, 0, 0, 0, getTextColor());
		glPopMatrix();
	}
}

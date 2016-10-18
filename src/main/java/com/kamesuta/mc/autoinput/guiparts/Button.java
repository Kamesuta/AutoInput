package com.kamesuta.mc.autoinput.guiparts;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.RenderHelper;
import com.kamesuta.mc.bnnwidget.WButton;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

public class Button extends WButton {

	public Button(final R position, final String text) {
		super(position, text);
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
		drawStringC(this.text, 0, 0, 0, 0, 0xffffff);
		glPopMatrix();
	}
}

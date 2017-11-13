package com.kamesuta.mc.autoinput.gui;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.Nonnull;

import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.component.MButton;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.bnnwidget.render.OpenGL;
import com.kamesuta.mc.autoinput.bnnwidget.render.RenderOption;
import com.kamesuta.mc.autoinput.bnnwidget.render.WRenderer;

public class GuiButton extends MButton {

	protected int textcolor = 0xffffff;

	public GuiButton(final R position) {
		super(position);
	}

	public void setTextColor(final int color) {
		this.textcolor = color;
	}

	public int getTextColor() {
		return this.textcolor;
	}

	@Override
	public void draw(final @Nonnull WEvent ev, final @Nonnull Area pgp, final @Nonnull Point p, final float frame, final float popacity, final @Nonnull RenderOption opt) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(p)) {
			glLineWidth(2f);
			glColor4f(1, 1, 1, 0.2f);
			WRenderer.startShape();
			draw(a, GL_LINE_LOOP);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
		}
		glPushMatrix();
		glTranslated(a.x1()+a.w()/2, a.y1()+a.h()/2, 0);
		//drawString(this.text, 0, 0, 0, 0, getTextColor());
		OpenGL.glColorRGB(getTextColor());
		final String text = this.text;
		if (text!=null)
			drawString(text, Area.abs(0, 0, 0, 0), Align.CENTER, VerticalAlign.MIDDLE, false);
		glPopMatrix();
	}
}

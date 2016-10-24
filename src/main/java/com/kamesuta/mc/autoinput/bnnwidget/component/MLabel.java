package com.kamesuta.mc.autoinput.bnnwidget.component;

import static org.lwjgl.opengl.GL11.*;

import org.apache.commons.lang3.StringUtils;

import com.kamesuta.mc.autoinput.bnnwidget.WBase;
import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.widget.RenderHelper;

public class MLabel extends WBase {
	protected int textcolor = 14737632;
	protected String text;

	public MLabel(final R position, final String text) {
		super(position);
		this.text = text;
	}

	public void setColor(final int color) {
		this.textcolor = color;
	}

	public int getColor() {
		return this.textcolor;
	}

	public void setText(final String s) {
		if (StringUtils.equals(s, getText())) {
			return;
		}
		final String oldText = getText();
		this.text = s;
		onTextChanged(oldText);
	}

	public String getText() {
		return this.text;
	}

	protected void onTextChanged(final String oldText) {
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		final Area out = getGuiPosition(pgp);
		drawText(out);
	}

	protected float wscale = 1f;

	public void setScaleWidth(final float f) {
		this.wscale = f;
	}

	public float getScaleWidth() {
		return this.wscale;
	}

	protected float hscale = 1f;

	public void setScaleHeight(final float f) {
		this.hscale = f;
	}

	public float getScaleHeight() {
		return this.hscale;
	}

	protected void drawText(final Area a) {
		glPushMatrix();
		glTranslated(a.x1()+a.w()/2, a.y1() + a.h()/2 , 0);
		glScaled(getScaleWidth(), getScaleHeight(), 1);
		RenderHelper.startTexture();
		drawStringC(getText(), 0, 0, 0, 0, getColor());
		glPopMatrix();
	}
}

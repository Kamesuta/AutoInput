package com.kamesuta.mc.autoinput.gui;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.guiwidget.GuiComponent;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.Point;
import com.kamesuta.mc.guiwidget.position.RelativePosition;

public class CWidgets {}

class CTitle extends GuiComponent {
	protected RelativePosition rp;
	protected String name;
	private final int colour;
	public CTitle(final RelativePosition rp, final String name, final int colour) {
		this.rp = rp;
		this.name = name;
		this.colour = colour;
	}

	@Override
	public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();

		glColor4f(1, 1, 1, 0.3f);
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glVertex3f(abs.x1(), abs.y1(), 0);
		glVertex3f(abs.x1(), abs.y2(), 0);
		glVertex3f(abs.x2(), abs.y2(), 0);
		glVertex3f(abs.x2(), abs.y1(), 0);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		glPushMatrix();
		glTranslated((abs.x1()+abs.x2())/2, (abs.y1()+abs.y2())/2, 0);
		glScaled(2.0, 2.0, 1);
		tools.g.drawStringC(this.name, 0, 0, 0, 0, this.colour);
		glPopMatrix();
	}
}

class CButton extends GuiComponent {
	protected RelativePosition rp;
	protected String name;
	public CButton(final RelativePosition rp, final String name) {
		this.rp = rp;
		this.name = name;
	}

	@Override
	public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();
		if (abs.pointInside(p)){
			glColor4f(1, 1, 1, 0.2f);
			glEnable(GL_BLEND);
			glDisable(GL_TEXTURE_2D);
			glLineWidth(2.0f);
			glBegin(GL_LINE_LOOP);
			glVertex3f(abs.x1(), abs.y1(), 0);
			glVertex3f(abs.x1(), abs.y2(), 0);
			glVertex3f(abs.x2(), abs.y2(), 0);
			glVertex3f(abs.x2(), abs.y1(), 0);
			glEnd();
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
		}
		glPushMatrix();
		glTranslated((abs.x1()+abs.x2())/2, (abs.y1()+abs.y2())/2, 0);
		tools.g.drawStringC(getName(), 0, -tools.g.fontRenderer.FONT_HEIGHT/2, 0xffffff);
		glPopMatrix();
	}

	public String getName() {
		return this.name;
	}
}

class CText extends GuiComponent {
	protected RelativePosition rp;
	protected String name;
	public CText(final RelativePosition rp, final String name) {
		this.rp = rp;
		this.name = name;
	}

	@Override
	public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
		final GuiPosition gp = pgp.child(this.rp);
		final IPositionAbsolute abs = gp.getAbsolute();

		glColor4f(1, 1, 1, 0.3f);
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glVertex3f(abs.x1(), abs.y1(), 0);
		glVertex3f(abs.x1(), abs.y2(), 0);
		glVertex3f(abs.x2(), abs.y2(), 0);
		glVertex3f(abs.x2(), abs.y1(), 0);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		glPushMatrix();
		glTranslated((abs.x1()+abs.x2())/2, (abs.y1()+abs.y2())/2, 0);
		tools.g.drawStringC(this.name, 0, 0, 0, 0, 0xffffff);
		glPopMatrix();
	}
}


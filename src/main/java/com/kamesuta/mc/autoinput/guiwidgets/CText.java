package com.kamesuta.mc.autoinput.guiwidgets;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.guiwidget.GuiComponent;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.Point;
import com.kamesuta.mc.guiwidget.position.RelativePosition;

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

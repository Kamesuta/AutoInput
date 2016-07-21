package com.kamesuta.mc.autoinput.guiwidgets;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.guiwidget.GuiComponent;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.IPositionRelative;
import com.kamesuta.mc.guiwidget.position.Point;

public class CButton extends GuiComponent {
	protected IPositionRelative rp;
	protected String name;

	public CButton(final IPositionRelative rp2, final String name) {
		this.rp = rp2;
		this.name = name;
	}
	public CButton(final IPositionRelative rp2) {
		this(rp2, "");
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

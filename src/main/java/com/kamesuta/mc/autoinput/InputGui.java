package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.guiwidget.GuiComponent;
import com.kamesuta.mc.guiwidget.GuiFrame;
import com.kamesuta.mc.guiwidget.GuiPanel;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.Point;
import com.kamesuta.mc.guiwidget.position.RelativePosition;
import com.kamesuta.mc.guiwidget.position.RelativeSizedPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

public class InputGui {
	public static void gui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiFrame() {
			@Override
			protected void initWidgets() {
				//				final GuiPanel p = new GuiPanel(new RelativePosition(40, 20, -40, -30)) {
				final Minecraft mc = Minecraft.getMinecraft();
				final ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				final int rwidth = res.getScaledWidth();
				final int rheight = res.getScaledHeight();
				final int relativeWidth = 200;
				final int relativeHeight = 170;
				final GuiPanel p = new GuiPanel(new RelativeSizedPosition((rwidth/(mc.displayWidth/(mc.displayWidth/2))-((rwidth/(mc.displayWidth/2)+relativeWidth)/2)), (rheight/(mc.displayHeight/(mc.displayHeight/2))-((rheight/(mc.displayHeight/2)+relativeHeight)/2)), rwidth/(mc.displayWidth/2)+relativeWidth, rheight/(mc.displayHeight/2)+relativeHeight)) {
					@Override
					public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
						final GuiPosition gp = pgp.child(this.position);
						final IPositionAbsolute abs = gp.getAbsolute();

						glColor4f(0, 0, 0, 0.4f);
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

						super.draw(tools, pgp, p, frame);//Bye
					}
				};
				final GuiComponent c1 = new CTitle(new RelativePosition(5, 5, -5, 25), I18n.format(Names.Gui.TITLE), 0xffffff) {};
				final GuiComponent c2 = new CButton(new RelativePosition(5, 35, -5, 55), null) {
					private boolean switchMode;
					private final String switchNames[] = new String[] {I18n.format(Names.Gui.SIWTCH_1), I18n.format(Names.Gui.SIWTCH_2)};

					@Override
					public String getName() {
						return this.switchNames[this.switchMode ? 0 : 1];
					}

					@Override
					public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (abs.pointInside(p)) {
							onClicked(button);
							//							final Minecraft mc = Minecraft.getMinecraft();
							//							mc.thePlayer.sendChatMessage("ButtonBが押されました");
							//							mc.displayGuiScreen(null);
						}
					}

					private void onClicked(final int button) {
						if (button == 0) {
							this.switchMode = !this.switchMode;
							ClientTickHandler.rightclick = this.switchMode;
						}
					}
				};
				final GuiComponent c99999999 = new CButton(new RelativePosition(75, 153, -75, 168), I18n.format(Names.Gui.DONE)) {
					@Override
					public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (abs.pointInside(p)) {
							final Minecraft mc = Minecraft.getMinecraft();
							mc.displayGuiScreen(null);
						}
					}
				};
				p.add(c1);
				p.add(c2);
				p.add(c99999999);
				add(p);
			}
		});
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

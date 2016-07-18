package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class InputGui {
	public static void gui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiFrame() {
			@Override
			protected void initWidgets() {
				final Minecraft mc = Minecraft.getMinecraft();
				final ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				final int rwidth = res.getScaledWidth();
				final int rheight = res.getScaledHeight();
				final int absoluteWidth = 190;
				final int absoluteHeight = 172;
				final GuiPanel p = new GuiPanel(new RelativeSizedPosition(rwidth/2-((rwidth/(mc.displayWidth/2)+absoluteWidth)/2), rheight/2-((rheight/(mc.displayHeight/2)+absoluteHeight)/2), rwidth/(mc.displayWidth/2)+absoluteWidth, rheight/(mc.displayHeight/2)+absoluteHeight)) {
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
				final GuiComponent c2 = new CButton(new RelativePosition(5, 30, -5, 50), I18n.format(Names.Gui.SIWTCH_1)) {
					private final String switchNames[] = new String[] {I18n.format(Names.Gui.SIWTCH_1), I18n.format(Names.Gui.SIWTCH_2)};

					@Override
					public String getName() {
						return this.switchNames[InputHandler.switchMode ? 0 : 1];
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
							InputHandler.switchMode = !InputHandler.switchMode;
						}
					}
				};

				final GuiComponent c3 = new CButton(new RelativePosition(5, 55, -5, 75), null){

					@Override
					public String getName() {
						if (InputHandler.receptionMode){
							return EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + InputHandler.displayString + EnumChatFormatting.WHITE + " <";
						} else {
							return InputHandler.displayString;
						}
					}
					@Override
					public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (abs.pointInside(p)) {
							if (button == 0) {
								onLeftClicked(button);
							} else if (InputHandler.receptionMode) {
								InputHandler.displayString = Mouse.getButtonName(button);
								InputHandler.receptionMode = false;
							}
						}
					}

					@Override
					public void keyTyped(final GuiTools tools, final GuiPosition pgp, final Point mouse, final char c, int keycode) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (InputHandler.receptionMode){
							if (keycode == 1){
								keycode = 0;
							}
							InputHandler.displayString = Keyboard.getKeyName(keycode);
							InputHandler.receptionMode = false;
						}
					}

					private void onLeftClicked(final int button) {
						if (InputHandler.receptionMode){
							InputHandler.displayString = Mouse.getButtonName(button);
							InputHandler.receptionMode = false;
						} else {
							InputHandler.receptionMode = true;
						}
					}
				};

				final GuiComponent c33 = new CButton(new RelativePosition (60, 125, -60, 145), I18n.format(Names.Gui.OPTIONS)) {
					@Override
					public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (abs.pointInside(p)) {
							onClicked(button);
						}
					}

					private void onClicked(final int button) {
						if (button == 0) {
							final EntityPlayer player = mc.thePlayer;
							player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + I18n.format(Names.Gui.OPTIONTEXT)));
							mc.displayGuiScreen(null);
						}
					}
				};

				final GuiComponent c44 = new CButton(new RelativePosition(70, 150, -70, 170), I18n.format(Names.Gui.DONE)) {
					@Override
					public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
						final GuiPosition gp = pgp.child(this.rp);
						final IPositionAbsolute abs = gp.getAbsolute();
						if (abs.pointInside(p)) {
							onClicked(button);
						}
					}

					private void onClicked(final int button) {
						if (button == 0) {
							final Minecraft mc = Minecraft.getMinecraft();
							mc.displayGuiScreen(null);
						}
					}
				};
				p.add(c1);
				p.add(c2);
				p.add(c3);
				p.add(c33);
				p.add(c44);
				add(p);
			}

			@Override
			public boolean isClosable() {
				if (InputHandler.receptionMode) {
					return false;
				} else {
					return true;
				}
			};
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

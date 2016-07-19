package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.guiwidgets.CButton;
import com.kamesuta.mc.autoinput.guiwidgets.CKeyButton;
import com.kamesuta.mc.autoinput.guiwidgets.CTitle;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.guiwidget.GuiCommon;
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
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class InputGui {
	public static void gui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiFrame() {
			@Override
			public boolean isClosable() {
				return !CKeyButton.getReceptionMode();
			};

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

						super.draw(tools, pgp, p, frame);
					}
					@Override
					public void keyTyped(final GuiTools tools, final GuiPosition pgp, final Point p, final char c, final int keycode) {
						final GuiPosition gp = pgp.child(this.position);
						if (gp.position.isVaild())
							for (final GuiCommon widget : this.widgets)
								widget.keyTyped(tools, gp, p, c, keycode);
						if (keycode == 1 && !CKeyButton.getReceptionMode()) {
							mc.getSoundHandler().playSound(PositionedSoundRecord
									.func_147673_a(new ResourceLocation("random.click")));
						} else {
							CKeyButton.setReceptionMode(false);
						}
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
							//							mc.thePlayer.sendChatMessage("ButtonBが押されました");
						}
					}

					private void onClicked(final int button) {
						if (button == 0) {
							InputHandler.switchMode = !InputHandler.switchMode;
						}
					}
				};

				final GuiComponent c3 = new CKeyButton(new RelativePosition(5, 55, -5, 75), "");

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
							CKeyButton.setReceptionMode(false);
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
							mc.getSoundHandler().playSound(PositionedSoundRecord
									.func_147673_a(new ResourceLocation("random.click")));
							CKeyButton.setReceptionMode(false);
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
		});
	}
}

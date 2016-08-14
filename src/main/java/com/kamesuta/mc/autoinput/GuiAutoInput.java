package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import com.kamesuta.mc.autoinput.guiwidgets.CButton;
import com.kamesuta.mc.autoinput.guiwidgets.CKeyButton;
import com.kamesuta.mc.autoinput.guiwidgets.CSwitchButton;
import com.kamesuta.mc.autoinput.guiwidgets.CText;
import com.kamesuta.mc.autoinput.guiwidgets.CTitle;
import com.kamesuta.mc.autoinput.guiwidgets.IGuiControllable;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiAutoInput extends GuiFrame implements IGuiControllable {
	public static List<GuiKeyBinding> keys = new ArrayList<GuiKeyBinding>() {{
		for (int i = 0; i < 3; i++)
			this.add(new GuiKeyBinding());
	}};
	protected GuiCommon controllable;
	protected List<GuiPanel> panels = new ArrayList<GuiPanel>();
	protected RelativeSizedPosition scrollpanel;

	public GuiPanel addKeyPanel(final int absoluteWidth, final GuiKeyBinding key) {
		final int i = this.panels.size();
		final GuiPanel panel = new GuiPanel(new RelativeSizedPosition(0, 23*i, absoluteWidth, 20)) {
			@Override
			public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
				final GuiPosition gp = pgp.child(this.position);
				final IPositionAbsolute abs = gp.getAbsolute();
				if (abs.y1() < abs.y2())
					super.draw(tools, pgp, p, frame);
			}
		};
		this.panels.add(panel);
		final GuiComponent sw = new CSwitchButton(new RelativeSizedPosition(5, 0, absoluteWidth*2/3-5, 20), key);
		final GuiComponent bt = new CKeyButton(new RelativeSizedPosition(-5+1, 0, absoluteWidth*1/3-5, 20), key, this);
		panel.add(sw);
		panel.add(bt);
		return panel;
	}

	@Override
	public void keyTyped(final char c, final int keycode) {
		if (this.controllable == null)
			super.keyTyped(c, keycode);
		else {
			final GuiPosition gp = new GuiPosition(null, this.position, getAbsolute());
			final Point p = getAbsoluteMousePosition();
			this.controllable.keyTyped(this.tools, gp, p, c, keycode);
		}
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int button) {
		if (this.controllable == null)
			super.mouseClicked(x, y, button);
		else {
			final GuiPosition gp = new GuiPosition(null, this.position, getAbsolute());
			final Point p = getAbsoluteMousePosition();
			this.controllable.mouseClicked(this.tools, gp, p, button);
		}
	}

	@Override
	public boolean isControllable() {
		return this.controllable == null;
	}

	@Override
	public void setControllable(final GuiCommon controllable) {
		this.controllable = controllable;
	}

	@Override
	protected void initWidgets() {
		final Minecraft mc = Minecraft.getMinecraft();
		final ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		final int rwidth = res.getScaledWidth();
		final int rheight = res.getScaledHeight();
		final int absoluteWidth = 200;
		final int absoluteHeight = 170;
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

				tools.drawDebug(gp);

				super.draw(tools, pgp, p, frame);
			}
		};
		final GuiComponent c1 = new CTitle(new RelativePosition(5, 5, -5, 25), I18n.format(Names.Gui.TITLE), 0xffffff) {};
		final GuiComponent c2 = new CText(new RelativePosition(5, 30, -5, 45), I18n.format(Names.Gui.SETTINGS)){};

		p.add(c1);
		p.add(c2);

		final GuiPanel keypanels = new GuiPanel(new RelativePosition(0, 50, -1, 116)) {
			@Override
			public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
				super.draw(tools, pgp, p, frame);
				final GuiPosition gp = pgp.child(this.position);
				//				tools.drawDebug(gp);
			}

			@Override
			public void mouseScrolled(final GuiTools tools, final GuiPosition pgp, final Point p, final int scroll) {
				final GuiPosition gp = pgp.child(this.position);
				if (gp.position.isVaild()) {
					//				GuiAutoInput.this.scrollpanel.y -= scroll/50;
				}
			}
		};

		this.scrollpanel = new RelativeSizedPosition(0, 0, absoluteWidth, 116);

		final GuiPanel keypanelinner = new GuiPanel(this.scrollpanel) {
			@Override
			public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
				super.draw(tools, pgp, p, frame);
				final GuiPosition gp = pgp.child(this.position);
				//				tools.drawDebug(gp);
			}
		};
		for (final GuiKeyBinding keySwitch : keys) {
			keypanelinner.add(addKeyPanel(absoluteWidth, keySwitch));
		}
		keypanels.add(keypanelinner);
		p.add(keypanels);

		final GuiComponent c3 = new CButton(new RelativePosition (60, 120, -60, 140), "") {
			private String displayString = I18n.format(Names.Gui.OPTIONS);
			@Override
			public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
				final GuiPosition gp = pgp.child(this.rp);
				final IPositionAbsolute abs = gp.getAbsolute();
				if (abs.pointInside(p)) {
					onClicked(button);
				}
			}

			@Override
			public String getName() {
				return this.displayString;
			}

			private void onClicked(final int button) {
				if (button == 0)
					this.displayString = EnumChatFormatting.RED + I18n.format(Names.Gui.NOTAVAIABLE);
			}
		};

		final GuiComponent c4 = new CButton(new RelativePosition(70, 145, -70, 165), I18n.format(Names.Gui.DONE)) {
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
							.func_147673_a(new ResourceLocation("gui.button.press")));
					mc.displayGuiScreen(null);
				}
			}
		};
		p.add(c3);
		p.add(c4);
		add(p);
	}
}
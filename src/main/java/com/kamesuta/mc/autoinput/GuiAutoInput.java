package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import com.kamesuta.mc.autoinput.bnnwidget.WCommon;
import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.WFrame;
import com.kamesuta.mc.autoinput.bnnwidget.WPanel;
import com.kamesuta.mc.autoinput.bnnwidget.component.MLabel;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Coord;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.RArea;
import com.kamesuta.mc.autoinput.guiparts.Button;
import com.kamesuta.mc.autoinput.guiparts.IGuiControllable;
import com.kamesuta.mc.autoinput.guiparts.KeyButton;
import com.kamesuta.mc.autoinput.guiparts.ToggleButton;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.autoinput.widget.RenderHelper;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiAutoInput extends WFrame implements IGuiControllable {

	public static List<GuiKeyBinding> keys = new ArrayList<GuiKeyBinding>() {
		{
			this.add(new GuiKeyBinding().setMode(true).setKeyCode(-99));
			this.add(new GuiKeyBinding());
			this.add(new GuiKeyBinding());
		}
	};

	private WCommon gui;

	@Override
	public void setControllable(final WCommon gui) {
		this.gui = gui;
	}

	@Override
	public boolean isControllable() {
		return this.gui==null;
	}

	@Override
	protected void init() {
		add(new WPanel(new RArea()) {
			@Override
			protected void initWidget() {
				add(new WPanel(new RArea(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(200), Coord.height(170))) {
					protected final RArea gp = new RArea(Coord.pleft(-.5f), Coord.ptop(-.5f), Coord.pwidth(1f), Coord.pheight(1f));

					@Override
					public Area getGuiPosition(final Area pgp) {
						return super.getGuiPosition(pgp).child(this.gp);
					}

					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
						final Area a = getGuiPosition(pgp);

						RenderHelper.startShape();
						glColor4f(0, 0, 0, 0.4f);
						draw(a, GL_QUADS);

						glLineWidth(2f);
						glColor4f(1, 1, 1, 0.2f);
						draw(a, GL_LINE_LOOP);

						super.draw(ev, pgp, p, frame, opacity);
					}

					@Override
					protected void initWidget() {
						add(new MLabel(new RArea(Coord.left(5), Coord.top(5), Coord.right(5), Coord.height(20)), I18n.format(Names.Gui.TITLE)) {
							{
								setScaleWidth(2f);
								setScaleHeight(2f);
								setColor(0xffffff);
							}

							@Override
							public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
								final Area a = getGuiPosition(pgp);

								glColor4f(1, 1, 1, 0.3f);
								RenderHelper.startShape();
								drawRect(a);

								super.draw(ev, pgp, p, frame, opacity);
							}
						});

						add(new MLabel(new RArea(Coord.left(5), Coord.top(30), Coord.right(5), Coord.height(15)), I18n.format(Names.Gui.SETTINGS)) {
							{
								setColor(0xffffff);
							}

							@Override
							public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
								final Area a = getGuiPosition(pgp);

								glColor4f(1, 1, 1, 0.3f);
								RenderHelper.startShape();
								drawRect(a);

								super.draw(ev, pgp, p, frame, opacity);
							}
						});

						int t = 50;
						for (final GuiKeyBinding binding : keys) {
							add(new ToggleButton(new RArea(Coord.left(5), Coord.top(t), Coord.right(200/3+5), Coord.height(20)), binding));
							add(new KeyButton(new RArea(Coord.left(200/3*2+5), Coord.top(t), Coord.right(5), Coord.height(20)), binding, GuiAutoInput.this));
							t += 25;
						}

						add(new Button(new RArea(Coord.left(60), Coord.top(120), Coord.right(60), Coord.height(20)), I18n.format(Names.Gui.OPTIONS)) {

							@Override
							public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
								final Area a = getGuiPosition(pgp);
								if (a.pointInside(p)&&button==0) {
									setTextColor(0xff5555);
									setText(I18n.format(Names.Gui.NOTAVAIABLE));
									return true;
								}
								return false;
							}
						});

						add(new Button(new RArea(Coord.left(70), Coord.top(145), Coord.right(70), Coord.height(20)), I18n.format(Names.Gui.DONE)) {

							@Override
							public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
								final Area a = getGuiPosition(pgp);
								if (a.pointInside(p)&&button==0) {
									requestClose();
									return true;
								}
								return false;
							}
						});
					}
				});
			}
		});
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int button) {
		if (isControllable()) {
			super.mouseClicked(x, y, button);
		} else {
			this.mousebutton = button;
			final Area gp = getAbsolute();
			final Point p = getMouseAbsolute();
			this.gui.mouseClicked(this.event, gp, p, button);
		}
	}

	@Override
	protected void keyTyped(final char c, final int keycode) {
		if (isControllable()) {
			super.keyTyped(c, keycode);
		} else {
			final Area gp = getAbsolute();
			final Point p = getMouseAbsolute();
			this.gui.keyTyped(this.event, gp, p, c, keycode);
		}
	}

	@Override
	public void onGuiClosed() {
		this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		super.onGuiClosed();
	}
}

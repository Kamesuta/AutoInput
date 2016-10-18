package com.kamesuta.mc.autoinput;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import com.kamesuta.mc.autoinput.guiparts.KeyButton;
import com.kamesuta.mc.autoinput.guiparts.ToggleButton;
import com.kamesuta.mc.autoinput.reference.Names;
import com.kamesuta.mc.bnnwidget.WCommon;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WFrame;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MLabel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.RArea;

import net.minecraft.client.resources.I18n;

public class GuiBnn extends WFrame {

	public static List<GuiKeyBinding> keys = new ArrayList<GuiKeyBinding>() {
		{
			this.add(new GuiKeyBinding().setMode(true).setKeyCode(-99));
			this.add(new GuiKeyBinding());
			this.add(new GuiKeyBinding());
		}
	};

	protected boolean hookEsc;

	public void setHook(final boolean status) {
		this.hookEsc = status;
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
							add(new KeyButton(new RArea(Coord.left(200/3*2+5), Coord.top(t), Coord.right(5), Coord.height(20)), binding, GuiBnn.this));
							t += 25;
						}

					}

				});
			}
		});
	}

	@Override
	protected void keyTyped(final char c, final int keycode) {
		if (this.hookEsc) {
			final Area gp = getAbsolute();
			final Point p = getMouseAbsolute();
			for (final WCommon widget : this.widgets)
				widget.keyTyped(this.event, gp, p, c, keycode);
		} else {
			super.keyTyped(c, keycode);
		}
	}
}

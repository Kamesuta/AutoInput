package net.teamfruit.autoinput.gui;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.teamfruit.autoinput.AutoInputKey;
import net.teamfruit.autoinput.bnnwidget.WEvent;
import net.teamfruit.autoinput.bnnwidget.WFrame;
import net.teamfruit.autoinput.bnnwidget.WPanel;
import net.teamfruit.autoinput.bnnwidget.component.MLabel;
import net.teamfruit.autoinput.bnnwidget.position.Area;
import net.teamfruit.autoinput.bnnwidget.position.Coord;
import net.teamfruit.autoinput.bnnwidget.position.Point;
import net.teamfruit.autoinput.bnnwidget.position.R;
import net.teamfruit.autoinput.bnnwidget.render.OpenGL;
import net.teamfruit.autoinput.bnnwidget.render.RenderOption;
import net.teamfruit.autoinput.bnnwidget.render.WRenderer;
import net.teamfruit.autoinput.reference.Names;

public class GuiAutoInput extends WFrame {

	public static ImmutableList<AutoInputKey> keys = ImmutableList.of(
			new AutoInputKey().setMode(true).setKeyCode(-99),
			new AutoInputKey(),
			new AutoInputKey());
	private List<GuiKeyButton> buttons = Lists.newArrayList();

	public boolean isAnyReception() {
		for (final GuiKeyButton line : this.buttons)
			if (line.isReception())
				return true;
		return false;
	}

	@Override
	protected void initWidget() {
		add(new WPanel(new R()) {
			@Override
			protected void initWidget() {
				add(new WPanel(new R(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(200), Coord.height(170))) {
					protected final R gp = new R(Coord.pleft(-.5f), Coord.ptop(-.5f), Coord.pwidth(1f), Coord.pheight(1f));

					@Override
					public Area getGuiPosition(final Area pgp) {
						return super.getGuiPosition(pgp).child(this.gp);
					}

					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity, final RenderOption opt) {
						final Area a = getGuiPosition(pgp);

						WRenderer.startShape();
						glColor4f(0, 0, 0, 0.4f);
						draw(a, GL_QUADS);

						glLineWidth(2f);
						glColor4f(1, 1, 1, 0.2f);
						draw(a, GL_LINE_LOOP);

						super.draw(ev, pgp, p, frame, opacity, opt);
					}

					@Override
					protected void initWidget() {
						add(new MLabel(new R(Coord.left(5), Coord.top(5), Coord.right(5), Coord.height(20))) {
							{
								setScaleWidth(2f);
								setScaleHeight(2f);
								setColor(0xffffff);
							}

							@Override
							public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity, final RenderOption opt) {
								final Area a = getGuiPosition(pgp);

								WRenderer.startShape();
								OpenGL.glColor4f(1, 1, 1, 0.3f);
								draw(a);

								super.draw(ev, pgp, p, frame, opacity, opt);
							}
						}.setText(I18n.format(Names.Gui.TITLE)));

						add(new MLabel(new R(Coord.left(5), Coord.top(30), Coord.right(5), Coord.height(15))) {
							{
								setColor(0xffffff);
							}

							@Override
							public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity, final RenderOption opt) {
								final Area a = getGuiPosition(pgp);

								WRenderer.startShape();
								OpenGL.glColor4f(1, 1, 1, 0.3f);
								draw(a);

								super.draw(ev, pgp, p, frame, opacity, opt);
							}
						}.setText(I18n.format(Names.Gui.SETTINGS)));

						int t = 50;
						for (final AutoInputKey binding : keys) {
							add(new GuiToggleButton(new R(Coord.left(5), Coord.top(t), Coord.right(200/3+5), Coord.height(20)), binding));
							final GuiKeyButton key = new GuiKeyButton(new R(Coord.left(200/3*2+5), Coord.top(t), Coord.right(5), Coord.height(20)), binding);
							add(key);
							GuiAutoInput.this.buttons.add(key);
							t += 25;
						}

						add(new GuiButton(new R(Coord.left(60), Coord.top(120), Coord.right(60), Coord.height(20))) {

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
						}.setText(I18n.format(Names.Gui.OPTIONS)));

						add(new GuiButton(new R(Coord.left(70), Coord.top(145), Coord.right(70), Coord.height(20))) {

							@Override
							public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
								final Area a = getGuiPosition(pgp);
								if (a.pointInside(p)&&button==0) {
									requestClose();
									return true;
								}
								return false;
							}
						}.setText(I18n.format(Names.Gui.DONE)));
					}
				});
			}
		});
	}

	@Override
	protected boolean dispatchMouseClicked(final Area pgp, final Point p, final int button) {
		for (final GuiKeyButton line : this.buttons)
			if (line.isReception())
				return line.mouseClicked(this.event, pgp, p, button);
		return super.dispatchMouseClicked(pgp, p, button);
	}

	@Override
	protected boolean dispatchKeyTyped(final Area pgp, final Point p, final char c, final int keycode) {
		for (final GuiKeyButton line : this.buttons)
			if (line.isReception())
				return line.keyTyped(this.event, pgp, p, c, keycode);
		return getContentPane().keyTyped(this.event, pgp, p, c, keycode);
	}

	@Override
	protected void keyTyped(final char c, final int keycode) {
		final Area gp = getAbsolute();
		final Point p = getMouseAbsolute();
		final boolean b = isAnyReception();
		dispatchKeyTyped(gp, p, c, keycode);
		if (!b)
			sKeyTyped(c, keycode);
	}

	@Override
	public void requestClose() {
		if (!isAnyReception())
			super.requestClose();
	}

	@Override
	public void onGuiClosed() {
		this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		super.onGuiClosed();
	}
}

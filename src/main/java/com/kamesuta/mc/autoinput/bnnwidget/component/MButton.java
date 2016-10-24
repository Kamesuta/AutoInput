package com.kamesuta.mc.autoinput.bnnwidget.component;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.bnnwidget.WButton;
import com.kamesuta.mc.autoinput.bnnwidget.WEvent;
import com.kamesuta.mc.autoinput.bnnwidget.position.Area;
import com.kamesuta.mc.autoinput.bnnwidget.position.Point;
import com.kamesuta.mc.autoinput.bnnwidget.position.R;
import com.kamesuta.mc.autoinput.widget.RenderHelper;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class MButton extends WButton {
	public static final ResourceLocation button = new ResourceLocation("autoinput", "textures/gui/buttons.png");

	public MButton(final R position, final String text) {
		super(position, text);
	}

	@Override
	protected void onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		super.onClicked(ev, pgp, p, button);
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		drawButtonTex(ev, pgp, p, frame);
		if (this.text != null) {
			drawText(ev, pgp, p, frame);
		}
	}

	protected void drawButtonTex(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area a = getGuiPosition(pgp);
		RenderHelper.startTexture();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		texture().bindTexture(button);
		final int state = getButtonTex(ev, pgp, p, frame);

		drawTexturedModalRect(a.x1(), a.y1(), 0, state * 80, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1() + a.w() / 2, a.y1(), 256 - a.w() / 2, state * 80, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1(), a.y1() + a.h() / 2, 0, state * 80 + 80 - a.h() / 2, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1() + a.w() / 2, a.y1() + a.h() / 2, 256 - a.w() / 2, state * 80 + 80 - a.h() / 2, a.w() / 2, a.h() / 2);
	}

	public int getButtonTex(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area abs = getGuiPosition(pgp);
		return !isEnabled() ? 0 : abs.pointInside(p) ? 2 : 1;
	}

	public void drawText(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area abs = getGuiPosition(pgp);
		RenderHelper.startTexture();
		drawCenteredString(this.text, abs.x1() + abs.w() / 2, abs.y1() + (abs.h() - 8) / 2,
				getTextColour(ev, pgp, p, frame));
	}

	public int getTextColour(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area abs = getGuiPosition(pgp);
		return abs.pointInside(p) ? -96 : !isEnabled() ? -6250336 : -2039584;
	}
}

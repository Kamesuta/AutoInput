package com.kamesuta.mc.bnnwidget.component;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.autoinput.widget.RenderHelper;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

import net.minecraft.util.ResourceLocation;

public class MPanel extends WPanel {
	public static final ResourceLocation background = new ResourceLocation("autoinput", "textures/gui/background.png");

	public MPanel(final R position) {
		super(position);
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		drawButtonTex(ev, pgp, p, frame);
		super.draw(ev, pgp, p, frame, opacity);
	}

	protected void drawButtonTex(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area a = getGuiPosition(pgp);
		RenderHelper.startTexture();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		texture().bindTexture(background);

		drawTexturedModalRect(a.x1(), a.y1(), 0, 0, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1() + a.w() / 2, a.y1(), 256 - a.w() / 2, 0, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1(), a.y1() + a.h() / 2, 0, 256 - a.h() / 2, a.w() / 2, a.h() / 2);
		drawTexturedModalRect(a.x1() + a.w() / 2, a.y1() + a.h() / 2, 256 - a.w() / 2, 256 - a.h() / 2, a.w() / 2, a.h() / 2);
	}
}

package com.kamesuta.mc.autoinput;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.kamesuta.mc.guiwidget.GuiComponent;
import com.kamesuta.mc.guiwidget.GuiFrame;
import com.kamesuta.mc.guiwidget.GuiPanel;
import com.kamesuta.mc.guiwidget.GuiPosition;
import com.kamesuta.mc.guiwidget.GuiTools;
import com.kamesuta.mc.guiwidget.position.IPositionAbsolute;
import com.kamesuta.mc.guiwidget.position.Point;
import com.kamesuta.mc.guiwidget.position.RelativePosition;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private static final KeyBinding KEY_BINDING_SWITCH = new KeyBinding("autoinputmod.key.load", Keyboard.KEY_K,
			"autoinputmod.key.category");
	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding("autoinputmod.key.gui", Keyboard.KEY_L,
			"autoinputmod.key.category");

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_SWITCH, KEY_BINDING_GUI };

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent event) {
		if (this.minecraft.currentScreen == null) {
			if (KEY_BINDING_SWITCH.isPressed()) {
				ClientTickHandler.enableclick = !ClientTickHandler.enableclick;
			}
			if (KEY_BINDING_GUI.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiFrame() {
					@Override
					protected void initWidgets() {
						GuiPanel p = new GuiPanel(new RelativePosition(0, 0, 100, -1));
						p.add(new GuiComponent() {
							private double i;
							private RelativePosition pp = new RelativePosition(5, 5, -6, 30);
							@Override
							public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
								GuiPosition gp = pgp.child(pp);
								IPositionAbsolute abs = gp.getAbsolute();
								if (abs.pointInside(p))
									i=Math.random()*360;
								tools.drawDebug(gp);
								GL11.glPushMatrix();
								GL11.glTranslated((abs.x1()+abs.x2()/2), (abs.y1()+abs.y2())/2, 0);
								GL11.glRotated(i, 0, 0, 1);
								tools.g.drawStringC("sethome", 0, -tools.g.fontRenderer.FONT_HEIGHT/2, 0xffffff);
								GL11.glPopMatrix();
							}

							@Override
							public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
								GuiPosition gp = pgp.child(pp);
								IPositionAbsolute abs = gp.getAbsolute();
								if (abs.pointInside(p)) {
//									ClientTickHandler.enableclick = !ClientTickHandler.enableclick;
									Minecraft mc = Minecraft.getMinecraft();
									mc.thePlayer.sendChatMessage("/kill");
									mc.displayGuiScreen(null);
								}
							}
						});
						this.add(p);
					}
				});
			}
		}
	}
}

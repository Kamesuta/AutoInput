package com.kamesuta.mc.autoinput;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

//	private static final KeyBinding KEY_BINDI]NG_GUI = new KeyBinding("autoinputmod.key.gui", Keyboard.KEY_L,
//			"autoinputmod.key.category");
	private static final KeyBinding KEY_BINDING_SWITCH_1 = new KeyBinding("autoinputmod.key.rightclick", Keyboard.KEY_K,
			"autoinputmod.key.category");
	private static final KeyBinding KEY_BINDING_SWITCH_2 = new KeyBinding("autoinputmod.key.leftclik", Keyboard.KEY_L,
			"autoinputmod.key.category");

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_SWITCH_1, KEY_BINDING_SWITCH_2 };

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private InputHandler() {
	}

	@SubscribeEvent
	public void RightInput(InputEvent event) {
		if (this.minecraft.currentScreen == null) {
			if (KEY_BINDING_SWITCH_1.isPressed()) {
				ClientTickHandler.rightclick = !ClientTickHandler.rightclick;
			}
		}
	}
	@SubscribeEvent
	public void LeftInput(InputEvent event) {
		if (this.minecraft.currentScreen == null){
			if (KEY_BINDING_SWITCH_2.isPressed()){
//				ClientTickHandler.leftclick = !ClientTickHandler.leftclick;
//				if (ClientTickHandler.leftclick) {
					KeyBinding.setKeyBindState(1 - 101, true);
//				} else {
//					KeyBinding.setKeyBindState(1 - 101, false);
//				}
			}
		}
	}
//	@SubscribeEvent
//	public void onKeyInput(InputEvent event) {
//		if (this.minecraft.currentScreen == null) {
////			if (KEY_BINDING_SWITCH.isPressed()) {
////				ClientTickHandler.enableclick = !ClientTickHandler.enableclick;
////			}
//			if (KEY_BINDING_GUI.isPressed()) {
//				Minecraft.getMinecraft().displayGuiScreen(new GuiFrame() {
//					@Override
//					protected void initWidgets() {
//						GuiPanel p = new GuiPanel(new RelativePosition(0, 0, 100, -1));
//						p.add(new GuiComponent() {
//							private double i;
//							private RelativePosition pp1 = new RelativePosition(5, 5, -6, 30);
//							@Override
//							public void draw(final GuiTools tools, final GuiPosition pgp, final Point p, final float frame) {
//								GuiPosition gp = pgp.child(pp1);
//								IPositionAbsolute abs = gp.getAbsolute();
//								if (abs.pointInside(p))
////									i=Math.random()*360;
//								tools.drawDebug(gp);
//								GL11.glPushMatrix();
//								GL11.glTranslated((abs.x1()+abs.x2()/2), (abs.y1()+abs.y2())/2, 0);
////								GL11.glRotated(i, 0, 0, 1);
//								tools.g.drawStringC("sethome", 0, -tools.g.fontRenderer.FONT_HEIGHT/2, 0xffffff);
//								GL11.glPopMatrix();
//							}
//
//							@Override
//							public void mouseClicked(final GuiTools tools, final GuiPosition pgp, final Point p, final int button) {
//								GuiPosition gp = pgp.child(pp1);
//								IPositionAbsolute abs = gp.getAbsolute();
//								if (abs.pointInside(p)) {
////									ClientTickHandler.enableclick = !ClientTickHandler.enableclick;
//									Minecraft mc = Minecraft.getMinecraft();
//									mc.thePlayer.sendChatMessage("/kill");
//									mc.displayGuiScreen(null);
//								}
//							}
//						});
//						this.add(p);
//					}
//				});
//			}
//		}
//	}
}

package com.kamesuta.mc.autoinput;

import com.kamesuta.mc.autoinput.handler.ClientTickHandler;
import com.kamesuta.mc.autoinput.handler.InputHandler;
import com.kamesuta.mc.autoinput.reference.Reference;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.settings.KeyBinding;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class AutoInputMod {

	@Instance(Reference.MODID)
	public static AutoInputMod instance;

	@EventHandler
	public void perInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		for (final KeyBinding keyBinding : InputHandler.KEY_BINDINGS) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(InputHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ClientTickHandler.INSTANCE);
	}

}
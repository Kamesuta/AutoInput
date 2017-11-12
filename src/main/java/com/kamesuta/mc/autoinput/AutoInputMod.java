package com.kamesuta.mc.autoinput;

import com.kamesuta.mc.autoinput.handler.ClientTickHandler;
import com.kamesuta.mc.autoinput.handler.GuiOpenHandler;
import com.kamesuta.mc.autoinput.handler.InputHandler;
import com.kamesuta.mc.autoinput.reference.Reference;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class AutoInputMod {

	@Instance(Reference.MODID)
	public static AutoInputMod instance;

	@EventHandler
	public void perInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		for (final KeyBinding keyBinding : InputHandler.KEY_BINDINGS)
			ClientRegistry.registerKeyBinding(keyBinding);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(InputHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ClientTickHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(GuiOpenHandler.INSTANCE);
	}

}
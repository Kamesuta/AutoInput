package com.kamesuta.mc.autoinput;

import java.util.Map;

import com.kamesuta.mc.autoinput.reference.Reference;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.settings.KeyBinding;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class AutoInputMod {
	@Instance(Reference.MODID)
	public static AutoInputMod instance;

	// レンダーIDの取得
	public static int RenderID;

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

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {

	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}
}
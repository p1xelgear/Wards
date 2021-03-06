package wards;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import wards.block.BlockWard;
import wards.block.TileEntityWard;
import wards.effect.WardEffectManager;
import wards.function.WardFunctionEvent;
import wards.proxy.ICommonProxy;

@Mod(modid = Wards.MODID, name = Wards.NAME, version = Wards.VERSION)
public class Wards
{
    public static final String MODID = "wards";
    public static final String NAME = "Wards";
    public static final String VERSION = "1.1";
    
    @SidedProxy(clientSide = "wards.proxy.ClientProxy", serverSide = "wards.proxy.ServerProxy")
    public static ICommonProxy proxy;

    public static Logger logger;

    public static Block ward;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        
        logger.info("pre init!!!!!!!!!!!!!!!!!!!!!!");
        ward = new BlockWard().setUnlocalizedName("ward").setRegistryName(new ResourceLocation(MODID, "ward"));
        ward.setCreativeTab(CreativeTabs.MISC);
        GameRegistry.registerTileEntity(TileEntityWard.class, new ResourceLocation(MODID, "ward"));
        WardEffectManager.initEffects();
        
    	MinecraftForge.EVENT_BUS.register(this);
    	MinecraftForge.EVENT_BUS.register(new WardFunctionEvent());
    	MinecraftForge.EVENT_BUS.register(new WardsConfig.ConfigEventHandler());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerTESRs();
    }
    
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(ward);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		ItemBlock item = new ItemBlock(ward);
		item.setRegistryName(new ResourceLocation(Wards.MODID, "ward"));
		event.getRegistry().register(item);
	}
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		IForgeRegistry<Potion> registry = event.getRegistry();
		for(Potion potion : WardEffectManager.EFFECTS)
		{
			registry.register(potion);
		}
	}
	
	@SubscribeEvent
	public void registerItemModels(ModelRegistryEvent event)
	{
		Item item = Item.getItemFromBlock(ward);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}

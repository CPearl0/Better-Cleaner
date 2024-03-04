package com.cpearl.bettercleaner;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(BetterCleaner.MODID)
public class BetterCleaner
{
    public static final String MODID = "bettercleaner";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BetterCleaner() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}

package com.cpearl.bettercleaner;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = BetterCleaner.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue CLEAN_INTERVAL = BUILDER
            .comment("The interval between two cleanings. Unit: second.")
            .defineInRange("cleanInterval", 90, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MAX_AGE = BUILDER
            .comment("The maximum age of item entities. Unit: second.")
            .defineInRange("maxAge", 90, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("Test. No use now.")
            .defineListAllowEmpty(Collections.singletonList("items"), () -> List.of("minecraft:oak_log"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int cleanInterval;
    public static int maxAge;
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        cleanInterval = CLEAN_INTERVAL.get();
        maxAge = MAX_AGE.get();

        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }
}

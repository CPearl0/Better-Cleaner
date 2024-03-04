package com.cpearl.bettercleaner.event;

import com.cpearl.bettercleaner.BetterCleaner;
import com.cpearl.bettercleaner.Config;
import com.cpearl.bettercleaner.itementitycleaner.ItemEntityCleaner;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = BetterCleaner.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ItemEntityCleaner.server = event.getServer();
        executor.scheduleAtFixedRate(new ItemEntityCleaner(), Config.cleanInterval, Config.cleanInterval, TimeUnit.SECONDS);
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        executor.shutdown();
    }
}

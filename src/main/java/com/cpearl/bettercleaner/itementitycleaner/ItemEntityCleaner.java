package com.cpearl.bettercleaner.itementitycleaner;

import com.cpearl.bettercleaner.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemEntityCleaner implements Runnable {
    public static MinecraftServer server;

    private static final Object FILL = new Object();
    public static Map<ItemEntity, Object> list = new ConcurrentHashMap<ItemEntity, Object>();

    public static void addEntity(ItemEntity entity) {
        list.put(entity, FILL);
    }

    @Override
    public void run() {
        // Do the cleaning
        List<ItemEntity> removed = new ArrayList<>();
        int count = 0;
        for (var entity : new ArrayList<>(list.keySet())) {
            if (entity.getRemovalReason() != null && entity.getRemovalReason().shouldDestroy()) {
                removed.add(entity);
            }
            else if (entity.getAge() > Config.maxAge * 20) {
                removed.add(entity);
                count += entity.getItem().getCount();
                entity.discard();
            }
        }
        for (var entity : removed)
            list.remove(entity);

        // Show the info
        if (count > 0)
            server.getPlayerList().broadcastSystemMessage(Component.translatable("message.bettercleaner.cleaned", count)
                    .withStyle(ChatFormatting.AQUA), false);
        else
            server.getPlayerList().broadcastSystemMessage(Component.translatable("message.bettercleaner.nothingcleaned")
                    .withStyle(ChatFormatting.AQUA), false);
    }
}

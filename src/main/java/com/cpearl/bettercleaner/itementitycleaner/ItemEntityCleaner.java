package com.cpearl.bettercleaner.itementitycleaner;

import com.cpearl.bettercleaner.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemEntityCleaner implements Runnable {
    public static MinecraftServer server;

    public static Set<ItemEntity> list = new HashSet<>();

    public static void addEntity(ItemEntity entity) {
        list.add(entity);
    }

    @Override
    public void run() {
        List<ItemEntity> removed = new ArrayList<>();
        int count = 0;
        for (var entity : list) {
            if (entity.getAge() > Config.maxAge * 20) {
                removed.add(entity);
                entity.setRemoved(Entity.RemovalReason.KILLED);
                count += entity.getItem().getCount();
            }
        }
        for (var entity : removed)
            list.remove(entity);
        if (count > 0)
            server.getPlayerList().broadcastSystemMessage(Component.literal(String.format("清理了%d个李武的亲亲！", count))
                    .withStyle(ChatFormatting.DARK_BLUE), false);
        else
            server.getPlayerList().broadcastSystemMessage(Component.literal("李武没有亲亲，最安静的一集。")
                    .withStyle(ChatFormatting.DARK_BLUE), false);
    }
}

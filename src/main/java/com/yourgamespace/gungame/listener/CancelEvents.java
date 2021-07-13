package com.yourgamespace.gungame.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CancelEvents implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPhysicalWorldEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }


    @EventHandler
    public void onEntityBreak(HangingBreakByEntityEvent event) {
        Hanging hanging = event.getEntity();
        if (hanging instanceof Painting) {
            event.setCancelled(true);
        }
        if (hanging instanceof ItemFrame) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ArmorStand) {
            event.setCancelled(true);
        }
        if (entity instanceof ItemFrame) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof ItemFrame) event.setCancelled(true);
    }


    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();


            // LAGACY OUTDATED
            //if (b.getType() == Material.CHEST) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.CRAFTING_TABLE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.ACACIA_DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.BIRCH_DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DARK_OAK_DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.JUNGLE_DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.SPRUCE_DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.TRAPDOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DOOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.ACACIA_FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.BIRCH_FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DARK_OAK_FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.JUNGLE_FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.SPRUCE_FENCE_GATE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.BEACON) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.ANVIL) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.HOPPER) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.BREWING_STAND) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.FURNACE) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DAYLIGHT_DETECTOR) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DROPPER) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.WOOD_BUTTON) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.STONE_BUTTON) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.TRAPPED_CHEST) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.LEVER) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.DRAGON_EGG) {
            //    e.setCancelled(true);
            //}
            //if (b.getType() == Material.NOTE_BLOCK) {
            //    e.setCancelled(true);
            //}
        }
    }

}

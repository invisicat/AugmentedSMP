package dev.ricecx.augmentedsmp.modules.toolmaintenance;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Module(
        name = "Tool Maintenance",
        parentConfig = "tool-maintenance",
        description = "No more dropping mistakes!",
        forUser = true,
        playerConfigClass = ToolMaintenancePlayerConfig.class
)
public class ToolMaintenanceModule extends AbstractModule {

    private final Cache<UUID, ItemStack> stackCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent evt) {
        if(!isModuleEnabled()) return;
        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        Boolean isEnabled = cachingManager.get(evt.getPlayer(), "dtq-enabled", false, Boolean.class);

        if(isTool(evt.getItemDrop().getItemStack()) && isEnabled) {
            ItemStack stack = stackCache.getIfPresent(evt.getPlayer().getUniqueId());
            if(stack == null) {
                evt.setCancelled(true);
                stackCache.put(evt.getPlayer().getUniqueId(), evt.getItemDrop().getItemStack());
                Utils.sendMessage(evt.getPlayer(), "Are you sure you want to drop your tool? Drop again in 10 seconds to drop.");
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent evt) {
        if(!isModuleEnabled()) return;
        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        Boolean isEnabled = cachingManager.get(evt.getPlayer(), "dur-sound", false, Boolean.class);

        if(isTool(evt.getItem()) && isEnabled) {
            Damageable meta = ((Damageable) evt.getItem().getItemMeta());
            if(meta == null) return;
            int damageAt = evt.getItem().getType().getMaxDurability() - meta.getDamage();
            if(damageAt <= 10) {
                evt.getPlayer().playSound(evt.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                Utils.sendMessage(evt.getPlayer(), "Careful! Your tool is at &c" + damageAt + " &7durability!");
            }
        }
    }
    private boolean isTool(ItemStack stack) {
        return stack.getType().name().contains("_PICKAXE") ||
                stack.getType().name().contains("_SPADE") ||
                stack.getType().name().contains("_SHOVEL") ||
                stack.getType().name().contains("_SWORD") ||
                stack.getType().name().contains("_HOE");
    }
}

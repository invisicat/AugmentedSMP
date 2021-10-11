package dev.ricecx.augmentedsmp.modules.foodbuff;


import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

@Module(
        name = "Food Buff",
        description = "Buff food saturation",
        configName = "food-buffs",
        parentConfig = "foodbuffs",
        configClass = FoodBuffConfig.class
)
public class FoodBuffModule extends AbstractModule {

    @EventHandler
    public void onPlayerFoodConsume(PlayerItemConsumeEvent evt) {
        float saturationBuff;

        switch (evt.getItem().getType()) {
            case PUMPKIN_PIE:
                saturationBuff = 8.0F;
                break;
            case BEETROOT_SOUP, MUSHROOM_STEW:
                saturationBuff = 4.8F;
                break;
            case BAKED_POTATO, BREAD, COOKIE:
                saturationBuff = 1.2F;
                break;
            default:
                return;
        }

        Player player = evt.getPlayer();
        player.setSaturation(player.getSaturation() + saturationBuff);
    }
}

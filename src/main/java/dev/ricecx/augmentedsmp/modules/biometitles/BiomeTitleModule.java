package dev.ricecx.augmentedsmp.modules.biometitles;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.events.PlayerBiomeChangeEvent;
import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;

import java.util.Locale;


@Module(
        name = "Biome Titles",
        parentConfig = "biome-titles",
        forUser = true,
        description = "Display a title when entering new biomes",
        playerConfigClass = BiomeTitlePlayerConfig.class
)
public class BiomeTitleModule extends AbstractModule {

    @EventHandler
    public void onBiomeEnter(PlayerBiomeChangeEvent evt) {
        if(!isModuleEnabled()) return;

        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        Boolean titleEnabled = cachingManager.get(evt.getPlayer(), BiomeTitleKeys.TITLE.getKey(), true, Boolean.class);
        Boolean soundEnabled = cachingManager.get(evt.getPlayer(), BiomeTitleKeys.SOUND.getKey(), false, Boolean.class);

        if(!titleEnabled) return;

        evt.getPlayer().sendTitle(Utils.fixNamespaceString(evt.getBiome().name()), BiomeTitleUtils.getSubText(evt.getBiome()), 2 * 20,3 * 20, 20);


        if(soundEnabled)
            evt.getPlayer().playSound(evt.getPlayer().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1,1);
    }

}

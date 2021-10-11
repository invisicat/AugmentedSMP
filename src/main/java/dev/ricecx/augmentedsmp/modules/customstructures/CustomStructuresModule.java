package dev.ricecx.augmentedsmp.modules.customstructures;

import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;


@Module(
        name = "Custom Structures",
        description = "Generate Custom structures",
        parentConfig = "custom_structures",
        configName = "custon-structures",
        configClass = CustomStructuresConfig.class
)
public class CustomStructuresModule extends AbstractModule {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (!isModuleEnabled()) return;

        // Allow new chunk to be disabled.
        boolean newChunk = getConfig(CustomStructuresConfig.class).isNewChunks();
        if (!newChunk && !e.isNewChunk()) return;


        Block b = e.getChunk().getBlock(8, 5, 8); //Grabs the block 8, 5, 8 in that chunk.

        /*
         * Schematic handler
         * This activity is done async to prevent the server from lagging.
         */
        /*
        StructurePicker s = new StructurePicker(b, e.getChunk(), CustomStructures.getInstance());
        s.runTaskTimer(CustomStructures.plugin, 1, 10);
         */
    }
}

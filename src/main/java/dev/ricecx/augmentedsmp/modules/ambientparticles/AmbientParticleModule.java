package dev.ricecx.augmentedsmp.modules.ambientparticles;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.events.AugmentedPlayerJoinEvent;
import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.task.SingularTask;
import xyz.xenondevs.particle.task.TaskManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Module(
        name = "Ambient Biome Particles",
        parentConfig = "ambient-particles"
)
public class AmbientParticleModule extends AbstractModule {

    @EventHandler
    public void onPlayerJoin(AugmentedPlayerJoinEvent evt) {
    }

    public void spawnParticles(Player player) {


        List<Location> locs = generateSampleSetPoints(player, 600);
        var a = AugmentedSMP.getInstance().getParticlesAPI().getParticles_1_13();

        for (Location loc : locs) {
            Object packet = a.PORTAL()
                    .packet(true,
                            loc.getX(), loc.getY(), loc.getZ(), generateRan(-5,5), generateRan(1,5),generateRan(-5,5), 0, 28);
            a.sendPacket(player, packet);
        }

    }


    public int generateRan(int l, int u) {
        return ThreadLocalRandom.current().nextInt(l, u);
    }
    public List<Location> generateSampleSetPoints(Player player, int pointsToGenerate) {
        List<Location> locs = new ArrayList<>();
        for (int i = 0; i < pointsToGenerate; i++) {
            Vector center = player.getLocation().toVector();
            var radius = player.getClientViewDistance() * 16;

            double x = center.getX();
            double y = center.getZ();

            var r = radius * Math.sqrt(ThreadLocalRandom.current().nextDouble());
            var t = ThreadLocalRandom.current().nextDouble() * 2 * Math.PI;

            var x1 = x + (r * Math.cos(t));
            var y2 = y + (r * Math.sin(t));

            locs.add(new Location(player.getWorld(), x1, ThreadLocalRandom.current().nextInt((int) player.getLocation().getY() - 15, (int) player.getLocation().getY() + 25) + ThreadLocalRandom.current().nextInt(3), y2));
        }

        return locs;
    }
}

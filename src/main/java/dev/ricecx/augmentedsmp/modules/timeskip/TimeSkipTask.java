package dev.ricecx.augmentedsmp.modules.timeskip;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Pose;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeSkipTask extends BukkitRunnable {

    private final World world;

    public TimeSkipTask(World world) {
        this.world = world;

        runTaskTimer(AugmentedSMP.getInstance(), 1, 1);
    }
    @Override
    public void run() {
        long time = world.getTime();
        double timeRate = 10.0;
        int dtt = AugmentedSMP.getInstance().getConfig().getInt("night-skip.daytime-ticks");
        int dayTime = Math.max(150, dtt);
        long playersSleeping = world.getPlayers().stream()
                .filter(player -> player.getPose() == Pose.SLEEPING)
                .count();

        timeRate = Math.min(timeRate, Math.round(timeRate / world.getPlayers().size() * Math.max(1, playersSleeping)));

        if (time >= (dayTime - timeRate * 1.5) && time <= dayTime) {
            if (AugmentedSMP.getInstance().getConfig().getBoolean("night-skip.reset-phantom-statistic")) {
                world.getPlayers().forEach(player -> player.setStatistic(Statistic.TIME_SINCE_REST, 0));
            }

            cancel();
            return;
        }

        world.setTime(time + (int) timeRate);

    }

}

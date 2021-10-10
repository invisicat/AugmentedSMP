package dev.ricecx.augmentedsmp.bStats;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.utils.Constants;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    public static void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(AugmentedSMP.getInstance(), () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + Constants.RESOURCE_ID).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                LoggingUtils.info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

    public static void checkForUpdate() {
        checkForUpdate(true);
    }

    public static void checkForUpdate(boolean silent) {
        String version = AugmentedSMP.getInstance().getDescription().getVersion();
        getVersion((ver) -> {
            if(version.equals(ver)) {
                if (!silent) LoggingUtils.info("AugmentedSMP is up to date!");
            } else
                if(!silent)
                    LoggingUtils.info("There is a new update available. (Current Version = " + version + ")" + "Latest: " + ver);
        });
    }
}

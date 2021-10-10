package dev.ricecx.augmentedsmp.utils;

import dev.ricecx.augmentedsmp.AugmentedSMP;

public class LoggingUtils {

    private static boolean VERBOSE = false;

    private LoggingUtils() {
        throw new InstantiationError("This class cannot be instantiated");
    }


    /**
     * Send a DEBUG text
     * @param text Text to send
     */
    public static void debug(String ...text) {
        if(!VERBOSE) return;

        AugmentedSMP.getInstance().getLogger().info(String.join(" ", text));
    }
    /**
     * Log an info text
     * @param text Text to send
     */
    public static void info(String text) {
        AugmentedSMP.getInstance().getLogger().info(text);
    }

    /**
     * Log a warning message
     * @param text Text to send
     */
    public static void warn(String text) {
        AugmentedSMP.getInstance().getLogger().warning(text);
    }

    /**
     * Log an error
     * @param text Text to send
     */
    public static void error(String text) {
        AugmentedSMP.getInstance().getLogger().severe(text);
    }



    /* Setters and Getters */

    public static boolean isVerbose() {
        return VERBOSE;
    }

    public static void setVerbose(boolean VERBOSE) {
        LoggingUtils.VERBOSE = VERBOSE;
    }

    public static void toggleVerbose() {
        LoggingUtils.VERBOSE = !LoggingUtils.VERBOSE;
    }
}

package dev.ricecx.augmentedsmp.core.command;

public enum CommandCategory {
    GENERAL,
    PLAYER,
    NONE
    ;

    private static final CommandCategory[] CACHE = values();

    public static CommandCategory fromName(String name) {
        for (CommandCategory category : CACHE) {
            if(category.name().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }
}

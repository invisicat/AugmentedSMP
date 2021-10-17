package dev.ricecx.augmentedsmp.modules.biometitles;

public enum BiomeTitleKeys {
    TITLE("biome-title-title"),
    SOUND("biome-title-sound")
    ;


    private final String key;
    BiomeTitleKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}

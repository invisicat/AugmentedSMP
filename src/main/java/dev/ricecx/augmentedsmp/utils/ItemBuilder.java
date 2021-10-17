package dev.ricecx.augmentedsmp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;


@SuppressWarnings("all")
public class ItemBuilder {

    private ItemStack is;
    private String skullOwner;

    public ItemBuilder(ItemBuilder builder) {
        this(builder.toItemStack().clone());
    }

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is.clone();
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDurability(byte dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setType(Material m) {
        is.setType(m);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public List<String> getLore() {
        return is.getItemMeta().getLore();
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        List<String> color = new ArrayList<>();
        for (String l : lore) {
            color.add(Utils.color(l));
        }
        im.setLore(color);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        List<String> color = new ArrayList<>();
        for (String l : lore) {
            color.add(Utils.color(l));
        }
        im.setLore(color);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLore() {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.clear();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setPotionEffect(PotionEffect effect) {
        try {
            PotionMeta meta = ((PotionMeta) this.is.getItemMeta());

            meta.setMainEffect(effect.getType());
            meta.addCustomEffect(effect, false);

            this.is.setItemMeta(meta);

            return this;
        } catch (ClassCastException im) {
            //
        }

        return this;
    }

    public ItemBuilder hideAttributes() {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.removeItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder createPotion(boolean splash) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        PotionEffect main = null;
        for (PotionEffect current : ((PotionMeta) this.is.getItemMeta()).getCustomEffects()) {
            if (main == null)
                main = current;
            meta.addCustomEffect(current, true);
        }

        if (main == null) {
            // Invalid potion. Just return itemstack.
            return this;
        }

        potion.setAmount(this.is.getAmount());
        potion.setItemMeta(meta);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setMainEffect(main.getType());
        Potion po = new Potion(PotionType.getByEffect(main.getType()));
        po.setSplash(splash);
        po.apply(potion);
        return new ItemBuilder(potion);
    }

    public ItemBuilder setSplash(boolean splash) {
        try {
            Potion potion = Potion.fromItemStack(this.is);

            potion.setSplash(splash);

            this.is = potion.toItemStack(this.is.getAmount());

            return this;
        } catch (ClassCastException im) {
            //
        }

        return this;
    }

    public ItemBuilder setSkullOwnerNMS(String url, UUID uuid) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(url.getBytes(StandardCharsets.UTF_8));

            String decoded = new String(decodedBytes).replace("{\"textures\":{\"SKIN\":{\"url\":\"", "").replace("\"}}}", "");

            SkullMeta headMeta = (SkullMeta) is.getItemMeta();
            GameProfile profile = new GameProfile(uuid, null);
            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", decoded).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField;
            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
                this.skullOwner = decoded;
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            is.setItemMeta(headMeta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public ItemBuilder setSkullOwnerNMS(String url) {
        return setSkullOwnerNMS(url, UUID.randomUUID());
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addBookEnchant(Enchantment ench, int level) {
        EnchantmentStorageMeta im = ((EnchantmentStorageMeta) is.getItemMeta());
        im.addStoredEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantGlow(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setBannerColor(DyeColor color) {
        ItemMeta im = is.getItemMeta();
        BannerMeta metaBan = (BannerMeta) im;
        metaBan.setBaseColor(color);
        is.setItemMeta(metaBan);
        return this;
    }

    public ItemBuilder setFireworkCharge(Color color) {
        ItemMeta im = is.getItemMeta();
        FireworkEffectMeta metaFw = (FireworkEffectMeta) im;
        FireworkEffect effect = FireworkEffect.builder().withColor(color).build();
        metaFw.setEffect(effect);
        is.setItemMeta(metaFw);
        return this;
    }

    public ItemBuilder addLoreLines(List<String> line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) {
            lore = new ArrayList<>(im.getLore());
        }
        for (String s : line) {
            if (s == null) {
                continue;
            }
            lore.add(Utils.color(s));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLines(String... line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) {
            lore = new ArrayList<>(im.getLore());
        }
        for (String s : line) {
            lore.add(Utils.color(s));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) {
            lore = new ArrayList<>(im.getLore());
        }
        lore.add(Utils.color(line));
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor color) {
        this.is.setDurability(color.getDyeData());
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }

    public String getSkullOwner() {
        return skullOwner;
    }

    public ItemBuilder setSkullOwner(Player owner) {
        return this.setSkullOwnerNMS(owner);
    }

    public ItemBuilder setSkullOwnerBukkit(Player owner) {
        try {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwningPlayer(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemBuilder setSkullOwnerNMS(ItemBuilder.SkullData data) {
        try {
            String url = data.getTexture();
            /*
            if (data.getType() == ItemBuilder.SkullDataType.NAME) {
                return this.setSkullOwner(url);
            }
            */
            SkullMeta headMeta = (SkullMeta) this.is.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            if (data.getType() == ItemBuilder.SkullDataType.URL) {
                byte[] decodedBytes = Base64.getDecoder().decode(url);
                String decoded = (new String(decodedBytes)).replace("{\"textures\":{\"SKIN\":{\"url\":\"", "").replace("\"}}}", "");
                byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{\"url\":\"%s\"}}}", decoded).getBytes());
                profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

                try {
                    Field profileField = headMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(headMeta, profile);
                    this.skullOwner = decoded;
                } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException var10) {
                    var10.printStackTrace();
                }
            } else if (data.getType() == ItemBuilder.SkullDataType.TEXTURE) {
                profile.getProperties().put("textures", new Property("textures", data.getTexture()));

                try {
                    Field profileField = headMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(headMeta, profile);
                } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException var9) {
                    var9.printStackTrace();
                }
            }

            this.is.setItemMeta(headMeta);
        } catch (ClassCastException var11) {
        }
        return this;
    }

    public ItemBuilder setSkullOwnerNMS(Player user) {
        String texture = "";
        for (Map.Entry<String, Property> entry : ((CraftPlayer) user).getHandle().getProfile().getProperties().entries()) {
            if (entry.getKey().equalsIgnoreCase("textures")) {
                String decoded = new String(Base64.getDecoder().decode(entry.getValue().getValue()));
                decoded = '{' + decoded.split(",")[decoded.split(",").length - 1];
                texture = new String(Base64.getEncoder().encode(decoded.getBytes()));
                break;
            }
        }

        if (!StringUtils.isEmpty(texture)) {
            return setSkullOwnerNMS(new ItemBuilder.SkullData(texture, ItemBuilder.SkullDataType.TEXTURE));
        }

        return setSkullOwnerBukkit(user);
    }

    public ItemBuilder unbreakable() {
        this.is.getItemMeta().setUnbreakable(true);
        this.is.getItemMeta().addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public enum SkullDataType {
        NAME,
        URL,
        TEXTURE;

        SkullDataType() {
        }
    }

    public static class SkullData {
        private final String texture;
        private final ItemBuilder.SkullDataType type;

        public SkullData(String texture, ItemBuilder.SkullDataType type) {
            this.texture = texture;
            this.type = type;
        }

        public String getTexture() {
            return this.texture;
        }

        public ItemBuilder.SkullDataType getType() {
            return this.type;
        }
    }
}


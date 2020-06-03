// 
// Decompiled by Procyon v0.5.30
// 

package pl.mineEconomy.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private final short data;
    private final List<String> lore;
    private final HashMap<Enchantment, Integer> enchants;
    private Material mat;
    private int amount;
    private String title;
    private Color color;

    public ItemBuilder(final Material mat) {
        this(mat, 1);
    }

    public ItemBuilder(final Material mat, final int amount) {
        this(mat, amount, (short) 0);
    }

    public ItemBuilder(final Material mat, final short data) {
        this(mat, 1, data);
    }

    public ItemBuilder(final Material mat, final int amount, final short data) {
        this.title = null;
        this.lore = new ArrayList<String>();
        this.enchants = new HashMap<Enchantment, Integer>();
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder addLores(final List<String> lores) {
        for (String s : lores){
            this.lore.add(Util.fix(s));
        }
        return this;
    }

    public ItemBuilder addLore(final String lore) {
        this.lore.add(Util.fix(lore));
        return this;
    }

    public ItemBuilder addEnchantment(final Enchantment enchant, final int level) {
		this.enchants.remove(enchant);
        this.enchants.put(enchant, level);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    public ItemStack build() {
        Material mat = this.mat;
        if (mat == null) {
            mat = Material.AIR;
            Bukkit.getLogger().warning("Null material!");
        }
        final ItemStack item = new ItemStack(this.mat, this.amount, this.data);
        final ItemMeta meta = item.getItemMeta();
        if (this.title != null) {
            meta.setDisplayName(this.title);
        }
        if (!this.lore.isEmpty()) {
            meta.setLore(this.lore);
        }
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(this.color);
        }
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        return item;
    }

    public ItemBuilder clone() {
        final ItemBuilder newBuilder = new ItemBuilder(this.mat);
        newBuilder.setTitle(this.title);
        for (final String lore : this.lore) {
            newBuilder.addLore(lore);
        }
        for (final Map.Entry<Enchantment, Integer> entry : this.enchants.entrySet()) {
            newBuilder.addEnchantment(entry.getKey(), entry.getValue());
        }
        newBuilder.setColor(this.color);
        return newBuilder;
    }

    public Material getType() {
        return this.mat;
    }

    public ItemBuilder setType(final Material mat) {
        this.mat = mat;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ItemBuilder setTitle(final String title) {
        this.title = Util.fix(title);
        return this;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public Color getColor() {
        return this.color;
    }

    public ItemBuilder setColor(final Color color) {
        if (!this.mat.name().contains("LEATHER_")) {
            throw new IllegalArgumentException("Can only dye leather armor!");
        }
        this.color = color;
        return this;
    }

    public boolean hasEnchantment(final Enchantment enchant) {
        return this.enchants.containsKey(enchant);
    }

    public int getEnchantmentLevel(final Enchantment enchant) {
        return this.enchants.get(enchant);
    }

    public HashMap<Enchantment, Integer> getAllEnchantments() {
        return this.enchants;
    }

    public boolean isItem(final ItemStack item) {
        return this.isItem(item, false);
    }

    public boolean isItem(final ItemStack item, final boolean strictDataMatch) {
        final ItemMeta meta = item.getItemMeta();
        if (item.getType() != this.getType()) {
            return false;
        }
        if (!meta.hasDisplayName() && this.getTitle() != null) {
            return false;
        }
        if (!meta.getDisplayName().equals(this.getTitle())) {
            return false;
        }
        if (!meta.hasLore() && !this.getLore().isEmpty()) {
            return false;
        }
        if (meta.hasLore()) {
            for (final String lore : meta.getLore()) {
                if (!this.getLore().contains(lore)) {
                    return false;
                }
            }
        }
        for (final Enchantment enchant : item.getEnchantments().keySet()) {
            if (!this.hasEnchantment(enchant)) {
                return false;
            }
        }
        return true;
    }
}

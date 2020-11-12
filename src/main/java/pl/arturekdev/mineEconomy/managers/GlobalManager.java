package pl.arturekdev.mineEconomy.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.arturekdev.mineEconomy.objects.User;
import pl.arturekdev.mineEconomy.utils.ItemBuilder;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class GlobalManager {

    public static ItemStack getItemStack(int ilosc) {
        ItemBuilder b = new ItemBuilder(Material.BLAZE_POWDER);
        b.setTitle(MUtil.fix("&6Iskry"));
        b.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        b.addLore(" &8>> &7Iskry to waluta serwerowa kórą możesz operować cyfrowo jak i fizycznie!");
        b.addLore(" &8>> &7Chcesz wpłacić walute na swoje konto? Wpisz &e/wplac");
        b.addLore(" &8>> &7Walute można też zakupić na naszej stronie &ewww.campfire.pl");
        b.setAmount(ilosc);
        return b.build();
    }

    public static ItemStack getItemStack() {
        ItemBuilder b = new ItemBuilder(Material.BLAZE_POWDER);
        b.setTitle(MUtil.fix("&6Iskry"));
        b.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        b.addLore(" &8>> &7Iskry to waluta serwerowa kórą możesz operować cyfrowo jak i fizycznie!");
        b.addLore(" &8>> &7Chcesz wpłacić walute na swoje konto? Wpisz &e/wplac");
        b.addLore(" &8>> &7Walute można też zakupić na naszej stronie &ewww.campfire.pl");
        return b.build();
    }

    public static boolean haveMoney(User u, int need) {
        if (need < 0) {
            return false;
        }
        return u.getMoney() >= need;
    }

    public static boolean isNumber(String arg) {
        if (arg == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isSimilar(ItemStack first, ItemStack second) {

        boolean similar = false;

        if (first == null || second == null) {
            return false;
        }

        boolean sameTypeId = (first.getType() == second.getType());
        boolean sameDurability = (first.getDurability() == second.getDurability());
        boolean sameHasItemMeta = (first.hasItemMeta() == second.hasItemMeta());
        boolean sameEnchantments = (first.getEnchantments().equals(second.getEnchantments()));
        boolean sameItemMeta = true;

        if (sameHasItemMeta) {
            sameItemMeta = Bukkit.getItemFactory().equals(first.getItemMeta(), second.getItemMeta());
        }

        if (sameTypeId && sameDurability && sameHasItemMeta && sameEnchantments && sameItemMeta) {
            similar = true;
        }

        return similar;

    }

    public static void giveItem(Player p, ItemStack item) {
        int space = 0;
        for (ItemStack i : p.getInventory().getStorageContents()) {

            if (i == null) {
                space += item.getMaxStackSize();
                continue;
            }

            if (i.getType() == item.getType()) {
                space += (i.getMaxStackSize() - i.getAmount());
            }
        }
        if (item.getAmount() > space) {
            p.getWorld().dropItemNaturally(p.getLocation(), item);
            MUtil.sendMsg(p, " &8>> &cNie posiadałeś wolnego miejsca w ekwipunku item &e" + item.getItemMeta().getDisplayName() + " &czostał wyrzucony na ziemię!");
        } else {
            p.getInventory().addItem(new ItemStack(item));
        }
    }

}

package pl.arturekdev.mineEconomy.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

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

    public static void removeItem(Player p, ItemStack itemStack) {
        if (p.getInventory().containsAtLeast(itemStack, itemStack.getAmount())) {
            p.getInventory().removeItem(itemStack);
        }
    }

}

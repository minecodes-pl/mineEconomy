package pl.arturekdev.mineEconomy.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.arturekdev.mineEconomy.EconomyService;
import pl.arturekdev.mineEconomy.utils.ItemUtil;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void event(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack itemStack = p.getInventory().getItemInMainHand();
        EconomyService economyService = new EconomyService();

        if (itemStack.getType() == Material.AIR) {
            return;
        }

        if (action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasKey("VALUE")) {
            return;
        }

        int value = nbtItem.getInteger("VALUE");

        economyService.giveMoney(p, value);

        ItemUtil.removeItem(p, itemStack);

        MUtil.sendMsg(p, " &8>> &aPomyślnie właciłeś Sakiewke o wartości &e" + value + " Iskier");
    }

}

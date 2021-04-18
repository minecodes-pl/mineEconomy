package pl.arturekdev.mineEconomy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.arturekdev.mineEconomy.user.User;
import pl.arturekdev.mineEconomy.user.UserService;

public class PlayerQuitListener implements Listener {

    private final UserService userService;

    public PlayerQuitListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = UserService.getUser(player.getName());
        user.update(userService);
    }

}

package pl.minecodes.mineeconomy;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minecodes.mineeconomy.command.admin.EconomyCommand;
import pl.minecodes.mineeconomy.command.admin.sub.EconomyCheckCommand;
import pl.minecodes.mineeconomy.command.admin.sub.EconomyClearCommand;
import pl.minecodes.mineeconomy.command.admin.sub.EconomyDepositCommand;
import pl.minecodes.mineeconomy.command.admin.sub.EconomySetCommand;
import pl.minecodes.mineeconomy.command.admin.sub.EconomyWithdrawCommand;
import pl.minecodes.mineeconomy.command.argument.DoubleArgument;
import pl.minecodes.mineeconomy.command.argument.OfflinePlayerArgument;
import pl.minecodes.mineeconomy.command.argument.PlayerArgument;
import pl.minecodes.mineeconomy.command.player.BalanceCommand;
import pl.minecodes.mineeconomy.command.player.RankingCommand;
import pl.minecodes.mineeconomy.command.player.TransferCommand;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.data.configuration.helper.ConfigurationFactory;
import pl.minecodes.mineeconomy.data.database.MongoDbService;
import pl.minecodes.mineeconomy.data.database.MySQLService;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.hook.placeholderapi.PlaceholderAPIHook;
import pl.minecodes.mineeconomy.hook.vault.VaultHook;
import pl.minecodes.mineeconomy.hook.vault.VaultManager;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.runnable.ProfileSaveTask;

import java.text.DecimalFormat;

public class EconomyPlugin extends JavaPlugin {

    public static final DecimalFormat FORMAT = new DecimalFormat("#.##");

    private Injector injector;
    private Messages messages;
    private Configuration configuration;
    private ProfileService profileService;
    private VaultHook vaultHook;

    private LiteCommands liteCommands;

    @Override
    public void onEnable() {
        this.injector = OkaeriInjector.create()
                .registerInjectable(this)
                .registerInjectable(this.getLogger());

        this.loadConfiguration();
        this.injector.registerInjectable(configuration);
        this.injector.registerInjectable(messages);

        DataService dataService;
        switch (this.configuration.getDatabaseData().getDatabaseType()) {
            case MYSQL:
                dataService = this.injector.createInstance(MySQLService.class);
                dataService.connect();
                break;
            case MONGODB:
                dataService = this.injector.createInstance(MongoDbService.class);
                dataService.connect();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.configuration.getDatabaseData().getDatabaseType());
        }
        this.injector.registerInjectable(dataService);

        this.profileService = this.injector.createInstance(ProfileService.class);
        this.injector.registerInjectable(profileService);

        this.registerCommands();

        VaultManager vaultManager = this.injector.createInstance(VaultManager.class);
        this.injector.registerInjectable(vaultManager);

        vaultHook = this.injector.createInstance(VaultHook.class);
        vaultHook.registerHook();

        PlaceholderAPIHook papiHook = this.injector.createInstance(PlaceholderAPIHook.class);
        papiHook.registerHook();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this.injector.createInstance(ProfileSaveTask.class), 40, 40);
    }

    @Override
    public void onDisable() {
        vaultHook.unregisterHook();
    }

    private void loadConfiguration() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory(this.getDataFolder());
        configuration = configurationFactory.produce(Configuration.class, "configuration.yml");
        messages = configurationFactory.produce(Messages.class, "messages.yml");
    }

    private void registerCommands() {
        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "mineEconomy")
                .argument(OfflinePlayer.class, this.injector.createInstance(OfflinePlayerArgument.class))
                .argument(Player.class, this.injector.createInstance(PlayerArgument.class))
                .argument(Double.class, this.injector.createInstance(DoubleArgument.class))
                .commandInstance(this.injector.createInstance(EconomyCommand.class))
                .commandInstance(this.injector.createInstance(EconomySetCommand.class))
                .commandInstance(this.injector.createInstance(EconomyDepositCommand.class))
                .commandInstance(this.injector.createInstance(EconomyWithdrawCommand.class))
                .commandInstance(this.injector.createInstance(EconomyClearCommand.class))
                .commandInstance(this.injector.createInstance(EconomyCheckCommand.class))
                .commandInstance(this.injector.createInstance(BalanceCommand.class))
                .commandInstance(this.injector.createInstance(RankingCommand.class))
                .commandInstance(this.injector.createInstance(TransferCommand.class))
                .message(ValidationInfo.INVALID_USE, messageInfoContext -> this.messages.getCommandUsage().replace("{usage}", messageInfoContext.getUseScheme()))
                .register();
    }
}

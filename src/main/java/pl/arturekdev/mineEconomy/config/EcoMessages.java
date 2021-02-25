package pl.arturekdev.mineEconomy.config;

import space.arim.dazzleconf.annote.*;

import java.util.*;

public interface EcoMessages {

    @ConfDefault.DefaultString("&cTime for logging in has expired!")
    String loginTimeout();

    @ConfDefault.DefaultString("&8>> &cNie posiadasz uprawnień &e%permission%")
    String dontHavePermission();

    @ConfDefault.DefaultString(" &8>> &cAby wykonać przelew użyj komendy &e/money pay <nick> <kwota>")
    String payCommandUsage();

    @ConfDefault.DefaultString(" &8>> &cTen przelew jest niemożliwy do zrealizowania.")
    String thisPayIsNotPossible();

    @ConfDefault.DefaultString(" &8>> &cPodany gracz jest offline!")
    String targetIsOffline();

    @ConfDefault.DefaultString(" &8>> &cPodana kwota nie jest liczbą.")
    String valueIsNotNumber();

    @ConfDefault.DefaultString(" &8>> &cNie posiadasz wystarczających środków.")
    String dontHaveFunds();

    @ConfDefault.DefaultString(" &8>> &aPomyślnie wysłałeś %value%$ do gracza %target%")
    String successSendPay();

    @ConfDefault.DefaultString(" &8>> &aOtrzymałeś przelew od gracza %sender% w wysokości %value%$")
    String successReceivedPay();

    @ConfDefault.DefaultStrings({" ", "&6Najbogatsi gracze", " ", "&8#1 &7%mineEco_top_name_1%&8: &e%mineEco_top_value_1%$", "&8#2 &7%mineEco_top_name_2%&8: &e%mineEco_top_value_2%$"})
    List<String> rankingMessage();

    @ConfDefault.DefaultString(" &8>> &aAby sprwadzić portef gracza użyj komendy &e/money check <nick>")
    String checkCommandUsage();

    @ConfDefault.DefaultString(" &8>> &cBrak takiego użytkownika w bazie.")
    String userIsNull();

    @ConfDefault.DefaultString(" &8>> &cStan portfela gracza %player% to &e%value%")
    String userMoneyCheck();

    @ConfDefault.DefaultString(" &8>> &7Twój stan portfela: &e%value%")
    String playerWalletInfo();

    @ConfDefault.DefaultStrings({"/money - Sprwadza stan konta",
            "/money check <nick> - Sprawdza stan konta gracza",
            "/money top - Raking bogatych graczy",
            "/money pay <nick> <kwota> - Przelew do innego gracza",
            "/money set <nick> <kwota> - Ustawienie kwoty w portefelu gracza",
            "/money give <nick> <kwota> - Dodanie kwotry do portfela gracza"})
    List<String> commandsList();

    @ConfDefault.DefaultString(" &8>> &aPomyslnie ustawiono kwote w portfelu gracza %player% na &e%value%")
    String successSetMoney();

    @ConfDefault.DefaultString(" &8>> &aPomyślnie dodano %value% do portfela gracza %player%")
    String successGiveMoney();

    @ConfDefault.DefaultString("&6Otrzymałeś %value% za bycie online na serwerze!")
    String actionBarPlayTimePrize();

    @ConfDefault.DefaultString("&cWykorzystałeś już limit nagród na dziś za bycie online!")
    String actionBarPlayTimePrizeLimit();

    @ConfDefault.DefaultString(" &8>> &aAby sprwadzić dodać pieniądze do konta gracza użyj komendy &e/money give <nick> <kwote>")
    String giveCommandUsage();

    @ConfDefault.DefaultString(" &8>> &aAby sprwadzić ustawić kwrote w portfelu gracza użyj komendy &e/money set <nick> <kwota>")
    String setCommandUsage();
}

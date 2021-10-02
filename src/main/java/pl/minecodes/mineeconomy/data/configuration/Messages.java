package pl.minecodes.mineeconomy.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class Messages extends OkaeriConfig {

    private String balanceCheck = "&aAktualnie posidasz: &e{balance} {currency}";
    private String balanceSuccessfullySet = "&aPomyślnie ustawiono stan konta gracza &e{player} &ana &e{balance} {currency}";
    private String balanceSuccessfullyDeposit = "&aPomyslnie dodano &e{value} {currency} &ado konta gracza &e{player}";
    private String balanceSuccessfullyWithdraw = "&aPomyslnie pobrano z konta gracza &e{player} &asume &e{value} {currency}";
    private String balanceSuccessfullyClear = "&aPomyslnie wyzerowano stan konta gracza &e{player}";
    private String balanceSuccessfullyTransferToSender = "&aPomyślnie przelano &e{value} {currency} &ana konto gracza &e{target}";
    private String balanceSuccessfullyTransferToTarget = "&aOtrzymałeś przelew w wysokości &e{value} {currency} &aod gracza &e{sender}";
    private String balanceAdministratorCheck = "&aStan konta gracza &e{player} &awynosi &e{balance} {currency}";
    private String balanceOperationParameterIsNegative = "&cPodana liczba jest mniejsza od zera.";
    private String balanceWithdrawNoFounds = "&cGracz nie posiada danej kwoty na koncie.";
    private String balanceIsNegative = "&cKonto gracza jest na minusie.";
    private String balanceNoFounds = "&cBrak wymaganych środków na koncie.";

    private String balanceRankingNullObject = "&cBrak danych.";
    private List<String> balanceRanking = new ArrayList<>(Arrays.asList(
            "&8#1: &e{username} &7-> &a{balance} {currency}",
            "&8#2: &e{username} &7-> &a{balance} {currency}",
            "&8#3: &e{username} &7-> &a{balance} {currency}"
    ));

    private List<String> economyAdminCommands = new ArrayList<>(Arrays.asList(
            " ",
            " &8* &e/eco help - Pokazule liste komend administratorskich.",
            " &8* &e/eco set <username> <balance> - Ustawia stan konta gracza.",
            " &8* &e/eco deposit <username> <value> - Wpłaca podaną kwote na konto gracza.",
            " &8* &e/eco withdraw <username> <value> - Wypłaca podaną kwote z konta gracza.",
            " &8* &e/eco check <username> - Sprawdza stan konta gracza.",
            " &8* &e/eco clear <username> - Zeruje stan konta gracza.",
            " "
    ));

    @Comment("Gdy podany gracz nie figuruje w plikach serwera.")
    private String playerIsNotExistsInCache = "&8Takiego gracza nigdy nie było na serwerze.";

    @Comment("Konfiguracja wiadomości dotyczących komend.")
    private Map<String, String> acfCore = this.loadAcfMessages();

    private HashMap<String, String> loadAcfMessages() {
        HashMap<String, String> messages = new HashMap<>();
        messages.put("permission_denied", "&cPrzykro mi, jednak nie posiadasz uprawnien do tej komendy.");
        messages.put("error_generic_logged", "&cWystapil niespodziewany blad, który zostal zarejestrowany.");
        messages.put("unknown_command", "&cTakowa komenda nie istnieje. Spróbuj uzyc: &e/help.");
        messages.put("invalid_syntax", "&cPoprawne uzycie: &e{command} {syntax}.");
        messages.put("error_prefix", "&cBlad: {message}.");
        messages.put("error_performing_command", "&cWystapil niespodziewany blad, który zostal zarejestrowany.");
        messages.put("info_message", "&7{message}");
        messages.put("please_specify_one_of", "&cBlad: Wybierz jeden z podanych argumentów: &e{valid}");
        messages.put("must_be_a_number", "&cBlad: Argument musi byc liczba!");
        messages.put("must_be_min_length", "&cBlad: Argument musi posiadac przynajmniej {min} znaki/znaków.");
        messages.put("must_be_max_length", "&cBlad: Argument musi posiadac maksymalnie {max} znaki/znaków.");
        messages.put("not_allowed_on_console", "&cBlad: Konsola nie moze wykonac tej komendy.");
        messages.put("could_not_find_player", "&cBlad: Nie znaleziono gracza o nicku: &c{search}");
        messages.put("help_format", "{command} {parameters} {separator} {description}");
        return messages;
    }


}

package pl.minecodes.mineeconomy.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.pluralize.Pluralize;

import java.util.Locale;

public class Configuration extends OkaeriConfig {

    @Comment("Nazwy waluty w odmianach.")
    private String currency = "Iskre|Iskry|Iskier";
    @Comment("Początkowy stan konta gracza.")
    private double startBalance = 10;


    public String getCurrency(double value) {
        String[] currencySplitter = this.currency.split("\\|");
        if (currencySplitter.length > 0 && currencySplitter.length < 3) {
            return currencySplitter[0];
        }
        return Pluralize.pluralize(Locale.forLanguageTag("pl"), (int) value, currencySplitter[0], currencySplitter[1], currencySplitter[2]);
    }

    @Deprecated
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getStartBalance() {
        return startBalance;
    }

    @Deprecated
    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }
}

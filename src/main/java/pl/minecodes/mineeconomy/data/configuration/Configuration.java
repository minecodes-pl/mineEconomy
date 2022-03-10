package pl.minecodes.mineeconomy.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.pluralize.Pluralize;
import pl.minecodes.mineeconomy.data.database.element.DatabaseData;

import java.util.Locale;

public class Configuration extends OkaeriConfig {

    @Comment("Nazwy waluty w odmianach.")
    private String currency = " Iskre| Iskry| Iskier";
    private CurrencyPositionVault currencyPositionVault = CurrencyPositionVault.BEHIND;
    @Comment("Początkowy stan konta gracza.")
    private double startBalance = 10;
    @Comment("Do ilu liczb po przecinku powinna być zaokrąglana kwota.")
    private int roundedScale = 2;
    private DatabaseData databaseData = new DatabaseData();


    public String getCurrency(double value) {
        String[] currencySplitter = this.currency.split("\\|");
        if (currencySplitter.length > 0 && currencySplitter.length < 3) {
            return currencySplitter[0];
        }
        return Pluralize.pluralize(Locale.forLanguageTag("pl"), (int) value, currencySplitter[0], currencySplitter[1], currencySplitter[2]);
    }

    public double getStartBalance() {
        return startBalance;
    }

    @Deprecated
    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    public CurrencyPositionVault getCurrencyPositionVault() {
        return currencyPositionVault;
    }

    @Deprecated
    public void setCurrencyPositionVault(CurrencyPositionVault currencyPositionVault) {
        this.currencyPositionVault = currencyPositionVault;
    }

    @Deprecated
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public DatabaseData getDatabaseData() {
        return databaseData;
    }

    @Deprecated
    public void setDatabaseData(DatabaseData databaseData) {
        this.databaseData = databaseData;
    }

    public enum CurrencyPositionVault {AHEAD, BEHIND}

    public int getRoundedScale() {
        return roundedScale;
    }

    public void setRoundedScale(int roundedScale) {
        this.roundedScale = roundedScale;
    }
}

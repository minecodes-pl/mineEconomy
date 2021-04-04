package pl.arturekdev.mineEconomy.config;

import space.arim.dazzleconf.annote.*;
import space.arim.dazzleconf.sorter.*;

public interface EcoConfiguration {

    @ConfComments("Format kwot")
    @AnnotationBasedSorter.Order(0)
    @ConfDefault.DefaultString("%.##")
    String format();

    @ConfComments("Waluta")
    @AnnotationBasedSorter.Order(0)
    @ConfDefault.DefaultString("$")
    String currencyName();

    @ConfComments("Maksymalna dzienna nagroda (do momentu restartu)")
    @ConfDefault.DefaultDouble(50)
    double limitMoneyPerDay();

    @ConfComments("Nagroda jaką dostanie gracz za czas gry")
    @ConfDefault.DefaultDouble(5)
    double playTimePrize();

    @ConfComments("Co jaki czas ma zostać nadawana nagroda z grę (w tickach)")
    @ConfDefault.DefaultInteger(60)
    int playTimePrizeTask();

    @ConfDefault.DefaultString("{currencyName}{amount}")
    String currencyFormat();
}

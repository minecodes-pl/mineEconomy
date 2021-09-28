package pl.minecodes.mineeconomy.data.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Configuration extends OkaeriConfig {

    @Comment("Nazwa waluty")
    private String currency = "Iskry";

}

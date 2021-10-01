package pl.minecodes.mineeconomy.data.database.element;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DatabaseData extends OkaeriConfig {

    @Comment("Typ bazy danych: MYSQL, MONGODB")
    private DatabaseType databaseType = DatabaseType.MYSQL;

    @Comment("Adres bazy danych")
    private String host = "localhost";

    @Comment("Nazwa bazy")
    private String database = "plots";

    @Comment("Port bazy")
    private int port = 2000;

    @Comment("Użytkownik")
    private String username = "plots";

    @Comment("Hasło dostępu")
    private String password = "password";

}

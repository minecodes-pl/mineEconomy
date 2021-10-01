package pl.minecodes.mineeconomy.data.database.element.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseUpdate {

    private long lastUpdate;
    private boolean neededUpdate;

}

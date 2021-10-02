package pl.minecodes.mineeconomy.data.database.element.model;

import pl.minecodes.mineeconomy.profile.Profile;

import java.util.UUID;

public interface DataService {

    Profile loadData(UUID uniqueId);

    void saveData(Profile profile);

    void deleteData(Profile profile);

    void connect();

    Profile order(int order);
}

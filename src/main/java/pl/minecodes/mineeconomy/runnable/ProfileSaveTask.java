package pl.minecodes.mineeconomy.runnable;

import eu.okaeri.injector.annotation.Inject;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.profile.ProfileService;

public class ProfileSaveTask implements Runnable {

    @Inject private DataService dataService;
    @Inject private ProfileService profileService;

    @Override
    public void run() {
        this.profileService.getProfilesCache().asMap().forEach(((uniqueId, profile) -> {
            if (profile.needUpdate()) {
                this.dataService.saveData(profile);
                profile.needUpdate(false);
            }
        }));
    }
}

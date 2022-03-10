package pl.minecodes.mineeconomy.profile;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import eu.okaeri.injector.annotation.Inject;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileService {

    private final Cache<UUID, Profile> profilesCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    @Inject private DataService dataService;
    @Inject private Configuration configuration;

    public Profile getProfile(UUID uniqueId) {
        AtomicReference<Profile> atomicProfile = new AtomicReference<>(this.profilesCache.getIfPresent(uniqueId));
        if (atomicProfile.get() == null) {
            atomicProfile.set(this.loadingProfile(uniqueId));
            this.profilesCache.put(uniqueId, atomicProfile.get());
        }
        return atomicProfile.get();
    }

    private Profile loadingProfile(UUID uniqueId) {
        Profile profile = this.dataService.loadData(uniqueId);
        if (profile == null) {
            return new Profile(uniqueId, this.configuration);
        } 
        
        return profile;
    }

    private void deleteProfile(Profile profile) {
        this.dataService.deleteData(profile);
    }

    public Cache<UUID, Profile> getProfilesCache() {
        return profilesCache;
    }
}

package pl.minecodes.mineeconomy.profile;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileService {

    private final Cache<UUID, Profile> profilesCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public Profile getProfile(UUID uniqueId) {
        AtomicReference<Profile> atomicProfile = new AtomicReference<>(this.profilesCache.getIfPresent(uniqueId));
        if (atomicProfile.get() == null) {
            atomicProfile.set(this.loadingProfile(uniqueId));
            this.profilesCache.put(uniqueId, atomicProfile.get());
        }
        return atomicProfile.get();
    }

    private Profile loadingProfile(UUID uniqueId) {
        //todo loading profile from database
        //if profile in not exists in database create new.
        return new Profile(uniqueId);
    }
}

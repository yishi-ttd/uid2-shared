package com.uid2.shared.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
public class Keyset {
    @JsonProperty("keyset_id")
    private final int keysetId;
    @JsonProperty("site_id")
    private final int siteId;
    private final String name;
    @JsonProperty("allowed_sites")
    private final Set<Integer> allowedSites;
    private final long created;
    @JsonProperty("enabled")
    private final boolean isEnabled;
    @JsonProperty("default")
    private final boolean isDefault;

    public Keyset(int keysetId, int siteId, String name, Set<Integer> allowedSites,
                  long created, boolean isEnabled, boolean isDefault) {
        this.keysetId = keysetId;
        this.siteId = siteId;
        this.name = name;
        this.allowedSites = allowedSites;
        this.created = created;
        this.isEnabled = isEnabled;
        this.isDefault = isDefault;
    }

    public int getKeysetId() {
        return keysetId;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getAllowedSites() {
        return allowedSites;
    }

    public long getCreated() {
        return created;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean canBeAccessedBySite(Integer siteId) {
        //QUEST do we want to check if the keyset is enabled here?
        return allowedSites.contains(siteId);
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Keyset)) return false;

        Keyset b = (Keyset) o;

        return this.keysetId == b.keysetId
                && this.siteId == b.siteId
                && this.name.equals(b.name)
                && this.allowedSites.equals(b.allowedSites)
                && this.created == b.created
                && this.isEnabled == b.isEnabled
                && this.isDefault == b.isDefault;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keysetId, siteId, name, Arrays.hashCode(allowedSites.toArray()), created, isEnabled, isDefault);
    }
}

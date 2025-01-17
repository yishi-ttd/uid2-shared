package com.uid2.shared.store.reader;

import com.uid2.shared.cloud.DownloadCloudStorage;
import com.uid2.shared.model.KeysetKey;
import com.uid2.shared.store.CloudPath;
import com.uid2.shared.store.IKeysetKeyStore;
import com.uid2.shared.store.ScopedStoreReader;
import com.uid2.shared.store.parser.KeysetKeyParser;
import com.uid2.shared.store.scope.StoreScope;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.Collection;

public class RotatingKeysetKeyStore implements IKeysetKeyStore, StoreReader<Collection<KeysetKey>> {
    private final ScopedStoreReader<IkeysetKeyStoreSnapshot> reader;

    public RotatingKeysetKeyStore(DownloadCloudStorage fileStreamProvider, StoreScope scope) {
        this.reader = new ScopedStoreReader<>(fileStreamProvider, scope, new KeysetKeyParser(), "keyset_keys");
    }

    @Override
    public long getVersion(JsonObject metadata) {
        return metadata.getLong("version");
    }

    @Override
    public long loadContent(JsonObject metadata) throws Exception {
        return reader.loadContent(metadata, "keyset_keys");
    }

    @Override
    public Collection<KeysetKey> getAll() {
        return reader.getSnapshot().getActiveKeysetKeys();
    }

    @Override
    public void loadContent() throws Exception {
        this.loadContent(this.getMetadata());
    }

    @Override
    public JsonObject getMetadata() throws Exception {
        return reader.getMetadata();
    }

    @Override
    public CloudPath getMetadataPath() {
        return reader.getMetadataPath();
    }

    @Override
    public IkeysetKeyStoreSnapshot getSnapshot(Instant asOf) {
        return reader.getSnapshot();
    }

    @Override
    public IkeysetKeyStoreSnapshot getSnapshot() {
        return this.getSnapshot(Instant.now());
    }
}

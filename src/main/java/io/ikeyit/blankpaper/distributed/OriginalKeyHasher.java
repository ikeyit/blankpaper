package io.ikeyit.blankpaper.distributed;


public class OriginalKeyHasher implements KeyHasher{
    @Override
    public long hash(String key) {
        return key.hashCode();
    }
}

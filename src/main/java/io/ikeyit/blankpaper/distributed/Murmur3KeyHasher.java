package io.ikeyit.blankpaper.distributed;

import java.nio.charset.Charset;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Murmur3KeyHasher implements KeyHasher {
    @Override
    public long hash(String key) {
        HashFunction hashFunction = Hashing.murmur3_128();
        return hashFunction.hashString(key, Charset.defaultCharset()).asLong();
    }
}

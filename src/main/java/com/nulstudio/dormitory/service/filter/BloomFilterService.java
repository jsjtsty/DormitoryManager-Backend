package com.nulstudio.dormitory.service.filter;

import io.rebloom.client.Client;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;

@Service
public class BloomFilterService {
    @Resource
    private Client redisBloomClient;

    public boolean contains(@NotNull String name, @Nullable String value) {
        return redisBloomClient.exists(name, value);
    }

    public boolean[] contains(@NotNull String name, @NotNull String... values) {
        return redisBloomClient.existsMulti(name, values);
    }

    public boolean contains(@NotNull String name, int value) {
        final byte[] data = ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
        return redisBloomClient.exists(name, data);
    }

    public void add(@NotNull String name, @NotNull String value) {
        redisBloomClient.add(name, value);
    }

    public void add(@NotNull String name, int value) {
        final byte[] data = ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
        redisBloomClient.add(name, data);
    }

    public void addIntegers(@NotNull String name, @NotNull List<Integer> values) {
        final byte[][] data = new byte[values.size()][];
        for (int i = 0; i < values.size(); i++) {
            data[i] = ByteBuffer.allocate(Integer.BYTES).putInt(values.get(i)).array();
        }
        redisBloomClient.addMulti(name, data);
    }

    public void addStrings(@NotNull String name, @NotNull String... values) {
        redisBloomClient.addMulti(name, values);
    }

    public void addStrings(@NotNull String name, @NotNull List<String> values) {
        this.addStrings(name, values.toArray(new String[0]));
    }

}

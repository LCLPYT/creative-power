package work.lclpnet.creativepower.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public record Palette<K, V>(Map<K, Integer> content, BiMap<V, Integer> mapping) {

    public static int NIL = -1;

    public int getId(K key) {
        Integer id = content.get(key);
        if (id == null) return NIL;

        return id;
    }

    @Nullable
    public V getMapping(int id) {
        return mapping.inverse().get(id);
    }

    public static <K, V> Palette<K, V> from(Map<? extends K, ? extends V> map) {
        final Map<K, Integer> content = new HashMap<>();
        final BiMap<V, Integer> mapping = HashBiMap.create();
        final var incrementer = new Incrementer();

        for (var entry : map.entrySet()) {
            var id = mapping.computeIfAbsent(entry.getValue(), incrementer);
            content.put(entry.getKey(), id);
        }

        return new Palette<>(content, mapping);
    }

    private static class Incrementer implements Function<Object, Integer> {

        private final AtomicInteger nextId = new AtomicInteger(0);

        @Override
        public Integer apply(Object ignored) {
            return nextId.getAndIncrement();
        }
    }
}

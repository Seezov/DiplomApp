package com.example.workloadtracker.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@linkplain JsonAdapter} that fallbacks to a default value of a primitive field annotated with
 * {@linkplain FallbackOnNull}.
 */
final class FallbackOnNullJsonAdapter<T> extends JsonAdapter<T> {
    /**
     * Set of primitives classes that are supported by <strong>this</strong> adapter.
     */
    static final Set<Class<?>> PRIMITIVE_CLASSES = new LinkedHashSet<>();

    static {
        PRIMITIVE_CLASSES.add(boolean.class);
        PRIMITIVE_CLASSES.add(byte.class);
        PRIMITIVE_CLASSES.add(char.class);
        PRIMITIVE_CLASSES.add(double.class);
        PRIMITIVE_CLASSES.add(float.class);
        PRIMITIVE_CLASSES.add(int.class);
        PRIMITIVE_CLASSES.add(long.class);
        PRIMITIVE_CLASSES.add(short.class);
    }

    private final JsonAdapter<T> delegate;
    private final T fallback;
    private final String fallbackType;

    FallbackOnNullJsonAdapter(JsonAdapter<T> delegate, T fallback, String fallbackType) {
        this.delegate = delegate;
        this.fallback = fallback;
        this.fallbackType = fallbackType;
    }

    @Override
    public T fromJson(JsonReader reader) throws IOException {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull(); // We need to consume the value.
            return fallback;
        }
        return delegate.fromJson(reader);
    }

    @Override
    public void toJson(JsonWriter writer, T value) throws IOException {
        delegate.toJson(writer, value);
    }

    @Override
    public String toString() {
        return delegate + ".fallbackOnNull(" + fallbackType + '=' + fallback + ')';
    }
}
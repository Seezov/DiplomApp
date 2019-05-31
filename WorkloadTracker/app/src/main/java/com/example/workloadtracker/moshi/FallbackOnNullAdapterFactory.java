package com.example.workloadtracker.moshi;

import android.util.Pair;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class FallbackOnNullAdapterFactory implements JsonAdapter.Factory {

    @Override
    public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        Pair<FallbackOnNull, Set<Annotation>> nextAnnotations = nextAnnotations(annotations, FallbackOnNull.class);
        if (nextAnnotations == null) return null;

        Class<?> rawType = Types.getRawType(type);
        if (!FallbackOnNullJsonAdapter.PRIMITIVE_CLASSES.contains(rawType)) return null;

        String fallbackType = fallbackType(rawType);
        Object fallback = retrieveFallback(nextAnnotations.first, fallbackType);

        return new FallbackOnNullJsonAdapter<>(moshi.adapter(type, nextAnnotations.second),
                fallback, fallbackType);
    }

    /**
     * Invokes the appropriate fallback method based on the {@code fallbackType}.
     */
    private Object retrieveFallback(FallbackOnNull annotation, String fallbackType) {
        try {
            Method fallbackMethod = FallbackOnNull.class.getMethod(fallbackType);
            return fallbackMethod.invoke(annotation);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Constructs the appropriate fallback method name based on the {@code rawType}.
     */
    private String fallbackType(Class<?> rawType) {
        String typeName = rawType.getSimpleName();
        String methodSuffix = typeName.substring(0, 1).toUpperCase(Locale.US) + typeName.substring(1);
        return "fallback" + methodSuffix;
    }

    /**
     * Checks if {@code annotations} contains {@code jsonQualifier}.
     * Returns a pair containing the subset of {@code annotations} without {@code jsonQualifier}
     * and the {@code jsonQualified} instance, or null if {@code annotations} does not contain
     * {@code jsonQualifier}.
     */
    private <A extends Annotation> Pair<A, Set<Annotation>> nextAnnotations(
            Set<? extends Annotation> annotations, Class<A> jsonQualifier
    ) {
        if (!jsonQualifier.isAnnotationPresent(JsonQualifier.class)) {
            throw new IllegalArgumentException(jsonQualifier + " is not a JsonQualifier.");
        }
        if (annotations.isEmpty()) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (jsonQualifier.equals(annotation.annotationType())) {
                Set<? extends Annotation> delegateAnnotations = new LinkedHashSet<>(annotations);
                delegateAnnotations.remove(annotation);
                //noinspection unchecked Protected by the if statment.
                return new Pair<>((A) annotation, Collections.unmodifiableSet(delegateAnnotations));
            }
            A delegate = findDelegatedAnnotation(annotation, jsonQualifier);
            if (delegate != null) {
                Set<? extends Annotation> delegateAnnotations = new LinkedHashSet<>(annotations);
                delegateAnnotations.remove(annotation);
                return new Pair<>(delegate, Collections.unmodifiableSet(delegateAnnotations));
            }
        }
        return null;
    }

    private <A extends Annotation> A findDelegatedAnnotation(
            Annotation annotation, Class<A> jsonQualifier) {
        for (Annotation delegatedAnnotation : annotation.annotationType().getAnnotations()) {
            if (jsonQualifier.equals(delegatedAnnotation.annotationType())) {
                //noinspection unchecked
                return (A) delegatedAnnotation;
            }
        }
        return null;
    }
}

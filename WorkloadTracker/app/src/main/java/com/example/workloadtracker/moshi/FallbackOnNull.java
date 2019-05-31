package com.example.workloadtracker.moshi;

import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;

import java.lang.annotation.*;

/**
 * Indicates that the annotated field may be {@code null} in the json source and thus requires a
 * fallback value.
 *
 * <p>To leverage from {@linkplain FallbackOnNull} {@linkplain FallbackOnNull#ADAPTER_FACTORY}
 * must be added to your {@linkplain Moshi Moshi instance}:
 *
 * <pre><code>
 *   Moshi moshi = new Moshi.Builder()
 *      .add(FallbackOnNull.ADAPTER_FACTORY)
 *      .build();
 * </code></pre>
 */
@Documented
@JsonQualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface FallbackOnNull {

    /**
     * Fallback value for {@code boolean} primitives. Default: {@code false}.
     */
    boolean fallbackBoolean() default false;

    /**
     * Fallback value for {@code byte} primitives. Default: {@code Byte.MIN_VALUE}.
     */
    byte fallbackByte() default 0;

    /**
     * Fallback value for {@code char} primitives. Default: {@code Character.MIN_VALUE}.
     */
    char fallbackChar() default 0;

    /**
     * Fallback value for {@code double} primitives. Default: {@code Double.MIN_VALUE}.
     */
    double fallbackDouble() default 0;

    /**
     * Fallback value for {@code float} primitives. Default: {@code Float.MIN_VALUE}.
     */
    float fallbackFloat() default 0;

    /**
     * Fallback value for {@code int} primitives. Default: {@code Integer.MIN_VALUE}.
     */
    int fallbackInt() default 0;

    /**
     * Fallback value for {@code long} primitives. Default: {@code Long.MIN_VALUE}.
     */
    long fallbackLong() default 0;

    /**
     * Fallback value for {@code short} primitives. Default: {@code Short.MIN_VALUE}.
     */
    short fallbackShort() default 0;
}

/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface ForeignCollectionField {
    public static final int MAX_EAGER_LEVEL = 1;

    public String columnName() default "";

    public boolean eager() default false;

    @Deprecated
    public String foreignColumnName() default "";

    public String foreignFieldName() default "";

    @Deprecated
    public int maxEagerForeignCollectionLevel() default 1;

    public int maxEagerLevel() default 1;

    public boolean orderAscending() default true;

    public String orderColumnName() default "";
}

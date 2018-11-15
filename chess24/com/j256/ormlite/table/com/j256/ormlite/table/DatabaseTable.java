/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface DatabaseTable {
    public Class<?> daoClass() default Void.class;

    public String tableName() default "";
}

/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.types.VoidType;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface DatabaseField {
    public static final int DEFAULT_MAX_FOREIGN_AUTO_REFRESH_LEVEL = 2;
    public static final String DEFAULT_STRING = "__ormlite__ no default value string was specified";
    public static final int NO_MAX_FOREIGN_AUTO_REFRESH_LEVEL_SPECIFIED = -1;

    public boolean allowGeneratedIdInsert() default false;

    public boolean canBeNull() default true;

    public String columnDefinition() default "";

    public String columnName() default "";

    public DataType dataType() default DataType.UNKNOWN;

    public String defaultValue() default "__ormlite__ no default value string was specified";

    public boolean foreign() default false;

    public boolean foreignAutoCreate() default false;

    public boolean foreignAutoRefresh() default false;

    public String foreignColumnName() default "";

    public String format() default "";

    public boolean generatedId() default false;

    public String generatedIdSequence() default "";

    public boolean id() default false;

    public boolean index() default false;

    public String indexName() default "";

    public int maxForeignAutoRefreshLevel() default -1;

    public boolean persisted() default true;

    public Class<? extends DataPersister> persisterClass() default VoidType.class;

    public boolean readOnly() default false;

    public boolean throwIfNull() default false;

    public boolean unique() default false;

    public boolean uniqueCombo() default false;

    public boolean uniqueIndex() default false;

    public String uniqueIndexName() default "";

    public String unknownEnumName() default "";

    public boolean useGetSet() default false;

    public boolean version() default false;

    public int width() default 0;
}

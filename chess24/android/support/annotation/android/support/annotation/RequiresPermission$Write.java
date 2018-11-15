/*
 * Decompiled with CFR 0_134.
 */
package android.support.annotation;

import android.support.annotation.RequiresPermission;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public static @interface RequiresPermission.Write {
    public RequiresPermission value() default @RequiresPermission;
}

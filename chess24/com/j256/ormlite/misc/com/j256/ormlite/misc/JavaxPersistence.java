/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.misc;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.misc.SqlExceptionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;

public class JavaxPersistence {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DatabaseFieldConfig createFieldConfig(DatabaseType var0, Field var1_6) throws SQLException {
        var14_7 = var1_6.getAnnotations();
        var3_8 = ((Annotation[])var14_7).length;
        var15_10 = null;
        var13_11 = null;
        var12_12 = null;
        var10_13 = null;
        var9_14 = null;
        var7_15 = null;
        var6_16 = null;
        var11_17 = null;
        var8_18 = null;
        for (var2_9 = 0; var2_9 < var3_8; ++var2_9) {
            var5_19 = var14_7[var2_9];
            var16_20 = var5_19.annotationType();
            if (var16_20.getName().equals("javax.persistence.Column")) {
                var15_10 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.Basic")) {
                var13_11 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.Id")) {
                var12_12 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.GeneratedValue")) {
                var11_17 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.OneToOne")) {
                var10_13 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.ManyToOne")) {
                var9_14 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.JoinColumn")) {
                var8_18 = var5_19;
            }
            if (var16_20.getName().equals("javax.persistence.Enumerated")) {
                var7_15 = var5_19;
            }
            if (!var16_20.getName().equals("javax.persistence.Version")) continue;
            var6_16 = var5_19;
        }
        if (var15_10 == null && var13_11 == null && var12_12 == null && var10_13 == null && var9_14 == null && var7_15 == null && var6_16 == null) {
            return null;
        }
        var16_20 = new DatabaseFieldConfig();
        var5_19 = var14_7 = var1_6.getName();
        if (var0.isEntityNamesMustBeUpCase()) {
            var5_19 = var14_7.toUpperCase();
        }
        var16_20.setFieldName((String)var5_19);
        if (var15_10 != null) {
            try {
                var0 = (String)var15_10.getClass().getMethod("name", new Class[0]).invoke(var15_10, new Object[0]);
                if (var0 != null && var0.length() > 0) {
                    var16_20.setColumnName((String)var0);
                }
                if ((var0 = (String)var15_10.getClass().getMethod("columnDefinition", new Class[0]).invoke(var15_10, new Object[0])) != null && var0.length() > 0) {
                    var16_20.setColumnDefinition((String)var0);
                }
                var16_20.setWidth(((Integer)var15_10.getClass().getMethod("length", new Class[0]).invoke(var15_10, new Object[0])).intValue());
                var0 = (Boolean)var15_10.getClass().getMethod("nullable", new Class[0]).invoke(var15_10, new Object[0]);
                if (var0 != null) {
                    var16_20.setCanBeNull(var0.booleanValue());
                }
                if ((var0 = (Boolean)var15_10.getClass().getMethod("unique", new Class[0]).invoke(var15_10, new Object[0])) != null) {
                    var16_20.setUnique(var0.booleanValue());
                }
            }
            catch (Exception var0_1) {
                var5_19 = new StringBuilder();
                var5_19.append("Problem accessing fields from the @Column annotation for field ");
                var5_19.append(var1_6);
                throw SqlExceptionUtil.create(var5_19.toString(), var0_1);
            }
        }
        var4_21 = true;
        if (var13_11 != null) {
            try {
                var0 = (Boolean)var13_11.getClass().getMethod("optional", new Class[0]).invoke(var13_11, new Object[0]);
                if (var0 == null) {
                    var16_20.setCanBeNull(true);
                } else {
                    var16_20.setCanBeNull(var0.booleanValue());
                }
            }
            catch (Exception var0_2) {
                var5_19 = new StringBuilder();
                var5_19.append("Problem accessing fields from the @Basic annotation for field ");
                var5_19.append(var1_6);
                throw SqlExceptionUtil.create(var5_19.toString(), var0_2);
            }
        }
        if (var12_12 != null) {
            if (var11_17 == null) {
                var16_20.setId(true);
            } else {
                var16_20.setGeneratedId(true);
            }
        }
        if (var10_13 != null || var9_14 != null) {
            if (!Collection.class.isAssignableFrom(var1_6.getType()) && !ForeignCollection.class.isAssignableFrom(var1_6.getType())) {
                var16_20.setForeign(true);
                if (var8_18 != null) {
                    try {
                        var0 = (String)var8_18.getClass().getMethod("name", new Class[0]).invoke(var8_18, new Object[0]);
                        if (var0 != null && var0.length() > 0) {
                            var16_20.setColumnName((String)var0);
                        }
                        if ((var0 = (Boolean)var8_18.getClass().getMethod("nullable", new Class[0]).invoke(var8_18, new Object[0])) != null) {
                            var16_20.setCanBeNull(var0.booleanValue());
                        }
                        if ((var0 = (Boolean)var8_18.getClass().getMethod("unique", new Class[0]).invoke(var8_18, new Object[0])) == null) ** GOTO lbl112
                        var16_20.setUnique(var0.booleanValue());
                    }
                    catch (Exception var0_3) {
                        var5_19 = new StringBuilder();
                        var5_19.append("Problem accessing fields from the @JoinColumn annotation for field ");
                        var5_19.append(var1_6);
                        throw SqlExceptionUtil.create(var5_19.toString(), var0_3);
                    }
                }
            } else {
                var16_20.setForeignCollection(true);
                if (var8_18 != null) {
                    try {
                        var0 = (String)var8_18.getClass().getMethod("name", new Class[0]).invoke(var8_18, new Object[0]);
                        if (var0 != null && var0.length() > 0) {
                            var16_20.setForeignCollectionColumnName((String)var0);
                        }
                        if ((var0 = var8_18.getClass().getMethod("fetch", new Class[0]).invoke(var8_18, new Object[0])) != null && var0.toString().equals("EAGER")) {
                            var16_20.setForeignCollectionEager(true);
                        }
                    }
                    catch (Exception var0_4) {
                        var5_19 = new StringBuilder();
                        var5_19.append("Problem accessing fields from the @JoinColumn annotation for field ");
                        var5_19.append(var1_6);
                        throw SqlExceptionUtil.create(var5_19.toString(), var0_4);
                    }
                }
            }
        }
lbl112: // 8 sources:
        if (var7_15 != null) {
            try {
                var0 = var7_15.getClass().getMethod("value", new Class[0]).invoke(var7_15, new Object[0]);
                if (var0 != null && var0.toString().equals("STRING")) {
                    var16_20.setDataType(DataType.ENUM_STRING);
                } else {
                    var16_20.setDataType(DataType.ENUM_INTEGER);
                }
            }
            catch (Exception var0_5) {
                var5_19 = new StringBuilder();
                var5_19.append("Problem accessing fields from the @Enumerated annotation for field ");
                var5_19.append(var1_6);
                throw SqlExceptionUtil.create(var5_19.toString(), var0_5);
            }
        }
        if (var6_16 != null) {
            var16_20.setVersion(true);
        }
        if (var16_20.getDataPersister() == null) {
            var16_20.setDataPersister(DataPersisterManager.lookupForField(var1_6));
        }
        if (DatabaseFieldConfig.findGetMethod(var1_6, false) == null || DatabaseFieldConfig.findSetMethod(var1_6, false) == null) {
            var4_21 = false;
        }
        var16_20.setUseGetSet(var4_21);
        return var16_20;
    }

    public static String getEntityName(Class<?> class_) {
        block5 : {
            Object object;
            int n;
            Annotation[] arrannotation = class_.getAnnotations();
            int n2 = arrannotation.length;
            Object object2 = null;
            for (n = 0; n < n2; ++n) {
                object = arrannotation[n];
                if (!object.annotationType().getName().equals("javax.persistence.Entity")) continue;
                object2 = object;
            }
            if (object2 == null) {
                return null;
            }
            try {
                object2 = (String)object2.getClass().getMethod("name", new Class[0]).invoke(object2, new Object[0]);
                if (object2 == null) break block5;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                object.append("Could not get entity name from class ");
                object.append(class_);
                throw new IllegalStateException(object.toString(), exception);
            }
            n = object2.length();
            if (n <= 0) break block5;
            return object2;
        }
        return null;
    }
}

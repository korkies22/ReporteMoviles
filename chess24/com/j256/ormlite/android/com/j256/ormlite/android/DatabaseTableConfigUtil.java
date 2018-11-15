/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.android;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfigUtil {
    private static final int ALLOW_GENERATED_ID_INSERT = 24;
    private static final int CAN_BE_NULL = 5;
    private static final int COLUMN_DEFINITON = 25;
    private static final int COLUMN_NAME = 1;
    private static final int DATA_TYPE = 2;
    private static final int DEFAULT_VALUE = 3;
    private static final int FOREIGN = 9;
    private static final int FOREIGN_AUTO_CREATE = 26;
    private static final int FOREIGN_AUTO_REFRESH = 21;
    private static final int FOREIGN_COLUMN_NAME = 28;
    private static final int FORMAT = 14;
    private static final int GENERATED_ID = 7;
    private static final int GENERATED_ID_SEQUENCE = 8;
    private static final int ID = 6;
    private static final int INDEX = 17;
    private static final int INDEX_NAME = 19;
    private static final int MAX_FOREIGN_AUTO_REFRESH_LEVEL = 22;
    private static final int PERSISTED = 13;
    private static final int PERSISTER_CLASS = 23;
    private static final int READ_ONLY = 29;
    private static final int THROW_IF_NULL = 12;
    private static final int UNIQUE = 15;
    private static final int UNIQUE_COMBO = 16;
    private static final int UNIQUE_INDEX = 18;
    private static final int UNIQUE_INDEX_NAME = 20;
    private static final int UNKNOWN_ENUM_NAME = 11;
    private static final int USE_GET_SET = 10;
    private static final int VERSION = 27;
    private static final int WIDTH = 4;
    private static Class<?> annotationFactoryClazz;
    private static Class<?> annotationMemberClazz;
    private static final int[] configFieldNums;
    private static Field elementsField;
    private static Field nameField;
    private static Field valueField;
    private static int workedC;

    static {
        configFieldNums = DatabaseTableConfigUtil.lookupClasses();
    }

    private static void assignConfigField(int n, DatabaseFieldConfig object, Field object2, Object object3) {
        switch (n) {
            default: {
                object = new StringBuilder();
                object.append("Could not find support for DatabaseField number ");
                object.append(n);
                throw new IllegalStateException(object.toString());
            }
            case 29: {
                object.setReadOnly((Boolean)object3);
                return;
            }
            case 28: {
                object.setForeignColumnName(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 27: {
                object.setVersion((Boolean)object3);
                return;
            }
            case 26: {
                object.setForeignAutoCreate((Boolean)object3);
                return;
            }
            case 25: {
                object.setColumnDefinition(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 24: {
                object.setAllowGeneratedIdInsert((Boolean)object3);
                return;
            }
            case 23: {
                object.setPersisterClass((Class)object3);
                return;
            }
            case 22: {
                object.setMaxForeignAutoRefreshLevel((Integer)object3);
                return;
            }
            case 21: {
                object.setForeignAutoRefresh((Boolean)object3);
                return;
            }
            case 20: {
                object.setUniqueIndexName(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 19: {
                object.setIndexName(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 18: {
                object.setUniqueIndex((Boolean)object3);
                return;
            }
            case 17: {
                object.setIndex((Boolean)object3);
                return;
            }
            case 16: {
                object.setUniqueCombo((Boolean)object3);
                return;
            }
            case 15: {
                object.setUnique((Boolean)object3);
                return;
            }
            case 14: {
                object.setFormat(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 13: {
                object.setPersisted((Boolean)object3);
                return;
            }
            case 12: {
                object.setThrowIfNull((Boolean)object3);
                return;
            }
            case 11: {
                object.setUnknownEnumValue(DatabaseFieldConfig.findMatchingEnumVal((Field)object2, (String)object3));
                return;
            }
            case 10: {
                object.setUseGetSet((Boolean)object3);
                return;
            }
            case 9: {
                object.setForeign((Boolean)object3);
                return;
            }
            case 8: {
                object.setGeneratedIdSequence(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
                return;
            }
            case 7: {
                object.setGeneratedId((Boolean)object3);
                return;
            }
            case 6: {
                object.setId((Boolean)object3);
                return;
            }
            case 5: {
                object.setCanBeNull((Boolean)object3);
                return;
            }
            case 4: {
                object.setWidth((Integer)object3);
                return;
            }
            case 3: {
                object2 = (String)object3;
                if (object2 == null || object2.equals("__ormlite__ no default value string was specified")) break;
                object.setDefaultValue((String)object2);
                return;
            }
            case 2: {
                object.setDataType((DataType)((Object)object3));
                return;
            }
            case 1: {
                object.setColumnName(DatabaseTableConfigUtil.valueIfNotBlank((String)object3));
            }
        }
    }

    private static DatabaseFieldConfig buildConfig(DatabaseField object, String object2, Field field) throws Exception {
        if ((object = Proxy.getInvocationHandler(object)).getClass() != annotationFactoryClazz) {
            return null;
        }
        object2 = elementsField.get(object);
        if (object2 == null) {
            return null;
        }
        object = new DatabaseFieldConfig(field.getName());
        object2 = object2;
        for (int i = 0; i < configFieldNums.length; ++i) {
            Object object3 = valueField.get(object2[i]);
            if (object3 == null) continue;
            DatabaseTableConfigUtil.assignConfigField(configFieldNums[i], (DatabaseFieldConfig)object, field, object3);
        }
        return object;
    }

    private static int configFieldNameToNum(String string) {
        if (string.equals("columnName")) {
            return 1;
        }
        if (string.equals("dataType")) {
            return 2;
        }
        if (string.equals("defaultValue")) {
            return 3;
        }
        if (string.equals("width")) {
            return 4;
        }
        if (string.equals("canBeNull")) {
            return 5;
        }
        if (string.equals("id")) {
            return 6;
        }
        if (string.equals("generatedId")) {
            return 7;
        }
        if (string.equals("generatedIdSequence")) {
            return 8;
        }
        if (string.equals("foreign")) {
            return 9;
        }
        if (string.equals("useGetSet")) {
            return 10;
        }
        if (string.equals("unknownEnumName")) {
            return 11;
        }
        if (string.equals("throwIfNull")) {
            return 12;
        }
        if (string.equals("persisted")) {
            return 13;
        }
        if (string.equals("format")) {
            return 14;
        }
        if (string.equals("unique")) {
            return 15;
        }
        if (string.equals("uniqueCombo")) {
            return 16;
        }
        if (string.equals("index")) {
            return 17;
        }
        if (string.equals("uniqueIndex")) {
            return 18;
        }
        if (string.equals("indexName")) {
            return 19;
        }
        if (string.equals("uniqueIndexName")) {
            return 20;
        }
        if (string.equals("foreignAutoRefresh")) {
            return 21;
        }
        if (string.equals("maxForeignAutoRefreshLevel")) {
            return 22;
        }
        if (string.equals("persisterClass")) {
            return 23;
        }
        if (string.equals("allowGeneratedIdInsert")) {
            return 24;
        }
        if (string.equals("columnDefinition")) {
            return 25;
        }
        if (string.equals("foreignAutoCreate")) {
            return 26;
        }
        if (string.equals("version")) {
            return 27;
        }
        if (string.equals("foreignColumnName")) {
            return 28;
        }
        if (string.equals("readOnly")) {
            return 29;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find support for DatabaseField ");
        stringBuilder.append(string);
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static DatabaseFieldConfig configFromField(DatabaseType databaseType, String string, Field field) throws SQLException {
        DatabaseFieldConfig databaseFieldConfig;
        if (configFieldNums == null) {
            return DatabaseFieldConfig.fromField(databaseType, string, field);
        }
        DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        DatabaseFieldConfig databaseFieldConfig2 = databaseFieldConfig = null;
        if (databaseField != null) {
            try {
                databaseFieldConfig2 = DatabaseTableConfigUtil.buildConfig(databaseField, string, field);
            }
            catch (Exception exception) {
                databaseFieldConfig2 = databaseFieldConfig;
            }
        }
        if (databaseFieldConfig2 == null) {
            return DatabaseFieldConfig.fromField(databaseType, string, field);
        }
        ++workedC;
        return databaseFieldConfig2;
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource class_, Class<T> class_2) throws SQLException {
        DatabaseType databaseType = class_.getDatabaseType();
        String string = DatabaseTableConfig.extractTableName(class_2);
        ArrayList<DatabaseFieldConfig> arrayList = new ArrayList<DatabaseFieldConfig>();
        for (class_ = class_2; class_ != null; class_ = class_.getSuperclass()) {
            Field[] arrfield = class_.getDeclaredFields();
            int n = arrfield.length;
            for (int i = 0; i < n; ++i) {
                DatabaseFieldConfig databaseFieldConfig = DatabaseTableConfigUtil.configFromField(databaseType, string, arrfield[i]);
                if (databaseFieldConfig == null || !databaseFieldConfig.isPersisted()) continue;
                arrayList.add(databaseFieldConfig);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return new DatabaseTableConfig<T>(class_2, string, arrayList);
    }

    public static int getWorkedC() {
        return workedC;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static int[] lookupClasses() {
        annotationFactoryClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationFactory");
        annotationMemberClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationMember");
        Object[] arrobject = Class.forName("[Lorg.apache.harmony.lang.annotation.AnnotationMember;");
        elementsField = annotationFactoryClazz.getDeclaredField("elements");
        elementsField.setAccessible(true);
        nameField = annotationMemberClazz.getDeclaredField("name");
        nameField.setAccessible(true);
        valueField = annotationMemberClazz.getDeclaredField("value");
        valueField.setAccessible(true);
        int[] arrn = DatabaseFieldSample.class.getDeclaredField("field");
        arrn = Proxy.getInvocationHandler(arrn.getAnnotation(DatabaseField.class));
        if (arrn.getClass() != annotationFactoryClazz) {
            return null;
        }
        try {
            arrn = elementsField.get(arrn);
            if (arrn == null) return null;
            if (arrn.getClass() != arrobject) {
                return null;
            }
            arrobject = arrn;
            arrn = new int[arrobject.length];
            int n = 0;
            while (n < arrobject.length) {
                arrn[n] = DatabaseTableConfigUtil.configFieldNameToNum((String)nameField.get(arrobject[n]));
                ++n;
            }
            return arrn;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
        catch (SecurityException securityException) {
            return null;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
    }

    private static String valueIfNotBlank(String string) {
        if (string != null && string.length() != 0) {
            return string;
        }
        return null;
    }

    private static class DatabaseFieldSample {
        @DatabaseField
        String field;

        private DatabaseFieldSample() {
        }
    }

}

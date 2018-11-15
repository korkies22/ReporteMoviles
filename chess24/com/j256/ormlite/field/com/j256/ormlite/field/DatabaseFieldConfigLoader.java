/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.misc.SqlExceptionUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

public class DatabaseFieldConfigLoader {
    private static final String CONFIG_FILE_END_MARKER = "# --field-end--";
    private static final String CONFIG_FILE_START_MARKER = "# --field-start--";
    private static final DataPersister DEFAULT_DATA_PERSISTER = DatabaseFieldConfig.DEFAULT_DATA_TYPE.getDataPersister();
    private static final int DEFAULT_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = 1;
    private static final String FIELD_NAME_ALLOW_GENERATED_ID_INSERT = "allowGeneratedIdInsert";
    private static final String FIELD_NAME_CAN_BE_NULL = "canBeNull";
    private static final String FIELD_NAME_COLUMN_DEFINITION = "columnDefinition";
    private static final String FIELD_NAME_COLUMN_NAME = "columnName";
    private static final String FIELD_NAME_DATA_PERSISTER = "dataPersister";
    private static final String FIELD_NAME_DEFAULT_VALUE = "defaultValue";
    private static final String FIELD_NAME_FIELD_NAME = "fieldName";
    private static final String FIELD_NAME_FOREIGN = "foreign";
    private static final String FIELD_NAME_FOREIGN_AUTO_CREATE = "foreignAutoCreate";
    private static final String FIELD_NAME_FOREIGN_AUTO_REFRESH = "foreignAutoRefresh";
    private static final String FIELD_NAME_FOREIGN_COLLECTION = "foreignCollection";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME = "foreignCollectionColumnName";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_EAGER = "foreignCollectionEager";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME = "foreignCollectionForeignFieldName";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME_OLD = "foreignCollectionForeignColumnName";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING = "foreignCollectionOrderAscending";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME = "foreignCollectionOrderColumnName";
    private static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME_OLD = "foreignCollectionOrderColumn";
    private static final String FIELD_NAME_FOREIGN_COLUMN_NAME = "foreignColumnName";
    private static final String FIELD_NAME_FORMAT = "format";
    private static final String FIELD_NAME_GENERATED_ID = "generatedId";
    private static final String FIELD_NAME_GENERATED_ID_SEQUENCE = "generatedIdSequence";
    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_INDEX = "index";
    private static final String FIELD_NAME_INDEX_NAME = "indexName";
    private static final String FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = "foreignCollectionMaxEagerLevel";
    private static final String FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL_OLD = "maxEagerForeignCollectionLevel";
    private static final String FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL = "maxForeignAutoRefreshLevel";
    private static final String FIELD_NAME_PERSISTER_CLASS = "persisterClass";
    private static final String FIELD_NAME_READ_ONLY = "readOnly";
    private static final String FIELD_NAME_THROW_IF_NULL = "throwIfNull";
    private static final String FIELD_NAME_UNIQUE = "unique";
    private static final String FIELD_NAME_UNIQUE_COMBO = "uniqueCombo";
    private static final String FIELD_NAME_UNIQUE_INDEX = "uniqueIndex";
    private static final String FIELD_NAME_UNIQUE_INDEX_NAME = "uniqueIndexName";
    private static final String FIELD_NAME_UNKNOWN_ENUM_VALUE = "unknownEnumValue";
    private static final String FIELD_NAME_USE_GET_SET = "useGetSet";
    private static final String FIELD_NAME_VERSION = "version";
    private static final String FIELD_NAME_WIDTH = "width";

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DatabaseFieldConfig fromReader(BufferedReader var0) throws SQLException {
        var3_2 = new DatabaseFieldConfig();
        var1_3 = false;
        do lbl-1000: // 2 sources:
        {
            do {
                if ((var2_4 = var0.readLine()) != null && !var2_4.equals("# --field-end--")) continue;
                if (var1_3 == false) return null;
                return var3_2;
            } while (var2_4.length() == 0 || var2_4.startsWith("#") || var2_4.equals("# --field-start--"));
            break;
        } while (true);
        catch (IOException var0_1) {
            throw SqlExceptionUtil.create("Could not read DatabaseFieldConfig from stream", var0_1);
        }
        {
            var4_5 = var2_4.split("=", -2);
            if (var4_5.length != 2) {
                var0 = new StringBuilder();
                var0.append("DatabaseFieldConfig reading from stream cannot parse line: ");
                var0.append(var2_4);
                throw new SQLException(var0.toString());
            }
            DatabaseFieldConfigLoader.readField(var3_2, var4_5[0], var4_5[1]);
            var1_3 = true;
            ** while (true)
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void readField(DatabaseFieldConfig object, String arrstring, String string) {
        if (arrstring.equals(FIELD_NAME_FIELD_NAME)) {
            object.setFieldName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_COLUMN_NAME)) {
            object.setColumnName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_DATA_PERSISTER)) {
            object.setDataPersister(DataType.valueOf(string).getDataPersister());
            return;
        }
        if (arrstring.equals(FIELD_NAME_DEFAULT_VALUE)) {
            object.setDefaultValue(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_WIDTH)) {
            object.setWidth(Integer.parseInt(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_CAN_BE_NULL)) {
            object.setCanBeNull(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_ID)) {
            object.setId(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_GENERATED_ID)) {
            object.setGeneratedId(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_GENERATED_ID_SEQUENCE)) {
            object.setGeneratedIdSequence(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN)) {
            object.setForeign(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_USE_GET_SET)) {
            object.setUseGetSet(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_UNKNOWN_ENUM_VALUE)) {
            int i;
            ?[] arrobj;
            block46 : {
                arrstring = string.split("#", -2);
                if (arrstring.length != 2) {
                    object = new StringBuilder();
                    object.append("Invalid value for unknownEnumValue which should be in class#name format: ");
                    object.append(string);
                    throw new IllegalArgumentException(object.toString());
                }
                i = 0;
                Class<?> class_ = Class.forName(arrstring[0]);
                arrobj = class_.getEnumConstants();
                if (arrobj != null) break block46;
                object = new StringBuilder();
                object.append("Invalid class is not an Enum for unknownEnumValue: ");
                object.append(string);
                throw new IllegalArgumentException(object.toString());
            }
            Enum[] arrenum = (Enum[])arrobj;
            int n = arrenum.length;
            boolean bl = false;
            do {
                if (i >= n) {
                    if (bl) return;
                    object = new StringBuilder();
                    object.append("Invalid enum value name for unknownEnumvalue: ");
                    object.append(string);
                    throw new IllegalArgumentException(object.toString());
                }
                Enum enum_ = arrenum[i];
                if (enum_.name().equals(arrstring[1])) {
                    object.setUnknownEnumValue(enum_);
                    bl = true;
                }
                ++i;
            } while (true);
        }
        if (arrstring.equals(FIELD_NAME_THROW_IF_NULL)) {
            object.setThrowIfNull(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FORMAT)) {
            object.setFormat(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_UNIQUE)) {
            object.setUnique(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_UNIQUE_COMBO)) {
            object.setUniqueCombo(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_INDEX)) {
            object.setIndex(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_INDEX_NAME)) {
            object.setIndex(true);
            object.setIndexName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_UNIQUE_INDEX)) {
            object.setUniqueIndex(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_UNIQUE_INDEX_NAME)) {
            object.setUniqueIndex(true);
            object.setUniqueIndexName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_AUTO_REFRESH)) {
            object.setForeignAutoRefresh(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL)) {
            object.setMaxForeignAutoRefreshLevel(Integer.parseInt(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_PERSISTER_CLASS)) {
            object.setPersisterClass(Class.forName(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_ALLOW_GENERATED_ID_INSERT)) {
            object.setAllowGeneratedIdInsert(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_COLUMN_DEFINITION)) {
            object.setColumnDefinition(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_AUTO_CREATE)) {
            object.setForeignAutoCreate(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_VERSION)) {
            object.setVersion(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLUMN_NAME)) {
            object.setForeignColumnName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_READ_ONLY)) {
            object.setReadOnly(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION)) {
            object.setForeignCollection(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_EAGER)) {
            object.setForeignCollectionEager(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL_OLD)) {
            object.setForeignCollectionMaxEagerLevel(Integer.parseInt(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL)) {
            object.setForeignCollectionMaxEagerLevel(Integer.parseInt(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME)) {
            object.setForeignCollectionColumnName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME_OLD)) {
            object.setForeignCollectionOrderColumnName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME)) {
            object.setForeignCollectionOrderColumnName(string);
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING)) {
            object.setForeignCollectionOrderAscending(Boolean.parseBoolean(string));
            return;
        }
        if (arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME_OLD)) {
            object.setForeignCollectionForeignFieldName(string);
            return;
        }
        if (!arrstring.equals(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME)) return;
        object.setForeignCollectionForeignFieldName(string);
        return;
        catch (ClassNotFoundException classNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown class specified for unknownEnumValue: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
        catch (ClassNotFoundException classNotFoundException) {}
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Could not find persisterClass: ");
        stringBuilder2.append(string);
        throw new IllegalArgumentException(stringBuilder2.toString());
    }

    public static void write(BufferedWriter bufferedWriter, DatabaseFieldConfig databaseFieldConfig, String string) throws SQLException {
        try {
            DatabaseFieldConfigLoader.writeConfig(bufferedWriter, databaseFieldConfig, string);
            return;
        }
        catch (IOException iOException) {
            throw SqlExceptionUtil.create("Could not write config to writer", iOException);
        }
    }

    public static void writeConfig(BufferedWriter appendable, DatabaseFieldConfig databaseFieldConfig, String string) throws IOException {
        Object object;
        ((Writer)appendable).append(CONFIG_FILE_START_MARKER);
        appendable.newLine();
        if (databaseFieldConfig.getFieldName() != null) {
            ((Writer)appendable).append(FIELD_NAME_FIELD_NAME).append('=').append(databaseFieldConfig.getFieldName());
            appendable.newLine();
        }
        if (databaseFieldConfig.getColumnName() != null) {
            ((Writer)appendable).append(FIELD_NAME_COLUMN_NAME).append('=').append(databaseFieldConfig.getColumnName());
            appendable.newLine();
        }
        if (databaseFieldConfig.getDataPersister() != DEFAULT_DATA_PERSISTER) {
            boolean bl;
            object = DataType.values();
            boolean bl2 = false;
            int n = ((DataType[])object).length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                DataType dataType = object[n2];
                if (dataType.getDataPersister() == databaseFieldConfig.getDataPersister()) {
                    ((Writer)appendable).append(FIELD_NAME_DATA_PERSISTER).append('=').append(dataType.name());
                    appendable.newLine();
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            if (!bl) {
                appendable = new StringBuilder();
                appendable.append("Unknown data persister field: ");
                appendable.append(databaseFieldConfig.getDataPersister());
                throw new IllegalArgumentException(appendable.toString());
            }
        }
        if (databaseFieldConfig.getDefaultValue() != null) {
            ((Writer)appendable).append(FIELD_NAME_DEFAULT_VALUE).append('=').append(databaseFieldConfig.getDefaultValue());
            appendable.newLine();
        }
        if (databaseFieldConfig.getWidth() != 0) {
            ((Writer)appendable).append(FIELD_NAME_WIDTH).append('=').append(Integer.toString(databaseFieldConfig.getWidth()));
            appendable.newLine();
        }
        if (!databaseFieldConfig.isCanBeNull()) {
            ((Writer)appendable).append(FIELD_NAME_CAN_BE_NULL).append('=').append(Boolean.toString(databaseFieldConfig.isCanBeNull()));
            appendable.newLine();
        }
        if (databaseFieldConfig.isId()) {
            ((Writer)appendable).append(FIELD_NAME_ID).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isGeneratedId()) {
            ((Writer)appendable).append(FIELD_NAME_GENERATED_ID).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getGeneratedIdSequence() != null) {
            ((Writer)appendable).append(FIELD_NAME_GENERATED_ID_SEQUENCE).append('=').append(databaseFieldConfig.getGeneratedIdSequence());
            appendable.newLine();
        }
        if (databaseFieldConfig.isForeign()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isUseGetSet()) {
            ((Writer)appendable).append(FIELD_NAME_USE_GET_SET).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getUnknownEnumValue() != null) {
            ((Writer)appendable).append(FIELD_NAME_UNKNOWN_ENUM_VALUE).append('=').append(databaseFieldConfig.getUnknownEnumValue().getClass().getName()).append("#").append(databaseFieldConfig.getUnknownEnumValue().name());
            appendable.newLine();
        }
        if (databaseFieldConfig.isThrowIfNull()) {
            ((Writer)appendable).append(FIELD_NAME_THROW_IF_NULL).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getFormat() != null) {
            ((Writer)appendable).append(FIELD_NAME_FORMAT).append('=').append(databaseFieldConfig.getFormat());
            appendable.newLine();
        }
        if (databaseFieldConfig.isUnique()) {
            ((Writer)appendable).append(FIELD_NAME_UNIQUE).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isUniqueCombo()) {
            ((Writer)appendable).append(FIELD_NAME_UNIQUE_COMBO).append('=').append("true");
            appendable.newLine();
        }
        if ((object = databaseFieldConfig.getIndexName(string)) != null) {
            ((Writer)appendable).append(FIELD_NAME_INDEX_NAME).append('=').append((CharSequence)object);
            appendable.newLine();
        }
        if ((string = databaseFieldConfig.getUniqueIndexName(string)) != null) {
            ((Writer)appendable).append(FIELD_NAME_UNIQUE_INDEX_NAME).append('=').append(string);
            appendable.newLine();
        }
        if (databaseFieldConfig.isForeignAutoRefresh()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_AUTO_REFRESH).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getMaxForeignAutoRefreshLevel() != -1) {
            ((Writer)appendable).append(FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL).append('=').append(Integer.toString(databaseFieldConfig.getMaxForeignAutoRefreshLevel()));
            appendable.newLine();
        }
        if (databaseFieldConfig.getPersisterClass() != DatabaseFieldConfig.DEFAULT_PERSISTER_CLASS) {
            ((Writer)appendable).append(FIELD_NAME_PERSISTER_CLASS).append('=').append(databaseFieldConfig.getPersisterClass().getName());
            appendable.newLine();
        }
        if (databaseFieldConfig.isAllowGeneratedIdInsert()) {
            ((Writer)appendable).append(FIELD_NAME_ALLOW_GENERATED_ID_INSERT).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getColumnDefinition() != null) {
            ((Writer)appendable).append(FIELD_NAME_COLUMN_DEFINITION).append('=').append(databaseFieldConfig.getColumnDefinition());
            appendable.newLine();
        }
        if (databaseFieldConfig.isForeignAutoCreate()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_AUTO_CREATE).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isVersion()) {
            ((Writer)appendable).append(FIELD_NAME_VERSION).append('=').append("true");
            appendable.newLine();
        }
        if ((string = databaseFieldConfig.getForeignColumnName()) != null) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLUMN_NAME).append('=').append(string);
            appendable.newLine();
        }
        if (databaseFieldConfig.isReadOnly()) {
            ((Writer)appendable).append(FIELD_NAME_READ_ONLY).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isForeignCollection()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.isForeignCollectionEager()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION_EAGER).append('=').append("true");
            appendable.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionMaxEagerLevel() != 1) {
            ((Writer)appendable).append(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL).append('=').append(Integer.toString(databaseFieldConfig.getForeignCollectionMaxEagerLevel()));
            appendable.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionColumnName() != null) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME).append('=').append(databaseFieldConfig.getForeignCollectionColumnName());
            appendable.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionOrderColumnName() != null) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME).append('=').append(databaseFieldConfig.getForeignCollectionOrderColumnName());
            appendable.newLine();
        }
        if (!databaseFieldConfig.isForeignCollectionOrderAscending()) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING).append('=').append(Boolean.toString(databaseFieldConfig.isForeignCollectionOrderAscending()));
            appendable.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionForeignFieldName() != null) {
            ((Writer)appendable).append(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME).append('=').append(databaseFieldConfig.getForeignCollectionForeignFieldName());
            appendable.newLine();
        }
        ((Writer)appendable).append(CONFIG_FILE_END_MARKER);
        appendable.newLine();
    }
}

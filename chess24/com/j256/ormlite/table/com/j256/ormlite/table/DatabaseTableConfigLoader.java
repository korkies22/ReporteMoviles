/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseTableConfigLoader {
    private static final String CONFIG_FILE_END_MARKER = "# --table-end--";
    private static final String CONFIG_FILE_FIELDS_END = "# --table-fields-end--";
    private static final String CONFIG_FILE_FIELDS_START = "# --table-fields-start--";
    private static final String CONFIG_FILE_START_MARKER = "# --table-start--";
    private static final String FIELD_NAME_DATA_CLASS = "dataClass";
    private static final String FIELD_NAME_TABLE_NAME = "tableName";

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <T> DatabaseTableConfig<T> fromReader(BufferedReader var0) throws SQLException {
        var3_2 = new DatabaseTableConfig<T>();
        var1_3 = false;
        do lbl-1000: // 4 sources:
        {
            var2_4 = var0.readLine();
            if (var2_4 == null || var2_4.equals("# --table-end--")) {
                if (var1_3 == false) return null;
                return var3_2;
            }
            if (!var2_4.equals("# --table-fields-start--")) break block5;
            break;
        } while (true);
        catch (IOException var0_1) {
            throw SqlExceptionUtil.create("Could not read DatabaseTableConfig from stream", var0_1);
        }
        {
            block5 : {
                DatabaseTableConfigLoader.readFields((BufferedReader)var0, var3_2);
                continue;
            }
            if (var2_4.length() == 0 || var2_4.startsWith("#") || var2_4.equals("# --table-start--")) continue;
            var4_5 = var2_4.split("=", -2);
            if (var4_5.length != 2) {
                var0 = new StringBuilder();
                var0.append("DatabaseTableConfig reading from stream cannot parse line: ");
                var0.append(var2_4);
                throw new SQLException(var0.toString());
            }
            DatabaseTableConfigLoader.readTableField(var3_2, var4_5[0], var4_5[1]);
            var1_3 = true;
            ** while (true)
        }
    }

    public static List<DatabaseTableConfig<?>> loadDatabaseConfigFromReader(BufferedReader bufferedReader) throws SQLException {
        ArrayList arrayList = new ArrayList();
        DatabaseTableConfig<T> databaseTableConfig;
        while ((databaseTableConfig = DatabaseTableConfigLoader.fromReader(bufferedReader)) != null) {
            arrayList.add(databaseTableConfig);
        }
        return arrayList;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T> void readFields(BufferedReader bufferedReader, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        ArrayList<DatabaseFieldConfig> arrayList = new ArrayList<DatabaseFieldConfig>();
        do {
            Object object = bufferedReader.readLine();
            if (object == null || object.equals(CONFIG_FILE_FIELDS_END) || (object = DatabaseFieldConfigLoader.fromReader(bufferedReader)) == null) break;
            arrayList.add((DatabaseFieldConfig)object);
        } while (true);
        databaseTableConfig.setFieldConfigs(arrayList);
        return;
        catch (IOException iOException) {
            throw SqlExceptionUtil.create("Could not read next field from config file", iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T> void readTableField(DatabaseTableConfig<T> databaseTableConfig, String string, String string2) {
        if (!string.equals(FIELD_NAME_DATA_CLASS)) {
            if (!string.equals(FIELD_NAME_TABLE_NAME)) return;
            databaseTableConfig.setTableName(string2);
            return;
        }
        try {
            databaseTableConfig.setDataClass(Class.forName(string2));
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown class specified for dataClass: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static <T> void write(BufferedWriter bufferedWriter, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        try {
            DatabaseTableConfigLoader.writeConfig(bufferedWriter, databaseTableConfig);
            return;
        }
        catch (IOException iOException) {
            throw SqlExceptionUtil.create("Could not write config to writer", iOException);
        }
    }

    private static <T> void writeConfig(BufferedWriter bufferedWriter, DatabaseTableConfig<T> databaseTableConfig) throws IOException, SQLException {
        bufferedWriter.append(CONFIG_FILE_START_MARKER);
        bufferedWriter.newLine();
        if (databaseTableConfig.getDataClass() != null) {
            bufferedWriter.append(FIELD_NAME_DATA_CLASS).append('=').append(databaseTableConfig.getDataClass().getName());
            bufferedWriter.newLine();
        }
        if (databaseTableConfig.getTableName() != null) {
            bufferedWriter.append(FIELD_NAME_TABLE_NAME).append('=').append(databaseTableConfig.getTableName());
            bufferedWriter.newLine();
        }
        bufferedWriter.append(CONFIG_FILE_FIELDS_START);
        bufferedWriter.newLine();
        if (databaseTableConfig.getFieldConfigs() != null) {
            Iterator<DatabaseFieldConfig> iterator = databaseTableConfig.getFieldConfigs().iterator();
            while (iterator.hasNext()) {
                DatabaseFieldConfigLoader.write(bufferedWriter, iterator.next(), databaseTableConfig.getTableName());
            }
        }
        bufferedWriter.append(CONFIG_FILE_FIELDS_END);
        bufferedWriter.newLine();
        bufferedWriter.append(CONFIG_FILE_END_MARKER);
        bufferedWriter.newLine();
    }
}

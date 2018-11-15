/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TableUtils {
    private static Logger logger = LoggerFactory.getLogger(TableUtils.class);
    private static final FieldType[] noFieldTypes = new FieldType[0];

    private TableUtils() {
    }

    private static <T, ID> void addCreateIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list, boolean bl, boolean bl2) {
        CharSequence charSequence;
        Object object = new HashMap<CharSequence, ArrayList<String>>();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            charSequence = bl2 ? fieldType.getUniqueIndexName() : fieldType.getIndexName();
            if (charSequence == null) continue;
            List iterator = (List)object.get(charSequence);
            ArrayList<String> arrayList = iterator;
            if (iterator == null) {
                arrayList = new ArrayList<String>();
                object.put(charSequence, arrayList);
            }
            arrayList.add(fieldType.getColumnName());
        }
        charSequence = new StringBuilder(128);
        for (Map.Entry entry : object.entrySet()) {
            logger.info("creating index '{}' for table '{}", entry.getKey(), (Object)tableInfo.getTableName());
            charSequence.append("CREATE ");
            if (bl2) {
                charSequence.append("UNIQUE ");
            }
            charSequence.append("INDEX ");
            if (bl && databaseType.isCreateIndexIfNotExistsSupported()) {
                charSequence.append("IF NOT EXISTS ");
            }
            databaseType.appendEscapedEntityName((StringBuilder)charSequence, (String)entry.getKey());
            charSequence.append(" ON ");
            databaseType.appendEscapedEntityName((StringBuilder)charSequence, tableInfo.getTableName());
            charSequence.append(" ( ");
            Iterator iterator = ((List)entry.getValue()).iterator();
            int n = 1;
            while (iterator.hasNext()) {
                object = (String)iterator.next();
                if (n != 0) {
                    n = 0;
                } else {
                    charSequence.append(", ");
                }
                databaseType.appendEscapedEntityName((StringBuilder)charSequence, (String)object);
            }
            charSequence.append(" )");
            list.add(((StringBuilder)charSequence).toString());
            charSequence.setLength(0);
        }
    }

    private static <T, ID> List<String> addCreateTableStatements(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean bl) throws SQLException {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        TableUtils.addCreateTableStatements(connectionSource.getDatabaseType(), tableInfo, arrayList, arrayList2, bl);
        return arrayList;
    }

    private static <T, ID> void addCreateTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list, List<String> object, boolean bl) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder(256);
        stringBuilder.append("CREATE TABLE ");
        if (bl && databaseType.isCreateIfNotExistsSupported()) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        databaseType.appendEscapedEntityName(stringBuilder, tableInfo.getTableName());
        stringBuilder.append(" (");
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        ArrayList<String> arrayList3 = new ArrayList<String>();
        FieldType[] object22 = tableInfo.getFieldTypes();
        boolean bl2 = false;
        int n = object22.length;
        boolean bl3 = true;
        for (int i = 0; i < n; ++i) {
            FieldType fieldType = object22[i];
            if (fieldType.isForeignCollection()) continue;
            if (bl3) {
                bl3 = bl2;
            } else {
                stringBuilder.append(", ");
            }
            String string = fieldType.getColumnDefinition();
            if (string == null) {
                databaseType.appendColumnArg(tableInfo.getTableName(), stringBuilder, fieldType, (List<String>)arrayList, (List<String>)arrayList2, (List<String>)arrayList3, (List<String>)((Object)object));
                continue;
            }
            databaseType.appendEscapedEntityName(stringBuilder, fieldType.getColumnName());
            stringBuilder.append(' ');
            stringBuilder.append(string);
            stringBuilder.append(' ');
        }
        databaseType.addPrimaryKeySql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, (List<String>)((Object)object));
        databaseType.addUniqueComboSql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, (List<String>)((Object)object));
        for (String string : arrayList) {
            stringBuilder.append(", ");
            stringBuilder.append(string);
        }
        stringBuilder.append(") ");
        databaseType.appendCreateTableSuffix(stringBuilder);
        list.addAll(arrayList2);
        list.add(stringBuilder.toString());
        list.addAll(arrayList3);
        TableUtils.addCreateIndexStatements(databaseType, tableInfo, list, bl, bl2);
        TableUtils.addCreateIndexStatements(databaseType, tableInfo, list, bl, true);
    }

    private static <T, ID> void addDropIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list) {
        Object object = new HashSet();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String object2;
            String string = fieldType.getIndexName();
            if (string != null) {
                object.add(string);
            }
            if ((object2 = fieldType.getUniqueIndexName()) == null) continue;
            object.add(object2);
        }
        StringBuilder stringBuilder = new StringBuilder(48);
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            logger.info("dropping index '{}' for table '{}", (Object)string, (Object)tableInfo.getTableName());
            stringBuilder.append("DROP INDEX ");
            databaseType.appendEscapedEntityName(stringBuilder, string);
            list.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
    }

    private static <T, ID> void addDropTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list) {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        Object object = tableInfo.getFieldTypes();
        int n = ((FieldType[])object).length;
        for (int i = 0; i < n; ++i) {
            databaseType.dropColumnArg(object[i], arrayList, arrayList2);
        }
        object = new StringBuilder(64);
        object.append("DROP TABLE ");
        databaseType.appendEscapedEntityName((StringBuilder)object, tableInfo.getTableName());
        object.append(' ');
        list.addAll(arrayList);
        list.add(object.toString());
        list.addAll(arrayList2);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        return TableUtils.clearTable(connectionSource, databaseTableConfig.getTableName());
    }

    public static <T> int clearTable(ConnectionSource connectionSource, Class<T> object) throws SQLException {
        String string = DatabaseTableConfig.extractTableName(object);
        object = string;
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            object = string.toUpperCase();
        }
        return TableUtils.clearTable(connectionSource, (String)object);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T> int clearTable(ConnectionSource connectionSource, String string) throws SQLException {
        DatabaseConnection databaseConnection;
        Object object;
        void var1_4;
        block8 : {
            int n;
            block7 : {
                object = connectionSource.getDatabaseType();
                Object object2 = new StringBuilder(48);
                if (object.isTruncateSupported()) {
                    object2.append("TRUNCATE TABLE ");
                } else {
                    object2.append("DELETE FROM ");
                }
                object.appendEscapedEntityName((StringBuilder)object2, string);
                object2 = object2.toString();
                logger.info("clearing table '{}' with '{}", (Object)string, object2);
                object = null;
                databaseConnection = connectionSource.getReadWriteConnection();
                object2 = databaseConnection.compileStatement((String)object2, StatementBuilder.StatementType.EXECUTE, noFieldTypes, -1);
                try {
                    n = object2.runExecute();
                    if (object2 == null) break block7;
                }
                catch (Throwable throwable) {
                    object = object2;
                }
                object2.close();
            }
            connectionSource.releaseConnection(databaseConnection);
            return n;
            break block8;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (object != null) {
            object.close();
        }
        connectionSource.releaseConnection(databaseConnection);
        throw var1_4;
    }

    public static <T> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        return TableUtils.createTable(connectionSource, databaseTableConfig, false);
    }

    private static <T, ID> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig, boolean bl) throws SQLException {
        Object d = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.doCreateTable(connectionSource, ((BaseDaoImpl)d).getTableInfo(), bl);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return TableUtils.doCreateTable(connectionSource, new TableInfo(connectionSource.getDatabaseType(), null, databaseTableConfig), bl);
    }

    public static <T> int createTable(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return TableUtils.createTable(connectionSource, class_, false);
    }

    private static <T, ID> int createTable(ConnectionSource connectionSource, Class<T> class_, boolean bl) throws SQLException {
        Object d = DaoManager.createDao(connectionSource, class_);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.doCreateTable(connectionSource, ((BaseDaoImpl)d).getTableInfo(), bl);
        }
        return TableUtils.doCreateTable(connectionSource, new TableInfo(connectionSource, null, class_), bl);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        return TableUtils.createTable(connectionSource, databaseTableConfig, true);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return TableUtils.createTable(connectionSource, class_, true);
    }

    private static <T, ID> int doCreateTable(ConnectionSource connectionSource, TableInfo<T, ID> object, boolean bl) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        logger.info("creating table '{}'", (Object)object.getTableName());
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        TableUtils.addCreateTableStatements(databaseType, object, arrayList, arrayList2, bl);
        object = connectionSource.getReadWriteConnection();
        try {
            int n = TableUtils.doStatements((DatabaseConnection)object, "create", arrayList, false, databaseType.isCreateTableReturnsNegative(), databaseType.isCreateTableReturnsZero());
            int n2 = TableUtils.doCreateTestQueries((DatabaseConnection)object, databaseType, arrayList2);
            return n + n2;
        }
        finally {
            connectionSource.releaseConnection((DatabaseConnection)object);
        }
    }

    /*
     * Exception decompiling
     */
    private static int doCreateTestQueries(DatabaseConnection var0, DatabaseType var1_4, List<String> var2_6) throws SQLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[WHILELOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    private static <T, ID> int doDropTable(DatabaseType databaseType, ConnectionSource connectionSource, TableInfo<T, ID> object, boolean bl) throws SQLException {
        logger.info("dropping table '{}'", (Object)object.getTableName());
        ArrayList<String> arrayList = new ArrayList<String>();
        TableUtils.addDropIndexStatements(databaseType, object, arrayList);
        TableUtils.addDropTableStatements(databaseType, object, arrayList);
        object = connectionSource.getReadWriteConnection();
        try {
            int n = TableUtils.doStatements((DatabaseConnection)object, "drop", arrayList, bl, databaseType.isCreateTableReturnsNegative(), false);
            return n;
        }
        finally {
            connectionSource.releaseConnection((DatabaseConnection)object);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static int doStatements(DatabaseConnection var0, String var1_4, Collection<String> var2_5, boolean var3_8, boolean var4_9, boolean var5_10) throws SQLException {
        block19 : {
            block21 : {
                var12_11 = var2_5.iterator();
                var7_12 = 0;
                while (var12_11.hasNext() != false) {
                    block17 : {
                        block22 : {
                            block18 : {
                                block20 : {
                                    var11_18 = (String)var12_11.next();
                                    var10_16 = null;
                                    var2_5 = null;
                                    var9_15 = var0.compileStatement(var11_18, StatementBuilder.StatementType.EXECUTE, TableUtils.noFieldTypes, -1);
                                    try {
                                        var6_13 = var9_15.runExecute();
                                        try {
                                            TableUtils.logger.info("executed {} table statement changed {} rows: {}", (Object)var1_4, (Object)var6_13, (Object)var11_18);
                                            var8_14 = var6_13;
                                            ** if (var9_15 == null) goto lbl-1000
                                        }
                                        catch (SQLException var10_17) {
                                            break block18;
                                        }
lbl-1000: // 1 sources:
                                        {
                                            var9_15.close();
                                            var8_14 = var6_13;
                                        }
lbl-1000: // 2 sources:
                                        {
                                            break block17;
                                        }
                                    }
                                    catch (Throwable var0_1) {
                                        var2_5 = var9_15;
                                        break block19;
                                    }
                                    catch (SQLException var2_6) {
                                        break block20;
                                    }
                                    catch (Throwable var0_2) {
                                        break block19;
                                    }
                                    catch (SQLException var2_7) {
                                        var9_15 = var10_16;
                                    }
                                }
                                var6_13 = 0;
                                var10_16 = var2_5;
                            }
                            if (!var3_8) break block21;
                            var2_5 = var9_15;
                            {
                                TableUtils.logger.info("ignoring {} error '{}' for statement: {}", (Object)var1_4, var10_16, (Object)var11_18);
                                if (var9_15 == null) break block22;
                            }
                            var9_15.close();
                        }
                        var8_14 = var6_13;
                    }
                    if (var8_14 < 0) {
                        if (!var4_9) {
                            var0 = new StringBuilder();
                            var0.append("SQL statement ");
                            var0.append(var11_18);
                            var0.append(" updated ");
                            var0.append(var8_14);
                            var0.append(" rows, we were expecting >= 0");
                            throw new SQLException(var0.toString());
                        }
                    } else if (var8_14 > 0 && var5_10) {
                        var0 = new StringBuilder();
                        var0.append("SQL statement updated ");
                        var0.append(var8_14);
                        var0.append(" rows, we were expecting == 0: ");
                        var0.append(var11_18);
                        throw new SQLException(var0.toString());
                    }
                    ++var7_12;
                }
                return var7_12;
            }
            var2_5 = var9_15;
            {
                var0 = new StringBuilder();
                var2_5 = var9_15;
                var0.append("SQL statement failed: ");
                var2_5 = var9_15;
                var0.append(var11_18);
                var2_5 = var9_15;
                throw SqlExceptionUtil.create(var0.toString(), (Throwable)var10_16);
            }
        }
        if (var2_5 == null) throw var0_3;
        var2_5.close();
        throw var0_3;
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig, boolean bl) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        Object d = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.doDropTable(databaseType, connectionSource, ((BaseDaoImpl)d).getTableInfo(), bl);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return TableUtils.doDropTable(databaseType, connectionSource, new TableInfo(databaseType, null, databaseTableConfig), bl);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, Class<T> class_, boolean bl) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        Object d = DaoManager.createDao(connectionSource, class_);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.doDropTable(databaseType, connectionSource, ((BaseDaoImpl)d).getTableInfo(), bl);
        }
        return TableUtils.doDropTable(databaseType, connectionSource, new TableInfo(connectionSource, null, class_), bl);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        Object d = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.addCreateTableStatements(connectionSource, ((BaseDaoImpl)d).getTableInfo(), false);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return TableUtils.addCreateTableStatements(connectionSource, new TableInfo(connectionSource.getDatabaseType(), null, databaseTableConfig), false);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        Object d = DaoManager.createDao(connectionSource, class_);
        if (d instanceof BaseDaoImpl) {
            return TableUtils.addCreateTableStatements(connectionSource, ((BaseDaoImpl)d).getTableInfo(), false);
        }
        return TableUtils.addCreateTableStatements(connectionSource, new TableInfo(connectionSource, null, class_), false);
    }
}

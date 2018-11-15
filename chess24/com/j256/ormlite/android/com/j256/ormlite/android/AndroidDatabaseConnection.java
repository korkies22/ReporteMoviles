/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteStatement
 */
package com.j256.ormlite.android;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.j256.ormlite.android.AndroidCompiledStatement;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.misc.VersionUtils;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.Savepoint;

public class AndroidDatabaseConnection
implements DatabaseConnection {
    private static final String ANDROID_VERSION = "VERSION__4.48__";
    private static final String[] NO_STRING_ARGS;
    private static Logger logger;
    private final boolean cancelQueriesEnabled;
    private final SQLiteDatabase db;
    private final boolean readWrite;

    static {
        logger = LoggerFactory.getLogger(AndroidDatabaseConnection.class);
        NO_STRING_ARGS = new String[0];
        VersionUtils.checkCoreVersusAndroidVersions(ANDROID_VERSION);
    }

    public AndroidDatabaseConnection(SQLiteDatabase sQLiteDatabase, boolean bl) {
        this(sQLiteDatabase, bl, false);
    }

    public AndroidDatabaseConnection(SQLiteDatabase sQLiteDatabase, boolean bl, boolean bl2) {
        this.db = sQLiteDatabase;
        this.readWrite = bl;
        this.cancelQueriesEnabled = bl2;
        logger.trace("{}: db {} opened, read-write = {}", this, (Object)sQLiteDatabase, (Object)bl);
    }

    private void bindArgs(SQLiteStatement object, Object[] arrobject, FieldType[] arrfieldType) throws java.sql.SQLException {
        if (arrobject == null) {
            return;
        }
        block7 : for (int i = 0; i < arrobject.length; ++i) {
            Object object2 = arrobject[i];
            if (object2 == null) {
                object.bindNull(i + 1);
                continue;
            }
            SqlType sqlType = arrfieldType[i].getSqlType();
            switch (.$SwitchMap$com$j256$ormlite$field$SqlType[sqlType.ordinal()]) {
                default: {
                    object = new StringBuilder();
                    object.append("Unknown sql argument type: ");
                    object.append((Object)sqlType);
                    throw new java.sql.SQLException(object.toString());
                }
                case 13: 
                case 14: 
                case 15: {
                    object = new StringBuilder();
                    object.append("Invalid Android type: ");
                    object.append((Object)sqlType);
                    throw new java.sql.SQLException(object.toString());
                }
                case 11: 
                case 12: {
                    object.bindBlob(i + 1, (byte[])object2);
                    continue block7;
                }
                case 9: 
                case 10: {
                    object.bindDouble(i + 1, ((Number)object2).doubleValue());
                    continue block7;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: {
                    object.bindLong(i + 1, ((Number)object2).longValue());
                    continue block7;
                }
                case 1: 
                case 2: 
                case 3: {
                    object.bindString(i + 1, object2.toString());
                }
            }
        }
    }

    private String[] toStrings(Object[] arrobject) {
        if (arrobject != null) {
            if (arrobject.length == 0) {
                return null;
            }
            String[] arrstring = new String[arrobject.length];
            for (int i = 0; i < arrobject.length; ++i) {
                Object object = arrobject[i];
                arrstring[i] = object == null ? null : object.toString();
            }
            return arrstring;
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int update(String string, Object[] sQLiteStatement, FieldType[] sQLiteStatement2, String charSequence) throws java.sql.SQLException {
        SQLiteStatement sQLiteStatement3;
        void var1_7;
        block17 : {
            block18 : {
                Object var11_17;
                int n;
                block21 : {
                    int n2;
                    block19 : {
                        SQLiteStatement sQLiteStatement4;
                        void var1_4;
                        block16 : {
                            int n3;
                            long l;
                            block20 : {
                                block15 : {
                                    sQLiteStatement3 = null;
                                    var11_17 = null;
                                    Object var12_18 = null;
                                    sQLiteStatement4 = this.db.compileStatement(string);
                                    this.bindArgs(sQLiteStatement4, (Object[])sQLiteStatement, (FieldType[])sQLiteStatement2);
                                    sQLiteStatement4.execute();
                                    if (sQLiteStatement4 == null) break block15;
                                    sQLiteStatement4.close();
                                    sQLiteStatement = var12_18;
                                    break block20;
                                }
                                sQLiteStatement = sQLiteStatement4;
                            }
                            sQLiteStatement2 = this.db.compileStatement("SELECT CHANGES()");
                            try {
                                l = sQLiteStatement2.simpleQueryForLong();
                            }
                            catch (Throwable throwable) {
                                sQLiteStatement = sQLiteStatement2;
                                break block16;
                            }
                            n = n3 = (int)l;
                            if (sQLiteStatement2 != null) {
                                sQLiteStatement2.close();
                                n = n3;
                            }
                            break block21;
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                        if (sQLiteStatement == null) throw var1_4;
                        sQLiteStatement.close();
                        throw var1_4;
                        catch (Throwable throwable) {
                            sQLiteStatement3 = sQLiteStatement4;
                            break block17;
                        }
                        catch (SQLException sQLException) {
                            sQLiteStatement = sQLiteStatement4;
                            break block18;
                        }
                        catch (SQLException sQLException) {
                            break block19;
                        }
                        catch (SQLException sQLException) {}
                        sQLiteStatement = sQLiteStatement2;
                    }
                    n = n2 = 1;
                    if (sQLiteStatement != null) {
                        sQLiteStatement.close();
                        n = n2;
                    }
                }
                logger.trace("{} statement is compiled and executed, changed {}: {}", charSequence, (Object)n, (Object)string);
                return n;
                catch (Throwable throwable) {
                    break block17;
                }
                catch (SQLException sQLException) {
                    sQLiteStatement = var11_17;
                }
            }
            sQLiteStatement3 = sQLiteStatement;
            {
                void var3_13;
                charSequence = new StringBuilder();
                sQLiteStatement3 = sQLiteStatement;
                charSequence.append("updating database failed: ");
                sQLiteStatement3 = sQLiteStatement;
                charSequence.append(string);
                sQLiteStatement3 = sQLiteStatement;
                throw SqlExceptionUtil.create(charSequence.toString(), (Throwable)var3_13);
            }
        }
        if (sQLiteStatement3 == null) throw var1_7;
        sQLiteStatement3.close();
        throw var1_7;
    }

    @Override
    public void close() throws java.sql.SQLException {
        try {
            this.db.close();
            logger.trace("{}: db {} closed", this, (Object)this.db);
            return;
        }
        catch (SQLException sQLException) {
            throw SqlExceptionUtil.create("problems closing the database connection", (Throwable)sQLException);
        }
    }

    @Override
    public void closeQuietly() {
        try {
            this.close();
            return;
        }
        catch (java.sql.SQLException sQLException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void commit(Savepoint savepoint) throws java.sql.SQLException {
        try {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
            if (savepoint == null) {
                logger.trace("{}: transaction is successfuly ended", this);
                return;
            }
            logger.trace("{}: transaction {} is successfuly ended", this, (Object)savepoint.getSavepointName());
            return;
        }
        catch (SQLException sQLException) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems commiting transaction", (Throwable)sQLException);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problems commiting transaction ");
            stringBuilder.append(savepoint.getSavepointName());
            throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
        }
    }

    @Override
    public CompiledStatement compileStatement(String string, StatementBuilder.StatementType object, FieldType[] arrfieldType, int n) {
        object = new AndroidCompiledStatement(string, this.db, (StatementBuilder.StatementType)((Object)object), this.cancelQueriesEnabled);
        logger.trace("{}: compiled statement got {}: {}", this, object, (Object)string);
        return object;
    }

    @Override
    public int delete(String string, Object[] arrobject, FieldType[] arrfieldType) throws java.sql.SQLException {
        return this.update(string, arrobject, arrfieldType, "deleted");
    }

    @Override
    public int executeStatement(String string, int n) throws java.sql.SQLException {
        return AndroidCompiledStatement.execSql(this.db, string, string, NO_STRING_ARGS);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int insert(String string, Object[] sQLiteStatement, FieldType[] arrfieldType, GeneratedKeyHolder object) throws java.sql.SQLException {
        void var1_4;
        SQLiteStatement sQLiteStatement2;
        block8 : {
            block9 : {
                Object var9_11 = null;
                sQLiteStatement2 = null;
                SQLiteStatement sQLiteStatement3 = this.db.compileStatement(string);
                try {
                    this.bindArgs(sQLiteStatement3, (Object[])sQLiteStatement, arrfieldType);
                    long l = sQLiteStatement3.executeInsert();
                    if (object != null) {
                        object.addKey(l);
                    }
                    logger.trace("{}: insert statement is compiled and executed, changed {}: {}", this, (Object)1, (Object)string);
                    if (sQLiteStatement3 == null) return 1;
                }
                catch (Throwable throwable) {
                    sQLiteStatement2 = sQLiteStatement3;
                    break block8;
                }
                catch (SQLException sQLException) {
                    sQLiteStatement = sQLiteStatement3;
                    break block9;
                }
                sQLiteStatement3.close();
                return 1;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (SQLException sQLException) {
                    sQLiteStatement = var9_11;
                }
            }
            sQLiteStatement2 = sQLiteStatement;
            {
                void var3_9;
                object = new StringBuilder();
                sQLiteStatement2 = sQLiteStatement;
                object.append("inserting to database failed: ");
                sQLiteStatement2 = sQLiteStatement;
                object.append(string);
                sQLiteStatement2 = sQLiteStatement;
                throw SqlExceptionUtil.create(object.toString(), (Throwable)var3_9);
            }
        }
        if (sQLiteStatement2 == null) throw var1_4;
        sQLiteStatement2.close();
        throw var1_4;
    }

    @Override
    public boolean isAutoCommit() throws java.sql.SQLException {
        try {
            boolean bl = this.db.inTransaction();
            logger.trace("{}: in transaction is {}", this, (Object)bl);
            return bl ^ true;
        }
        catch (SQLException sQLException) {
            throw SqlExceptionUtil.create("problems getting auto-commit from database", (Throwable)sQLException);
        }
    }

    @Override
    public boolean isAutoCommitSupported() {
        return true;
    }

    @Override
    public boolean isClosed() throws java.sql.SQLException {
        try {
            boolean bl = this.db.isOpen();
            logger.trace("{}: db {} isOpen returned {}", this, (Object)this.db, (Object)bl);
            return bl ^ true;
        }
        catch (SQLException sQLException) {
            throw SqlExceptionUtil.create("problems detecting if the database is closed", (Throwable)sQLException);
        }
    }

    public boolean isReadWrite() {
        return this.readWrite;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isTableExists(String string) {
        SQLiteDatabase sQLiteDatabase = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '");
        stringBuilder.append(string);
        stringBuilder.append("'");
        sQLiteDatabase = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        try {
            boolean bl = sQLiteDatabase.getCount() > 0;
            logger.trace("{}: isTableExists '{}' returned {}", this, (Object)string, (Object)bl);
            return bl;
        }
        finally {
            sQLiteDatabase.close();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public long queryForLong(String string) throws java.sql.SQLException {
        void var1_4;
        SQLiteStatement sQLiteStatement;
        block8 : {
            SQLiteStatement sQLiteStatement2;
            block7 : {
                long l;
                sQLiteStatement = sQLiteStatement2 = this.db.compileStatement(string);
                try {
                    l = sQLiteStatement2.simpleQueryForLong();
                    sQLiteStatement = sQLiteStatement2;
                    logger.trace("{}: query for long simple query returned {}: {}", this, (Object)l, (Object)string);
                    if (sQLiteStatement2 == null) return l;
                }
                catch (SQLException sQLException) {
                    break block7;
                }
                sQLiteStatement2.close();
                return l;
                catch (Throwable throwable) {
                    sQLiteStatement = null;
                    break block8;
                }
                catch (SQLException sQLException) {
                    sQLiteStatement2 = null;
                }
            }
            sQLiteStatement = sQLiteStatement2;
            try {
                void var6_10;
                StringBuilder stringBuilder = new StringBuilder();
                sQLiteStatement = sQLiteStatement2;
                stringBuilder.append("queryForLong from database failed: ");
                sQLiteStatement = sQLiteStatement2;
                stringBuilder.append(string);
                sQLiteStatement = sQLiteStatement2;
                throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)var6_10);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (sQLiteStatement == null) throw var1_4;
        sQLiteStatement.close();
        throw var1_4;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public long queryForLong(String string, Object[] cursor, FieldType[] cursor2) throws java.sql.SQLException {
        void var1_4;
        block10 : {
            block9 : {
                long l;
                cursor = cursor2 = this.db.rawQuery(string, this.toStrings((Object[])cursor));
                try {
                    AndroidDatabaseResults androidDatabaseResults = new AndroidDatabaseResults(cursor2, null);
                    cursor = cursor2;
                    if (androidDatabaseResults.first()) {
                        cursor = cursor2;
                        l = androidDatabaseResults.getLong(0);
                    } else {
                        l = 0L;
                    }
                    cursor = cursor2;
                    logger.trace("{}: query for long raw query returned {}: {}", this, (Object)l, (Object)string);
                    if (cursor2 == null) return l;
                }
                catch (SQLException sQLException) {
                    break block9;
                }
                cursor2.close();
                return l;
                catch (Throwable throwable) {
                    cursor = null;
                    break block10;
                }
                catch (SQLException sQLException) {
                    cursor2 = null;
                }
            }
            cursor = cursor2;
            try {
                void var6_10;
                StringBuilder stringBuilder = new StringBuilder();
                cursor = cursor2;
                stringBuilder.append("queryForLong from database failed: ");
                cursor = cursor2;
                stringBuilder.append(string);
                cursor = cursor2;
                throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)var6_10);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (cursor == null) throw var1_4;
        cursor.close();
        throw var1_4;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public <T> Object queryForOne(String string, Object[] cursor, FieldType[] cursor2, GenericRowMapper<T> object, ObjectCache object2) throws java.sql.SQLException {
        void var1_4;
        block11 : {
            block10 : {
                block9 : {
                    block8 : {
                        cursor = cursor2 = this.db.rawQuery(string, this.toStrings((Object[])cursor));
                        object2 = new AndroidDatabaseResults(cursor2, (ObjectCache)object2);
                        cursor = cursor2;
                        logger.trace("{}: queried for one result: {}", this, (Object)string);
                        cursor = cursor2;
                        boolean bl = object2.first();
                        if (bl) break block8;
                        if (cursor2 == null) return null;
                        cursor2.close();
                        return null;
                    }
                    cursor = cursor2;
                    try {
                        object = object.mapRow((DatabaseResults)object2);
                        cursor = cursor2;
                        if (!object2.next()) break block9;
                        cursor = cursor2;
                        object = MORE_THAN_ONE;
                        if (cursor2 == null) return object;
                    }
                    catch (SQLException sQLException) {
                        break block10;
                    }
                    cursor2.close();
                    return object;
                }
                if (cursor2 == null) return object;
                cursor2.close();
                return object;
                catch (Throwable throwable) {
                    cursor = null;
                    break block11;
                }
                catch (SQLException sQLException) {
                    cursor2 = null;
                }
            }
            cursor = cursor2;
            try {
                void var4_10;
                object2 = new StringBuilder();
                cursor = cursor2;
                object2.append("queryForOne from database failed: ");
                cursor = cursor2;
                object2.append(string);
                cursor = cursor2;
                throw SqlExceptionUtil.create(object2.toString(), (Throwable)var4_10);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (cursor == null) throw var1_4;
        cursor.close();
        throw var1_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void rollback(Savepoint savepoint) throws java.sql.SQLException {
        try {
            this.db.endTransaction();
            if (savepoint == null) {
                logger.trace("{}: transaction is ended, unsuccessfuly", this);
                return;
            }
            logger.trace("{}: transaction {} is ended, unsuccessfuly", this, (Object)savepoint.getSavepointName());
            return;
        }
        catch (SQLException sQLException) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems rolling back transaction", (Throwable)sQLException);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problems rolling back transaction ");
            stringBuilder.append(savepoint.getSavepointName());
            throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
        }
    }

    @Override
    public void setAutoCommit(boolean bl) {
        if (bl) {
            if (this.db.inTransaction()) {
                this.db.setTransactionSuccessful();
                this.db.endTransaction();
                return;
            }
        } else if (!this.db.inTransaction()) {
            this.db.beginTransaction();
        }
    }

    @Override
    public Savepoint setSavePoint(String string) throws java.sql.SQLException {
        try {
            this.db.beginTransaction();
            logger.trace("{}: save-point set with name {}", this, (Object)string);
            OurSavePoint ourSavePoint = new OurSavePoint(string);
            return ourSavePoint;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problems beginning transaction ");
            stringBuilder.append(string);
            throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(super.hashCode()));
        return stringBuilder.toString();
    }

    @Override
    public int update(String string, Object[] arrobject, FieldType[] arrfieldType) throws java.sql.SQLException {
        return this.update(string, arrobject, arrfieldType, "updated");
    }

    private static class OurSavePoint
    implements Savepoint {
        private String name;

        public OurSavePoint(String string) {
            this.name = string;
        }

        @Override
        public int getSavepointId() {
            return 0;
        }

        @Override
        public String getSavepointName() {
            return this.name;
        }
    }

}

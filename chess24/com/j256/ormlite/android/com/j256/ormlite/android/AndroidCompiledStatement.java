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
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.android.compat.ApiCompatibilityUtils;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseResults;
import java.util.ArrayList;
import java.util.List;

public class AndroidCompiledStatement
implements CompiledStatement {
    private static final String[] NO_STRING_ARGS;
    private static final ApiCompatibility apiCompatibility;
    private static Logger logger;
    private List<Object> args;
    private final boolean cancelQueriesEnabled;
    private ApiCompatibility.CancellationHook cancellationHook;
    private Cursor cursor;
    private final SQLiteDatabase db;
    private Integer max;
    private final String sql;
    private final StatementBuilder.StatementType type;

    static {
        logger = LoggerFactory.getLogger(AndroidCompiledStatement.class);
        NO_STRING_ARGS = new String[0];
        apiCompatibility = ApiCompatibilityUtils.getCompatibility();
    }

    public AndroidCompiledStatement(String string, SQLiteDatabase sQLiteDatabase, StatementBuilder.StatementType statementType, boolean bl) {
        this.sql = string;
        this.db = sQLiteDatabase;
        this.type = statementType;
        this.cancelQueriesEnabled = bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static int execSql(SQLiteDatabase object, String string, String string2, Object[] object2) throws java.sql.SQLException {
        void var2_12;
        int n;
        void var1_8;
        block12 : {
            int n2;
            void var0_4;
            block11 : {
                Object var8_15;
                void var0_3;
                void var1_11;
                block10 : {
                    StringBuilder stringBuilder;
                    long l;
                    try {
                        object.execSQL((String)var2_12, (Object[])stringBuilder);
                        var8_15 = null;
                        stringBuilder = null;
                    }
                    catch (SQLException sQLException) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Problems executing ");
                        stringBuilder.append((String)var1_8);
                        stringBuilder.append(" Android statement: ");
                        stringBuilder.append((String)var2_12);
                        throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
                    }
                    SQLiteStatement sQLiteStatement = object.compileStatement("SELECT CHANGES()");
                    try {
                        l = sQLiteStatement.simpleQueryForLong();
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    n = (int)l;
                    if (sQLiteStatement != null) {
                        sQLiteStatement.close();
                    }
                    break block12;
                    catch (Throwable throwable) {
                        StringBuilder stringBuilder2 = stringBuilder;
                    }
                }
                if (var0_3 == null) throw var1_11;
                var0_3.close();
                throw var1_11;
                catch (SQLException sQLException) {
                    Object var0_7 = var8_15;
                    break block11;
                }
                catch (SQLException sQLException) {}
            }
            n = n2 = 1;
            if (var0_4 != null) {
                var0_4.close();
                n = n2;
            }
        }
        logger.trace("executing statement {} changed {} rows: {}", var1_8, (Object)n, (Object)var2_12);
        return n;
    }

    private Object[] getArgArray() {
        if (this.args == null) {
            return NO_STRING_ARGS;
        }
        return this.args.toArray(new Object[this.args.size()]);
    }

    private String[] getStringArray() {
        if (this.args == null) {
            return NO_STRING_ARGS;
        }
        return this.args.toArray(new String[this.args.size()]);
    }

    private void isInPrep() throws java.sql.SQLException {
        if (this.cursor != null) {
            throw new java.sql.SQLException("Query already run. Cannot add argument values.");
        }
    }

    @Override
    public void cancel() {
        if (this.cancellationHook != null) {
            this.cancellationHook.cancel();
        }
    }

    @Override
    public void close() throws java.sql.SQLException {
        if (this.cursor != null) {
            try {
                this.cursor.close();
            }
            catch (SQLException sQLException) {
                throw SqlExceptionUtil.create("Problems closing Android cursor", (Throwable)sQLException);
            }
        }
        this.cancellationHook = null;
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

    @Override
    public int getColumnCount() throws java.sql.SQLException {
        return this.getCursor().getColumnCount();
    }

    @Override
    public String getColumnName(int n) throws java.sql.SQLException {
        return this.getCursor().getColumnName(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Cursor getCursor() throws java.sql.SQLException {
        String string;
        if (this.cursor != null) return this.cursor;
        String string2 = string = null;
        try {
            if (this.max == null) {
                string2 = string;
                string2 = string = this.sql;
            } else {
                string2 = string;
                StringBuilder stringBuilder = new StringBuilder();
                string2 = string;
                stringBuilder.append(this.sql);
                string2 = string;
                stringBuilder.append(" ");
                string2 = string;
                stringBuilder.append(this.max);
                string2 = string;
                string2 = string = stringBuilder.toString();
            }
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems executing Android query: ");
            stringBuilder.append(string2);
            throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
        }
        string = string2;
        string2 = string;
        if (this.cancelQueriesEnabled) {
            string2 = string;
            this.cancellationHook = apiCompatibility.createCancellationHook();
        }
        string2 = string;
        this.cursor = apiCompatibility.rawQuery(this.db, string, this.getStringArray(), this.cancellationHook);
        string2 = string;
        this.cursor.moveToFirst();
        string2 = string;
        logger.trace("{}: started rawQuery cursor for: {}", this, (Object)string);
        return this.cursor;
    }

    @Override
    public int runExecute() throws java.sql.SQLException {
        if (!this.type.isOkForExecute()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot call execute on a ");
            stringBuilder.append((Object)this.type);
            stringBuilder.append(" statement");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return AndroidCompiledStatement.execSql(this.db, "runExecute", this.sql, this.getArgArray());
    }

    @Override
    public DatabaseResults runQuery(ObjectCache object) throws java.sql.SQLException {
        if (!this.type.isOkForQuery()) {
            object = new StringBuilder();
            object.append("Cannot call query on a ");
            object.append((Object)this.type);
            object.append(" statement");
            throw new IllegalArgumentException(object.toString());
        }
        return new AndroidDatabaseResults(this.getCursor(), (ObjectCache)object);
    }

    @Override
    public int runUpdate() throws java.sql.SQLException {
        CharSequence charSequence;
        if (!this.type.isOkForUpdate()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot call update on a ");
            stringBuilder.append((Object)this.type);
            stringBuilder.append(" statement");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.max == null) {
            charSequence = this.sql;
        } else {
            charSequence = new StringBuilder();
            charSequence.append(this.sql);
            charSequence.append(" ");
            charSequence.append(this.max);
            charSequence = charSequence.toString();
        }
        return AndroidCompiledStatement.execSql(this.db, "runUpdate", (String)charSequence, this.getArgArray());
    }

    @Override
    public void setMaxRows(int n) throws java.sql.SQLException {
        this.isInPrep();
        this.max = n;
    }

    @Override
    public void setObject(int n, Object object, SqlType sqlType) throws java.sql.SQLException {
        this.isInPrep();
        if (this.args == null) {
            this.args = new ArrayList<Object>();
        }
        if (object == null) {
            this.args.add(n, null);
            return;
        }
        switch (.$SwitchMap$com$j256$ormlite$field$SqlType[sqlType.ordinal()]) {
            default: {
                object = new StringBuilder();
                object.append("Unknown sql argument type: ");
                object.append((Object)sqlType);
                throw new java.sql.SQLException(object.toString());
            }
            case 14: 
            case 15: {
                object = new StringBuilder();
                object.append("Invalid Android type: ");
                object.append((Object)sqlType);
                throw new java.sql.SQLException(object.toString());
            }
            case 12: 
            case 13: {
                this.args.add(n, object);
                return;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
        }
        this.args.add(n, object.toString());
    }

    @Override
    public void setQueryTimeout(long l) {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(super.hashCode()));
        return stringBuilder.toString();
    }

}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 */
package com.j256.ormlite.android;

import android.database.Cursor;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AndroidDatabaseResults
implements DatabaseResults {
    private static final int MIN_NUM_COLUMN_NAMES_MAP = 8;
    private static final DatabaseType databaseType = new SqliteAndroidDatabaseType();
    private final Map<String, Integer> columnNameMap;
    private final String[] columnNames;
    private final Cursor cursor;
    private final ObjectCache objectCache;

    public AndroidDatabaseResults(Cursor cursor, ObjectCache objectCache) {
        this.cursor = cursor;
        this.columnNames = cursor.getColumnNames();
        if (this.columnNames.length >= 8) {
            this.columnNameMap = new HashMap<String, Integer>();
            for (int i = 0; i < this.columnNames.length; ++i) {
                this.columnNameMap.put(this.columnNames[i], i);
            }
        } else {
            this.columnNameMap = null;
        }
        this.objectCache = objectCache;
    }

    @Deprecated
    public AndroidDatabaseResults(Cursor cursor, boolean bl, ObjectCache objectCache) {
        this(cursor, objectCache);
    }

    private int lookupColumn(String object) {
        if (this.columnNameMap == null) {
            for (int i = 0; i < this.columnNames.length; ++i) {
                if (!this.columnNames[i].equals(object)) continue;
                return i;
            }
            return -1;
        }
        if ((object = this.columnNameMap.get(object)) == null) {
            return -1;
        }
        return object.intValue();
    }

    @Override
    public void close() {
        this.cursor.close();
    }

    @Override
    public void closeQuietly() {
        this.close();
    }

    @Override
    public int findColumn(String string) throws SQLException {
        int n = this.lookupColumn(string);
        if (n >= 0) {
            return n;
        }
        Object[] arrobject = new Object[](string.length() + 4);
        databaseType.appendEscapedEntityName((StringBuilder)arrobject, string);
        n = this.lookupColumn(arrobject.toString());
        if (n >= 0) {
            return n;
        }
        arrobject = this.cursor.getColumnNames();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown field '");
        stringBuilder.append(string);
        stringBuilder.append("' from the Android sqlite cursor, not in:");
        stringBuilder.append(Arrays.toString(arrobject));
        throw new SQLException(stringBuilder.toString());
    }

    @Override
    public boolean first() {
        return this.cursor.moveToFirst();
    }

    @Override
    public BigDecimal getBigDecimal(int n) throws SQLException {
        throw new SQLException("Android does not support BigDecimal type.  Use BIG_DECIMAL or BIG_DECIMAL_STRING types");
    }

    @Override
    public InputStream getBlobStream(int n) {
        return new ByteArrayInputStream(this.cursor.getBlob(n));
    }

    @Override
    public boolean getBoolean(int n) {
        if (!this.cursor.isNull(n) && this.cursor.getShort(n) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public byte getByte(int n) {
        return (byte)this.getShort(n);
    }

    @Override
    public byte[] getBytes(int n) {
        return this.cursor.getBlob(n);
    }

    @Override
    public char getChar(int n) throws SQLException {
        CharSequence charSequence = this.cursor.getString(n);
        if (charSequence != null) {
            if (charSequence.length() == 0) {
                return '\u0000';
            }
            if (charSequence.length() == 1) {
                return charSequence.charAt(0);
            }
            charSequence = new StringBuilder();
            charSequence.append("More than 1 character stored in database column: ");
            charSequence.append(n);
            throw new SQLException(charSequence.toString());
        }
        return '\u0000';
    }

    @Override
    public int getColumnCount() {
        return this.cursor.getColumnCount();
    }

    @Override
    public String[] getColumnNames() {
        int n = this.getColumnCount();
        String[] arrstring = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring[i] = this.cursor.getColumnName(i);
        }
        return arrstring;
    }

    public int getCount() {
        return this.cursor.getCount();
    }

    @Override
    public double getDouble(int n) {
        return this.cursor.getDouble(n);
    }

    @Override
    public float getFloat(int n) {
        return this.cursor.getFloat(n);
    }

    @Override
    public int getInt(int n) {
        return this.cursor.getInt(n);
    }

    @Override
    public long getLong(int n) {
        return this.cursor.getLong(n);
    }

    @Override
    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public int getPosition() {
        return this.cursor.getPosition();
    }

    public Cursor getRawCursor() {
        return this.cursor;
    }

    @Override
    public short getShort(int n) {
        return this.cursor.getShort(n);
    }

    @Override
    public String getString(int n) {
        return this.cursor.getString(n);
    }

    @Override
    public Timestamp getTimestamp(int n) throws SQLException {
        throw new SQLException("Android does not support timestamp.  Use JAVA_DATE_LONG or JAVA_DATE_STRING types");
    }

    @Override
    public boolean last() {
        return this.cursor.moveToLast();
    }

    @Override
    public boolean moveAbsolute(int n) {
        return this.cursor.moveToPosition(n);
    }

    @Override
    public boolean moveRelative(int n) {
        return this.cursor.move(n);
    }

    @Override
    public boolean next() {
        return this.cursor.moveToNext();
    }

    @Override
    public boolean previous() {
        return this.cursor.moveToPrevious();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(super.hashCode()));
        return stringBuilder.toString();
    }

    @Override
    public boolean wasNull(int n) {
        return this.cursor.isNull(n);
    }
}

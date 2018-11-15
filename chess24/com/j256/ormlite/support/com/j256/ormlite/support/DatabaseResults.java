/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface DatabaseResults {
    public void close() throws SQLException;

    public void closeQuietly();

    public int findColumn(String var1) throws SQLException;

    public boolean first() throws SQLException;

    public BigDecimal getBigDecimal(int var1) throws SQLException;

    public InputStream getBlobStream(int var1) throws SQLException;

    public boolean getBoolean(int var1) throws SQLException;

    public byte getByte(int var1) throws SQLException;

    public byte[] getBytes(int var1) throws SQLException;

    public char getChar(int var1) throws SQLException;

    public int getColumnCount() throws SQLException;

    public String[] getColumnNames() throws SQLException;

    public double getDouble(int var1) throws SQLException;

    public float getFloat(int var1) throws SQLException;

    public int getInt(int var1) throws SQLException;

    public long getLong(int var1) throws SQLException;

    public ObjectCache getObjectCache();

    public short getShort(int var1) throws SQLException;

    public String getString(int var1) throws SQLException;

    public Timestamp getTimestamp(int var1) throws SQLException;

    public boolean last() throws SQLException;

    public boolean moveAbsolute(int var1) throws SQLException;

    public boolean moveRelative(int var1) throws SQLException;

    public boolean next() throws SQLException;

    public boolean previous() throws SQLException;

    public boolean wasNull(int var1) throws SQLException;
}

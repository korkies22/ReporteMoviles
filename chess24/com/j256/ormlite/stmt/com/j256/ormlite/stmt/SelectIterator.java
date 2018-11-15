/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class SelectIterator<T, ID>
implements CloseableIterator<T> {
    private static final Logger logger = LoggerFactory.getLogger(SelectIterator.class);
    private boolean alreadyMoved;
    private final Dao<T, ID> classDao;
    private boolean closed;
    private final CompiledStatement compiledStmt;
    private final DatabaseConnection connection;
    private final ConnectionSource connectionSource;
    private final Class<?> dataClass;
    private boolean first = true;
    private T last;
    private final DatabaseResults results;
    private int rowC;
    private final GenericRowMapper<T> rowMapper;
    private final String statement;

    public SelectIterator(Class<?> class_, Dao<T, ID> dao, GenericRowMapper<T> genericRowMapper, ConnectionSource connectionSource, DatabaseConnection databaseConnection, CompiledStatement compiledStatement, String string, ObjectCache objectCache) throws SQLException {
        this.dataClass = class_;
        this.classDao = dao;
        this.rowMapper = genericRowMapper;
        this.connectionSource = connectionSource;
        this.connection = databaseConnection;
        this.compiledStmt = compiledStatement;
        this.results = compiledStatement.runQuery(objectCache);
        this.statement = string;
        if (string != null) {
            logger.debug("starting iterator @{} for '{}'", this.hashCode(), (Object)string);
        }
    }

    private T getCurrent() throws SQLException {
        this.last = this.rowMapper.mapRow(this.results);
        this.alreadyMoved = false;
        ++this.rowC;
        return this.last;
    }

    @Override
    public void close() throws SQLException {
        if (!this.closed) {
            this.compiledStmt.close();
            this.closed = true;
            this.last = null;
            if (this.statement != null) {
                logger.debug("closed iterator @{} after {} rows", this.hashCode(), (Object)this.rowC);
            }
            this.connectionSource.releaseConnection(this.connection);
        }
    }

    @Override
    public void closeQuietly() {
        try {
            this.close();
            return;
        }
        catch (SQLException sQLException) {
            return;
        }
    }

    @Override
    public T current() throws SQLException {
        if (this.closed) {
            return null;
        }
        if (this.first) {
            return this.first();
        }
        return this.getCurrent();
    }

    @Override
    public T first() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.first()) {
            return this.getCurrent();
        }
        return null;
    }

    @Override
    public DatabaseResults getRawResults() {
        return this.results;
    }

    @Override
    public boolean hasNext() {
        try {
            boolean bl = this.hasNextThrow();
            return bl;
        }
        catch (SQLException sQLException) {
            this.last = null;
            this.closeQuietly();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Errors getting more results of ");
            stringBuilder.append(this.dataClass);
            throw new IllegalStateException(stringBuilder.toString(), sQLException);
        }
    }

    public boolean hasNextThrow() throws SQLException {
        boolean bl;
        if (this.closed) {
            return false;
        }
        if (this.alreadyMoved) {
            return true;
        }
        if (this.first) {
            this.first = false;
            bl = this.results.first();
        } else {
            bl = this.results.next();
        }
        if (!bl) {
            this.close();
        }
        this.alreadyMoved = true;
        return bl;
    }

    @Override
    public T moveRelative(int n) throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.moveRelative(n)) {
            return this.getCurrent();
        }
        return null;
    }

    @Override
    public void moveToNext() {
        this.last = null;
        this.first = false;
        this.alreadyMoved = false;
    }

    @Override
    public T next() {
        T t;
        block2 : {
            try {
                t = this.nextThrow();
                if (t == null) break block2;
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
            return t;
        }
        t = null;
        this.last = null;
        this.closeQuietly();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not get next result for ");
        stringBuilder.append(this.dataClass);
        throw new IllegalStateException(stringBuilder.toString(), (Throwable)t);
    }

    @Override
    public T nextThrow() throws SQLException {
        if (this.closed) {
            return null;
        }
        if (!this.alreadyMoved) {
            boolean bl;
            if (this.first) {
                this.first = false;
                bl = this.results.first();
            } else {
                bl = this.results.next();
            }
            if (!bl) {
                this.first = false;
                return null;
            }
        }
        this.first = false;
        return this.getCurrent();
    }

    @Override
    public T previous() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.previous()) {
            return this.getCurrent();
        }
        return null;
    }

    @Override
    public void remove() {
        try {
            this.removeThrow();
            return;
        }
        catch (SQLException sQLException) {
            this.closeQuietly();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not delete ");
            stringBuilder.append(this.dataClass);
            stringBuilder.append(" object ");
            stringBuilder.append(this.last);
            throw new IllegalStateException(stringBuilder.toString(), sQLException);
        }
    }

    public void removeThrow() throws SQLException {
        if (this.last == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No last ");
            stringBuilder.append(this.dataClass);
            stringBuilder.append(" object to remove. Must be called after a call to next.");
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.classDao == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot remove ");
            stringBuilder.append(this.dataClass);
            stringBuilder.append(" object because classDao not initialized");
            throw new IllegalStateException(stringBuilder.toString());
        }
        try {
            this.classDao.delete(this.last);
            return;
        }
        finally {
            this.last = null;
        }
    }
}

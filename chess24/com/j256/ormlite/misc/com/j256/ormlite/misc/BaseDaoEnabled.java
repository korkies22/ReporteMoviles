/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.misc;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

public abstract class BaseDaoEnabled<T, ID> {
    protected transient Dao<T, ID> dao;

    private void checkForDao() throws SQLException {
        if (this.dao == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dao has not been set on ");
            stringBuilder.append(this.getClass());
            stringBuilder.append(" object: ");
            stringBuilder.append(this);
            throw new SQLException(stringBuilder.toString());
        }
    }

    public int create() throws SQLException {
        this.checkForDao();
        return this.dao.create(this);
    }

    public int delete() throws SQLException {
        this.checkForDao();
        return this.dao.delete(this);
    }

    public ID extractId() throws SQLException {
        this.checkForDao();
        return this.dao.extractId(this);
    }

    public Dao<T, ID> getDao() {
        return this.dao;
    }

    public String objectToString() {
        try {
            this.checkForDao();
            return this.dao.objectToString(this);
        }
        catch (SQLException sQLException) {
            throw new IllegalArgumentException(sQLException);
        }
    }

    public boolean objectsEqual(T t) throws SQLException {
        this.checkForDao();
        return this.dao.objectsEqual(this, (BaseDaoEnabled)t);
    }

    public int refresh() throws SQLException {
        this.checkForDao();
        return this.dao.refresh(this);
    }

    public void setDao(Dao<T, ID> dao) {
        this.dao = dao;
    }

    public int update() throws SQLException {
        this.checkForDao();
        return this.dao.update(this);
    }

    public int updateId(ID ID) throws SQLException {
        this.checkForDao();
        return this.dao.updateId(this, ID);
    }
}

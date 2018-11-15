/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.misc;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionManager {
    private static final String SAVE_POINT_PREFIX = "ORMLITE";
    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    private static AtomicInteger savePointCounter = new AtomicInteger();
    private ConnectionSource connectionSource;

    public TransactionManager() {
    }

    public TransactionManager(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        this.initialize();
    }

    public static <T> T callInTransaction(ConnectionSource connectionSource, Callable<T> callable) throws SQLException {
        DatabaseConnection databaseConnection = connectionSource.getReadWriteConnection();
        try {
            callable = TransactionManager.callInTransaction(databaseConnection, connectionSource.saveSpecialConnection(databaseConnection), connectionSource.getDatabaseType(), callable);
            return (T)callable;
        }
        finally {
            connectionSource.clearSpecialConnection(databaseConnection);
            connectionSource.releaseConnection(databaseConnection);
        }
    }

    public static <T> T callInTransaction(DatabaseConnection databaseConnection, DatabaseType databaseType, Callable<T> callable) throws SQLException {
        return TransactionManager.callInTransaction(databaseConnection, false, databaseType, callable);
    }

    /*
     * Exception decompiling
     */
    public static <T> T callInTransaction(DatabaseConnection var0, boolean var1_1, DatabaseType var2_2, Callable<T> var3_8) throws SQLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[CATCHBLOCK]], but top level block is 9[CATCHBLOCK]
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

    private static void commit(DatabaseConnection databaseConnection, Savepoint savepoint) throws SQLException {
        String string = savepoint == null ? null : savepoint.getSavepointName();
        databaseConnection.commit(savepoint);
        if (string == null) {
            logger.debug("committed savePoint transaction");
            return;
        }
        logger.debug("committed savePoint transaction {}", (Object)string);
    }

    private static void rollBack(DatabaseConnection databaseConnection, Savepoint savepoint) throws SQLException {
        String string = savepoint == null ? null : savepoint.getSavepointName();
        databaseConnection.rollback(savepoint);
        if (string == null) {
            logger.debug("rolled back savePoint transaction");
            return;
        }
        logger.debug("rolled back savePoint transaction {}", (Object)string);
    }

    public <T> T callInTransaction(Callable<T> callable) throws SQLException {
        return TransactionManager.callInTransaction(this.connectionSource, callable);
    }

    public void initialize() {
        if (this.connectionSource == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("dataSource was not set on ");
            stringBuilder.append(this.getClass().getSimpleName());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;

public class SerializableType
extends BaseDataType {
    private static final SerializableType singleTon = new SerializableType();

    private SerializableType() {
        super(SqlType.SERIALIZABLE, new Class[0]);
    }

    protected SerializableType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static SerializableType getSingleton() {
        return singleTon;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return Serializable.class;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }

    @Override
    public boolean isArgumentHolderRequired() {
        return true;
    }

    @Override
    public boolean isComparable() {
        return false;
    }

    @Override
    public boolean isStreamType() {
        return true;
    }

    @Override
    public boolean isValidForField(Field field) {
        return Serializable.class.isAssignableFrom(field.getType());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Object javaToSqlArg(FieldType arrby, Object object) throws SQLException {
        void var2_7;
        block10 : {
            Exception exception;
            StringBuilder stringBuilder;
            byte[] arrby2;
            block11 : {
                exception = null;
                stringBuilder = null;
                arrby = stringBuilder;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                arrby = stringBuilder;
                arrby2 = new ObjectOutputStream(byteArrayOutputStream);
                arrby2.writeObject(object);
                arrby2.close();
                arrby = stringBuilder;
                try {
                    return byteArrayOutputStream.toByteArray();
                    catch (Throwable throwable) {
                        arrby = arrby2;
                        break block10;
                    }
                    catch (Exception exception2) {
                        break block11;
                    }
                }
                catch (Throwable throwable) {
                    break block10;
                }
                catch (Exception exception3) {
                    arrby2 = exception;
                    exception = exception3;
                }
            }
            arrby = arrby2;
            stringBuilder = new StringBuilder();
            arrby = arrby2;
            stringBuilder.append("Could not write serialized object to byte array: ");
            arrby = arrby2;
            stringBuilder.append(object);
            arrby = arrby2;
            throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
        }
        if (arrby == null) throw var2_7;
        try {
            arrby.close();
        }
        catch (IOException iOException) {
            throw var2_7;
        }
        throw var2_7;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        throw new SQLException("Default values for serializable types are not supported");
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        throw new SQLException("Serializable type cannot be converted from string to Java");
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getBytes(n);
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
    public Object sqlArgToJava(FieldType object, Object object2, int n) throws SQLException {
        block11 : {
            StringBuilder stringBuilder;
            byte[] arrby;
            block12 : {
                arrby = (byte[])object2;
                stringBuilder = null;
                object = null;
                object2 = new ObjectInputStream(new ByteArrayInputStream(arrby));
                object = object2.readObject();
                if (object2 == null) return object;
                try {
                    object2.close();
                    return object;
                }
                catch (IOException iOException) {
                    return object;
                }
                catch (Throwable throwable) {
                    break block11;
                }
                catch (Exception exception) {
                    break block12;
                }
                catch (Throwable throwable) {
                    object2 = object;
                    object = throwable;
                    break block11;
                }
                catch (Exception exception) {
                    object2 = stringBuilder;
                }
            }
            object = object2;
            {
                void var4_12;
                stringBuilder = new StringBuilder();
                object = object2;
                stringBuilder.append("Could not read serialized object from byte array: ");
                object = object2;
                stringBuilder.append(Arrays.toString(arrby));
                object = object2;
                stringBuilder.append("(len ");
                object = object2;
                stringBuilder.append(arrby.length);
                object = object2;
                stringBuilder.append(")");
                object = object2;
                throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)var4_12);
            }
        }
        if (object2 == null) throw object;
        try {
            object2.close();
        }
        catch (IOException iOException) {
            throw object;
        }
        throw object;
    }
}

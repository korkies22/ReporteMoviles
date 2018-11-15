/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.mapped.BaseMappedStatement;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableInfo;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMappedQuery<T, ID>
extends BaseMappedStatement<T, ID>
implements GenericRowMapper<T> {
    private Map<String, Integer> columnPositions = null;
    private Object parent = null;
    private Object parentId = null;
    protected final FieldType[] resultsFieldTypes;

    protected BaseMappedQuery(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, FieldType[] arrfieldType2) {
        super(tableInfo, string, arrfieldType);
        this.resultsFieldTypes = arrfieldType2;
    }

    @Override
    public T mapRow(DatabaseResults arrfieldType) throws SQLException {
        Object object;
        int n;
        Map<String, Integer> map = this.columnPositions == null ? new HashMap<String, Integer>() : this.columnPositions;
        ObjectCache objectCache = arrfieldType.getObjectCache();
        if (objectCache != null) {
            object = this.idField.resultToJava((DatabaseResults)arrfieldType, map);
            if ((object = objectCache.get(this.clazz, object)) != null) {
                return object;
            }
        }
        Object t = this.tableInfo.createObject();
        Object object2 = this.resultsFieldTypes;
        int n2 = ((FieldType[])object2).length;
        object = null;
        int n3 = n = 0;
        while (n < n2) {
            int n4;
            FieldType fieldType = object2[n];
            if (fieldType.isForeignCollection()) {
                n4 = 1;
            } else {
                Object object3 = fieldType.resultToJava((DatabaseResults)arrfieldType, map);
                if (object3 != null && this.parent != null && fieldType.getField().getType() == this.parent.getClass() && object3.equals(this.parentId)) {
                    fieldType.assignField(t, this.parent, true, objectCache);
                } else {
                    fieldType.assignField(t, object3, false, objectCache);
                }
                n4 = n3;
                if (fieldType == this.idField) {
                    object = object3;
                    n4 = n3;
                }
            }
            ++n;
            n3 = n4;
        }
        if (n3 != 0) {
            for (FieldType fieldType : this.resultsFieldTypes) {
                if (!fieldType.isForeignCollection() || (object2 = fieldType.buildForeignCollection(t, object)) == null) continue;
                fieldType.assignField(t, object2, false, objectCache);
            }
        }
        if (objectCache != null && object != null) {
            objectCache.put(this.clazz, object, t);
        }
        if (this.columnPositions == null) {
            this.columnPositions = map;
        }
        return t;
    }

    public void setParentInformation(Object object, Object object2) {
        this.parent = object;
        this.parentId = object2;
    }
}

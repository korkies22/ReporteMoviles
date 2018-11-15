/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.types.EnumStringType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataPersisterManager {
    private static final DataPersister DEFAULT_ENUM_PERSISTER = EnumStringType.getSingleton();
    private static final Map<String, DataPersister> builtInMap = new HashMap<String, DataPersister>();
    private static List<DataPersister> registeredPersisters;

    static {
        DataType[] arrdataType = DataType.values();
        int n = arrdataType.length;
        for (int i = 0; i < n; ++i) {
            DataPersister dataPersister = arrdataType[i].getDataPersister();
            if (dataPersister == null) continue;
            for (Class<?> class_ : dataPersister.getAssociatedClasses()) {
                builtInMap.put(class_.getName(), dataPersister);
            }
            if (dataPersister.getAssociatedClassNames() == null) continue;
            for (String string : dataPersister.getAssociatedClassNames()) {
                builtInMap.put(string, dataPersister);
            }
        }
    }

    private DataPersisterManager() {
    }

    public static void clear() {
        registeredPersisters = null;
    }

    public static DataPersister lookupForField(Field field) {
        Object object;
        if (registeredPersisters != null) {
            object = registeredPersisters.iterator();
            while (object.hasNext()) {
                DataPersister dataPersister = object.next();
                if (dataPersister.isValidForField(field)) {
                    return dataPersister;
                }
                for (Class<?> class_ : dataPersister.getAssociatedClasses()) {
                    if (field.getType() != class_) continue;
                    return dataPersister;
                }
            }
        }
        if ((object = builtInMap.get(field.getType().getName())) != null) {
            return object;
        }
        if (field.getType().isEnum()) {
            return DEFAULT_ENUM_PERSISTER;
        }
        return null;
    }

    public static /* varargs */ void registerDataPersisters(DataPersister ... arrdataPersister) {
        ArrayList<DataPersister> arrayList = new ArrayList<DataPersister>();
        if (registeredPersisters != null) {
            arrayList.addAll(registeredPersisters);
        }
        int n = arrdataPersister.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrdataPersister[i]);
        }
        registeredPersisters = arrayList;
    }
}

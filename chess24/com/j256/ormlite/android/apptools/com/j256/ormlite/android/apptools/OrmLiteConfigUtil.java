/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.android.apptools;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.DatabaseTableConfigLoader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrmLiteConfigUtil {
    protected static final String RAW_DIR_NAME = "raw";
    protected static final String RESOURCE_DIR_NAME = "res";
    private static final DatabaseType databaseType = new SqliteAndroidDatabaseType();
    protected static int maxFindSourceLevel = 20;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean classHasAnnotations(Class<?> object) {
        while (object != null) {
            Field[] arrfield;
            if (object.getAnnotation(DatabaseTable.class) != null) {
                return true;
            }
            try {
                arrfield = object.getDeclaredFields();
            }
            catch (Throwable throwable) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not load get delcared fields from: ");
                stringBuilder.append(object);
                printStream.println(stringBuilder.toString());
                object = System.err;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("     ");
                stringBuilder2.append(throwable);
                object.println(stringBuilder2.toString());
                return false;
            }
            int n = arrfield.length;
            for (int i = 0; i < n; ++i) {
                Field field = arrfield[i];
                if (field.getAnnotation(DatabaseField.class) != null) {
                    return true;
                }
                if (field.getAnnotation(ForeignCollectionField.class) == null) continue;
                return true;
            }
            try {
                Class class_ = object.getSuperclass();
                object = class_;
            }
            catch (Throwable throwable) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get super class for: ");
                stringBuilder.append(object);
                printStream.println(stringBuilder.toString());
                object = System.err;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("     ");
                stringBuilder3.append(throwable);
                object.println(stringBuilder3.toString());
                return false;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void findAnnotatedClasses(List<Class<?>> list, File arrfile, int n) throws SQLException, IOException {
        arrfile = arrfile.listFiles();
        int n2 = arrfile.length;
        int n3 = 0;
        do {
            block13 : {
                File throwable;
                block14 : {
                    if (n3 >= n2) {
                        return;
                    }
                    throwable = arrfile[n3];
                    if (!throwable.isDirectory()) break block14;
                    if (n >= maxFindSourceLevel) break block13;
                    OrmLiteConfigUtil.findAnnotatedClasses(list, throwable, n + 1);
                    break block13;
                }
                if (throwable.getName().endsWith(".java")) {
                    Serializable serializable;
                    Object object = OrmLiteConfigUtil.getPackageOfClass(throwable);
                    if (object == null) {
                        object = System.err;
                        serializable = new StringBuilder();
                        serializable.append("Could not find package name for: ");
                        serializable.append(throwable);
                        object.println(serializable.toString());
                    } else {
                        Class<?>[] arrclass;
                        StringBuilder stringBuilder;
                        int n22;
                        serializable = throwable.getName();
                        serializable = serializable.substring(0, serializable.length() - ".java".length());
                        stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object);
                        stringBuilder.append(".");
                        stringBuilder.append((String)((Object)serializable));
                        object = stringBuilder.toString();
                        try {
                            object = Class.forName((String)object);
                        }
                        catch (Throwable throwable2) {
                            serializable = System.err;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Could not load class file for: ");
                            stringBuilder.append(throwable);
                            serializable.println(stringBuilder.toString());
                            PrintStream printStream = System.err;
                            serializable = new StringBuilder();
                            serializable.append("     ");
                            serializable.append(throwable2);
                            printStream.println(serializable.toString());
                            break block13;
                        }
                        if (OrmLiteConfigUtil.classHasAnnotations(object)) {
                            list.add((Class<?>)object);
                        }
                        arrclass = object.getDeclaredClasses();
                        n22 = arrclass.length;
                        for (int i = 0; i < n22; ++i) {
                            serializable = arrclass[i];
                            try {
                                if (!OrmLiteConfigUtil.classHasAnnotations(serializable)) continue;
                                list.add((Class<?>)serializable);
                                continue;
                            }
                            catch (Throwable throwable3) {
                                serializable = System.err;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Could not load inner classes for: ");
                                stringBuilder.append(object);
                                serializable.println(stringBuilder.toString());
                                object = System.err;
                                serializable = new StringBuilder();
                                serializable.append("     ");
                                serializable.append(throwable3);
                                object.println(serializable.toString());
                                break;
                            }
                        }
                    }
                }
            }
            ++n3;
        } while (true);
    }

    protected static File findRawDir(File file) {
        for (int n = 0; file != null && n < 20; file = file.getParentFile(), ++n) {
            File file2 = OrmLiteConfigUtil.findResRawDir(file);
            if (file2 == null) continue;
            return file2;
        }
        return null;
    }

    private static File findResRawDir(File arrfile) {
        for (File file : arrfile.listFiles()) {
            File[] arrfile2;
            if (!file.getName().equals(RESOURCE_DIR_NAME) || !file.isDirectory() || (arrfile2 = file.listFiles(new FileFilter(){

                @Override
                public boolean accept(File file) {
                    if (file.getName().equals(OrmLiteConfigUtil.RAW_DIR_NAME) && file.isDirectory()) {
                        return true;
                    }
                    return false;
                }
            })).length != 1) continue;
            return arrfile2[0];
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String getPackageOfClass(File object) throws IOException {
        Object object2;
        object = new BufferedReader(new FileReader((File)object));
        do {
            block4 : {
                object2 = object.readLine();
                if (object2 != null) break block4;
                object.close();
                return null;
            }
            if (!object2.contains("package") || ((String[])(object2 = object2.split("[ \t;]"))).length <= 1 || !object2[0].equals("package")) continue;
            break;
        } while (true);
        object2 = object2[1];
        object.close();
        return object2;
        catch (Throwable throwable) {
            object.close();
            throw throwable;
        }
    }

    public static void main(String[] arrstring) throws Exception {
        if (arrstring.length != 1) {
            throw new IllegalArgumentException("Main can take a single file-name argument.");
        }
        OrmLiteConfigUtil.writeConfigFile(arrstring[0]);
    }

    public static void writeConfigFile(File file) throws SQLException, IOException {
        OrmLiteConfigUtil.writeConfigFile(file, new File("."));
    }

    public static void writeConfigFile(File file, File file2) throws SQLException, IOException {
        ArrayList arrayList = new ArrayList();
        OrmLiteConfigUtil.findAnnotatedClasses(arrayList, file2, 0);
        OrmLiteConfigUtil.writeConfigFile(file, arrayList.toArray(new Class[arrayList.size()]));
    }

    public static void writeConfigFile(File file, Class<?>[] arrclass) throws SQLException, IOException {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Writing configurations to ");
        stringBuilder.append(file.getAbsolutePath());
        printStream.println(stringBuilder.toString());
        OrmLiteConfigUtil.writeConfigFile((OutputStream)new FileOutputStream(file), arrclass);
    }

    public static void writeConfigFile(OutputStream outputStream, File file) throws SQLException, IOException {
        ArrayList arrayList = new ArrayList();
        OrmLiteConfigUtil.findAnnotatedClasses(arrayList, file, 0);
        OrmLiteConfigUtil.writeConfigFile(outputStream, arrayList.toArray(new Class[arrayList.size()]));
    }

    public static void writeConfigFile(OutputStream closeable, Class<?>[] arrclass) throws SQLException, IOException {
        closeable = new BufferedWriter(new OutputStreamWriter((OutputStream)closeable), 4096);
        OrmLiteConfigUtil.writeHeader((BufferedWriter)closeable);
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            OrmLiteConfigUtil.writeConfigForTable((BufferedWriter)closeable, arrclass[i]);
        }
        try {
            System.out.println("Done.");
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            ((BufferedWriter)closeable).close();
        }
    }

    public static void writeConfigFile(String string) throws SQLException, IOException {
        ArrayList arrayList = new ArrayList();
        OrmLiteConfigUtil.findAnnotatedClasses(arrayList, new File("."), 0);
        OrmLiteConfigUtil.writeConfigFile(string, arrayList.toArray(new Class[arrayList.size()]));
    }

    public static void writeConfigFile(String string, Class<?>[] arrclass) throws SQLException, IOException {
        File file = OrmLiteConfigUtil.findRawDir(new File("."));
        if (file == null) {
            System.err.println("Could not find raw directory which is typically in the res directory");
            return;
        }
        OrmLiteConfigUtil.writeConfigFile(new File(file, string), arrclass);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void writeConfigForTable(BufferedWriter var0, Class<?> var1_2) throws SQLException, IOException {
        var5_3 = DatabaseTableConfig.extractTableName(var1_2);
        var6_4 = new ArrayList<DatabaseFieldConfig>();
        var4_5 = var1_2;
        block3 : do {
            if (var4_5 != null) {
                var7_8 = var4_5.getDeclaredFields();
                var3_7 = var7_8.length;
                var2_6 = 0;
            }
            if (var6_4.isEmpty()) {
                var0 = System.out;
                var4_5 = new StringBuilder();
                var4_5.append("Skipping ");
                var4_5.append(var1_2);
                var4_5.append(" because no annotated fields found");
                var0.println(var4_5.toString());
                return;
            }
            DatabaseTableConfigLoader.write((BufferedWriter)var0, new DatabaseTableConfig<?>(var1_2, (String)var5_3, var6_4));
            var0.append("#################################");
            var0.newLine();
            var0 = System.out;
            var4_5 = new StringBuilder();
            var4_5.append("Wrote config for ");
            var4_5.append(var1_2);
            var0.println(var4_5.toString());
            return;
            do {
                block8 : {
                    if (var2_6 >= var3_7) ** GOTO lbl36
                    var8_9 = var7_8[var2_6];
                    try {
                        var8_9 = DatabaseFieldConfig.fromField(OrmLiteConfigUtil.databaseType, (String)var5_3, (Field)var8_9);
                        if (var8_9 != null) {
                            var6_4.add((DatabaseFieldConfig)var8_9);
                        }
                        break block8;
lbl36: // 1 sources:
                        var4_5 = var4_5.getSuperclass();
                        continue block3;
                    }
                    catch (Error var0_1) {
                        var4_5 = System.err;
                        var5_3 = new StringBuilder();
                        var5_3.append("Skipping ");
                        var5_3.append(var1_2);
                        var5_3.append(" because we got an error finding its definition: ");
                        var5_3.append(var0_1.getMessage());
                        var4_5.println(var5_3.toString());
                        return;
                    }
                }
                ++var2_6;
            } while (true);
            break;
        } while (true);
    }

    private static void writeHeader(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.append('#');
        bufferedWriter.newLine();
        bufferedWriter.append("# generated on ").append(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
        bufferedWriter.newLine();
        bufferedWriter.append('#');
        bufferedWriter.newLine();
    }

}

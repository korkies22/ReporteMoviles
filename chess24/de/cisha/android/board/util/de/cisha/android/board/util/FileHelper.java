/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.util.Log
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class FileHelper {
    public static byte[] readFile(File file) {
        byte[] arrby = new byte[]{};
        if (file.exists()) {
            int n = (int)file.length();
            arrby = new byte[n];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(arrby, 0, n);
                fileInputStream.close();
            }
            catch (IOException iOException) {
                String string = FileHelper.class.getName();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception reading File ");
                stringBuilder.append(file);
                Log.d((String)string, (String)stringBuilder.toString(), (Throwable)iOException);
            }
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String readFileAsString(File file) {
        try {
            StringBuffer stringBuffer = new StringBuffer(1024);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Object object = new char[1024];
            do {
                int n;
                if ((n = bufferedReader.read((char[])object)) == -1) {
                    bufferedReader.close();
                    return stringBuffer.toString();
                }
                stringBuffer.append(String.valueOf(object, 0, n));
                object = new char[1024];
            } while (true);
        }
        catch (IOException iOException) {
            String string = FileHelper.class.getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception reading File ");
            stringBuilder.append(file);
            Log.d((String)string, (String)stringBuilder.toString(), (Throwable)iOException);
            return "";
        }
    }

    public static void readFileAsString(File file, final FileReaderDelegate fileReaderDelegate) {
        new AsyncTask<File, Void, String>(){

            protected /* varargs */ String doInBackground(File ... arrfile) {
                return FileHelper.readFileAsString(arrfile[0]);
            }

            protected void onPostExecute(String string) {
                fileReaderDelegate.fileRead(string);
            }
        }.execute((Object[])new File[]{file});
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String readStreamAsString(InputStream var0, Charset var1_2) {
        if (var1_2 == null) ** GOTO lbl5
        try {
            block4 : {
                var0 /* !! */  = new InputStreamReader((InputStream)var0 /* !! */ , (Charset)var1_2);
                break block4;
lbl5: // 1 sources:
                var0 /* !! */  = new InputStreamReader((InputStream)var0 /* !! */ );
            }
            var1_2 = new BufferedReader((Reader)var0 /* !! */ );
            var3_3 = new StringBuffer(1024);
            var0 /* !! */  = new char[1024];
            do {
                if ((var2_4 = var1_2.read(var0 /* !! */ )) == -1) {
                    var1_2.close();
                    return var3_3.toString();
                }
                var3_3.append(String.valueOf(var0 /* !! */ , 0, var2_4));
                var0 /* !! */  = new char[1024];
            } while (true);
        }
        catch (IOException var0_1) {}
        Log.d((String)FileHelper.class.getName(), (String)"Exception reading InputStream ", (Throwable)var0_1);
        return "";
    }

    public static void readStreamAsString(InputStream inputStream, final FileReaderDelegate fileReaderDelegate, final Charset charset) {
        new AsyncTask<InputStream, Void, String>(){

            protected /* varargs */ String doInBackground(InputStream ... arrinputStream) {
                return FileHelper.readStreamAsString(arrinputStream[0], charset);
            }

            protected void onPostExecute(String string) {
                fileReaderDelegate.fileRead(string);
            }
        }.execute((Object[])new InputStream[]{inputStream});
    }

    public static interface FileReaderDelegate {
        public void fileRead(String var1);
    }

}

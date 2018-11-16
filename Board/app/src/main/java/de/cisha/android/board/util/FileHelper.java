// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.InputStream;
import android.os.AsyncTask;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;
import java.io.FileInputStream;
import java.io.File;

public class FileHelper
{
    public static byte[] readFile(final File file) {
        byte[] array = new byte[0];
        if (file.exists()) {
            final int n = (int)file.length();
            array = new byte[n];
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(array, 0, n);
                fileInputStream.close();
            }
            catch (IOException ex) {
                final String name = FileHelper.class.getName();
                final StringBuilder sb = new StringBuilder();
                sb.append("Exception reading File ");
                sb.append(file);
                Log.d(name, sb.toString(), (Throwable)ex);
            }
        }
        return array;
    }
    
    public static String readFileAsString(final File file) {
        try {
            final StringBuffer sb = new StringBuffer(1024);
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            char[] array = new char[1024];
            while (true) {
                final int read = bufferedReader.read(array);
                if (read == -1) {
                    break;
                }
                sb.append(String.valueOf(array, 0, read));
                array = new char[1024];
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (IOException ex) {
            final String name = FileHelper.class.getName();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception reading File ");
            sb2.append(file);
            Log.d(name, sb2.toString(), (Throwable)ex);
            return "";
        }
    }
    
    public static void readFileAsString(final File file, final FileReaderDelegate fileReaderDelegate) {
        new AsyncTask<File, Void, String>() {
            protected String doInBackground(final File... array) {
                return FileHelper.readFileAsString(array[0]);
            }
            
            protected void onPostExecute(final String s) {
                fileReaderDelegate.fileRead(s);
            }
        }.execute((Object[])new File[] { file });
    }
    
    public static String readStreamAsString(final InputStream inputStream, final Charset charset) {
        Label_0017: {
            if (charset == null) {
                break Label_0017;
            }
            while (true) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
                    while (true) {
                        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        final StringBuffer sb = new StringBuffer(1024);
                        char[] array = new char[1024];
                        while (true) {
                            final int read = bufferedReader.read(array);
                            if (read == -1) {
                                break;
                            }
                            sb.append(String.valueOf(array, 0, read));
                            array = new char[1024];
                        }
                        bufferedReader.close();
                        return sb.toString();
                        inputStreamReader = new InputStreamReader(inputStream);
                        continue;
                    }
                    final IOException ex;
                    Log.d(FileHelper.class.getName(), "Exception reading InputStream ", (Throwable)ex);
                    return "";
                }
                catch (IOException ex) {
                    continue;
                }
                break;
            }
        }
    }
    
    public static void readStreamAsString(final InputStream inputStream, final FileReaderDelegate fileReaderDelegate, final Charset charset) {
        new AsyncTask<InputStream, Void, String>() {
            protected String doInBackground(final InputStream... array) {
                return FileHelper.readStreamAsString(array[0], charset);
            }
            
            protected void onPostExecute(final String s) {
                fileReaderDelegate.fileRead(s);
            }
        }.execute((Object[])new InputStream[] { inputStream });
    }
    
    public interface FileReaderDelegate
    {
        void fileRead(final String p0);
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.DocumentsContract
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

@RequiresApi(value=19)
class DocumentsContractApi19 {
    private static final int FLAG_VIRTUAL_DOCUMENT = 512;
    private static final String TAG = "DocumentFile";

    DocumentsContractApi19() {
    }

    public static boolean canRead(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 1) != 0) {
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)DocumentsContractApi19.getRawType(context, uri))) {
            return false;
        }
        return true;
    }

    public static boolean canWrite(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String string = DocumentsContractApi19.getRawType(context, uri);
        int n = DocumentsContractApi19.queryForInt(context, uri, "flags", 0);
        if (TextUtils.isEmpty((CharSequence)string)) {
            return false;
        }
        if ((n & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals(string) && (n & 8) != 0) {
            return true;
        }
        if (!TextUtils.isEmpty((CharSequence)string) && (n & 2) != 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable == null) return;
        try {
            autoCloseable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean exists(Context context, Uri object) {
        block8 : {
            StringBuilder stringBuilder;
            block9 : {
                boolean bl;
                block7 : {
                    ContentResolver contentResolver = context.getContentResolver();
                    bl = true;
                    stringBuilder = null;
                    context = null;
                    object = contentResolver.query((Uri)object, new String[]{"document_id"}, null, null, null);
                    try {
                        int n = object.getCount();
                        if (n > 0) break block7;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        context = object;
                        object = throwable;
                        break block8;
                    }
                    catch (Exception exception) {
                        break block9;
                    }
                }
                DocumentsContractApi19.closeQuietly((AutoCloseable)object);
                return bl;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (Exception exception) {
                    object = stringBuilder;
                }
            }
            context = object;
            {
                void var4_7;
                stringBuilder = new StringBuilder();
                context = object;
                stringBuilder.append("Failed query: ");
                context = object;
                stringBuilder.append(var4_7);
                context = object;
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            DocumentsContractApi19.closeQuietly((AutoCloseable)object);
            return false;
        }
        DocumentsContractApi19.closeQuietly((AutoCloseable)context);
        throw object;
    }

    public static long getFlags(Context context, Uri uri) {
        return DocumentsContractApi19.queryForLong(context, uri, "flags", 0L);
    }

    public static String getName(Context context, Uri uri) {
        return DocumentsContractApi19.queryForString(context, uri, "_display_name", null);
    }

    private static String getRawType(Context context, Uri uri) {
        return DocumentsContractApi19.queryForString(context, uri, "mime_type", null);
    }

    public static String getType(Context object, Uri uri) {
        if ("vnd.android.document/directory".equals(object = DocumentsContractApi19.getRawType(object, uri))) {
            return null;
        }
        return object;
    }

    public static boolean isDirectory(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(DocumentsContractApi19.getRawType(context, uri));
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri((Context)context, (Uri)uri);
    }

    public static boolean isFile(Context object, Uri uri) {
        if (!"vnd.android.document/directory".equals(object = DocumentsContractApi19.getRawType(object, uri)) && !TextUtils.isEmpty((CharSequence)object)) {
            return true;
        }
        return false;
    }

    public static boolean isVirtual(Context context, Uri uri) {
        boolean bl = DocumentsContractApi19.isDocumentUri(context, uri);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if ((DocumentsContractApi19.getFlags(context, uri) & 512L) != 0L) {
            bl2 = true;
        }
        return bl2;
    }

    public static long lastModified(Context context, Uri uri) {
        return DocumentsContractApi19.queryForLong(context, uri, "last_modified", 0L);
    }

    public static long length(Context context, Uri uri) {
        return DocumentsContractApi19.queryForLong(context, uri, "_size", 0L);
    }

    private static int queryForInt(Context context, Uri uri, String string, int n) {
        return (int)DocumentsContractApi19.queryForLong(context, uri, string, n);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static long queryForLong(Context context, Uri object, String string, long l) {
        block8 : {
            StringBuilder stringBuilder;
            block9 : {
                block7 : {
                    long l2;
                    ContentResolver contentResolver = context.getContentResolver();
                    stringBuilder = null;
                    context = null;
                    object = contentResolver.query((Uri)object, new String[]{string}, null, null, null);
                    try {
                        if (!object.moveToFirst() || object.isNull(0)) break block7;
                        l2 = object.getLong(0);
                    }
                    catch (Throwable throwable) {
                        context = object;
                        object = throwable;
                        break block8;
                    }
                    catch (Exception exception) {
                        break block9;
                    }
                    DocumentsContractApi19.closeQuietly((AutoCloseable)object);
                    return l2;
                }
                DocumentsContractApi19.closeQuietly((AutoCloseable)object);
                return l;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (Exception exception) {
                    object = stringBuilder;
                }
            }
            context = object;
            {
                void var2_7;
                stringBuilder = new StringBuilder();
                context = object;
                stringBuilder.append("Failed query: ");
                context = object;
                stringBuilder.append(var2_7);
                context = object;
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            DocumentsContractApi19.closeQuietly((AutoCloseable)object);
            return l;
        }
        DocumentsContractApi19.closeQuietly((AutoCloseable)context);
        throw object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String queryForString(Context object, Uri object2, String string, String string2) {
        block8 : {
            StringBuilder stringBuilder;
            block9 : {
                block7 : {
                    ContentResolver contentResolver = object.getContentResolver();
                    stringBuilder = null;
                    object = null;
                    object2 = contentResolver.query((Uri)object2, new String[]{string}, null, null, null);
                    try {
                        if (!object2.moveToFirst() || object2.isNull(0)) break block7;
                        object = object2.getString(0);
                    }
                    catch (Throwable throwable) {
                        object = object2;
                        object2 = throwable;
                        break block8;
                    }
                    catch (Exception exception) {
                        break block9;
                    }
                    DocumentsContractApi19.closeQuietly((AutoCloseable)object2);
                    return object;
                }
                DocumentsContractApi19.closeQuietly((AutoCloseable)object2);
                return string2;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (Exception exception) {
                    object2 = stringBuilder;
                }
            }
            object = object2;
            {
                void var2_7;
                stringBuilder = new StringBuilder();
                object = object2;
                stringBuilder.append("Failed query: ");
                object = object2;
                stringBuilder.append(var2_7);
                object = object2;
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            DocumentsContractApi19.closeQuietly((AutoCloseable)object2);
            return string2;
        }
        DocumentsContractApi19.closeQuietly((AutoCloseable)object);
        throw object2;
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 */
package com.crashlytics.android.core;

import android.app.ActivityManager;
import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.TrimmedThrowableData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.IdManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SessionProtobufHelper {
    private static final String SIGNAL_DEFAULT = "0";
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
    private static final ByteString UNITY_PLATFORM_BYTE_STRING = ByteString.copyFromUtf8("Unity");

    private SessionProtobufHelper() {
    }

    private static int getBinaryImageSize(ByteString byteString, ByteString byteString2) {
        int n;
        int n2 = n = 0 + CodedOutputStream.computeUInt64Size(1, 0L) + CodedOutputStream.computeUInt64Size(2, 0L) + CodedOutputStream.computeBytesSize(3, byteString);
        if (byteString2 != null) {
            n2 = n + CodedOutputStream.computeBytesSize(4, byteString2);
        }
        return n2;
    }

    private static int getDeviceIdentifierSize(IdManager.DeviceIdentifierType deviceIdentifierType, String string) {
        return CodedOutputStream.computeEnumSize(1, deviceIdentifierType.protobufIndex) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string));
    }

    private static int getEventAppCustomAttributeSize(String string, String string2) {
        int n = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(string));
        string = string2;
        if (string2 == null) {
            string = "";
        }
        return n + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string));
    }

    private static int getEventAppExecutionExceptionSize(TrimmedThrowableData object, int n, int n2) {
        int n3 = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(object.className));
        int n4 = 0;
        int n5 = n3 + 0;
        Object object2 = object.localizedMessage;
        n3 = n5;
        if (object2 != null) {
            n3 = n5 + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8((String)object2));
        }
        object2 = object.stacktrace;
        int n6 = ((StackTraceElement[])object2).length;
        for (n5 = 0; n5 < n6; ++n5) {
            int n7 = SessionProtobufHelper.getFrameSize(object2[n5], true);
            n3 += CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(n7) + n7;
        }
        object2 = object.cause;
        n5 = n3;
        if (object2 != null) {
            n5 = n4;
            object = object2;
            if (n < n2) {
                n = SessionProtobufHelper.getEventAppExecutionExceptionSize((TrimmedThrowableData)object2, n + 1, n2);
                return n3 + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(n) + n);
            }
            while (object != null) {
                object = object.cause;
                ++n5;
            }
            n5 = n3 + CodedOutputStream.computeUInt32Size(7, n5);
        }
        return n5;
    }

    private static int getEventAppExecutionSignalSize() {
        return 0 + CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING) + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING) + CodedOutputStream.computeUInt64Size(3, 0L);
    }

    private static int getEventAppExecutionSize(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2) {
        int n2 = SessionProtobufHelper.getThreadSize(thread, arrstackTraceElement, 4, true);
        int n3 = CodedOutputStream.computeTagSize(1);
        int n4 = CodedOutputStream.computeRawVarint32Size(n2);
        int n5 = arrthread.length;
        n2 = n3 + n4 + n2 + 0;
        for (n3 = 0; n3 < n5; ++n3) {
            n4 = SessionProtobufHelper.getThreadSize(arrthread[n3], list.get(n3), 0, false);
            n2 += CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(n4) + n4;
        }
        n = SessionProtobufHelper.getEventAppExecutionExceptionSize(trimmedThrowableData, 1, n);
        n3 = CodedOutputStream.computeTagSize(2);
        n5 = CodedOutputStream.computeRawVarint32Size(n);
        n4 = SessionProtobufHelper.getEventAppExecutionSignalSize();
        int n6 = CodedOutputStream.computeTagSize(3);
        int n7 = CodedOutputStream.computeRawVarint32Size(n4);
        int n8 = SessionProtobufHelper.getBinaryImageSize(byteString, byteString2);
        return n2 + (n3 + n5 + n) + (n6 + n7 + n4) + (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(n8) + n8);
    }

    private static int getEventAppSize(TrimmedThrowableData object, Thread object2, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2) {
        n = SessionProtobufHelper.getEventAppExecutionSize((TrimmedThrowableData)object, (Thread)object2, arrstackTraceElement, arrthread, list, n, byteString, byteString2);
        int n3 = CodedOutputStream.computeTagSize(1);
        int n4 = CodedOutputStream.computeRawVarint32Size(n);
        boolean bl = false;
        n = n3 = n3 + n4 + n + 0;
        if (map != null) {
            object = map.entrySet().iterator();
            do {
                n = n3;
                if (!object.hasNext()) break;
                object2 = (Map.Entry)object.next();
                n = SessionProtobufHelper.getEventAppCustomAttributeSize((String)object2.getKey(), (String)object2.getValue());
                n3 += CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(n) + n;
            } while (true);
        }
        n3 = n;
        if (runningAppProcessInfo != null) {
            if (runningAppProcessInfo.importance != 100) {
                bl = true;
            }
            n3 = n + CodedOutputStream.computeBoolSize(3, bl);
        }
        return n3 + CodedOutputStream.computeUInt32Size(4, n2);
    }

    private static int getEventDeviceSize(Float f, int n, boolean bl, int n2, long l, long l2) {
        int n3 = 0;
        if (f != null) {
            n3 = 0 + CodedOutputStream.computeFloatSize(1, f.floatValue());
        }
        return n3 + CodedOutputStream.computeSInt32Size(2, n) + CodedOutputStream.computeBoolSize(3, bl) + CodedOutputStream.computeUInt32Size(4, n2) + CodedOutputStream.computeUInt64Size(5, l) + CodedOutputStream.computeUInt64Size(6, l2);
    }

    private static int getEventLogSize(ByteString byteString) {
        return CodedOutputStream.computeBytesSize(1, byteString);
    }

    private static int getFrameSize(StackTraceElement stackTraceElement, boolean bl) {
        int n;
        boolean bl2 = stackTraceElement.isNativeMethod();
        int n2 = 0;
        int n3 = bl2 ? CodedOutputStream.computeUInt64Size(1, Math.max(stackTraceElement.getLineNumber(), 0)) + 0 : CodedOutputStream.computeUInt64Size(1, 0L) + 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stackTraceElement.getClassName());
        stringBuilder.append(".");
        stringBuilder.append(stackTraceElement.getMethodName());
        n3 = n = n3 + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(stringBuilder.toString()));
        if (stackTraceElement.getFileName() != null) {
            n3 = n + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        n = n3;
        if (!stackTraceElement.isNativeMethod()) {
            n = n3;
            if (stackTraceElement.getLineNumber() > 0) {
                n = n3 + CodedOutputStream.computeUInt64Size(4, stackTraceElement.getLineNumber());
            }
        }
        n3 = n2;
        if (bl) {
            n3 = 2;
        }
        return n + CodedOutputStream.computeUInt32Size(5, n3);
    }

    private static int getSessionAppOrgSize(ByteString byteString) {
        return 0 + CodedOutputStream.computeBytesSize(1, byteString);
    }

    private static int getSessionAppSize(ByteString byteString, ByteString byteString2, ByteString byteString3, ByteString byteString4, ByteString byteString5, int n, ByteString byteString6) {
        int n2 = CodedOutputStream.computeBytesSize(1, byteString);
        int n3 = CodedOutputStream.computeBytesSize(2, byteString3);
        int n4 = CodedOutputStream.computeBytesSize(3, byteString4);
        int n5 = SessionProtobufHelper.getSessionAppOrgSize(byteString2);
        n2 = n3 = 0 + n2 + n3 + n4 + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(n5) + n5) + CodedOutputStream.computeBytesSize(6, byteString5);
        if (byteString6 != null) {
            n2 = n3 + CodedOutputStream.computeBytesSize(8, UNITY_PLATFORM_BYTE_STRING) + CodedOutputStream.computeBytesSize(9, byteString6);
        }
        return n2 + CodedOutputStream.computeEnumSize(10, n);
    }

    private static int getSessionDeviceSize(int n, ByteString object, int n2, long l, long l2, boolean bl, Map<IdManager.DeviceIdentifierType, String> object2, int n3, ByteString byteString, ByteString byteString2) {
        int n4 = CodedOutputStream.computeEnumSize(3, n);
        int n5 = 0;
        n = object == null ? 0 : CodedOutputStream.computeBytesSize(4, (ByteString)object);
        n2 = n = n4 + 0 + n + CodedOutputStream.computeUInt32Size(5, n2) + CodedOutputStream.computeUInt64Size(6, l) + CodedOutputStream.computeUInt64Size(7, l2) + CodedOutputStream.computeBoolSize(10, bl);
        if (object2 != null) {
            object = object2.entrySet().iterator();
            do {
                n2 = n;
                if (!object.hasNext()) break;
                object2 = (Map.Entry)object.next();
                n2 = SessionProtobufHelper.getDeviceIdentifierSize((IdManager.DeviceIdentifierType)((Object)object2.getKey()), (String)object2.getValue());
                n += CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(n2) + n2;
            } while (true);
        }
        n4 = CodedOutputStream.computeUInt32Size(12, n3);
        n = byteString == null ? 0 : CodedOutputStream.computeBytesSize(13, byteString);
        n3 = byteString2 == null ? n5 : CodedOutputStream.computeBytesSize(14, byteString2);
        return n2 + n4 + n + n3;
    }

    private static int getSessionEventSize(long l, String string, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2, ByteString byteString, ByteString byteString2, Float f, int n3, boolean bl, long l2, long l3, ByteString byteString3) {
        int n4 = CodedOutputStream.computeUInt64Size(1, l);
        int n5 = CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string));
        n = SessionProtobufHelper.getEventAppSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2, map, runningAppProcessInfo, n2);
        int n6 = CodedOutputStream.computeTagSize(3);
        int n7 = CodedOutputStream.computeRawVarint32Size(n);
        n2 = SessionProtobufHelper.getEventDeviceSize(f, n3, bl, n2, l2, l3);
        n = n2 = 0 + n4 + n5 + (n6 + n7 + n) + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(n2) + n2);
        if (byteString3 != null) {
            n = SessionProtobufHelper.getEventLogSize(byteString3);
            n = n2 + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(n) + n);
        }
        return n;
    }

    private static int getSessionOSSize(ByteString byteString, ByteString byteString2, boolean bl) {
        return 0 + CodedOutputStream.computeEnumSize(1, 3) + CodedOutputStream.computeBytesSize(2, byteString) + CodedOutputStream.computeBytesSize(3, byteString2) + CodedOutputStream.computeBoolSize(4, bl);
    }

    private static int getThreadSize(Thread thread, StackTraceElement[] arrstackTraceElement, int n, boolean bl) {
        int n2 = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, n);
        int n3 = arrstackTraceElement.length;
        for (n = 0; n < n3; ++n) {
            int n4 = SessionProtobufHelper.getFrameSize(arrstackTraceElement[n], bl);
            n2 += CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(n4) + n4;
        }
        return n2;
    }

    private static ByteString stringToByteString(String string) {
        if (string == null) {
            return null;
        }
        return ByteString.copyFromUtf8(string);
    }

    public static void writeBeginSession(CodedOutputStream codedOutputStream, String string, String string2, long l) throws Exception {
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(string2));
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(string));
        codedOutputStream.writeUInt64(3, l);
    }

    private static void writeFrame(CodedOutputStream codedOutputStream, int n, StackTraceElement stackTraceElement, boolean bl) throws Exception {
        codedOutputStream.writeTag(n, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getFrameSize(stackTraceElement, bl));
        if (stackTraceElement.isNativeMethod()) {
            codedOutputStream.writeUInt64(1, Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            codedOutputStream.writeUInt64(1, 0L);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stackTraceElement.getClassName());
        stringBuilder.append(".");
        stringBuilder.append(stackTraceElement.getMethodName());
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(stringBuilder.toString()));
        if (stackTraceElement.getFileName() != null) {
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        boolean bl2 = stackTraceElement.isNativeMethod();
        n = 4;
        if (!bl2 && stackTraceElement.getLineNumber() > 0) {
            codedOutputStream.writeUInt64(4, stackTraceElement.getLineNumber());
        }
        if (!bl) {
            n = 0;
        }
        codedOutputStream.writeUInt32(5, n);
    }

    public static void writeSessionApp(CodedOutputStream codedOutputStream, String object, String object2, String object3, String object4, String object5, int n, String string) throws Exception {
        ByteString byteString = ByteString.copyFromUtf8((String)object);
        object2 = ByteString.copyFromUtf8((String)object2);
        object3 = ByteString.copyFromUtf8((String)object3);
        object4 = ByteString.copyFromUtf8((String)object4);
        object5 = ByteString.copyFromUtf8((String)object5);
        object = string != null ? ByteString.copyFromUtf8(string) : null;
        codedOutputStream.writeTag(7, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionAppSize(byteString, (ByteString)object2, (ByteString)object3, (ByteString)object4, (ByteString)object5, n, (ByteString)object));
        codedOutputStream.writeBytes(1, byteString);
        codedOutputStream.writeBytes(2, (ByteString)object3);
        codedOutputStream.writeBytes(3, (ByteString)object4);
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionAppOrgSize((ByteString)object2));
        codedOutputStream.writeBytes(1, (ByteString)object2);
        codedOutputStream.writeBytes(6, (ByteString)object5);
        if (object != null) {
            codedOutputStream.writeBytes(8, UNITY_PLATFORM_BYTE_STRING);
            codedOutputStream.writeBytes(9, (ByteString)object);
        }
        codedOutputStream.writeEnum(10, n);
    }

    public static void writeSessionDevice(CodedOutputStream codedOutputStream, int n, String object, int n2, long l, long l2, boolean bl, Map<IdManager.DeviceIdentifierType, String> object2, int n3, String object3, String object42) throws Exception {
        ByteString byteString = SessionProtobufHelper.stringToByteString((String)object);
        object = SessionProtobufHelper.stringToByteString((String)object42);
        object3 = SessionProtobufHelper.stringToByteString((String)object3);
        codedOutputStream.writeTag(9, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionDeviceSize(n, byteString, n2, l, l2, bl, (Map<IdManager.DeviceIdentifierType, String>)((Object)object2), n3, (ByteString)object3, (ByteString)object));
        codedOutputStream.writeEnum(3, n);
        codedOutputStream.writeBytes(4, byteString);
        codedOutputStream.writeUInt32(5, n2);
        codedOutputStream.writeUInt64(6, l);
        codedOutputStream.writeUInt64(7, l2);
        codedOutputStream.writeBool(10, bl);
        for (Map.Entry entry : object2.entrySet()) {
            codedOutputStream.writeTag(11, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getDeviceIdentifierSize((IdManager.DeviceIdentifierType)((Object)entry.getKey()), (String)entry.getValue()));
            codedOutputStream.writeEnum(1, ((IdManager.DeviceIdentifierType)entry.getKey()).protobufIndex);
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String)entry.getValue()));
        }
        codedOutputStream.writeUInt32(12, n3);
        if (object3 != null) {
            codedOutputStream.writeBytes(13, (ByteString)object3);
        }
        if (object != null) {
            codedOutputStream.writeBytes(14, (ByteString)object);
        }
    }

    public static void writeSessionEvent(CodedOutputStream codedOutputStream, long l, String string, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, Map<String, String> map, LogFileManager logFileManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n, String object, String object2, Float f, int n2, boolean bl, long l2, long l3) throws Exception {
        ByteString byteString = ByteString.copyFromUtf8((String)object);
        object = object2 == null ? null : ByteString.copyFromUtf8(object2.replace("-", ""));
        object2 = logFileManager.getByteStringForLog();
        if (object2 == null) {
            Fabric.getLogger().d("CrashlyticsCore", "No log data to include with this event.");
        }
        logFileManager.clearLog();
        codedOutputStream.writeTag(10, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionEventSize(l, string, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, 8, map, runningAppProcessInfo, n, byteString, (ByteString)object, f, n2, bl, l2, l3, (ByteString)object2));
        codedOutputStream.writeUInt64(1, l);
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(string));
        SessionProtobufHelper.writeSessionEventApp(codedOutputStream, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, 8, byteString, (ByteString)object, map, runningAppProcessInfo, n);
        SessionProtobufHelper.writeSessionEventDevice(codedOutputStream, f, n2, bl, n, l2, l3);
        SessionProtobufHelper.writeSessionEventLog(codedOutputStream, (ByteString)object2);
    }

    private static void writeSessionEventApp(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2) throws Exception {
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2, map, runningAppProcessInfo, n2));
        SessionProtobufHelper.writeSessionEventAppExecution(codedOutputStream, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2);
        if (map != null && !map.isEmpty()) {
            SessionProtobufHelper.writeSessionEventAppCustomAttributes(codedOutputStream, map);
        }
        if (runningAppProcessInfo != null) {
            boolean bl = runningAppProcessInfo.importance != 100;
            codedOutputStream.writeBool(3, bl);
        }
        codedOutputStream.writeUInt32(4, n2);
    }

    private static void writeSessionEventAppCustomAttributes(CodedOutputStream codedOutputStream, Map<String, String> object2) throws Exception {
        for (Map.Entry entry : object2.entrySet()) {
            String string;
            void var1_6;
            codedOutputStream.writeTag(2, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppCustomAttributeSize((String)entry.getKey(), (String)entry.getValue()));
            codedOutputStream.writeBytes(1, ByteString.copyFromUtf8((String)entry.getKey()));
            String string2 = string = (String)entry.getValue();
            if (string == null) {
                String string3 = "";
            }
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String)var1_6));
        }
    }

    private static void writeSessionEventAppExecution(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2));
        SessionProtobufHelper.writeThread(codedOutputStream, thread, arrstackTraceElement, 4, true);
        int n2 = arrthread.length;
        for (int i = 0; i < n2; ++i) {
            SessionProtobufHelper.writeThread(codedOutputStream, arrthread[i], list.get(i), 0, false);
        }
        SessionProtobufHelper.writeSessionEventAppExecutionException(codedOutputStream, trimmedThrowableData, 1, n, 2);
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionSignalSize());
        codedOutputStream.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeUInt64(3, 0L);
        codedOutputStream.writeTag(4, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getBinaryImageSize(byteString, byteString2));
        codedOutputStream.writeUInt64(1, 0L);
        codedOutputStream.writeUInt64(2, 0L);
        codedOutputStream.writeBytes(3, byteString);
        if (byteString2 != null) {
            codedOutputStream.writeBytes(4, byteString2);
        }
    }

    private static void writeSessionEventAppExecutionException(CodedOutputStream codedOutputStream, TrimmedThrowableData object, int n, int n2, int n3) throws Exception {
        codedOutputStream.writeTag(n3, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionExceptionSize((TrimmedThrowableData)object, 1, n2));
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(object.className));
        Object object2 = object.localizedMessage;
        if (object2 != null) {
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8((String)object2));
        }
        object2 = object.stacktrace;
        int n4 = 0;
        int n5 = ((StackTraceElement[])object2).length;
        for (n3 = 0; n3 < n5; ++n3) {
            SessionProtobufHelper.writeFrame(codedOutputStream, 4, object2[n3], true);
        }
        object2 = object.cause;
        if (object2 != null) {
            n3 = n4;
            object = object2;
            if (n < n2) {
                SessionProtobufHelper.writeSessionEventAppExecutionException(codedOutputStream, (TrimmedThrowableData)object2, n + 1, n2, 6);
                return;
            }
            while (object != null) {
                object = object.cause;
                ++n3;
            }
            codedOutputStream.writeUInt32(7, n3);
        }
    }

    private static void writeSessionEventDevice(CodedOutputStream codedOutputStream, Float f, int n, boolean bl, int n2, long l, long l2) throws Exception {
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventDeviceSize(f, n, bl, n2, l, l2));
        if (f != null) {
            codedOutputStream.writeFloat(1, f.floatValue());
        }
        codedOutputStream.writeSInt32(2, n);
        codedOutputStream.writeBool(3, bl);
        codedOutputStream.writeUInt32(4, n2);
        codedOutputStream.writeUInt64(5, l);
        codedOutputStream.writeUInt64(6, l2);
    }

    private static void writeSessionEventLog(CodedOutputStream codedOutputStream, ByteString byteString) throws Exception {
        if (byteString != null) {
            codedOutputStream.writeTag(6, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventLogSize(byteString));
            codedOutputStream.writeBytes(1, byteString);
        }
    }

    public static void writeSessionOS(CodedOutputStream codedOutputStream, String object, String object2, boolean bl) throws Exception {
        object = ByteString.copyFromUtf8((String)object);
        object2 = ByteString.copyFromUtf8((String)object2);
        codedOutputStream.writeTag(8, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionOSSize((ByteString)object, (ByteString)object2, bl));
        codedOutputStream.writeEnum(1, 3);
        codedOutputStream.writeBytes(2, (ByteString)object);
        codedOutputStream.writeBytes(3, (ByteString)object2);
        codedOutputStream.writeBool(4, bl);
    }

    public static void writeSessionUser(CodedOutputStream codedOutputStream, String object, String string, String string2) throws Exception {
        int n;
        Object object2 = object;
        if (object == null) {
            object2 = "";
        }
        object = ByteString.copyFromUtf8((String)object2);
        object2 = SessionProtobufHelper.stringToByteString(string);
        ByteString byteString = SessionProtobufHelper.stringToByteString(string2);
        int n2 = n = 0 + CodedOutputStream.computeBytesSize(1, (ByteString)object);
        if (string != null) {
            n2 = n + CodedOutputStream.computeBytesSize(2, (ByteString)object2);
        }
        n = n2;
        if (string2 != null) {
            n = n2 + CodedOutputStream.computeBytesSize(3, byteString);
        }
        codedOutputStream.writeTag(6, 2);
        codedOutputStream.writeRawVarint32(n);
        codedOutputStream.writeBytes(1, (ByteString)object);
        if (string != null) {
            codedOutputStream.writeBytes(2, (ByteString)object2);
        }
        if (string2 != null) {
            codedOutputStream.writeBytes(3, byteString);
        }
    }

    private static void writeThread(CodedOutputStream codedOutputStream, Thread thread, StackTraceElement[] arrstackTraceElement, int n, boolean bl) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getThreadSize(thread, arrstackTraceElement, n, bl));
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        codedOutputStream.writeUInt32(2, n);
        int n2 = arrstackTraceElement.length;
        for (n = 0; n < n2; ++n) {
            SessionProtobufHelper.writeFrame(codedOutputStream, 3, arrstackTraceElement[n], bl);
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.InvalidSessionReport;
import com.crashlytics.android.core.NativeSessionReport;
import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.SessionReport;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReportUploader {
    static final Map<String, String> HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
    private static final short[] RETRY_INTERVALS = new short[]{10, 20, 30, 60, 120, 300};
    private final String apiKey;
    private final CreateReportSpiCall createReportCall;
    private final Object fileAccessLock = new Object();
    private final HandlingExceptionCheck handlingExceptionCheck;
    private final ReportFilesProvider reportFilesProvider;
    private Thread uploadThread;

    public ReportUploader(String string, CreateReportSpiCall createReportSpiCall, ReportFilesProvider reportFilesProvider, HandlingExceptionCheck handlingExceptionCheck) {
        if (createReportSpiCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportSpiCall;
        this.apiKey = string;
        this.reportFilesProvider = reportFilesProvider;
        this.handlingExceptionCheck = handlingExceptionCheck;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<Report> findReports() {
        File[] arrfile;
        StringBuilder stringBuilder;
        Object object;
        Object object2;
        File[] arrfile2;
        Fabric.getLogger().d("CrashlyticsCore", "Checking for crash reports...");
        LinkedList<Report> linkedList = this.fileAccessLock;
        synchronized (linkedList) {
            object2 = this.reportFilesProvider.getCompleteSessionFiles();
            arrfile = this.reportFilesProvider.getInvalidSessionFiles();
            arrfile2 = this.reportFilesProvider.getNativeReportFiles();
        }
        linkedList = new LinkedList<Report>();
        int n = 0;
        if (object2 != null) {
            for (Object object3 : object2) {
                object = Fabric.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Found crash report ");
                stringBuilder.append(object3.getPath());
                object.d("CrashlyticsCore", stringBuilder.toString());
                linkedList.add(new SessionReport((File)object3));
            }
        }
        object2 = new HashMap();
        if (arrfile != null) {
            for (Object object3 : arrfile) {
                object = CrashlyticsController.getSessionIdFromSessionFile((File)object3);
                if (!object2.containsKey(object)) {
                    object2.put(object, new LinkedList());
                }
                ((List)object2.get(object)).add(object3);
            }
        }
        for (Object object3 : object2.keySet()) {
            object = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Found invalid session: ");
            stringBuilder.append((String)object3);
            object.d("CrashlyticsCore", stringBuilder.toString());
            object = (List)object2.get(object3);
            linkedList.add(new InvalidSessionReport((String)object3, object.toArray(new File[object.size()])));
        }
        if (arrfile2 != null) {
            int n2 = arrfile2.length;
            for (int i = n; i < n2; ++i) {
                linkedList.add(new NativeSessionReport(arrfile2[i]));
            }
        }
        if (linkedList.isEmpty()) {
            Fabric.getLogger().d("CrashlyticsCore", "No reports found.");
        }
        return linkedList;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    boolean forceUpload(Report report) {
        boolean bl3;
        Object object = this.fileAccessLock;
        // MONITORENTER : object
        boolean bl = false;
        Object object2 = new CreateReportRequest(this.apiKey, report);
        boolean bl2 = this.createReportCall.invoke((CreateReportRequest)object2);
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics report upload ");
        object2 = bl2 ? "complete: " : "FAILED: ";
        catch (Exception exception) {
            Logger logger2 = Fabric.getLogger();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Error occurred sending report ");
            stringBuilder2.append(report);
            logger2.e("CrashlyticsCore", stringBuilder2.toString(), exception);
            bl3 = bl;
            return bl3;
        }
        stringBuilder.append((String)object2);
        stringBuilder.append(report.getIdentifier());
        logger.i("CrashlyticsCore", stringBuilder.toString());
        bl3 = bl;
        if (bl2) {
            report.remove();
            return true;
        }
        // MONITOREXIT : object
        return bl3;
    }

    boolean isUploading() {
        if (this.uploadThread != null) {
            return true;
        }
        return false;
    }

    public void uploadReports(float f, SendCheck sendCheck) {
        synchronized (this) {
            if (this.uploadThread != null) {
                Fabric.getLogger().d("CrashlyticsCore", "Report upload has already been started.");
                return;
            }
            this.uploadThread = new Thread((Runnable)new Worker(f, sendCheck), "Crashlytics Report Uploader");
            this.uploadThread.start();
            return;
        }
    }

    static final class AlwaysSendCheck
    implements SendCheck {
        AlwaysSendCheck() {
        }

        @Override
        public boolean canSendReports() {
            return true;
        }
    }

    static interface HandlingExceptionCheck {
        public boolean isHandlingException();
    }

    static interface ReportFilesProvider {
        public File[] getCompleteSessionFiles();

        public File[] getInvalidSessionFiles();

        public File[] getNativeReportFiles();
    }

    static interface SendCheck {
        public boolean canSendReports();
    }

    private class Worker
    extends BackgroundPriorityRunnable {
        private final float delay;
        private final SendCheck sendCheck;

        Worker(float f, SendCheck sendCheck) {
            this.delay = f;
            this.sendCheck = sendCheck;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void attemptUploadWithRetry() {
            block11 : {
                List<Report> list;
                block12 : {
                    list = Fabric.getLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Starting report processing in ");
                    stringBuilder.append(this.delay);
                    stringBuilder.append(" second(s)...");
                    list.d("CrashlyticsCore", stringBuilder.toString());
                    if (this.delay > 0.0f) {
                        Thread.sleep((long)(this.delay * 1000.0f));
                    }
                    list = ReportUploader.this.findReports();
                    if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                        return;
                    }
                    if (!list.isEmpty() && !this.sendCheck.canSendReports()) {
                        Logger logger = Fabric.getLogger();
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("User declined to send. Removing ");
                        stringBuilder2.append(list.size());
                        stringBuilder2.append(" Report(s).");
                        logger.d("CrashlyticsCore", stringBuilder2.toString());
                        list = list.iterator();
                        while (list.hasNext()) {
                            ((Report)list.next()).remove();
                        }
                        return;
                    }
                    break block12;
                    catch (InterruptedException interruptedException) {}
                    Thread.currentThread().interrupt();
                    return;
                }
                int n = 0;
                while (!list.isEmpty()) {
                    List<Report> list2;
                    if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                        return;
                    }
                    Logger logger = Fabric.getLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Attempting to send ");
                    stringBuilder.append(list.size());
                    stringBuilder.append(" report(s)");
                    logger.d("CrashlyticsCore", stringBuilder.toString());
                    for (Report report : list) {
                        ReportUploader.this.forceUpload(report);
                    }
                    list = list2 = ReportUploader.this.findReports();
                    if (list2.isEmpty()) continue;
                    long l = RETRY_INTERVALS[Math.min(n, RETRY_INTERVALS.length - 1)];
                    list = Fabric.getLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Report submisson: scheduling delayed retry in ");
                    stringBuilder.append(l);
                    stringBuilder.append(" seconds");
                    list.d("CrashlyticsCore", stringBuilder.toString());
                    try {
                        Thread.sleep(l * 1000L);
                        ++n;
                        list = list2;
                        continue;
                    }
                    catch (InterruptedException interruptedException) {}
                    break block11;
                }
                return;
            }
            Thread.currentThread().interrupt();
        }

        @Override
        public void onRun() {
            try {
                this.attemptUploadWithRetry();
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "An unexpected error occurred while attempting to upload crash reports.", exception);
            }
            ReportUploader.this.uploadThread = null;
        }
    }

}

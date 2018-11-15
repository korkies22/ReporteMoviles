/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.DefaultCreateReportSpiCall;
import com.crashlytics.android.core.NativeCreateReportSpiCall;
import com.crashlytics.android.core.Report;

class CompositeCreateReportSpiCall
implements CreateReportSpiCall {
    private final DefaultCreateReportSpiCall javaReportSpiCall;
    private final NativeCreateReportSpiCall nativeReportSpiCall;

    public CompositeCreateReportSpiCall(DefaultCreateReportSpiCall defaultCreateReportSpiCall, NativeCreateReportSpiCall nativeCreateReportSpiCall) {
        this.javaReportSpiCall = defaultCreateReportSpiCall;
        this.nativeReportSpiCall = nativeCreateReportSpiCall;
    }

    @Override
    public boolean invoke(CreateReportRequest createReportRequest) {
        switch (.$SwitchMap$com$crashlytics$android$core$Report$Type[createReportRequest.report.getType().ordinal()]) {
            default: {
                return false;
            }
            case 2: {
                this.nativeReportSpiCall.invoke(createReportRequest);
                return true;
            }
            case 1: 
        }
        this.javaReportSpiCall.invoke(createReportRequest);
        return true;
    }

}

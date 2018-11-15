/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android;

import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.InitializationException;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;

class InitializationTask<Result>
extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    private TimingMetric createAndStartTimingMetric(String object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.kit.getIdentifier());
        stringBuilder.append(".");
        stringBuilder.append((String)object);
        object = new TimingMetric(stringBuilder.toString(), TIMING_METRIC_TAG);
        object.startMeasuring();
        return object;
    }

    protected /* varargs */ Result doInBackground(Void ... object) {
        TimingMetric timingMetric = this.createAndStartTimingMetric("doInBackground");
        object = !this.isCancelled() ? this.kit.doInBackground() : null;
        timingMetric.stopMeasuring();
        return (Result)object;
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    protected void onCancelled(Result object) {
        this.kit.onCancelled(object);
        object = new StringBuilder();
        object.append(this.kit.getIdentifier());
        object.append(" Initialization was cancelled");
        object = new InitializationException(object.toString());
        this.kit.initializationCallback.failure((Exception)object);
    }

    @Override
    protected void onPostExecute(Result Result) {
        this.kit.onPostExecute(Result);
        this.kit.initializationCallback.success(Result);
    }

    /*
     * Exception decompiling
     */
    @Override
    protected void onPreExecute() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:401)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }
}

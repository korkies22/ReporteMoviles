/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements IabHelper.OnIabPurchaseFinishedListener {
    PurchaseService() {
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onIabPurchaseFinished(IabResult var1_1, Purchase var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
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

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import android.os.Bundle;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import java.util.List;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieResumeGameDialogFragment extends RookieInfoDialogFragment
{
    private ResumeGameDialogListener _resumeGameDialogListener;
    
    public RookieResumeGameDialogFragment() {
        this.setCancelable(true);
    }
    
    private void notifyListenerDiscard() {
        if (this._resumeGameDialogListener != null) {
            this._resumeGameDialogListener.onDiscardClicked();
        }
    }
    
    private void notifyListenerResume() {
        if (this._resumeGameDialogListener != null) {
            this._resumeGameDialogListener.onResumeClicked();
        }
    }
    
    @Override
    protected List<CustomButton> getButtons() {
        final LinkedList<CustomButtonNeutral> list = (LinkedList<CustomButtonNeutral>)new LinkedList<CustomButton>();
        final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689952);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RookieResumeGameDialogFragment.this.notifyListenerDiscard();
                RookieResumeGameDialogFragment.this.dismiss();
            }
        });
        list.add(customButtonNeutral);
        final CustomButtonPositive customButtonPositive = new CustomButtonPositive((Context)this.getActivity());
        customButtonPositive.setText(2131690272);
        customButtonPositive.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RookieResumeGameDialogFragment.this.notifyListenerResume();
                RookieResumeGameDialogFragment.this.dismiss();
            }
        });
        list.add((CustomButtonNeutral)customButtonPositive);
        return (List<CustomButton>)list;
    }
    
    @Override
    protected RookieType getRookieType() {
        return RookieType.RUNNING_ENGINE;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getString(2131690276));
    }
    
    public void setResumeGameDialogListener(final ResumeGameDialogListener resumeGameDialogListener) {
        this._resumeGameDialogListener = resumeGameDialogListener;
    }
    
    public interface ResumeGameDialogListener
    {
        void onDiscardClicked();
        
        void onResumeClicked();
    }
}

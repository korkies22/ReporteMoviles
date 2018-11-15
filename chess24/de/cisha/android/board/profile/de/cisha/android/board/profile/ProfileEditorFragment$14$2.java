/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;

class ProfileEditorFragment
implements Runnable {
    final /* synthetic */ List val$errors;

    ProfileEditorFragment(List list) {
        this.val$errors = list;
    }

    @Override
    public void run() {
        14.this.this$0.getView().post(new Runnable(){

            @Override
            public void run() {
                14.this.this$0.hideDialogWaiting();
                if (14.this.this$0._dialogEditEmail != null) {
                    14.this.this$0._dialogEditEmail.setErrors(2.this.val$errors);
                }
            }
        });
    }

}

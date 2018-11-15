/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.profile.EditPasswordDialogFragment;
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
        13.this.this$0.getView().post(new Runnable(){

            @Override
            public void run() {
                if (13.this.this$0._dialogEditPassword != null && 1.this.val$errors != null) {
                    13.this.this$0._dialogEditPassword.setErrors(1.this.val$errors);
                }
                13.this.this$0.hideDialogWaiting();
            }
        });
    }

}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package de.cisha.android.board.profile;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import de.cisha.android.board.profile.GenderOption;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.util.LinkedList;
import java.util.List;

class ProfileEditorFragment
implements View.OnClickListener {
    ProfileEditorFragment() {
    }

    public void onClick(View object) {
        ProfileEditorFragment.this._sexField.getEditText().requestFocus();
        OptionDialogFragment<View> optionDialogFragment = new OptionDialogFragment<View>();
        optionDialogFragment.setTitle((CharSequence)ProfileEditorFragment.this.getString(2131690200));
        LinkedList<Object> linkedList = new LinkedList<Object>();
        object = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.MALE.getNameResId()), User.Gender.MALE);
        linkedList.add(object);
        GenderOption genderOption = new GenderOption(ProfileEditorFragment.this.getString(User.Gender.FEMALE.getNameResId()), User.Gender.FEMALE);
        linkedList.add(genderOption);
        if (ProfileEditorFragment.this._selectedGender != User.Gender.MALE) {
            object = genderOption;
        }
        optionDialogFragment.setOptions((List<View>)linkedList, (View)object);
        optionDialogFragment.setOptionSelectionListener(new OptionDialogFragment.OptionSelectionListener<GenderOption>(){

            @Override
            public void onOptionSelected(GenderOption genderOption) {
                ProfileEditorFragment.this._selectedGender = genderOption.getSex();
                ProfileEditorFragment.this._sexField.setText(genderOption.getName());
            }
        });
        optionDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), null);
    }

}

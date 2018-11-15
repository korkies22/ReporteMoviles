/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;
import de.cisha.android.ui.patterns.input.CustomRadioButton;
import java.util.List;

public class OptionDialogFragment<T extends Option>
extends EmptyDialogFragment {
    private OptionSelectionListener<T> _listener;
    private List<T> _options;
    private T _selectedOption;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle object) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, (Bundle)object);
        for (final Option option : this._options) {
            View view = layoutInflater.inflate(R.layout.option_view, viewGroup, false);
            TextView textView = (TextView)view.findViewById(R.id.option_view_name);
            ((CustomRadioButton)view.findViewById(R.id.option_dialog_selection_indicator)).setBackgroundResource(0);
            textView.setText((CharSequence)option.getName());
            view.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    if (OptionDialogFragment.this._listener != null) {
                        OptionDialogFragment.this._listener.onOptionSelected(option);
                    }
                    OptionDialogFragment.this.dismiss();
                }
            });
            if (option == this._selectedOption) {
                ((CustomRadioButton)view.findViewById(R.id.option_dialog_selection_indicator)).setChecked(true);
            }
            viewGroup.addView(view);
        }
        return viewGroup;
    }

    public void setOptionSelectionListener(OptionSelectionListener<T> optionSelectionListener) {
        this._listener = optionSelectionListener;
    }

    public void setOptions(List<T> list, T t) {
        this._options = list;
        this._selectedOption = t;
    }

    public static class Option {
        private String _name;

        public Option(String string) {
            this._name = string;
        }

        public String getName() {
            return this._name;
        }
    }

    public static interface OptionSelectionListener<Type extends Option> {
        public void onOptionSelected(Type var1);
    }

}

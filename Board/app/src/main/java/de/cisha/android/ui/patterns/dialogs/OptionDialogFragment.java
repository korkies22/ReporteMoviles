// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import java.util.Iterator;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.input.CustomRadioButton;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.List;

public class OptionDialogFragment<T extends Option> extends EmptyDialogFragment
{
    private OptionSelectionListener<T> _listener;
    private List<T> _options;
    private T _selectedOption;
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, final Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        for (final Option option : this._options) {
            final View inflate = layoutInflater.inflate(R.layout.option_view, viewGroup, false);
            final TextView textView = (TextView)inflate.findViewById(R.id.option_view_name);
            ((CustomRadioButton)inflate.findViewById(R.id.option_dialog_selection_indicator)).setBackgroundResource(0);
            textView.setText((CharSequence)option.getName());
            inflate.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (OptionDialogFragment.this._listener != null) {
                        OptionDialogFragment.this._listener.onOptionSelected(option);
                    }
                    OptionDialogFragment.this.dismiss();
                }
            });
            if (option == this._selectedOption) {
                ((CustomRadioButton)inflate.findViewById(R.id.option_dialog_selection_indicator)).setChecked(true);
            }
            viewGroup.addView(inflate);
        }
        return (View)viewGroup;
    }
    
    public void setOptionSelectionListener(final OptionSelectionListener<T> listener) {
        this._listener = listener;
    }
    
    public void setOptions(final List<T> options, final T selectedOption) {
        this._options = options;
        this._selectedOption = selectedOption;
    }
    
    public static class Option
    {
        private String _name;
        
        public Option(final String name) {
            this._name = name;
        }
        
        public String getName() {
            return this._name;
        }
    }
    
    public interface OptionSelectionListener<Type extends Option>
    {
        void onOptionSelected(final Type p0);
    }
}

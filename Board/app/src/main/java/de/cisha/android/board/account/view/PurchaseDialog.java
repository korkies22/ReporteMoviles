// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.view;

import android.widget.TextView;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.os.Bundle;
import java.util.Iterator;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import de.cisha.android.board.account.model.Product;
import java.util.List;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;

public class PurchaseDialog extends AbstractDialogFragment implements ModelChangeListener<List<Product>>
{
    private int _descrRes;
    private IPurchaseDialogListener _dialogListener;
    private ViewGroup _itemsContainer;
    private ModelHolder<List<Product>> _productsHolder;
    private boolean _purchaseEnabled;
    private int _titleRes;
    
    public PurchaseDialog() {
        this._titleRes = 2131690301;
        this._descrRes = 2131690304;
    }
    
    public static PurchaseDialog createInstance(final int titleRes, final int descrRes, final boolean purchaseEnabled, final ModelHolder<List<Product>> productsHolder, final IPurchaseDialogListener dialogListener) {
        final PurchaseDialog purchaseDialog = new PurchaseDialog();
        purchaseDialog._titleRes = titleRes;
        purchaseDialog._descrRes = descrRes;
        (purchaseDialog._productsHolder = productsHolder).addModelChangeListener((ModelHolder.ModelChangeListener<List<Product>>)purchaseDialog);
        purchaseDialog._dialogListener = dialogListener;
        purchaseDialog._purchaseEnabled = purchaseEnabled;
        return purchaseDialog;
    }
    
    private View.OnClickListener createOnClickListerForProduct(final Product product) {
        return (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                PurchaseDialog.this.purchaseProduct(product);
            }
        };
    }
    
    private View.OnClickListener createRestoreOnClickListener() {
        return (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (PurchaseDialog.this._dialogListener != null) {
                    PurchaseDialog.this._dialogListener.onRestorePressed();
                    PurchaseDialog.this.dismiss();
                }
            }
        };
    }
    
    private void fillItemsContainer() {
        if (this._itemsContainer != null && this._productsHolder != null && this._productsHolder.getModel() != null) {
            final List<Product> list = this._productsHolder.getModel();
            if (list.size() > 0) {
                this._itemsContainer.removeAllViews();
                for (final Product product : list) {
                    final PurchaseItemView purchaseItemView = new PurchaseItemView((Context)this.getActivity(), product);
                    purchaseItemView.setBuyButtonClickListener(this.createOnClickListerForProduct(product));
                    purchaseItemView.setEnabled(this._purchaseEnabled);
                    this._itemsContainer.addView((View)purchaseItemView);
                }
            }
        }
    }
    
    private void purchaseProduct(final Product product) {
        if (this._dialogListener != null) {
            this._dialogListener.onSelectProduct(product);
            this.dismiss();
        }
    }
    
    public void modelChanged(final List<Product> list) {
        this.fillItemsContainer();
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setAnimationEnabled(false);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        return layoutInflater.inflate(2131427553, viewGroup, false);
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._itemsContainer = (ViewGroup)view.findViewById(2131297062);
        view.findViewById(2131297063).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                PurchaseDialog.this.dismiss();
            }
        });
        view.findViewById(2131297066).setOnClickListener(this.createRestoreOnClickListener());
        final TextView textView = (TextView)view.findViewById(2131297069);
        final TextView textView2 = (TextView)view.findViewById(2131297068);
        textView.setText(this._titleRes);
        textView2.setText(this._descrRes);
        this.fillItemsContainer();
    }
    
    public interface IPurchaseDialogListener
    {
        void onRestorePressed();
        
        void onSelectProduct(final Product p0);
    }
}

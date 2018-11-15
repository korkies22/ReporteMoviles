/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.account.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.view.PurchaseItemView;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import java.util.Iterator;
import java.util.List;

public class PurchaseDialog
extends AbstractDialogFragment
implements ModelHolder.ModelChangeListener<List<Product>> {
    private int _descrRes = 2131690304;
    private IPurchaseDialogListener _dialogListener;
    private ViewGroup _itemsContainer;
    private ModelHolder<List<Product>> _productsHolder;
    private boolean _purchaseEnabled;
    private int _titleRes = 2131690301;

    public static PurchaseDialog createInstance(int n, int n2, boolean bl, ModelHolder<List<Product>> modelHolder, IPurchaseDialogListener iPurchaseDialogListener) {
        PurchaseDialog purchaseDialog = new PurchaseDialog();
        purchaseDialog._titleRes = n;
        purchaseDialog._descrRes = n2;
        purchaseDialog._productsHolder = modelHolder;
        modelHolder.addModelChangeListener(purchaseDialog);
        purchaseDialog._dialogListener = iPurchaseDialogListener;
        purchaseDialog._purchaseEnabled = bl;
        return purchaseDialog;
    }

    private View.OnClickListener createOnClickListerForProduct(final Product product) {
        return new View.OnClickListener(){

            public void onClick(View view) {
                PurchaseDialog.this.purchaseProduct(product);
            }
        };
    }

    private View.OnClickListener createRestoreOnClickListener() {
        return new View.OnClickListener(){

            public void onClick(View view) {
                if (PurchaseDialog.this._dialogListener != null) {
                    PurchaseDialog.this._dialogListener.onRestorePressed();
                    PurchaseDialog.this.dismiss();
                }
            }
        };
    }

    private void fillItemsContainer() {
        Object object;
        if (this._itemsContainer != null && this._productsHolder != null && this._productsHolder.getModel() != null && (object = this._productsHolder.getModel()).size() > 0) {
            this._itemsContainer.removeAllViews();
            object = object.iterator();
            while (object.hasNext()) {
                Product product = (Product)object.next();
                PurchaseItemView purchaseItemView = new PurchaseItemView((Context)this.getActivity(), product);
                purchaseItemView.setBuyButtonClickListener(this.createOnClickListerForProduct(product));
                purchaseItemView.setEnabled(this._purchaseEnabled);
                this._itemsContainer.addView((View)purchaseItemView);
            }
        }
    }

    private void purchaseProduct(Product product) {
        if (this._dialogListener != null) {
            this._dialogListener.onSelectProduct(product);
            this.dismiss();
        }
    }

    @Override
    public void modelChanged(List<Product> list) {
        this.fillItemsContainer();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setAnimationEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2131427553, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._itemsContainer = (ViewGroup)view.findViewById(2131297062);
        view.findViewById(2131297063).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PurchaseDialog.this.dismiss();
            }
        });
        view.findViewById(2131297066).setOnClickListener(this.createRestoreOnClickListener());
        bundle = (TextView)view.findViewById(2131297069);
        view = (TextView)view.findViewById(2131297068);
        bundle.setText(this._titleRes);
        view.setText(this._descrRes);
        this.fillItemsContainer();
    }

    public static interface IPurchaseDialogListener {
        public void onRestorePressed();

        public void onSelectProduct(Product var1);
    }

}

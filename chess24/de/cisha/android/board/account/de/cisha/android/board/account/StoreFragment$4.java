/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.account;

import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

class StoreFragment
extends LoadCommandCallbackWithTimeout<List<Product>> {
    StoreFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        StoreFragment.this._flagLoadingProductsFailed = true;
    }

    @Override
    protected void succeded(List<Product> object) {
        LinkedList<Product> linkedList = new LinkedList<Product>();
        LinkedList<Product> linkedList2 = new LinkedList<Product>();
        object = object.iterator();
        while (object.hasNext()) {
            Product product = (Product)object.next();
            if (product.getProductInformation().isWebPremium()) {
                linkedList2.add(product);
                continue;
            }
            linkedList.add(product);
        }
        StoreFragment.this._mobileSubscriptionHolder.setModel(linkedList);
        StoreFragment.this._webSubscriptionHolder.setModel(linkedList2);
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.view;

import android.view.View;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ViewSelectionHelper {
    public static /* varargs */ <ViewType extends View> void initExclusiveSelectionForViewSet(View view, ResourceSelectionAdapter<ViewType> resourceSelectionAdapter, int n, Integer ... object) {
        Object object2 = Arrays.asList(object);
        object = new View.OnClickListener((List)object2, resourceSelectionAdapter, view){
            final /* synthetic */ View val$parentView;
            final /* synthetic */ List val$selectableViews;
            final /* synthetic */ ResourceSelectionAdapter val$selectionAdapter;
            {
                this.val$selectableViews = list;
                this.val$selectionAdapter = resourceSelectionAdapter;
                this.val$parentView = view;
            }

            public void onClick(View view) {
                for (Integer n : this.val$selectableViews) {
                    ResourceSelectionAdapter resourceSelectionAdapter = this.val$selectionAdapter;
                    View view2 = this.val$parentView.findViewById(n.intValue());
                    boolean bl = n.intValue() == view.getId();
                    resourceSelectionAdapter.selectView(view2, bl);
                }
                this.val$selectionAdapter.onResourceSelected(view.getId());
            }
        };
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Integer n2 = (Integer)object2.next();
            View view2 = view.findViewById(n2.intValue());
            resourceSelectionAdapter.getClickableFrom((ViewType)view2).setOnClickListener((View.OnClickListener)object);
            boolean bl = n2 == n;
            resourceSelectionAdapter.selectView((ViewType)view2, bl);
        }
    }

    public static /* varargs */ <ViewType extends View> void initExclusiveSelectionForViewSet(ResourceSelectionAdapter<ViewType> resourceSelectionAdapter, ViewType ViewType, ViewType ... object) {
        Object object2 = Arrays.asList(object);
        object = new View.OnClickListener((List)object2, resourceSelectionAdapter){
            final /* synthetic */ List val$selectableViews;
            final /* synthetic */ ResourceSelectionAdapter val$selectionAdapter;
            {
                this.val$selectableViews = list;
                this.val$selectionAdapter = resourceSelectionAdapter;
            }

            public void onClick(View view) {
                for (View view2 : this.val$selectableViews) {
                    ResourceSelectionAdapter resourceSelectionAdapter = this.val$selectionAdapter;
                    boolean bl = view2 == view;
                    resourceSelectionAdapter.selectView(view2, bl);
                }
                this.val$selectionAdapter.onResourceSelected(view.getId());
            }
        };
        object2 = object2.iterator();
        while (object2.hasNext()) {
            View view = (View)object2.next();
            resourceSelectionAdapter.getClickableFrom((ViewType)view).setOnClickListener(object);
            boolean bl = view == ViewType;
            resourceSelectionAdapter.selectView((ViewType)view, bl);
        }
    }

    public static interface ResourceSelectionAdapter<Type extends View> {
        public View getClickableFrom(Type var1);

        public void onResourceSelected(int var1);

        public void selectView(Type var1, boolean var2);
    }

}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import java.util.Iterator;
import java.util.List;
import android.view.View.OnClickListener;
import java.util.Arrays;
import android.view.View;

public class ViewSelectionHelper
{
    public static <ViewType extends View> void initExclusiveSelectionForViewSet(final View view, final ResourceSelectionAdapter<ViewType> resourceSelectionAdapter, final int n, final Integer... array) {
        final List<Integer> list = Arrays.asList(array);
        final View.OnClickListener onClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                for (final Integer n : list) {
                    resourceSelectionAdapter.selectView(view.findViewById((int)n), n == view.getId());
                }
                resourceSelectionAdapter.onResourceSelected(view.getId());
            }
        };
        for (final Integer n2 : list) {
            final View viewById = view.findViewById((int)n2);
            resourceSelectionAdapter.getClickableFrom(viewById).setOnClickListener((View.OnClickListener)onClickListener);
            resourceSelectionAdapter.selectView(viewById, n2 == n);
        }
    }
    
    public static <ViewType extends View> void initExclusiveSelectionForViewSet(final ResourceSelectionAdapter<ViewType> resourceSelectionAdapter, final ViewType viewType, final ViewType... array) {
        final List<ViewType> list = Arrays.asList(array);
        final View.OnClickListener onClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                for (final View view2 : list) {
                    resourceSelectionAdapter.selectView(view2, view2 == view);
                }
                resourceSelectionAdapter.onResourceSelected(view.getId());
            }
        };
        for (final View view : list) {
            resourceSelectionAdapter.getClickableFrom((ViewType)view).setOnClickListener((View.OnClickListener)onClickListener);
            resourceSelectionAdapter.selectView((ViewType)view, view == viewType);
        }
    }
    
    public interface ResourceSelectionAdapter<Type extends View>
    {
        View getClickableFrom(final Type p0);
        
        void onResourceSelected(final int p0);
        
        void selectView(final Type p0, final boolean p1);
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.navigationbar;

import android.content.res.TypedArray;
import android.view.animation.Transformation;
import android.graphics.drawable.StateListDrawable;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MenuBarItem extends LinearLayout
{
    private ImageView _imageView;
    private boolean _necessaryInMainBar;
    private boolean _selectable;
    private int _selectionGroup;
    private TextView _textView;
    
    public MenuBarItem(Context obtainStyledAttributes, final AttributeSet set) {
        super(obtainStyledAttributes, set);
        this._necessaryInMainBar = false;
        this._selectionGroup = 0;
        this._selectable = true;
        this.initLayout(obtainStyledAttributes);
        obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.MenuBarItem);
        try {
            this.setTitle(((TypedArray)obtainStyledAttributes).getString(R.styleable.MenuBarItem_text));
            final Drawable drawable = ((TypedArray)obtainStyledAttributes).getDrawable(R.styleable.MenuBarItem_mIcon);
            if (drawable != null) {
                this._imageView.setImageDrawable(drawable);
            }
            else {
                this.setIconDrawablesForStates(((TypedArray)obtainStyledAttributes).getDrawable(R.styleable.MenuBarItem_icon_drawable_usual), ((TypedArray)obtainStyledAttributes).getDrawable(R.styleable.MenuBarItem_icon_drawable_selected), ((TypedArray)obtainStyledAttributes).getDrawable(R.styleable.MenuBarItem_icon_drawable_disabled));
            }
            this._selectionGroup = ((TypedArray)obtainStyledAttributes).getInt(R.styleable.MenuBarItem_selection_group, 0);
            this._selectable = ((TypedArray)obtainStyledAttributes).getBoolean(R.styleable.MenuBarItem_selectable, true);
        }
        finally {
            ((TypedArray)obtainStyledAttributes).recycle();
        }
    }
    
    public MenuBarItem(final Context p0, final String p1, final int p2, final int p3, final int p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_0        
        //     1: aload_1        
        //     2: invokespecial   android/widget/LinearLayout.<init>:(Landroid/content/Context;)V
        //     5: aload_0        
        //     6: iconst_0       
        //     7: putfield        de/cisha/android/ui/patterns/navigationbar/MenuBarItem._necessaryInMainBar:Z
        //    10: aload_0        
        //    11: iconst_0       
        //    12: putfield        de/cisha/android/ui/patterns/navigationbar/MenuBarItem._selectionGroup:I
        //    15: aload_0        
        //    16: iconst_1       
        //    17: putfield        de/cisha/android/ui/patterns/navigationbar/MenuBarItem._selectable:Z
        //    20: aload_0        
        //    21: aload_1        
        //    22: invokespecial   de/cisha/android/ui/patterns/navigationbar/MenuBarItem.initLayout:(Landroid/content/Context;)V
        //    25: aload_0        
        //    26: aload_2        
        //    27: invokevirtual   de/cisha/android/ui/patterns/navigationbar/MenuBarItem.setTitle:(Ljava/lang/String;)V
        //    30: aload_1        
        //    31: invokevirtual   android/content/Context.getResources:()Landroid/content/res/Resources;
        //    34: iload_3        
        //    35: invokevirtual   android/content/res/Resources.getDrawable:(I)Landroid/graphics/drawable/Drawable;
        //    38: astore          6
        //    40: aload_1        
        //    41: invokevirtual   android/content/Context.getResources:()Landroid/content/res/Resources;
        //    44: iload           4
        //    46: invokevirtual   android/content/res/Resources.getDrawable:(I)Landroid/graphics/drawable/Drawable;
        //    49: astore_2       
        //    50: goto            55
        //    53: aconst_null    
        //    54: astore_2       
        //    55: aload_1        
        //    56: invokevirtual   android/content/Context.getResources:()Landroid/content/res/Resources;
        //    59: iload           5
        //    61: invokevirtual   android/content/res/Resources.getDrawable:(I)Landroid/graphics/drawable/Drawable;
        //    64: astore_1       
        //    65: goto            70
        //    68: aconst_null    
        //    69: astore_1       
        //    70: aload_0        
        //    71: aload           6
        //    73: aload_2        
        //    74: aload_1        
        //    75: invokevirtual   de/cisha/android/ui/patterns/navigationbar/MenuBarItem.setIconDrawablesForStates:(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V
        //    78: return         
        //    79: astore_2       
        //    80: goto            53
        //    83: astore_1       
        //    84: goto            68
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                             
        //  -----  -----  -----  -----  -------------------------------------------------
        //  40     50     79     55     Landroid/content/res/Resources.NotFoundException;
        //  55     65     83     70     Landroid/content/res/Resources.NotFoundException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0055:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:692)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:529)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void initLayout(final Context context) {
        this.setOrientation(1);
        this.setGravity(17);
        inflate(context, R.layout.menubar_item, (ViewGroup)this);
        this.setClickable(true);
        this._imageView = (ImageView)this.findViewById(R.id.playzone_after_game_menubar_item_image);
        this._textView = (TextView)this.findViewById(R.id.playzone_after_game_menubar_item_text);
        this.setTitle(null);
    }
    
    public int getSelectionGroup() {
        return this._selectionGroup;
    }
    
    public boolean isNecessaryInMainBar() {
        return this._necessaryInMainBar;
    }
    
    public boolean isSelectable() {
        return this._selectable;
    }
    
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        this._imageView.setEnabled(enabled);
        this._textView.setEnabled(enabled);
    }
    
    public void setGlowing(final boolean b) {
        if (b) {
            this._imageView.startAnimation((Animation)new GlowAnimation(this._imageView));
            return;
        }
        final Animation animation = this._imageView.getAnimation();
        if (animation != null) {
            animation.cancel();
            this._imageView.setAnimation((Animation)null);
            this._imageView.setAlpha(255);
        }
    }
    
    public void setIconDrawablesForStates(final Drawable drawable, final Drawable drawable2, final Drawable drawable3) {
        final StateListDrawable mIcon = new StateListDrawable();
        if (drawable3 != null) {
            mIcon.addState(new int[] { -16842910 }, drawable3);
        }
        if (drawable2 != null) {
            mIcon.addState(MenuBarItem.SELECTED_STATE_SET, drawable2);
            mIcon.addState(MenuBarItem.PRESSED_ENABLED_STATE_SET, drawable2);
        }
        mIcon.addState(MenuBarItem.EMPTY_STATE_SET, drawable);
        this.setMIcon((Drawable)mIcon);
    }
    
    public void setMIcon(final Drawable imageDrawable) {
        this._imageView.setImageDrawable(imageDrawable);
    }
    
    public void setNecessaryInMainBar(final boolean necessaryInMainBar) {
        this._necessaryInMainBar = necessaryInMainBar;
    }
    
    public void setSelectable(final boolean selectable) {
        this._selectable = selectable;
    }
    
    public void setSelectionGroup(final int selectionGroup) {
        this._selectionGroup = selectionGroup;
    }
    
    public void setTitle(final String text) {
        if (text != null && !text.isEmpty()) {
            this._textView.setText((CharSequence)text);
        }
        final TextView textView = this._textView;
        int visibility;
        if (text != null) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        textView.setVisibility(visibility);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        CharSequence text;
        if (this._textView != null) {
            text = this._textView.getText();
        }
        else {
            text = "no text set yet";
        }
        sb.append((Object)text);
        return sb.toString();
    }
    
    public class GlowAnimation extends Animation
    {
        private ImageView _blinkingImage;
        
        public GlowAnimation(final ImageView blinkingImage) {
            this._blinkingImage = blinkingImage;
            this.setRepeatMode(2);
            this.setRepeatCount(-1);
            this.setDuration(1000L);
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            this._blinkingImage.setAlpha((int)(200.0f * n) + 55);
        }
    }
}

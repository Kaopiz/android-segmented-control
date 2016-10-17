package info.hoang8f.android.segmented;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class SegmentedGroup extends RadioGroup {

    private int mMarginDp;
    private Resources resources;
    private int mTintColor;
    private int mBorderColor;
    private int mUnCheckedTintColor;
    private int mCheckedTextColor = Color.WHITE;
    private int mUncheckedTextColor;
    private int mAnimateDelay;
    private LayoutSelector mLayoutSelector;
    private Float mCornerRadius;
    private OnCheckedChangeListener mCheckedChangeListener;
    private HashMap<Integer, TransitionDrawable> mDrawableMap;
    private int mLastCheckId;

    public SegmentedGroup(Context context) {
        super(context);
        resources = getResources();
        mTintColor = resources.getColor(R.color.radio_button_selected_color);
        mUnCheckedTintColor = resources.getColor(R.color.radio_button_unselected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = getResources();
        mTintColor = resources.getColor(R.color.radio_button_selected_color);
        mUnCheckedTintColor = resources.getColor(R.color.radio_button_unselected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius);
        initAttrs(attrs);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }

    /* Reads the attributes from the layout */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SegmentedGroup,
                0, 0);

        try {
            mMarginDp = (int) typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_border_width,
                    getResources().getDimension(R.dimen.radio_button_stroke_border));

            mCornerRadius = typedArray.getDimension(
                    R.styleable.SegmentedGroup_sc_corner_radius,
                    getResources().getDimension(R.dimen.radio_button_conner_radius));

            mTintColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_tint_color,
                    getResources().getColor(R.color.radio_button_selected_color));

            mBorderColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_border_color,
                    mTintColor);

            mCheckedTextColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_checked_text_color,
                    getResources().getColor(android.R.color.white));

            mUncheckedTextColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_unchecked_text_color,
                    mTintColor);

            mUnCheckedTintColor = typedArray.getColor(
                    R.styleable.SegmentedGroup_sc_unchecked_tint_color,
                    getResources().getColor(R.color.radio_button_unselected_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mAnimateDelay = typedArray.getInt(R.styleable.SegmentedGroup_sc_animate_delay, 400);
            }else {
                mAnimateDelay = 0;
            }
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Use holo light for default
        updateBackground();
    }

    public void setTintColor(int tintColor) {
        mTintColor = tintColor;
        updateBackground();
    }

    public void setTintColor(int tintColor, int checkedTextColor) {
        mTintColor = tintColor;
        mCheckedTextColor = checkedTextColor;
        updateBackground();
    }

    public void setUnCheckedTintColor(int unCheckedTintColor, int unCheckedTextColor) {
        mUnCheckedTintColor = unCheckedTintColor;
        mUncheckedTextColor = unCheckedTextColor;
        updateBackground();
    }

    public void updateBackground() {
        mDrawableMap = new HashMap<>();
        int count = super.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            updateBackground(i ,child);

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break;

            LayoutParams initParams = (LayoutParams) child.getLayoutParams();
            LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
            // Check orientation for proper margins
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0);
            } else {
                params.setMargins(0, 0, 0, -mMarginDp);
            }
            child.setLayoutParams(params);
        }
    }

    private void updateBackground(int index, View view) {
        int checked = mLayoutSelector.getSelected();
        int unchecked = mLayoutSelector.getUnselected();
        //Set text color
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                {-android.R.attr.state_checked},
                {android.R.attr.state_checked}},
                new int[]{mUncheckedTextColor, mCheckedTextColor});
        ((Button) view).setTextColor(colorStateList);

        //Redraw with tint color
        Drawable checkedDrawable = resources.getDrawable(checked).mutate();
        Drawable uncheckedDrawable = resources.getDrawable(unchecked).mutate();

        ((GradientDrawable) checkedDrawable).setColor(mTintColor);
        ((GradientDrawable) checkedDrawable).setStroke(mMarginDp, mBorderColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(mMarginDp, mBorderColor);
        ((GradientDrawable) uncheckedDrawable).setColor(mUnCheckedTintColor);
        //Set proper radius
        ((GradientDrawable) checkedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(view));
        ((GradientDrawable) uncheckedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(view));

        GradientDrawable maskDrawable = (GradientDrawable) resources.getDrawable(unchecked).mutate();
        maskDrawable.setStroke(mMarginDp, mBorderColor);
        maskDrawable.setColor(mUnCheckedTintColor);
        maskDrawable.setCornerRadii(mLayoutSelector.getChildRadii(view));
        int maskColor = Color.argb(70, Color.red(mTintColor), Color.green(mTintColor), Color.blue(mTintColor));
        maskDrawable.setColor(maskColor);

        LayerDrawable pressedDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pressedDrawable = new RippleDrawable(colorStateList, uncheckedDrawable, maskDrawable);
        } else {
            pressedDrawable= new LayerDrawable(new Drawable[] {uncheckedDrawable, maskDrawable});
        }


        Drawable[] drawables = {uncheckedDrawable, checkedDrawable};
        TransitionDrawable transitionDrawable = new TransitionDrawable(drawables);
        onBorderStyle(transitionDrawable, index);

        if (((RadioButton) view).isChecked()) {
            transitionDrawable.reverseTransition(0);
        }

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[] {-android.R.attr.state_checked, android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, transitionDrawable);

        mDrawableMap.put(view.getId(), transitionDrawable);

        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }

        super.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TransitionDrawable current = mDrawableMap.get(checkedId);
                current.reverseTransition(mAnimateDelay);
                if (mLastCheckId != 0) {
                    TransitionDrawable last = mDrawableMap.get(mLastCheckId);
                    if (last != null) last.reverseTransition(mAnimateDelay);
                }
                mLastCheckId = checkedId;

                if (mCheckedChangeListener != null) {
                    mCheckedChangeListener.onCheckedChanged(group, checkedId);
                }
            }
        });
    }

    private void onBorderStyle(LayerDrawable layerDrawable, int index){
        if(getOrientation() == VERTICAL){
            if(index == 0){
                layerDrawable.setLayerInset(1, 0, 0, 0, -mMarginDp * 2);
                layerDrawable.setLayerInset(0, 0, 0, 0, 0);
            } else if (index == getChildCount() - 1){
                layerDrawable.setLayerInset(1, 0,-mMarginDp * 2, 0, 0);
                layerDrawable.setLayerInset(0, 0,-mMarginDp * 2, 0, 0);
            } else {
                layerDrawable.setLayerInset(1, 0, -mMarginDp * 2, 0, -mMarginDp * 2);
                layerDrawable.setLayerInset(0, 0, -mMarginDp * 2, 0, 0);
            }
            return;
        }
        if(index == 0){
            layerDrawable.setLayerInset(1, 0, 0, -mMarginDp * 2, 0);
            layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        } else if (index == getChildCount() - 1){
            layerDrawable.setLayerInset(1, -mMarginDp * 2, 0, 0, 0);
            layerDrawable.setLayerInset(0, -mMarginDp * 2, 0, 0, 0);
        } else {
            layerDrawable.setLayerInset(1, -mMarginDp * 2, 0, -mMarginDp * 2, 0);
            layerDrawable.setLayerInset(0, -mMarginDp * 2, 0, 0, 0);
        }
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        mDrawableMap.remove(child.getId());
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mCheckedChangeListener = listener;
    }

    /*
         * This class is used to provide the proper layout based on the view.
         * Also provides the proper radius for corners.
         * The layout is the same for each selected left/top middle or right/bottom button.
         * float tables for setting the radius via Gradient.setCornerRadii are used instead
         * of multiple xml drawables.
         */
    private class LayoutSelector {

        private int children;
        private int child;
        private final int SELECTED_LAYOUT = R.drawable.radio_checked;
        private final int UNSELECTED_LAYOUT = R.drawable.radio_unchecked;

        private float r;    //this is the radios read by attributes or xml dimens
        private final float r1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 0.1f, getResources().getDisplayMetrics());    //0.1 dp to px
        private final float[] rLeft;    // left radio button
        private final float[] rRight;   // right radio button
        private final float[] rMiddle;  // middle radio button
        private final float[] rDefault; // default radio button
        private final float[] rTop;     // top radio button
        private final float[] rBot;     // bot radio button
        private float[] radii;          // result radii float table

        public LayoutSelector(float cornerRadius) {
            children = -1; // Init this to force setChildRadii() to enter for the first time.
            child = -1; // Init this to force setChildRadii() to enter for the first time
            r = cornerRadius;
            rLeft = new float[]{r, r, r1, r1, r1, r1, r, r};
            rRight = new float[]{r1, r1, r, r, r, r, r1, r1};
            rMiddle = new float[]{r1, r1, r1, r1, r1, r1, r1, r1};
            rDefault = new float[]{r, r, r, r, r, r, r, r};
            rTop = new float[]{r, r, r, r, r1, r1, r1, r1};
            rBot = new float[]{r1, r1, r1, r1, r, r, r, r};
        }

        private int getChildren() {
            return SegmentedGroup.this.getChildCount();
        }

        private int getChildIndex(View view) {
            return SegmentedGroup.this.indexOfChild(view);
        }

        private void setChildRadii(int newChildren, int newChild) {

            // If same values are passed, just return. No need to update anything
            if (children == newChildren && child == newChild)
                return;

            // Set the new values
            children = newChildren;
            child = newChild;

            // if there is only one child provide the default radio button
            if (children == 1) {
                radii = rDefault;
            } else if (child == 0) { //left or top
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rLeft : rTop;
            } else if (child == children - 1) {  //right or bottom
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rRight : rBot;
            } else {  //middle
                radii = rMiddle;
            }
        }

        /* Returns the selected layout id based on view */
        public int getSelected() {
            return SELECTED_LAYOUT;
        }

        /* Returns the unselected layout id based on view */
        public int getUnselected() {
            return UNSELECTED_LAYOUT;
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        public float[] getChildRadii(View view) {
            int newChildren = getChildren();
            int newChild = getChildIndex(view);
            setChildRadii(newChildren, newChild);
            return radii;
        }
    }
}
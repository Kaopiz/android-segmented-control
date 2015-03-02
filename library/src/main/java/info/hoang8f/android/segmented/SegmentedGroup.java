package info.hoang8f.android.segmented;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class SegmentedGroup extends RadioGroup {

    private int mMarginDp;
    private Resources resources;
    private int mTintColor;
    private int mCheckedTextColor = Color.WHITE;
    private LayoutSelector mLayoutSelector;

    public SegmentedGroup(Context context) {
        super(context);
        resources = getResources();
        mTintColor = resources.getColor(R.color.radio_button_selected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mLayoutSelector = new LayoutSelector();
    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = getResources();
        mTintColor = resources.getColor(R.color.radio_button_selected_color);
        mMarginDp = (int) getResources().getDimension(R.dimen.radio_button_stroke_border);
        mLayoutSelector = new LayoutSelector();
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

    public void updateBackground() {
        int count = super.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            updateBackground(child);

            if (i == count - 1)
                break;

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

    private void updateBackground(View view) {
        int checked = mLayoutSelector.getSelected(view);
        int unchecked = mLayoutSelector.getUnselected(view);
        //Set text color
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                {android.R.attr.state_pressed},
                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
                {-android.R.attr.state_pressed, android.R.attr.state_checked}},
                new int[]{Color.GRAY, mTintColor, mCheckedTextColor});
        ((Button) view).setTextColor(colorStateList);

        //Redraw with tint color
        Drawable checkedDrawable = resources.getDrawable(checked).mutate();
        Drawable uncheckedDrawable = resources.getDrawable(unchecked).mutate();
        ((GradientDrawable) checkedDrawable).setColor(mTintColor);
        ((GradientDrawable) checkedDrawable).setStroke(mMarginDp, mTintColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(mMarginDp, mTintColor);

        //Create drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);

        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }

    private class LayoutSelector{

        private int children;
        private int child;
        private int selectedLayout;
        private int unselectedLayout;

        private int getChildren(){
             return SegmentedGroup.this.getChildCount();
        }

        private void setLayoutFromChild(){
            if (children == 1){
                selectedLayout = R.drawable.radio_checked_default;
                unselectedLayout = R.drawable.radio_unchecked_default;
            } else if (child == 0){
                if (getOrientation() == LinearLayout.HORIZONTAL){
                    selectedLayout = R.drawable.radio_checked_left;
                    unselectedLayout = R.drawable.radio_unchecked_left;
                } else {
                    selectedLayout = R.drawable.radio_checked_top;
                    unselectedLayout = R.drawable.radio_unchecked_top;
                }
            } else if (child == children - 1){
                if (getOrientation() == LinearLayout.HORIZONTAL){
                    selectedLayout = R.drawable.radio_checked_right;
                    unselectedLayout = R.drawable.radio_unchecked_right;
                } else {
                    selectedLayout = R.drawable.radio_checked_bottom;
                    unselectedLayout = R.drawable.radio_unchecked_bottom;
                }
            } else{
                selectedLayout = R.drawable.radio_checked_middle;
                if (getOrientation() == LinearLayout.HORIZONTAL){
                    unselectedLayout = R.drawable.radio_unchecked_middle;
                } else {
                    unselectedLayout = R.drawable.radio_unchecked_middle_vertical;
                }
            }
        }

        private int getChildIndex(View view){
            return SegmentedGroup.this.indexOfChild(view);
        }

        public int getSelected(View view){
            int newChildren = getChildren();
            int newChild = getChildIndex(view);
            if (this.children != newChildren || this.child != newChild){
                this.children = newChildren;
                this.child = newChild;
                setLayoutFromChild();
            }
            return selectedLayout;
        }

        public int getUnselected(View view){
            int newChildren = getChildren();
            int newChild = getChildIndex(view);
            if (this.children != newChildren || this.child != newChild){
                this.children = newChildren;
                this.child = newChild;
                setLayoutFromChild();
            }
            return unselectedLayout;
        }
    }
}
package info.hoang8f.android.segmented;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SegmentedGroup extends RadioGroup {

    private int oneDP;
    private Resources resources;
    private int mTintColor;
    private int mCheckedTextColor = Color.WHITE;
    private int mPressedTintColor;
    private int mPressedStrokeColor;

//    private TransitionDrawable transitionDrawable;

//    private OnSegmentedGroupChecked listener;

//    public interface OnSegmentedGroupChecked {
//        public void onCheckedChanged(RadioGroup group, int checkedId);
//    }

    public SegmentedGroup(Context context) {
        super(context);
        init(context, null);
//        resources = getResources();
//        mTintColor = resources.getColor(R.color.radio_button_selected_color);
//        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, resources.getDisplayMetrics());
    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
//        resources = getResources();
//        mTintColor = resources.getColor(R.color.radio_button_selected_color);
//        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, resources.getDisplayMetrics());
    }


    private void init(Context context, AttributeSet attrs) {
        resources = getResources();
        mTintColor = resources.getColor(R.color.ios_color);
        mCheckedTextColor = Color.WHITE;
        mPressedTintColor = resources.getColor(R.color.ios_color_pressed);
        mPressedStrokeColor = resources.getColor(R.color.ios_color);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SegmentedGroup);
            mTintColor = a.getColor(R.styleable.SegmentedGroup_tint_color, resources.getColor(R.color.ios_color));
            mCheckedTextColor = a.getColor(R.styleable.SegmentedGroup_checked_color, Color.WHITE);
            mPressedTintColor = a.getColor(R.styleable.SegmentedGroup_pressed_tint_color, resources.getColor(R.color.ios_color_pressed));
            mPressedStrokeColor = a.getColor(R.styleable.SegmentedGroup_pressed_stroke_color, resources.getColor(R.color.ios_color));
        }

        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, resources.getDisplayMetrics());

//        setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                transitionDrawable.startTransition(200);
////                ObjectAnimator anim = ObjectAnimator.ofInt(group, "backgroundColor", color1, color2);
//            }
//        });
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

    public void setPressedTintColor(int pressedTintColor) {
        mPressedTintColor = pressedTintColor;
        updateBackground();
    }

    public void setPressedTintColor(int pressedTintColor, int pressedStrokeColor) {
        mPressedTintColor = pressedTintColor;
        mPressedStrokeColor = pressedStrokeColor;
        updateBackground();
    }

    public void updateBackground() {
        int count = super.getChildCount();
        if (count > 1) {
            View childFirst = getChildAt(0);
            LayoutParams initParams = (LayoutParams) childFirst.getLayoutParams();
            LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
            params.setMargins(0, 0, -oneDP, 0);
            childFirst.setLayoutParams(params);
            updateBackground(getChildAt(0), R.drawable.radio_checked_left, R.drawable.radio_unchecked_left,
                    R.drawable.radio_pressed_left);
            for (int i = 1; i < count - 1; i++) {
                updateBackground(getChildAt(i), R.drawable.radio_checked_middle, R.drawable.radio_unchecked_middle,
                        R.drawable.radio_pressed_middle);
                View childMiddle = getChildAt(i);
                initParams = (LayoutParams) childMiddle.getLayoutParams();
                params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
                params.setMargins(0, 0, -oneDP, 0);
                childMiddle.setLayoutParams(params);
            }
            updateBackground(getChildAt(count - 1), R.drawable.radio_checked_right, R.drawable.radio_unchecked_right,
                    R.drawable.radio_pressed_right);
        } else if (count == 1) {
            updateBackground(getChildAt(0), R.drawable.radio_checked_default, R.drawable.radio_unchecked_default,
                    R.drawable.radio_pressed_default);
        }
    }


    private void updateBackground(View view, int checked, int unchecked, int pressed) {
        //Set text color
        ColorStateList colorStateList = new ColorStateList(new int[][]{
//                {android.R.attr.state_pressed},
                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
                {android.R.attr.state_pressed, -android.R.attr.state_checked},
                {-android.R.attr.state_pressed, android.R.attr.state_checked},
                {android.R.attr.state_pressed, android.R.attr.state_checked}},
                new int[]{mTintColor, mTintColor, mCheckedTextColor, mCheckedTextColor});
        ((Button) view).setTextColor(colorStateList);

        //Redraw with tint color
        Drawable checkedDrawable = resources.getDrawable(checked).mutate();
        Drawable uncheckedDrawable = resources.getDrawable(unchecked).mutate();
        Drawable pressedDrawable = resources.getDrawable(pressed).mutate();
        ((GradientDrawable) checkedDrawable).setColor(mTintColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(oneDP, mTintColor);
        ((GradientDrawable) pressedDrawable).setColor(mPressedTintColor);
        ((GradientDrawable) pressedDrawable).setStroke(oneDP, mPressedStrokeColor);

//        transitionDrawable = new TransitionDrawable(new Drawable[]{uncheckedDrawable, checkedDrawable});


        //Create drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
//        stateListDrawable.addState(new int[]{}, transitionDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_checked}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, -android.R.attr.state_checked}, pressedDrawable);
        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }



}

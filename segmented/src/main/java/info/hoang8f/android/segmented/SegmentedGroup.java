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
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SegmentedGroup extends RadioGroup {

    private int oneDP;
    private Resources res;

    public SegmentedGroup(Context context) {
        super(context);
        res = getResources();
        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, res.getDisplayMetrics());

    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        res = getResources();
        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, res.getDisplayMetrics());

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Use holo light for default
        updateBackground(0);
    }

    public void setTintColor(int tintColor) {
        updateBackground(tintColor);
    }

    private void updateBackground(int tintColor) {
        int count = super.getChildCount();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, -oneDP, 0);
        if (count > 1) {
            super.getChildAt(0).setLayoutParams(params);
            updateBackground(getChildAt(0), R.drawable.radio_checked_left, R.drawable.radio_unchecked_left, tintColor);

            for (int i = 1; i < count - 1; i++) {
                updateBackground(getChildAt(i), R.drawable.radio_checked_middle, R.drawable.radio_unchecked_middle, tintColor);
                super.getChildAt(i).setLayoutParams(params);
            }
            updateBackground(getChildAt(count - 1), R.drawable.radio_checked_right, R.drawable.radio_unchecked_right, tintColor);
        } else if (count == 1) {
            updateBackground(getChildAt(0), R.drawable.radio_checked_default, R.drawable.radio_unchecked_default, tintColor);
        }
    }

    private void updateBackground(View view, int checked, int unchecked, int tintColor) {
        //Set text color
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                {android.R.attr.state_pressed},
                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
                {-android.R.attr.state_pressed, android.R.attr.state_checked}},
                new int[]{Color.GRAY, (tintColor != 0) ? tintColor : res.getColor(R.color.radio_button_selected_color), Color.WHITE});
        ((Button) view).setTextColor(colorStateList);

        //Redraw with tint color
        Drawable checkedDrawable = res.getDrawable(checked).mutate();
        Drawable uncheckedDrawable = res.getDrawable(unchecked).mutate();
        ((GradientDrawable) checkedDrawable).setColor((tintColor != 0) ? tintColor : res.getColor(R.color.radio_button_selected_color));
        ((GradientDrawable) uncheckedDrawable).setStroke(oneDP, (tintColor != 0) ? tintColor : res.getColor(R.color.radio_button_selected_color));

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
}

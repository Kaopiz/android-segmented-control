package info.hoang8f.android.segmented;

import android.content.Context;
import android.content.res.ColorStateList;
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

public class SegmentedRadioGroup extends RadioGroup {

    private int oneDP;

    public SegmentedRadioGroup(Context context) {
        super(context);
        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    }

    public SegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        changeButtonsImages(0);
    }

    public void setTintColor(int tintColor) {
        changeButtonsImages(tintColor);
    }

    private void changeButtonsImages(int tintColor) {

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
                new int[]{Color.GRAY, (tintColor != 0) ? tintColor : getResources().getColor(R.color.radio_button_selected_color), Color.WHITE});
        ((Button) view).setTextColor(colorStateList);

        //Set set background
        Drawable checkedDrawable = getResources().getDrawable(checked);
        Drawable uncheckedDrawable = getResources().getDrawable(unchecked);
        ((GradientDrawable) checkedDrawable).setColor((tintColor != 0) ? tintColor : getResources().getColor(R.color.radio_button_selected_color));
        ((GradientDrawable) uncheckedDrawable).setStroke(oneDP, (tintColor != 0) ? tintColor : getResources().getColor(R.color.radio_button_selected_color));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);

        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }
}

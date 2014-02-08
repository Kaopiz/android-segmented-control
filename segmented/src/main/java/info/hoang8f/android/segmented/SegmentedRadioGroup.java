package info.hoang8f.android.segmented;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RadioGroup;

public class SegmentedRadioGroup extends RadioGroup {

    public SegmentedRadioGroup(Context context) {
        super(context);
    }

    public SegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        changeButtonsImages();
    }

    private void changeButtonsImages() {

        int count = super.getChildCount();

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics());
        params.setMargins(0, 0, marginRight, 0);

        if (count > 1) {
            super.getChildAt(0).setBackgroundResource(R.drawable.lecture_radio_button_left_background);
            super.getChildAt(0).setLayoutParams(params);
            for (int i = 1; i < count - 1; i++) {
                super.getChildAt(i).setBackgroundResource(R.drawable.lecture_radio_button_middle_background);
                super.getChildAt(i).setLayoutParams(params);

            }
            super.getChildAt(count - 1).setBackgroundResource(R.drawable.lecture_radio_button_right_background);
        } else if (count == 1) {
            super.getChildAt(0).setBackgroundResource(R.drawable.lecture_radio_button_default_background);
        }
    }
}

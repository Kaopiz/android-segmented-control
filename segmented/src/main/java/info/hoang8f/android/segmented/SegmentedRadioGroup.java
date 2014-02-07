package info.hoang8f.android.segmented;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class SegmentedRadioGroup extends RadioGroup{
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

        if (count > 1) {
            super.getChildAt(0).setBackgroundResource(R.drawable.lecture_radio_button_left_background);
            for (int i = 1; i < count - 1; i++) {
                super.getChildAt(i).setBackgroundResource(R.drawable.lecture_radio_button_middle_background);
            }
            super.getChildAt(count - 1).setBackgroundResource(R.drawable.lecture_radio_button_right_background);
        } else if (count == 1) {
            super.getChildAt(0).setBackgroundResource(R.drawable.lecture_radio_button_default_background);
        }
    }
}

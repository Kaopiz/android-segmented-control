package info.hoang8f.mymo.segmented.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by tchar on 6/12/2014.
 */
public class VerticalRadioButtonsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_radio_buttons);
        View rootView = findViewById(android.R.id.content).getRootView();

        SegmentedGroup segmented2 = (SegmentedGroup) rootView.findViewById(R.id.segmented2);
        segmented2.setTintColor(Color.DKGRAY);

        SegmentedGroup segmented3 = (SegmentedGroup) rootView.findViewById(R.id.segmented3);
        //Tint color, and text color when checked
        segmented3.setTintColor(Color.parseColor("#FFD0FF3C"), Color.parseColor("#FF7B07B2"));

        SegmentedGroup segmented4 = (SegmentedGroup) rootView.findViewById(R.id.segmented4);
        segmented4.setTintColor(getResources().getColor(R.color.radio_button_selected_color));
    }
}

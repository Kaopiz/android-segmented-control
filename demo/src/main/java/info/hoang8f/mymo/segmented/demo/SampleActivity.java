package info.hoang8f.mymo.segmented.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import info.hoang8f.android.segmented.AwesomeRadioButton;
import info.hoang8f.android.segmented.SegmentedGroup;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

        SegmentedGroup segmented5;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sample, container, false);

//            SegmentedGroup segmented2 = (SegmentedGroup) rootView.findViewById(R.id.segmented2);
//            segmented2.setTintColor(Color.DKGRAY);
//
//            SegmentedGroup segmented3 = (SegmentedGroup) rootView.findViewById(R.id.segmented3);
//            //Tint color, and text color when checked
////            segmented3.setTintColor(Color.parseColor("#FFD0FF3C"), Color.parseColor("#FF7B07B2"));
//

            segmented5 = (SegmentedGroup) rootView.findViewById(R.id.segmented5);
            Button addBtn = (Button) rootView.findViewById(R.id.add_segmented);
            Button removeBtn = (Button) rootView.findViewById(R.id.remove_segmented);

            //Set listener for button
            addBtn.setOnClickListener(this);
            removeBtn.setOnClickListener(this);

            //Set change listener on SegmentedGroup
//            segmented2.setOnCheckedChangeListener(this);
//            segmented3.setOnCheckedChangeListener(this);
            segmented5.setOnCheckedChangeListener(this);

            // Support awesome font
            AwesomeRadioButton button = (AwesomeRadioButton) rootView.findViewById(R.id.button42);
            button.setMarkdownText("{fa_facebook} facebook");
            return rootView;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.button21:
                    Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button22:
                    Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button31:
                    Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button32:
                    Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button33:
                    Toast.makeText(getActivity(), "Three", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    // Nothing to do
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_segmented:
                    addButton(segmented5);
                    break;
                case R.id.remove_segmented:
                    removeButton(segmented5);
                    break;
                default:
                    // Nothing to do
            }
        }

        private void addButton(SegmentedGroup group) {
            RadioButton radioButton = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.radio_button_item, null);
            radioButton.setText("Button " + (group.getChildCount() + 1));
            group.addView(radioButton);
            group.updateBackground();
        }

        private void removeButton(SegmentedGroup group) {
            if (group.getChildCount() < 1) return;
            group.removeViewAt(group.getChildCount() - 1);
            group.updateBackground();

            //Update margin for last item
            if (group.getChildCount() < 1) return;
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            group.getChildAt(group.getChildCount() - 1).setLayoutParams(layoutParams);
        }
    }
}

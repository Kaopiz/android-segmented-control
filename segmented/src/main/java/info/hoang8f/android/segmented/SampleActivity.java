package info.hoang8f.android.segmented;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SampleActivity extends ActionBarActivity {

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
    public static class PlaceholderFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sample, container, false);

            SegmentedGroup segmented2 = (SegmentedGroup) rootView.findViewById(R.id.segmented2);
            segmented2.setTintColor(Color.DKGRAY);

            SegmentedGroup segmented3 = (SegmentedGroup) rootView.findViewById(R.id.segmented3);
            //Tint color, and text color when checked
            segmented3.setTintColor(Color.parseColor("#FFD0FF3C"), Color.parseColor("#FF7B07B2"));

            SegmentedGroup segmented4 = (SegmentedGroup) rootView.findViewById(R.id.segmented4);
            segmented4.setTintColor(getResources().getColor(R.color.radio_button_selected_color));

            //Set change listener on SegmentedGroup
            segmented2.setOnCheckedChangeListener(this);
            segmented3.setOnCheckedChangeListener(this);
            segmented4.setOnCheckedChangeListener(this);

            return rootView;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.button21:
                    Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button22:
                    Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button31:
                    Toast.makeText(getActivity(), "One", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button32:
                    Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button33:
                    Toast.makeText(getActivity(), "Three", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button41:
                    Toast.makeText(getActivity(), "Asia", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button42:
                    Toast.makeText(getActivity(), "Africa", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button43:
                    Toast.makeText(getActivity(), "Europe", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.button44:
                    Toast.makeText(getActivity(), "America", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

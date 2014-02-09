android-segmented-control
=========================

iOS 7 segmented control for android

This is my implement of iOS 7 segmented control, based on RadioGroup and RadioButton widget on Android.
 
![Sample Image](https://raw2.github.com/hoang8f/android-segmented-control/master/screenshot/screenshot.png)

Usage
-----
Copy(or merge) below files into coresponding file/folder:
  + SegmentedGroup.java
  + res/drawable/*
  + res/drawable-v14/*
  + res/values/colors.xml
  + res/values/dimens.xml
  + res/values/styles.xml (only RadioButton style)

Define in xml like this:

        <info.hoang8f.android.segmented.SegmentedGroup
            android:id="@+id/segmented2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:orientation="horizontal">

            <RadioButton
                android:id="@+id/button21"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="One"
                style="@style/RadioButton" />

            <RadioButton
                android:id="@+id/button22"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Two"
                style="@style/RadioButton" />
        </info.hoang8f.android.segmented.SegmentedGroup>

You also can be change the tint color and title color when button is checked by `setTintColor` method.
Here is sample code:

            SegmentedGroup segmented2 = (SegmentedGroup) rootView.findViewById(R.id.segmented2);
            segmented2.setTintColor(Color.DKGRAY);

            SegmentedGroup segmented3 = (SegmentedGroup) rootView.findViewById(R.id.segmented3);
            segmented3.setTintColor(Color.parseColor("#FFD0FF3C"), Color.parseColor("#FF7B07B2"));

            SegmentedGroup segmented4 = (SegmentedGroup) rootView.findViewById(R.id.segmented4);
            segmented4.setTintColor(getResources().getColor(R.color.radio_button_selected_color));


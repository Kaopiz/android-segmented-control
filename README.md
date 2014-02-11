android-segmented-control
=========================

iOS 7 segmented control for android

Custom View of iOS 7 segmented control for Android. My implementation is based on RadioGroup and RadioButton widget.
 
![Sample Image](https://raw2.github.com/hoang8f/android-segmented-control/master/screenshot/screenshot.png)

Usage
-----
Copy(or merge) below files into corresponding file/folder:
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

License
-------
    The MIT License (MIT)
    
    Copyright (c) 2014 Le Van Hoang
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

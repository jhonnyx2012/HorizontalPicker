Horizontal Picker
=================

What is this?
-------------
HorizontalPicker is a custom-build Android View used for choosing dates (similar to the native date picker) but draws horizontally into a vertically narrow container. It allows easy day picking using the horizontal pan gesture.

Too see it in action, [download the demo app](https://github.com/jhonnyx2012/HorizontalPicker/blob/master/appDemoDebug.apk?raw=true) to try it out.

This is what it looks like.

![Screenshot_1](https://raw.githubusercontent.com/jhonnyx2012/HorizontalPicker/master/Screenshot_custom.png)
![Screenshot_2](https://raw.githubusercontent.com/jhonnyx2012/HorizontalPicker/master/Screenshot_palette.png)

Features
--------

* Date selection using a smooth swipe gesture
* Date selection by clicking on a day slot
* Date selection from code using the HorizontalPicker java object
* Month and year view
* _Today_ button to jump to the current day
* Localized day and month names
* Configurable number of generated days (default: 120)
* Configurable number of offset generated days before the current date (default: 7)
* Customizable set of colors, or themed through the app theming engine

**Note**: This library uses the [JodaTime](https://github.com/JodaOrg/joda-time) library to work with days.

Requirements
------------
- Android 4.1 or later (Minimum SDK level 16)
- Android Studio (to compile and use)
- **Eclipse is not supported**

Getting Started
---------------

In your app module's Gradle config file, add the following dependency:
```groovy
dependencies {
    compile 'com.github.jhonnyx2012:horizontal-picker:1.0.6'
}
```

Then to include it into your layout, add the following:
```xml
<com.github.jhonnyx2012.horizontalpicker.HorizontalPicker
    android:id="@+id/datePicker"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

In your activity, you need to initialize it and set a listener, like this:
```java
public class MainActivity extends AppCompatActivity implements DatePickerListener {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        // setup activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the picker
        HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker
            .setListener(this)
            .init();
    }

    @Override
    public void onDateSelected(@NonNull final DateTime dateSelected) {
        // log it for demo
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
    }
}
```

Finally, you can also configure the number of days to show, the date offset, or set a date directly to the picker. For all options, see the full configuration below.


```java
    // at init time
    picker
        .setListener(listner)
        .setDays(20)
        .setOffset(10)
        .setDateSelectedColor(Color.DKGRAY)
        .setDateSelectedTextColor(Color.WHITE)
        .setMonthAndYearTextColor(Color.DKGRAY)
        .setTodayButtonTextColor(getColor(R.color.colorPrimary))
        .setTodayDateTextColor(getColor(R.color.colorPrimary))
        .setTodayDateBackgroundColor(Color.GRAY)
        .setUnselectedDayTextColor(Color.DKGRAY)
        .setDayOfWeekTextColor(Color.DKGRAY)
        .setUnselectedDayTextColor(getColor(R.color.textColor))
        .showTodayButton(false)
        .init();

    // or on the View directly after init was completed
    picker.setBackgroundColor(Color.LTGRAY);
    picker.setDate(new DateTime().plusDays(4));
```

```text
Copyright 2017 Jhonny Barrios

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```

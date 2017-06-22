AutoSlidingViewPager
====================
Android ViewPager that endlessly slides with progress bar.
Useful for showcasing.

![AutoSlidingViewPager][demo]

Usage
-----
#### `AutoSlidingViewPager`
Simple ViewPager with auto-sliding behavior.

```xml
<com.hendraanggrian.AutoSlidingViewPager
    android:id="@+id/pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:slidingCountDown="3000"
    app:stopSlideOnTouch="true" />
```

```java
AutoSlidingViewPager pager = (AutoSlidingViewPager) findById(R.id.pager);
pager.setAdapter(mAdapter);
pager.start();
```

#### `AutoSlidingViewPagerGroup`
A container of `AutoSlidingViewPager` with a counting down progress bar.

```xml
<com.hendraanggrian.AutoSlidingViewPagerGroup
    android:id="@+id/group"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:slidingCountDown="3000"
    app:stopSlideOnTouch="true" />
```

```java
AutoSlidingViewPagerGroup group = (AutoSlidingViewPagerGroup) findById(R.id.group);
group.getViewPager().setAdapter(mAdapter);
group.start();
```

#### Attributes
| Attribute          | Description                                                    | Default value/behavior |
|--------------------|----------------------------------------------------------------|------------------------|
| `slidingCountDown` | Count down to next pager sliding, in milliseconds.             | 5 seconds              |
| `stopSlideOnTouch` | Stop auto-sliding behavior when user interacts with ViewPager. | false                  |

Download
--------
```gradle
repositories {
    maven { url 'https://maven.google.com' }
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian:viewpager-autosliding:?.?'
}
```

License
-------
    Copyright 2017 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[demo]: /art/demo.gif
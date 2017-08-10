# CBRatingBar

等级评分控件，支持填充渐变，支持设置颜色、调整大小等，支持监听点击事件

## gif

![gif](/raw/ratingbar.gif)

## 使用

* xml
```xml
    <com.cb.ratingbar.CBRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```
```xml
    <com.cb.ratingbar.CBRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:starSize="20dp"
        app:starCount="5"
        app:starSpace="10dp"
        app:starStrokeWidth="1dp"
        app:starCanTouch="true"
        app:starMaxProgress="120"
        app:starProgress="60"
        app:starShowStroke="true"
        app:starUseGradient="true"
        app:starStartColor="#0000ff"
        app:starEndColor="#00ff00"
        app:starCoverColor="#ff0000"
        app:starFillColor="#666666"
        app:starPointCount="5"
        app:starStrokeColor="#0f0f0f"/>
```
* java
```java
cbRatingBar.setStarSize(20) //大小
        .setStarCount(5) //数量
        .setStarSpace(10) //间距
        .setStarPointCount(5) //角数(n角星)
        .setShowStroke(true) //是否显示边框
        .setStarStrokeColor(Color.parseColor("#00ff00")) //边框颜色
        .setStarStrokeWidth(5) //边框大小
        .setStarFillColor(Color.parseColor("#00ff00")) //填充的背景颜色
        .setStarCoverColor(Color.parseColor("#00ff00")) //填充的进度颜色
        .setStarMaxProgress(120) //最大进度
        .setStarProgress(50) //当前显示的进度
        .setUseGradient(true) //是否使用渐变填充（如果使用则coverColor无效）
        .setStartColor(Color.parseColor("#000000")) //渐变的起点颜色
        .setEndColor(Color.parseColor("#ffffff")) //渐变的终点颜色
        .setCanTouch(true) //是否可以点击
        .setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() { //点击监听
            @Override
            public void onStarTouch(int touchCount) {
                Toast.makeText(MainActivity.this, "点击第" + touchCount + "个星星", Toast.LENGTH_SHORT).show();
            }
        });
```

```
Copyright 2017 CodeBear

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
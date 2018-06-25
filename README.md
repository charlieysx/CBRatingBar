# CBRatingBar
[![](https://jitpack.io/v/CB-ysx/CBRatingBar.svg)](https://jitpack.io/#CB-ysx/CBRatingBar)

等级评分控件，支持填充渐变，支持设置颜色、调整大小等，支持监听点击事件

## gif

![gif](/raw/ratingbar1.gif)
![gif](/raw/ratingbar2.gif)

## Gradle

* add jitpack to your project's build.gradle:
```xml
    allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
```

* add the compile statement to your module's build.gradle:
```xml
    dependencies {
        compile 'com.github.CB-ysx:CBRatingBar:3.0.0'
    }
```

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
        app:starStrokeColor="#0f0f0f"
        app:pathData="@string/bird"
        app:pathDataId="@string/bird"
        app:starCoverDir="top_to_bottom"/>
```
* java
```java
cbRatingBar.setStarSize(20) //大小
        .setStarCount(5) //数量
        .setStarSpace(10) //间距
        .setShowStroke(true) //是否显示边框
        .setStarStrokeColor(Color.parseColor("#00ff00")) //边框颜色
        .setStarStrokeWidth(5) //边框大小
        .setStarFillColor(Color.parseColor("#00ff00")) //填充的背景颜色
        .setStarCoverColor(Color.parseColor("#ffffff")) //填充的进度颜色
        .setStarMaxProgress(120) //最大进度
        .setStarProgress(50) //当前显示的进度
        .setUseGradient(true) //是否使用渐变填充（如果使用则coverColor无效）
        .setStartColor(Color.parseColor("#000000")) //渐变的起点颜色
        .setEndColor(Color.parseColor("#ffffff")) //渐变的终点颜色
        .setCanTouch(true) //是否可以点击
        .setPathData(getResources().getString(R.string.pig)) //传入path的数据
        .setPathDataId(R.string.pig) //传入path数据id
        .setDefaultPath() //设置使用默认path
        .setPath(path) //传入path
        .setCoverDir(CBRatingBar.CoverDir.topToBottom) //设置进度覆盖的方向
        .setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() { //点击监听
            @Override
            public void onStarTouch(int touchCount) {
                Toast.makeText(MainActivity.this, "点击第" + touchCount + "个星星", Toast.LENGTH_SHORT).show();
            }
        });
```

## 说明
pathData为svg文件中的path数据

## 感谢

> 解析svg数据转为path，用了[RichPath][1]中的两个类，感谢[tarek360][2]

## License

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


  [1]: https://github.com/tarek360/RichPath
  [2]: https://github.com/tarek360

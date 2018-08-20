## 3.0.1
 * 修复填充渐变时方向不改变的bug

## 3.0.0
 * 添加自定义填充方向
    ```
    // 在xml中使用
    left_to_right、right_to_left、top_to_bottom、bottom_to_top
    
    // 在java中使用
    CBRatingBar.CoverDir.leftToRight
    CBRatingBar.CoverDir.rightToLeft
    CBRatingBar.CoverDir.topToBottom
    CBRatingBar.CoverDir.bottomToTop
    ```
    ```xml
    ...
    app:starCoverDir="top_to_bottom"
    ...
    ```
    ```java
    ...
    .setCoverDir(CBRatingBar.CoverDir.topToBottom) //设置进度覆盖的方向
    ...
    ```
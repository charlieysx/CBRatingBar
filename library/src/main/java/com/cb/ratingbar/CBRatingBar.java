package com.cb.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * description:
 * <p>
 * Created by CodeBear on 2017/8/10.
 */

public class CBRatingBar extends View {

    public interface OnStarTouchListener {
        void onStarTouch(int touchCount);
    }

    /**
     * 一些默认的数值
     */
    private static final int DEFAULT_STAR_SIZE = 20;
    private static final int DEFAULT_STAR_COUNT = 1;
    private static final int DEFAULT_STAR_SPACE = 0;
    private static final int DEFAULT_STAR_STROKE_WIDTH = 1;
    private static final boolean DEFAULT_STAR_SHOW_STROKE = true;
    private static final int DEFAULT_STAR_STROKE_COLOR = Color.RED;
    private static final int DEFAULT_STAR_FILL_COLOR = Color.WHITE;
    private static final int DEFAULT_STAR_COVER_COLOR = Color.RED;
    private static final float DEFAULT_STAR_MAX_PROGRESS = 100;
    private static final float DEFAULT_STAR_PROGRESS = 0;
    private static final boolean DEFAULT_STAR_USE_GRADIENT = false;
    private static final int DEFAULT_STAR_START_COLOR = Color.YELLOW;
    private static final int DEFAULT_STAR_END_COLOR = Color.RED;
    private static final boolean DEFAULT_STAR_CAN_TOUCH = false;
    private static final int DEFAULT_STAR_PATH_DATA_ID = R.string.round_star;

    public final class CoverDir {
        public final static int leftToRight = 0;
        public final static int rightToLeft = 1;
        public final static int topToBottom = 2;
        public final static int bottomToTop = 3;
    }

    /**
     * 画星星边框的画笔
     */
    private Paint starStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 画星星并填充颜色的画笔
     */
    private Paint starFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 覆盖星星上的颜色的画笔(也就是绘制进度)
     */
    private Paint starCoverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 用该画笔才能将要覆盖的颜色(进度)填充到不规则的星星上
     */
    private Paint starPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 一个星星的宽度(宽高一样)
     */
    private int starSize;
    /**
     * 星星的个数
     */
    private int starCount;
    /**
     * 星星的间距
     */
    private int starSpace;
    /**
     * 星星边框的宽度
     */
    private int starStrokeWidth;
    /**
     * 是否显示星星边框
     */
    private boolean showStroke;
    /**
     * 星星边框的颜色
     */
    private int starStrokeColor;
    /**
     * 填充星星的颜色
     */
    private int starFillColor;
    /**
     * 覆盖星星(进度)的颜色
     */
    private int starCoverColor;
    /**
     * 最大的进度
     */
    private float starMaxProgress;
    /**
     * 当前显示的进度
     */
    private float starProgress;
    /**
     * 是否使用渐变
     */
    private boolean useGradient;
    /**
     * 渐变的起点颜色、终点颜色
     */
    private int startColor;
    private int endColor;
    /**
     * 是否有触摸事件
     */
    private boolean canTouch;
    /**
     * 触摸显示的星星数
     */
    private int touchCount = -1;

    private int width;
    private OnStarTouchListener onStarTouchListener;

    /**
     * 要绘制的图案的path
     */
    private Path mPath = new Path();
    /**
     * path的数据
     */
    private String pathData;
    /**
     * path的资源id
     */
    private
    @StringRes
    int pathDataId;

    /**
     * 进度填充方向
     */
    private int coverDir;

    public CBRatingBar(Context context) {
        this(context, null);
    }

    public CBRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CBRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);

        initPath();

        width = starSize * starCount + (starCount - 1) * starSpace;
        setupStrokePaint();
        setupFillPaint();
        setupCoverPaint();

        starPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CBRatingBar);

        starSize = array.getDimensionPixelOffset(R.styleable.CBRatingBar_starSize, DEFAULT_STAR_SIZE);
        starCount = array.getInteger(R.styleable.CBRatingBar_starCount, DEFAULT_STAR_COUNT);
        starSpace = array.getDimensionPixelOffset(R.styleable.CBRatingBar_starSpace, DEFAULT_STAR_SPACE);
        starStrokeWidth = array.getDimensionPixelOffset(R.styleable.CBRatingBar_starStrokeWidth,
                DEFAULT_STAR_STROKE_WIDTH);
        showStroke = array.getBoolean(R.styleable.CBRatingBar_starShowStroke, DEFAULT_STAR_SHOW_STROKE);
        starStrokeColor = array.getColor(R.styleable.CBRatingBar_starStrokeColor, DEFAULT_STAR_STROKE_COLOR);
        starFillColor = array.getColor(R.styleable.CBRatingBar_starFillColor, DEFAULT_STAR_FILL_COLOR);
        starCoverColor = array.getColor(R.styleable.CBRatingBar_starCoverColor, DEFAULT_STAR_COVER_COLOR);
        starMaxProgress = array.getFloat(R.styleable.CBRatingBar_starMaxProgress, DEFAULT_STAR_MAX_PROGRESS);
        starProgress = array.getFloat(R.styleable.CBRatingBar_starProgress, DEFAULT_STAR_PROGRESS);
        useGradient = array.getBoolean(R.styleable.CBRatingBar_starUseGradient, DEFAULT_STAR_USE_GRADIENT);
        startColor = array.getColor(R.styleable.CBRatingBar_starStartColor, DEFAULT_STAR_START_COLOR);
        endColor = array.getColor(R.styleable.CBRatingBar_starEndColor, DEFAULT_STAR_END_COLOR);
        canTouch = array.getBoolean(R.styleable.CBRatingBar_starCanTouch, DEFAULT_STAR_CAN_TOUCH);
        pathData = array.getString(R.styleable.CBRatingBar_starPathData);
        pathDataId = array.getResourceId(R.styleable.CBRatingBar_starPathDataId, DEFAULT_STAR_PATH_DATA_ID);
        coverDir = array.getInteger(R.styleable.CBRatingBar_starCoverDir, CoverDir.leftToRight);

        starProgress = Math.max(starProgress, 0);
        starProgress = Math.min(starProgress, starMaxProgress);

        array.recycle();
    }

    private void setupStrokePaint() {
        starStrokePaint.setStyle(Paint.Style.STROKE);
        starStrokePaint.setStrokeWidth(starStrokeWidth);
        starStrokePaint.setColor(starStrokeColor);
    }

    private void setupFillPaint() {
        starFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        starFillPaint.setColor(starFillColor);
    }

    private void setupCoverPaint() {
        starCoverPaint.reset();
        starCoverPaint.setAntiAlias(true);
        starCoverPaint.setStyle(Paint.Style.FILL);
        if (!useGradient) {
            starCoverPaint.setColor(starCoverColor);
        } else {
            float x0 = 0;
            float y0 = 0;
            float x1 = 0;
            float y1 = 0;

            switch (coverDir) {
                case CoverDir.leftToRight:
                    x0 = 0;
                    y0 = 0;
                    x1 = width;
                    y1 = 0;
                    break;
                case CoverDir.rightToLeft:
                    x0 = width;
                    y0 = 0;
                    x1 = 0;
                    y1 = 0;
                    break;
                case CoverDir.topToBottom:
                    x0 = 0;
                    y0 = 0;
                    x1 = 0;
                    y1 = starSize;
                    break;
                case CoverDir.bottomToTop:
                    x0 = 0;
                    y0 = starSize;
                    x1 = 0;
                    y1 = 0;
                    break;
            }
            starCoverPaint.setShader(new LinearGradient(x0, y0, x1, y1, startColor, endColor, Shader.TileMode.CLAMP));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float coverSize = (float) (width * starProgress * 1.0 / starMaxProgress);
        if (coverDir == CoverDir.topToBottom || coverDir == CoverDir.bottomToTop) {
            coverSize = (float) (starSize * starProgress * 1.0 / starMaxProgress);
        }

        //将已经填充了颜色的星星绘制到画布上
        canvas.drawBitmap(getCoverStarBitmap(coverSize), 0, 0, null);
        if (showStroke) {
            //给星星添加边框
            drawStar(canvas, starStrokePaint);
        }
    }

    /**
     * 获取已经绘制了进度的星星的bitmap对象
     *
     * @param coverSize 覆盖的大小
     *
     * @return
     */
    private Bitmap getCoverStarBitmap(float coverSize) {
        //将星星绘制到star上
        Bitmap star = Bitmap.createBitmap(width, starSize, Bitmap.Config.ARGB_8888);
        Canvas starCanvas = new Canvas(star);
        drawStar(starCanvas, starFillPaint);

        //在star上填充颜色
        Bitmap finalStar = Bitmap.createBitmap(width, starSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalStar);
        canvas.save();

        float left = 0;
        float right = 0;
        float top = 0;
        float bottom = 0;

        switch (coverDir) {
            case CoverDir.leftToRight:
                left = 0;
                top = 0;
                right = coverSize;
                bottom = starSize;
                break;
            case CoverDir.rightToLeft:
                left = width - coverSize;
                top = 0;
                right = width;
                bottom = starSize;
                break;
            case CoverDir.topToBottom:
                left = 0;
                top = 0;
                right = width;
                bottom = coverSize;
                break;
            case CoverDir.bottomToTop:
                left = 0;
                top = starSize - coverSize;
                right = width;
                bottom = starSize;
                break;
        }
        canvas.clipRect(left, top, right, bottom);
        canvas.drawRect(left, top, right, bottom, starCoverPaint);
        canvas.restore();

        canvas.drawBitmap(star, 0, 0, starPaint);

        return finalStar;
    }

    /**
     * 绘制星星
     *
     * @param canvas 画布
     * @param paint  画笔
     */
    private void drawStar(Canvas canvas, Paint paint) {
        int x = 0;
        for (int i = 0; i < starCount; ++i) {
            Path path = getOffsetPath(x);
            canvas.drawPath(path, paint);
            x += (starSize + starSpace);
        }
    }

    /**
     * 获取偏移dx距离的path
     *
     * @param dx x轴距离起点距离
     *
     * @return
     */
    private Path getOffsetPath(int dx) {
        Path path = new Path();
        //直接用offset方法很方便，但是在as中预览时会显示不出来
        //mPath.offset(dx, 0, path);
        Matrix offsetMatrix = new Matrix();
        offsetMatrix.setTranslate(dx, 0);
        path.addPath(mPath, offsetMatrix);
        return path;
    }

    /**
     * 初始化path
     *
     * @return
     */
    private void initPath() {
        if (pathDataId != -1) {
            pathData = getResources().getString(pathDataId);
        }
        if (pathData != null && !"".equals(pathData.trim().replace(" ", ""))) {
            mPath = PathParserCompat.createPathFromPathData(pathData);
        }
        resizePath(mPath, starSize, starSize);
    }

    public void resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        RectF src = new RectF();
        path.computeBounds(src, true);
        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.FILL);
        path.transform(resizeMatrix);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = starSize * starCount + (starCount - 1) * starSpace;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        width = (int) result;
        return (int) result;
    }

    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = starSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return (int) result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canTouch && (coverDir == CoverDir.leftToRight || coverDir == CoverDir.rightToLeft)) {
            checkTouch(new PointF(event.getX(), event.getY()));
        }
        return super.onTouchEvent(event);
    }

    private void checkTouch(PointF touch) {
        if (touch.x > (starSize * starCount + (starCount - 1) * starSpace) || touch.y > starSize) {
            return;
        }
        int nowTouchCount;
        nowTouchCount = (int) (touch.x / (starSize + starSpace)) + 1;
        if (touch.x > (nowTouchCount * (starSize + starSpace) - starSpace)) {
            nowTouchCount = 0;
        }
        if (nowTouchCount > 0) {
            touchCount = nowTouchCount;
            if (coverDir == CoverDir.rightToLeft) {
                touchCount = starCount - touchCount + 1;
            }
            setStarProgress(starMaxProgress / starCount * touchCount);
            if (onStarTouchListener != null) {
                onStarTouchListener.onStarTouch(touchCount);
            }
        }
    }

    /**
     * 刷新
     *
     * @param requestLayout 是否需要重新计算宽高
     */
    private void reDraw(boolean requestLayout) {
        if (requestLayout) {
            requestLayout();
        }
        invalidate();
    }

    public CBRatingBar setStarSize(int starSize) {
        if (starSize <= 0) {
            starSize = DEFAULT_STAR_SIZE;
        }
        this.starSize = starSize;
        resizePath(mPath, starSize, starSize);
        reDraw(true);
        return this;
    }

    public CBRatingBar setStarCount(int starCount) {
        if (starCount <= 0) {
            starCount = DEFAULT_STAR_COUNT;
        }
        this.starCount = starCount;
        reDraw(true);
        return this;
    }

    public CBRatingBar setStarSpace(int starSpace) {
        this.starSpace = starSpace;
        reDraw(true);
        return this;
    }

    public CBRatingBar setStarStrokeWidth(int starStrokeWidth) {
        this.starStrokeWidth = starStrokeWidth;
        setupStrokePaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setShowStroke(boolean showStroke) {
        this.showStroke = showStroke;
        reDraw(false);
        return this;
    }

    public CBRatingBar setStarStrokeColor(int starStrokeColor) {
        this.starStrokeColor = starStrokeColor;
        setupStrokePaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setStarFillColor(int starFillColor) {
        this.starFillColor = starFillColor;
        setupFillPaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setStarCoverColor(int starCoverColor) {
        this.starCoverColor = starCoverColor;
        setupCoverPaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setStarMaxProgress(float starMaxProgress) {
        if (starMaxProgress <= 0) {
            starMaxProgress = DEFAULT_STAR_MAX_PROGRESS;
        }
        this.starMaxProgress = starMaxProgress;
        reDraw(false);
        return this;
    }

    public CBRatingBar setStarProgress(float starProgress) {
        starProgress = Math.max(0, starProgress);
        starProgress = Math.min(starProgress, starMaxProgress);
        this.starProgress = starProgress;
        reDraw(false);
        return this;
    }

    public CBRatingBar setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
        setupCoverPaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setStartColor(int startColor) {
        this.startColor = startColor;
        setupCoverPaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setEndColor(int endColor) {
        this.endColor = endColor;
        setupCoverPaint();
        reDraw(false);
        return this;
    }

    public CBRatingBar setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
        return this;
    }

    public CBRatingBar setOnStarTouchListener(OnStarTouchListener onStarTouchListener) {
        this.onStarTouchListener = onStarTouchListener;
        return this;
    }

    public CBRatingBar setPath(Path mPath) {
        this.mPath = mPath;
        reDraw(false);

        return this;
    }

    public CBRatingBar setPathData(String pathData) {
        this.pathData = pathData;
        this.pathDataId = -1;
        initPath();
        reDraw(false);

        return this;
    }

    public CBRatingBar setPathDataId(int pathDataId) {
        this.pathDataId = pathDataId;
        pathData = null;
        initPath();
        reDraw(false);

        return this;
    }

    public CBRatingBar setDefaultPathData() {
        return this.setPathDataId(DEFAULT_STAR_PATH_DATA_ID);
    }

    public CBRatingBar setCoverDir(int coverDir) {
        if (coverDir < 0 || coverDir > 3) {
            coverDir = 0;
        }
        this.coverDir = coverDir;
        if (useGradient) {
            setupCoverPaint();
        }
        reDraw(false);

        return this;
    }

    public int getStarSize() {
        return starSize;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getStarSpace() {
        return starSpace;
    }

    public int getStarStrokeWidth() {
        return starStrokeWidth;
    }

    public boolean isShowStroke() {
        return showStroke;
    }

    public int getStarStrokeColor() {
        return starStrokeColor;
    }

    public int getStarFillColor() {
        return starFillColor;
    }

    public int getStarCoverColor() {
        return starCoverColor;
    }

    public float getStarMaxProgress() {
        return starMaxProgress;
    }

    public float getStarProgress() {
        return starProgress;
    }

    public boolean isUseGradient() {
        return useGradient;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public int getTouchCount() {
        return touchCount;
    }

    public Path getPath() {
        return mPath;
    }

    public String getPathData() {
        return pathData;
    }

    public int getPathDataId() {
        return pathDataId;
    }

    public int getCoverDir() {
        return coverDir;
    }
}

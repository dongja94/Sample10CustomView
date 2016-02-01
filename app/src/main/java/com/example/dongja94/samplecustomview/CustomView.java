package com.example.dongja94.samplecustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

/**
 * Created by dongja94 on 2016-01-29.
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    Paint mPaint = new Paint();

    float[] points;
    Path mDrawPath;
    Path mTextPath;

    Path mShapePath;

    GestureDetector mDetector;

    private void init(AttributeSet attrs) {
        points = new float[(300 / 10 + 1) * 2 * 2];
        int index = 0;
        for (int i = 0; i <= 300; i += 10) {
            points[index++] = 0;
            points[index++] = i;
            points[index++] = 300 - i;
            points[index++] = 0;
        }

        mDrawPath = new Path();
        mDrawPath.moveTo(200, 200);
        mDrawPath.lineTo(100, 100);
        mDrawPath.lineTo(300, 100);
        mDrawPath.lineTo(400, 200);
        mDrawPath.lineTo(300, 300);
        mDrawPath.lineTo(100, 300);

        mTextPath = new Path();
        mTextPath.addCircle(400, 400, 300, Path.Direction.CW);


//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_0);

        InputStream is = getResources().openRawResource(R.drawable.sample_0);

        mBitmap = BitmapFactory.decodeStream(is);

        Bitmap bm = Bitmap.createScaledBitmap(mBitmap, 400, 400, false);
        mBitmap.recycle();

        mBitmap = bm;

        mShapePath = new Path();
        mShapePath.lineTo(-5, -5);
        mShapePath.lineTo(0, -5);
        mShapePath.lineTo(5, 0);
        mShapePath.lineTo(0, 5);
        mShapePath.lineTo(-5, 5);

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
            Drawable d = ta.getDrawable(R.styleable.CustomView_icon);
            if (d != null) {
                mBitmap = ((BitmapDrawable) d).getBitmap();
            }
            String myname = (String) ta.getText(R.styleable.CustomView_myname);
            String title = (String)ta.getText(R.styleable.CustomView_android_title);
            ta.recycle();
        }

        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mScale *= 1.5f;
                invalidate();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mScale /= 1.5f;
                invalidate();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mX -= distanceX / mScale;
                mY -= distanceY / mScale;
                invalidate();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 != null && e2 != null) {
                    float deltaX = e1.getX() - e2.getX();
                    if (deltaX > 0) {
                        Log.i("CustomView", "fling next : " + deltaX);
                    } else {
                        Log.i("CustomView", "fling prev : " + deltaX);
                    }
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    float mScale = 1.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consumed = mDetector.onTouchEvent(event);
        return consumed || super.onTouchEvent(event);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        requestLayout();
        invalidate();
    }

    Bitmap mBitmap;
    Matrix mMatrix = new Matrix();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingTop() + getPaddingBottom();

        if (mBitmap != null) {
            width += mBitmap.getWidth();
            height += mBitmap.getHeight();
        }

//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//        switch (mode) {
//            case MeasureSpec.EXACTLY :
//                width = size;
//                break;
//            case MeasureSpec.AT_MOST :
//                if (width > size) {
//                    width = size;
//                }
//                break;
//            case MeasureSpec.UNSPECIFIED :
//                break;
//        }

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);

    }

    int mX, mY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mX = getPaddingLeft() + ((right - left) - getPaddingLeft() - getPaddingRight() - mBitmap.getWidth()) / 2;
        mY = getPaddingTop() + ((bottom - top) - getPaddingTop() - getPaddingBottom() - mBitmap.getHeight()) / 2;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
//        drawRect(canvas);
//        drawCircle(canvas);
//        drawPath(canvas);
//        drawText(canvas);
//        drawBitmap(canvas);
//        drawStroke(canvas);
//        drawPathEffect(canvas);
//        drawShader(canvas);
//        drawColorEffect(canvas);
        drawLayout(canvas);
    }

    private void drawLayout(Canvas canvas) {
        mMatrix.reset();
        mMatrix.setScale(mScale, mScale, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.postTranslate(mX, mY);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }

    float[] src = {
        -1, 0, 0, 0, 255,
        0, -1, 0, 0, 255,
        0, 0, -1, 0, 255,
        0, 0, 0, 1, 0
    };

    float saturation = 1;
    private void drawColorEffect(Canvas canvas) {

        mPaint.setColorFilter(null);
        canvas.drawBitmap(mBitmap, 100, 100, mPaint);

//        ColorMatrix colorMatrix = new ColorMatrix(src);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturation);
        saturation-= 0.01f;
        if (saturation < 0) {
            saturation = 1;
        }
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, 100, 600, mPaint);
        invalidate();
    }

    private void drawShader(Canvas canvas) {

        int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.RED};
//        LinearGradient shader = new LinearGradient(100, 100, 500, 500, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
//        LinearGradient shader = new LinearGradient(200, 100, 400, 100, colors, null, Shader.TileMode.MIRROR);
//        RadialGradient shader = new RadialGradient(300, 300, 200, colors, null, Shader.TileMode.CLAMP);
        SweepGradient shader = new SweepGradient(300, 300, colors, null);
//        mPaint.setColor(Color.RED);
        mPaint.setShader(shader);
        canvas.drawCircle(300, 300, 200, mPaint);

//        mPaint.setAlpha(0x80);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);


    }
    private void drawPathEffect(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
//        float[] intervals = {20, 10, 30, 10, 10, 10};
//        PathEffect effect = new DashPathEffect(intervals, 20);

        PathEffect effect = new PathDashPathEffect(mShapePath, 10, 0, PathDashPathEffect.Style.ROTATE);

        mPaint.setPathEffect(effect);

        canvas.drawCircle(300, 300, 200, mPaint);
    }

    private void drawStroke(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);

        canvas.drawCircle(300, 300, 200, mPaint);
    }
    float[] meshPoints = { 100, 100, 200, 200, 300, 100, 400, 200, 500, 100,
                             100, 500, 200, 600, 300, 500, 400, 600, 500, 500};
    private void drawBitmap(Canvas canvas) {
//        mMatrix.reset();
////        mMatrix.setTranslate(100, 100);
////        mMatrix.postRotate(45, 0, 0);
//        mMatrix.setScale(1, -1, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//        mMatrix.postTranslate(100, 100);
//        canvas.drawBitmap(mBitmap, mMatrix, mPaint);

        canvas.drawBitmapMesh(mBitmap, 4, 1, meshPoints, 0, null, 0, mPaint);
    }

    private static final String TEXT_MESSAGE = "Hello! Android g";

    int vOffset = 0;
    private void drawText(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
        mPaint.setTextSkewX(0.5f);
        mPaint.setFakeBoldText(true);
        canvas.drawText(TEXT_MESSAGE, 40, 40, mPaint);


        canvas.drawTextOnPath(TEXT_MESSAGE, mTextPath, vOffset, 0, mPaint);

        vOffset += 5;
        invalidate();
    }

    private void drawPath(Canvas canvas) {
        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mDrawPath, mPaint);
    }

    private void drawRect(Canvas canvas) {
        mPaint.setColor(Color.GREEN);

        canvas.drawRect(100, 100, 400, 400, mPaint);

        mPaint.setColor(Color.BLUE);
        RectF rect = new RectF(100, 500, 400, 900);
        canvas.drawRoundRect(rect, 50, 50, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, 100, mPaint);

        RectF rect = new RectF(100, 400, 400, 600);
        canvas.drawOval(rect, mPaint);

        RectF rect2 = new RectF(100, 700, 300, 900);
        canvas.drawArc(rect2, 45, 90, true, mPaint);

        RectF rect3 = new RectF(400, 700, 600, 900);
        canvas.drawArc(rect3, 45, 90, false, mPaint);
    }

    private void drawLineAndPoint(Canvas canvas) {
        canvas.translate(100, 100);
        canvas.rotate(30, 100, 100);
//        for (int i = 0 ; i <= 300; i+=10) {
//            mPaint.setColor(Color.RED);
//            mPaint.setStrokeWidth(3);
//            canvas.drawLine(0 , i , 300 - i , 0, mPaint);
//
//            mPaint.setColor(Color.BLUE);
//            mPaint.setStrokeWidth(5);
//            canvas.drawPoint(0, i, mPaint);
//            canvas.drawPoint(300 - i , 0, mPaint);
//        }

        mPaint.setAntiAlias(true);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        canvas.drawLines(points, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);

        canvas.drawPoints(points, mPaint);
    }
}

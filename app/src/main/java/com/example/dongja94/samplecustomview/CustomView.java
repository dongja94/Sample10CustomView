package com.example.dongja94.samplecustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dongja94 on 2016-01-29.
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint = new Paint();

    float[] points;
    Path mDrawPath;

    private void init() {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
//        drawRect(canvas);
//        drawCircle(canvas);
        drawPath(canvas);
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

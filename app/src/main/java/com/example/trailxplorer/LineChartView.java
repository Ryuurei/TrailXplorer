package com.example.trailxplorer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class LineChartView extends View {

    private float[] datapoints = new float[] {};
    private Paint paint = new Paint();
    private Paint paint2 = new Paint();
    private int y;

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // datapoints are y values of the chart
    public void setChartData(float[] datapoints) {
        this.datapoints = datapoints.clone();
        invalidate();
    }

    // Draw the graph background, and the lines between two points
    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLineChart(canvas);
    }

    // Draw the graph background
    private void drawBackground(Canvas canvas) {

        //The speed is said to be between 0 and 10 km/h.
        // Putting 12 as the max value to see the full graph
        float maxValue = 12;
        int range = 2;

        paint.setStyle(Style.STROKE);
        paint.setColor(Color.GRAY);
        paint2 = paint;
        paint2.setTextSize(30f);

        // Draw the background grey line for every y number (range of 2)

        for (y = 0; y < maxValue; y += range) {
            final float yPos = getYPos(y);
            canvas.drawText(String.valueOf(y), 0, yPos, paint2);
            canvas.drawLine(0, yPos, getWidth(), yPos, paint);
        }
        canvas.drawLine(0, getYPos((float) 0.001), getWidth(), getYPos((float) 0.001), paint);
    }

    private void drawLineChart(Canvas canvas) {
        // Draw lines between 2 consecutive points from the array

        Path path = new Path();
        path.moveTo(getXPos(0), getYPos(datapoints[0]));
        for (int i = 1; i < datapoints.length; i++) {
            path.lineTo(getXPos(i), getYPos(datapoints[i]));
        }

        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(0xFF33B5F4);
        canvas.drawPath(path, paint);
    }

    private float getYPos(float value) {
        // Y-axis view adjustment, this is to make sure all the Y axis is fully visible

        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxValue = 12;

        // Scale it to the view size
        value = (value / maxValue) * height;
        value = height - value;

        // Padding adjustment
        value += getPaddingTop();
        return value;
    }

    private float getXPos(float value) {
        // X-axis view adjustment, this is to make sure the X axis is fully visible

        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = datapoints.length - 1;

        // Scale it to the view size
        value = (value / maxValue) * width;

        // Padding adjustment
        value += getPaddingLeft();

        return value;
    }
}
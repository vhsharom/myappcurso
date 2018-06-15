package com.domain.navigationdrawer.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class MySwitch extends View {

    int width, height;

    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        ((AppCompatActivity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Log.d("App", "Width: " + getWidth() + ", Height: " + getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(2);

        width = getWidth();
        height = getHeight();

        int[] values = new int[] {20, 40, 60, 80, 100};
        int columWidth = width / values.length;

        for (int i = 0; i<values.length; i++){

            double columnHeight = height * values[i]/100.0f;
            double columnTop = height * (1.0f - (values[i]/100.0f));
            canvas.drawRect((float) columWidth * i, (float)columnTop, (float)columWidth, (float) columnHeight, paint);
        }

        //canvas.drawRect(100, 200, 200, 300, paint);


        canvas.save();

    }

}

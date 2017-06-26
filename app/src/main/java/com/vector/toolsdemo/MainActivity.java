package com.vector.toolsdemo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.vector.libtools.ui.DrawableUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GradientDrawable gradientDrawable = new GradientDrawable();
        // 设置绘画图片色值
        gradientDrawable.setColor(Color.WHITE);
        // 绘画的是矩形
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        // 设置矩形的圆角半径
        gradientDrawable.setCornerRadius(30);

        gradientDrawable.setStroke(4, Color.RED);

        Button button = (Button) findViewById(R.id.btn);


        button.setBackgroundDrawable(DrawableUtils.getDrawable(6, 3, Color.MAGENTA, Color.LTGRAY));

    }
}

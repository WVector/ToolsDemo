package com.vector.toolsdemo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vector.libtools.ui.DrawableUtils;

import static com.vector.toolsdemo.R.id.btn;

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

        Button button = (Button) findViewById(btn);

        double v = ColorUtils.calculateLuminance(Color.RED);

        Log.d("MainActivity", "v:" + v);

        button.setBackgroundDrawable(DrawableUtils.getDrawable(6, 3, Color.MAGENTA, Color.LTGRAY));


        final ImageView srcIv = (ImageView) findViewById(R.id.iv_src);
        final ImageView descIv = (ImageView) findViewById(R.id.iv_desc);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int red = 0xff000000;
                srcIv.setBackgroundColor(red);
            }
        });

    }


}

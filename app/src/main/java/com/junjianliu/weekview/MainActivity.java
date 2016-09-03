package com.junjianliu.weekview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.junjianliu.library.widget.WeekView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeekView weekView = (WeekView) findViewById(R.id.week_view);
        weekView.setStatusChooseColor(android.R.color.black);            // 设置当前日期的状态颜色
        weekView.setStatusOldColor(android.R.color.holo_blue_light);     // 设置过去日期的状态颜色
        weekView.setStatusNewColor(android.R.color.darker_gray);         // 设置未来日期的状态颜色

        weekView.setOnWeekClickListener(new WeekView.OnClickListener() {
            @Override
            public void onClick(int pos, int code, int index, String weedday, String day) {

            }
        });

    }
}

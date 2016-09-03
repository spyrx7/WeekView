package com.junjianliu.library.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.junjianliu.library.R;
import com.junjianliu.library.adapter.ViewPagerAdapter;
import com.junjianliu.library.bean.DateModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by junjianliu
 * on 16/9/1
 * email:spyhanfeng@qq.com
 */
public class WeekView extends RelativeLayout {

    private static final int STATUS_CHOOSE = 1;   // 当前选中的状态
    private static final int STATUS_OLD = 2;      // 过去一周内的状态
    private static final int STATUS_NEW = 3;      // 未来一周的状态

    private int statusChooseColor = 0;
    private int statusOldColor = 0;
    private int statusNewColor = 0;

    private ViewPager viewPager;
    private Context context;

    private LinearLayout lastWeek;
    private LinearLayout nowWeek;
    private ViewPagerAdapter adapter;
    private List<DateModel> data = new ArrayList<>();

    private int index = 1;

    public WeekView(Context context) {
        super(context);
        init(context);
    }

    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(layoutParams);

        lastWeek = new LinearLayout(context);
        lastWeek.setOrientation(LinearLayout.HORIZONTAL);
        lastWeek.setLayoutParams(layoutParams);

        nowWeek = new LinearLayout(context);
        nowWeek.setOrientation(LinearLayout.HORIZONTAL);
        nowWeek.setLayoutParams(layoutParams);

        getWeekinfo();

        adapter = new ViewPagerAdapter();
        adapter.addView(lastWeek);
        adapter.addView(nowWeek);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.addView(viewPager);
    }

    private void getWeekinfo(){

        this.lastWeek.removeAllViews();
        this.nowWeek.removeAllViews();

        Calendar calendar = Calendar.getInstance();
        calendar.getFirstDayOfWeek();

        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) - 1,calendar.get(Calendar.DAY_OF_MONTH));

        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int lastMaxDay = lastCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        String[] days = new String[14];
        String[] weekdays = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
        days[0] = calendar.get(Calendar.DAY_OF_MONTH) + "";
        for (int i = 0; i < 7; i++) {

            int mday = 0;
            int day = calendar.get(Calendar.DAY_OF_MONTH)+(i + 1 - weekday - 7);
            if(day > maxDay){
                mday = day - maxDay;
            }else if (day < 1){
                mday = lastMaxDay - weekday - (7 - (i + 1));
            }else {
                mday = day;
            }

            DateModel temp = new DateModel();
            temp.setId(i);
            temp.setDay(mday+"");
            temp.setWeekday(weekdays[i]);

            lastWeek.addView(addlastWeekView(weekdays[i],mday + "",STATUS_NEW,i));

            data.add(temp);
        }

        for (int i = 0; i < 7; i++) {

            int mday = 0;
            int day = calendar.get(Calendar.DAY_OF_MONTH)+(i + 1 - weekday);
            if(day > maxDay){
                mday = day - maxDay;
            }else if (day < 1){
                mday = lastMaxDay - day;
            }else {
                mday = day;
            }

            DateModel temp = new DateModel();
            temp.setId(i);
            temp.setDay(mday + "");
            temp.setWeekday(weekdays[i]);
            if(i == weekday - 1){
                temp.setClick(true);
                nowWeek.addView(addlastWeekView(weekdays[i],mday + "",STATUS_CHOOSE,i));
            }else if(i > weekday - 1){
                nowWeek.addView(addlastWeekView(weekdays[i],mday + "",STATUS_OLD,i));
            }else{
                temp.setClick(false);
                nowWeek.addView(addlastWeekView(weekdays[i],mday + "",STATUS_NEW,i));
            }

            data.add(temp);
        }

    }

    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(int pos,int code,int index,String weedday,String day);
    }

    public void setStatusChooseColor(int color){
        this.statusChooseColor = color;
        getWeekinfo();
    }

    public void setStatusOldColor(int color){
        this.statusOldColor = color;
        getWeekinfo();
    }

    public void setStatusNewColor(int color){
        this.statusNewColor = color;
        getWeekinfo();
    }

    public void setOnWeekClickListener(OnClickListener listener){
        this.listener = listener;
    }

    private LinearLayout addlastWeekView(final String weekday, final String day, final int code, final int id){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.gravity = Gravity.CENTER;


        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setTag(id);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        TextView tvWeekday = new TextView(context);
        tvWeekday.setLayoutParams(params);
        tvWeekday.setTextSize(14);
        tvWeekday.setText(weekday);


        TextView tvDay = new TextView(context);
        tvDay.setLayoutParams(params);
        tvDay.setTextSize(20);
        tvDay.setText(day);

        if(code == STATUS_CHOOSE){
            if(statusChooseColor != 0){
                tvWeekday.setTextColor(getResources().getColor(statusChooseColor));
                tvDay.setTextColor(getResources().getColor(statusChooseColor));
            }else{
                tvWeekday.setTextColor(getResources().getColor(R.color.app_color_text_blue));
                tvDay.setTextColor(getResources().getColor(R.color.app_color_text_blue));
            }
        }else if( code == STATUS_OLD){
            if(statusOldColor != 0){
                tvWeekday.setTextColor(getResources().getColor(statusOldColor));
                tvDay.setTextColor(getResources().getColor(statusOldColor));
            }else{
                tvWeekday.setTextColor(getResources().getColor(R.color.app_color_bg));
                tvDay.setTextColor(getResources().getColor(R.color.app_color_bg));
            }
        }else{
            if(statusNewColor != 0){
                tvWeekday.setTextColor(getResources().getColor(statusNewColor));
                tvDay.setTextColor(getResources().getColor(statusNewColor));
            }
        }


        linearLayout.addView(tvWeekday);
        linearLayout.addView(tvDay);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(id,code,index,weekday,day);
                }
            }
        });

        return linearLayout;
    }


}

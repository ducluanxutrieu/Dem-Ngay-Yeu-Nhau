package com.ducluanxutrieu.ducluan.demngayyeunhau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ShowCountDayActivity extends AppCompatActivity {
    TextView tvDateLove;
    int dayLove, monthLove, yearLove;
    int dayNow, monthNow, yearNow;
    int days = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_count_day);

        tvDateLove = findViewById(R.id.tvDateLove);

        Intent intent = getIntent();
        String value = intent.getStringExtra("dateLove");

        Log.i("value" , value + "dasfasdf");
        dayLove = Integer.parseInt(value.substring(0, 2));
        monthLove = Integer.parseInt(value.substring(3, 5)) + 1;
        yearLove = Integer.parseInt(value.substring(6));

        GetDayNow();
        Check();

        tvDateLove.setText(days + " Ngày");
        //tvDateLove.setText(dayLove + "/"+ monthLove+ "/" + yearLove+ "/" + "temp");
    }

    private int CheckMonth(int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (LeapYears(yearLove)) {
                    return 29;
                } else {
                    return 28;
                }
            default: return 0;
        }
    }
    private boolean LeapYears(int nam){
        if ((nam % 4 == 0) && (nam % 100 != 0) || (nam % 400 == 0) ){
            return true;
        }
        return false;
    }
    private void GetDayNow(){
        Calendar calendar = Calendar.getInstance();
        dayNow = calendar.get(Calendar.DAY_OF_MONTH);
        monthNow = calendar.get(Calendar.MONTH) + 1;
        yearNow = calendar.get(Calendar.YEAR);
    }
    private void Check(){
        Log.i("days", dayNow + "day now");
        Log.i("days", monthNow + "month now");
        Log.i("days",  CheckMonth(monthNow) + "day month");


        days = dayNow - CheckMonth(monthNow);
        Log.i("days", days + "day 1");
        while (yearNow > yearLove) {
            Log.i("days", days + "day test");
            if (monthNow > 0) {
                days += CheckMonth(monthNow);
                 monthNow --;
            }else {
                monthNow += 12;
                yearNow --;
            }
        }

        //yearNow = yearLove
        while (monthNow > monthLove) {
            days += CheckMonth(monthNow);
            Log.i("days", days + "day bang");
            monthNow--;
        }
        days += (CheckMonth(monthNow) - dayLove);
        Log.i("days", days + "day tong");
    }

    public void ChangeDate(View view){
        MainActivity.sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(ShowCountDayActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

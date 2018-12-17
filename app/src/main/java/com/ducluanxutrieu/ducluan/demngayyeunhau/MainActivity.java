package com.ducluanxutrieu.ducluan.demngayyeunhau;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    DatePicker datePicker;
    TextView tvDateLove;
    String dateLove;
    int dayNow, monthNow, yearNow;
    String days = "";

    int dayLove, monthLove, yearLove;
    static SharedPreferences sharedPreferences;
    DecimalFormat decimalFormat = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.datePicker);
        btnStart = findViewById(R.id.btnStart);
        tvDateLove = findViewById(R.id.tvDateLove);

        sharedPreferences = getSharedPreferences("com.ducluanxutrieu.ducluan.demngayyeunhau", MODE_PRIVATE);
        days = sharedPreferences.getString("dateLove", null);

        GetDate();
        DatePicker();


    }


    private boolean CheckInvalid(){
        if (yearLove < yearNow ){
            return true;
        }else if (yearLove == yearNow){
            if (monthLove < monthNow){
                return true;
            }else if (monthLove == monthNow){
                if (dayLove <= dayNow) return true;
                else return false;
            }else return false;
        }else return false;
    }

    private void GetDate(){
        if (days == null){
            Calendar calendar = Calendar.getInstance();
            dayNow = calendar.get(Calendar.DAY_OF_MONTH);
            monthNow = calendar.get(Calendar.MONTH);
            yearNow = calendar.get(Calendar.YEAR);

            days = decimalFormat.format(dayNow) ;
            days += "/" + decimalFormat.format(monthNow) ;
            days += "/" + yearNow;

            tvDateLove.setText(dayNow + "/" + monthNow + "/" + yearNow);
        }else {
            Intent intent = new Intent(MainActivity.this, ShowCountDayActivity.class);
            intent.putExtra("dateLove", days);
            startActivity(intent);
            finish();
        }


    }

    public void DatePicker(){

        datePicker.init(yearNow, monthNow, dayNow, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                yearLove = year;
                monthLove = monthOfYear;
                dayLove = dayOfMonth;

                days = decimalFormat.format(dayLove) ;
                days += "/" + decimalFormat.format(monthLove) ;
                days += "/" + yearLove;



                if (CheckInvalid()){
                    Toast.makeText(MainActivity.this,dayLove + "/" + monthLove + 1 + "/" + yearLove, Toast.LENGTH_SHORT ).show();
                    tvDateLove.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    btnStart.setEnabled(true);
                }else {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    tvDateLove.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    btnStart.setEnabled(false);
                }
            }
        });
    }

    public void Start(View view) {
        sharedPreferences.edit().putString("dateLove", days).apply();
        Intent intent = new Intent(this, ShowCountDayActivity.class);
        intent.putExtra("dateLove", days);
        startActivity(intent);
        finish();

    }

}

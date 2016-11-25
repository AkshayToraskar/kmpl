package com.ak.kmpl.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ak.kmpl.R;
import com.ak.kmpl.app.PrefManager;
import com.jackpocket.scratchoff.ScratchoffController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TipsAndTrickActivity extends AppCompatActivity {

    ScratchoffController controller;
    TextView tvTips;

    public static int count = 0;
    PrefManager prefManager;
    String[] tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_and_trick);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tips = getResources().getStringArray(R.array.tips_tricks);
        tvTips = (TextView) findViewById(R.id.tvTips);
        prefManager = new PrefManager(this);
        getSupportActionBar().setTitle("Tips & Tricks");


        setTips();


        controller = new ScratchoffController(this)
                .setThresholdPercent(0.40d)
                .setTouchRadiusDip(this, 30)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .attach(findViewById(R.id.scratch_view), findViewById(R.id.scratch_view_behind));

        /*controller.setCompletionCallback(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(TipsAndTrickActivity.this,"asdf",Toast.LENGTH_SHORT).show();
                *//*if (count >= tips.length) {
                    count = 0;
                } else {
                    int count=prefManager.getTipsCount()+1;
                    prefManager.setTipsTimeAndCount(count);
                }*//*

                setTips();

            }
        });*/

    }

    public void setTips() {
        long lastLoginTime = prefManager.getLastTipsTime();
        Date dt = new Date(lastLoginTime);
        long diff = getDateDiff(dt, new Date(), TimeUnit.HOURS);

        if (diff >= 8) {

            if (count > tips.length) {
                count = 0;
            } else {
                count = prefManager.getTipsCount() + 1;
            }
            prefManager.setTipsTimeAndCount(count);

            tvTips.setText(Html.fromHtml(tips[prefManager.getTipsCount()]));

        } else {
            tvTips.setText(Html.fromHtml(tips[prefManager.getTipsCount()]));
        }
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        Log.v("Date Difference", "is : " + timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS));
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                //overridePendingTransition(R.anim.anim_close_act,R.anim.anim_start_act);
                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        controller.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.onResume();
    }

    @Override
    public void onDestroy() {
        controller.onDestroy();
        super.onDestroy();
    }
}

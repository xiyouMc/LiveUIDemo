package top.codemc.liveappuidemo;

import com.nineoldandroids.animation.ObjectAnimator;
import com.vivavideo.mobile.commonui.HoloCircularProgressBar;
import com.vivavideo.mobile.danmu.danmu.DanmuControl;
import com.vivavideo.mobile.danmu.model.Danmu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button create_live_bt, play_live_bt, show_userinfo, gird_view_gift, live_over, viewpage_btn, live_anim, charge, activity_time_count_down,heart;

    private HoloCircularProgressBar mHoloCircularProgressBar;
    private ObjectAnimator mProgressBarAnimator;
    public static final int TIME_RANGE = 30;

    private IDanmakuView mDanmakuView;
    private DanmuControl mDanmuControl;

    private Button btnAddDanmu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_main);

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(R.id.progressBar);
        mHoloCircularProgressBar.setTextSize(50);
        mHoloCircularProgressBar.setText("连送");
        mHoloCircularProgressBar.setTimeRange(TIME_RANGE);

        final CountDownTimer start = new CountDownTimer(TIME_RANGE * 1000, 1 * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("millisUntilFinished = " + millisUntilFinished);
                mHoloCircularProgressBar.setLeftTime((int) (millisUntilFinished / 1000));
                mHoloCircularProgressBar.setText("连送 " + mHoloCircularProgressBar.getText());
            }

            @Override
            public void onFinish() {

            }
        };
//        start.start();

        mHoloCircularProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mHoloCircularProgressBar.setTimeRange(TIME_RANGE);
//                mHoloCircularProgressBar.setLeftTime(TIME_RANGE);
                start.cancel();
                mHoloCircularProgressBar.setLeftTime(TIME_RANGE);
                start.start();
            }
        });


        create_live_bt = (Button) findViewById(R.id.create_live_bt);
        play_live_bt = (Button) findViewById(R.id.play_live_bt);
        show_userinfo = (Button) findViewById(R.id.show_userinfo);
        gird_view_gift = (Button) findViewById(R.id.gird_view_gift);
        gird_view_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GirdViewActivity.class);
                startActivity(intent);
            }
        });
        create_live_bt.setOnClickListener(this);
        play_live_bt.setOnClickListener(this);
        show_userinfo.setOnClickListener(this);
        live_over = (Button) findViewById(R.id.live_over);
        live_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CloseLiveActivity.class);
                startActivity(intent);
            }
        });
        viewpage_btn = (Button) findViewById(R.id.viewpage_btn);
        viewpage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPageActivity.class);
                startActivity(intent);
            }
        });
        live_anim = (Button) findViewById(R.id.live_anim);
        live_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.animation.MainActivity.class);
                startActivity(intent);
            }
        });
//        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        TextView test_text = (TextView) findViewById(R.id.test_text);
        TextPaint tp = test_text.getPaint();
        tp.setFakeBoldText(true);
        test_text.setTextColor(Color.parseColor("#FF6600"));

        charge = (Button) findViewById(R.id.charge);
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });

        activity_time_count_down = (Button) findViewById(R.id.activity_time_count_down);
        activity_time_count_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimeCountDown.class);
                startActivity(intent);
            }
        });

        heart = (Button)findViewById(R.id.heart_btn);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HeartActivity.class);
                startActivity(intent);
            }
        });

        danmu();
    }

    private void danmu(){
        mDanmuControl = new DanmuControl(this);
        mDanmakuView = (IDanmakuView) findViewById(R.id.danmakuView);
        mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
            @Override
            public void onDanmakuClick(BaseDanmaku latest) {
                Log.d("Main","onDanmakuClick:" + latest.userHash);
            }

            @Override
            public void onDanmakuClick(IDanmakus danmakus) {
                Log.d("Main","onDanmakuClick:" + danmakus.toString());
            }
        });
        btnAddDanmu = (Button) findViewById(R.id.btnAddDanmu);
        mDanmuControl.setDanmakuView(mDanmakuView);
        btnAddDanmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
    }
//
    private void setData() {
        List<Danmu> danmus = new ArrayList<>();
        Danmu danmu1 = new Danmu(0, 1, "Like", R.drawable.ic_default_header, "");
        Danmu danmu2 = new Danmu(0, 2, "Comment", R.drawable.ic_default_header, "这是一条弹幕啦啦啦");
        Danmu danmu3 = new Danmu(0, 3, "Like", R.drawable.ic_default_header, "");
        Danmu danmu4 = new Danmu(0, 1, "Comment", R.drawable.wat, "这又是一条弹幕啦啦啦");
        Danmu danmu5 = new Danmu(0, 2, "Like", R.drawable.wat, "");
        Danmu danmu6 = new Danmu(0, 3, "Comment", R.drawable.wat, "这还是一条弹幕啦啦啦");
        danmus.add(danmu1);
        danmus.add(danmu2);
        danmus.add(danmu3);
        danmus.add(danmu4);
        danmus.add(danmu5);
        danmus.add(danmu6);
        Collections.shuffle(danmus);
        mDanmuControl.addDanmuList(danmus);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.create_live_bt && v.getId() != R.id.play_live_bt && v.getId() != R.id.show_userinfo) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, v.getId() == R.id.create_live_bt ? CreateLiveActivity.class
                : v.getId() != R.id.show_userinfo ? PlayLiveActivity.class : ShowUserInfo.class);
        this.startActivity(intent);
        this.overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        mDanmuControl.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mDanmuControl.resume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDanmuControl.destroy();
//    }
}


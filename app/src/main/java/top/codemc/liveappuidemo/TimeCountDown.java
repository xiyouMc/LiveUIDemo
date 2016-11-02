package top.codemc.liveappuidemo;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class TimeCountDown extends ActionBarActivity {

    private TextView timeCountDown;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);
        time = 10;
        timeCountDown = (TextView) findViewById(R.id.time);
        timeCountDown.setText(time + "");
        final Spring timeScaleSpring = SpringSystem.create().createSpring().setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(10, 7)).addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - value;
                timeCountDown.setScaleX(scale);
                timeCountDown.setScaleY(scale);
            }
        });
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time -= 1;
                timeCountDown.setText(time + "");
                timeScaleSpring.setEndValue(1);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeCountDown.invalidate();
                        timeScaleSpring.setEndValue(0);

                    }
                }, 500);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}

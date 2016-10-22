package top.codemc.liveappuidemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;

import top.codemc.liveappuidemo.view.likeanimationlayout.LikeAnimationLayout;

public class HeartActivity extends ActionBarActivity {

    private LikeAnimationLayout likeAnimationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        likeAnimationLayout = (LikeAnimationLayout) findViewById(R.id.like);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            likeAnimationLayout.addFavor();

                        }
                    });

                }
            }
        }).start();
    }

}

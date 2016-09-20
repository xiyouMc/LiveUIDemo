package top.codemc.liveappuidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button create_live_bt, play_live_bt, show_userinfo, gird_view_gift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
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
}

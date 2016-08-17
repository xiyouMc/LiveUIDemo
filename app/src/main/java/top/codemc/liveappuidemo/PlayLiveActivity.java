package top.codemc.liveappuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class PlayLiveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.play_live_activity);
    }
}

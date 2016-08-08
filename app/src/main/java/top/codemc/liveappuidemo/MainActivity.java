package top.codemc.liveappuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import top.codemc.liveappuidemo.fragment.McLiveFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_main);
    }
}

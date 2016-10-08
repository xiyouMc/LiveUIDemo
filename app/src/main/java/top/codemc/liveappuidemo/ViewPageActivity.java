package top.codemc.liveappuidemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import top.codemc.liveappuidemo.view.VerticalViewPager;

public class ViewPageActivity extends ActionBarActivity {

    private VerticalViewPager mVerticalViewPager;
    private final int[] imgSource = { R.drawable.bg_android_v7_1,
            R.drawable.bg_android_v7_2, R.drawable.bg_android_v7_3,
            R.drawable.bg_android_v7_4, R.drawable.bg_android_v7_5,
            R.drawable.bg_android_v7_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_main);

        mVerticalViewPager = (VerticalViewPager)findViewById(R.id.viewpage);
        List<View> viewList = new ArrayList<View>();
        for (int i = 0; i < imgSource.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.pager, null);
            ImageView img = (ImageView) view.findViewById(R.id.pager);
            img.setImageResource(imgSource[i]);
            viewList.add(view);
        }
        mVerticalViewPager.setViewList(viewList);
        mVerticalViewPager.snapToScreen(2);
    }

}

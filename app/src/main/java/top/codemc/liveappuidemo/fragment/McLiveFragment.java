package top.codemc.liveappuidemo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.adapter.LiveChatListAdapter;
import top.codemc.liveappuidemo.model.MessageModel;
import top.codemc.liveappuidemo.model.UsersContainerModel;
import top.codemc.liveappuidemo.util.ScaleUtil;
import top.codemc.liveappuidemo.view.RvSpaceItemDecoration;

/**
 * Created by xiyoumc on 16/8/5.
 */
public class McLiveFragment extends Fragment implements LayoutAdapter.OnRecyclerViewListener, View.OnTouchListener {

    private static final String TAG = "McLiveFragment";

    private ListView liveChatListView;
    private LiveChatListAdapter liveChatListAdapter;
    private ImageView like;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        RecyclerView usersView = (RecyclerView) view.findViewById(R.id.listview_users);
        like = (ImageView) view.findViewById(R.id.like_pop);
        like.setOnTouchListener(this);
        usersView.setHasFixedSize(true);
        usersView.setLongClickable(false);
        configRecyclerView(activity, usersView);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHigth = metrics.heightPixels;
        final EditText inputEdit = (EditText) view.findViewById(R.id.live_edit_text);
        ViewGroup.LayoutParams params = inputEdit.getLayoutParams();
        params.width = screenWidth - 130;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        inputEdit.setLayoutParams(params);
        inputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String text = inputEdit.getText().toString();
                    if (text.isEmpty()) {
                        return false;
                    }
                    MessageModel messageModel = new MessageModel.Builder().userName("xiyouMc:")
                            .content("Github:https://www.github.com/xiyouMc" + " content:" + text).build();
                    liveChatListAdapter.addMessage(messageModel);
                    inputEdit.setText("");
                }
                return false;
            }
        });

        liveChatListView = (ListView) view.findViewById(R.id.live_chatlist);
        liveChatListAdapter = new LiveChatListAdapter();
        liveChatListView.setAdapter(liveChatListAdapter);

    }

    private void configRecyclerView(Activity activity, RecyclerView usersView) {
        Resources resources = getResources();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        usersView.setLayoutManager(layoutManager);
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.user_img);
        UsersContainerModel model = new UsersContainerModel.Builder().userProtrait(bmp).userType(bmp).build();
        LayoutAdapter adapter = new LayoutAdapter(activity);
        adapter.setOnRecyclerViewListener(this);
        usersView.setAdapter(adapter);
        for (int index = 0; index < 100; index++) {
            adapter.addItem(index, model);
        }
        adapter.notifyDataSetChanged();
        int spacingInPixels = resources.getDimensionPixelOffset(R.dimen.space);
        usersView.addItemDecoration(new RvSpaceItemDecoration(spacingInPixels));
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "position:" + position);
    }

    @Override
    public boolean onItemLongClick(int positon) {
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.like_pop) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    ScaleUtil.scale(like, ScaleUtil.smallScale,
                            BitmapFactory.decodeResource(getResources(), R.drawable.vivasam_currency_icon_like));
                    return true;
                case MotionEvent.ACTION_UP:
                    ScaleUtil.scale(like, ScaleUtil.bigScale,
                            BitmapFactory.decodeResource(getResources(), R.drawable.vivasam_currency_icon_like));
                    return true;
            }
        }
        return false;
    }
}

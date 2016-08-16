package top.codemc.liveappuidemo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.adapter.LayoutAdapter;
import top.codemc.liveappuidemo.adapter.LiveChatListAdapter;
import top.codemc.liveappuidemo.model.UsersContainerModel;
import top.codemc.liveappuidemo.view.RvSpaceItemDecoration;

/**
 * Created by xiyoumc on 16/8/5.
 */
public class YKLiveFragment extends Fragment implements LayoutAdapter.OnRecyclerViewListener, View.OnTouchListener,
        View.OnLayoutChangeListener {

    private static final String TAG = "McLiveFragment";

    private RelativeLayout root_view;
    private ListView liveChatListView;
    private LiveChatListAdapter liveChatListAdapter;
    private RelativeLayout bollow_msg_rl;
    private LinearLayout live_bollow_ln;
    private EditText msg_edit_text;
    private Button msg_send_bt;
    private int screenHeight = 0;
    private int keyHeight = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();

        root_view = (RelativeLayout) view.findViewById(R.id.root_view);
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        //top view
        RecyclerView usersView = (RecyclerView) view.findViewById(R.id.listview_users);
        usersView.setHasFixedSize(true);
        usersView.setLongClickable(false);
        configRecyclerView(activity, usersView);

//        DisplayMetrics metrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int screenWidth = metrics.widthPixels;
//        int screenHigth = metrics.heightPixels;
//        inputEdit.setText("teststtt");
//        ViewGroup.LayoutParams params = inputEdit.getLayoutParams();
//        params.width = screenWidth - 130;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        inputEdit.setLayoutParams(params);
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = inputEdit.getText().toString();
//                if (text.isEmpty()) {
//                    return;
//                }
//                MessageModel messageModel = new MessageModel.Builder().userName("xiyouMc:")
//                        .content("Github:https://www.github.com/xiyouMc" + " content:" + text).build();
//                liveChatListAdapter.addMessage(messageModel);
//                inputEdit.setText("");
//            }
//        });

        //msg view
        liveChatListView = (ListView) view.findViewById(R.id.live_chatlist);
        liveChatListAdapter = new LiveChatListAdapter();
        liveChatListView.setAdapter(liveChatListAdapter);


        //bollow view
        live_bollow_ln = (LinearLayout) view.findViewById(R.id.live_bollow_ln);
        ImageView live_send_msg = (ImageView) view.findViewById(R.id.live_send_msg);
        ImageView live_send_gift = (ImageView) view.findViewById(R.id.live_send_gift);
        ImageView live_share = (ImageView) view.findViewById(R.id.live_share);
        bollow_msg_rl = (RelativeLayout) view.findViewById(R.id.send_rl);
        msg_edit_text = (EditText) view.findViewById(R.id.live_msg);
//        msg_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                    //pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });
        msg_send_bt = (Button) view.findViewById(R.id.live_send);
        msg_send_bt.setOnTouchListener(this);
        live_send_msg.setOnTouchListener(this);
        live_send_gift.setOnTouchListener(this);
        live_share.setOnTouchListener(this);

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
        int id = v.getId();

        switch (id) {
            case R.id.live_send_msg:
                live_bollow_ln.setVisibility(View.GONE);
                bollow_msg_rl.setVisibility(View.VISIBLE);
                msg_edit_text.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                return true;
            case R.id.live_send_gift:
                return true;
            case R.id.live_share:
                return true;
            case R.id.live_send:
                msg_edit_text.requestFocus();
//                imm.hideSoftInputFromWindow(msg_edit_text.getWindowToken(), 0);
//                bollow_msg_rl.setVisibility(View.GONE);
//                live_bollow_ln.setVisibility(View.VISIBLE);
                return true;
        }
        return false;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            bollow_msg_rl.setVisibility(View.GONE);
            live_bollow_ln.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        root_view.addOnLayoutChangeListener(this);
    }
}
